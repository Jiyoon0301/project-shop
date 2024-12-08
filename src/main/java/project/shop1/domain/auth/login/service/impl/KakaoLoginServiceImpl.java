package project.shop1.domain.auth.login.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import project.shop1.domain.auth.login.service.LoginService;
import project.shop1.domain.auth.login.dto.response.LoginResponseDto;
import project.shop1.domain.auth.login.dto.request.SocialLoginRequestDto;
import project.shop1.domain.user.repository.UserRepository;
import project.shop1.global.exception.BusinessException;
import project.shop1.global.exception.ErrorCode;
import project.shop1.global.security.jwt.JwtTokenProvider;
import project.shop1.global.security.jwt.dto.JwtToken;
import project.shop1.global.security.redis.dto.RefreshToken;
import project.shop1.global.security.redis.repository.RefreshTokenRepository;
import project.shop1.domain.user.entity.UserEntity;
import project.shop1.domain.user.enums.UserRank;
import project.shop1.domain.auth.login.dto.response.KakaoUserLoginResponseDto;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class KakaoLoginServiceImpl implements LoginService<SocialLoginRequestDto> {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${spring.security.oauth2.client.registration.kakao.client_id}")
    private String clientId;
    @Value("${spring.security.oauth2.client.registration.kakao.redirect_uri}")
    private String redirectUri;

    /* 카카오 로그인 전체 서비스 로직 */
    @Transactional
    @Override
    public LoginResponseDto login(SocialLoginRequestDto requestDto) {
        //0. 동적으로 redirect URI 선택 - 배포 시 필요
//        String redirectUri=selectRedirectUri(currentDomain);

        // 1. "인가 코드"로 "액세스 토큰" 요청
        String accessToken = getAccessToken(requestDto);

        // 2. 토큰으로 카카오 API 호출
        HashMap<String, Object> userInfo = getKakaoUserInfo(accessToken);

        // 3. 카카오 ID 로 회원가입 & 로그인 처리
        KakaoUserLoginResponseDto kakaoUserLoginResponseDto = kakaoUserLogin(userInfo);

        // 4. 자체 토큰 발급 후 responseDto 생성
        return generateTokenByKakao(kakaoUserLoginResponseDto);
    }


    /* 1. "인가 코드"로 "액세스 토큰" 요청 */
    private String getAccessToken(SocialLoginRequestDto requestDto) { //private 메서드는 인터페이스에 구현할 필요 없다
        String code = requestDto.getCode();
        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP Body 생성
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", clientId);
        body.add("redirect_uri", redirectUri);
        body.add("code", code);

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(body, headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        // HTTP 응답 (JSON) -> 액세스 토큰 파싱
        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(responseBody);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonNode.get("access_token").asText(); //토큰 전송
    }

    /* 2. 토큰으로 카카오 API 호출 */
    private HashMap<String, Object> getKakaoUserInfo(String accessToken) {
        HashMap<String, Object> userInfo = new HashMap<String, Object>();

        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoUserInfoRequest = new HttpEntity<>(headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoUserInfoRequest,
                String.class
        );

        // responseBody에 있는 정보 꺼냄
        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(responseBody);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        Long id = jsonNode.get("id").asLong();
        String email = jsonNode.get("kakao_account").get("email").asText();
        String nickname = jsonNode.get("properties").get("nickname").asText();

        userInfo.put("id", id);
        userInfo.put("email", email); // 카카오 로그인 이메일
        userInfo.put("nickname", nickname); // 카카오에서 사용하는 이름

        return userInfo;
    }

    /* 3. 카카오 ID로 회원가입 & 로그인 처리 */
    @Transactional
    public KakaoUserLoginResponseDto kakaoUserLogin(HashMap<String, Object> userInfo) {

        String id = userInfo.get("id").toString();
        String kakaoEmail = userInfo.get("email").toString();
        String nickName = userInfo.get("nickname").toString();

        Optional<UserEntity> kakaoUser = userRepository.findByEmail(kakaoEmail);

        if (kakaoUser.isEmpty()) { //회원가입

            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encodedPassword = passwordEncoder.encode(id);

            UserEntity userEntity = UserEntity.builder()
                    .account(kakaoEmail) //account를 eamil로 설정
                    .password(encodedPassword) //password를 id로
                    .name(nickName)
                    .phoneNumber(null)
                    .email(kakaoEmail)
                    .loginType("kakao")
                    .userRank(UserRank.MEMBER)
                    .address(null)
                    .build();
            userEntity.addRole("USER");
            userRepository.save(userEntity);
        }
        return new KakaoUserLoginResponseDto(id, nickName, kakaoEmail);
    }

    /* 4. 자체 토큰 발급 */
    private LoginResponseDto generateTokenByKakao(KakaoUserLoginResponseDto kakaoUserLoginResponseDto) {
        String id = kakaoUserLoginResponseDto.getId();
        String nickName = kakaoUserLoginResponseDto.getNickname();
        String email = kakaoUserLoginResponseDto.getEmail();

        //토큰 생성 (account = email, password = 회원 id)
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, id);
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        JwtToken jwtToken = jwtTokenProvider.generateToken(authentication);

        //redis 에 토큰 저장
        refreshTokenRepository.save(new RefreshToken(authentication.getName(), jwtToken.getRefreshToken(), jwtToken.getAccessToken())); //account, refreshToken, accessToken

        return createLoginResponseDto(jwtToken, email);
    }

    private LoginResponseDto createLoginResponseDto(JwtToken jwtToken, String email) {
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "사용자를 찾을 수 없습니다."));

        String accessToken = jwtToken.getAccessToken();
        String refreshToken = jwtToken.getRefreshToken();
        String grantType = jwtToken.getGrantType();
        String name = userEntity.getName();
        List<String> role = userEntity.getRoles();
        return new LoginResponseDto(accessToken, refreshToken, grantType, name, email, role);
    }
}
