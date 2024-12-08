package project.shop1.domain.auth.login.service.impl;

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
import project.shop1.domain.auth.login.dto.request.SocialLoginRequestDto;
import project.shop1.domain.auth.login.dto.response.LoginResponseDto;
import project.shop1.domain.auth.login.dto.response.NaverAccessTokenResponseDto;
import project.shop1.domain.auth.login.dto.response.NaverUserInfoResponseDto;
import project.shop1.domain.auth.login.dto.response.NaverUserLoginResponseDto;
import project.shop1.domain.auth.login.service.LoginService;
import project.shop1.domain.user.repository.UserRepository;
import project.shop1.global.exception.BusinessException;
import project.shop1.global.exception.ErrorCode;
import project.shop1.global.security.jwt.JwtTokenProvider;
import project.shop1.global.security.jwt.dto.JwtToken;
import project.shop1.global.security.redis.dto.RefreshToken;
import project.shop1.global.security.redis.repository.RefreshTokenRepository;
import project.shop1.domain.user.entity.UserEntity;
import project.shop1.domain.user.enums.UserRank;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NaverLoginServiceImpl implements LoginService<SocialLoginRequestDto> {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${spring.security.oauth2.client.registration.naver.client-id}")
    private String clientId;
    @Value("${spring.security.oauth2.client.registration.naver.client-secret}")
    private String clientSecret;
    @Value("${spring.security.oauth2.client.provider.naver.token-uri}")
    private String tokenUri;

    @Transactional
    @Override
    public LoginResponseDto login(SocialLoginRequestDto socialLoginRequestDto) {

        // 1. Naver Access Token 요청
        final String accessToken = requestNaverAccessToken(socialLoginRequestDto);

        // 2. 액세스 토큰으로 google 계정 프로필 정보 요청
        NaverUserInfoResponseDto naverUserInfoResponseDto = requestNaverUserInfo(accessToken);

        // 3. google ID로 회원가입 & 로그인 처리
        NaverUserLoginResponseDto naverUserLoginResponseDto = naverUserLogin(naverUserInfoResponseDto);

        // 4. 자체 jwt 토큰 발급
        return generateTokenByNaver(naverUserLoginResponseDto);
    }

    /* 1. Naver Access Token 요청 */
    public String requestNaverAccessToken(SocialLoginRequestDto socialLoginRequestDto) {
        String code = socialLoginRequestDto.getCode();

        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP Body 생성
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);
        body.add("code", code);
        body.add("state", "1234");

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> naverTokenRequest = new HttpEntity<>(body, headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<NaverAccessTokenResponseDto> response = rt.exchange(
                tokenUri,
        HttpMethod.POST,
                naverTokenRequest,
                NaverAccessTokenResponseDto.class
        );
        return response.getBody().getAccess_token();
    }

    /* 2. 액세스 토큰으로 naver 계정 프로필 정보 요청 */
    public NaverUserInfoResponseDto requestNaverUserInfo(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();
        // HTTP 요청에 필요한 헤더 생성
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);

        // RestTemplate 을 사용하여 사용자 정보 요청 보내기
        ResponseEntity<NaverUserInfoResponseDto> responseEntity = restTemplate.exchange(
                "https://openapi.naver.com/v1/nid/me",
                HttpMethod.GET,
                requestEntity,
                NaverUserInfoResponseDto.class
        );

        // 응답에서 사용자 정보 추출하여 반환
        return responseEntity.getBody();
    }

    /* 3. google ID로 회원가입 & 로그인 처리
     * Account = Email
     * Password = id
     */
    @Transactional
    public NaverUserLoginResponseDto naverUserLogin(NaverUserInfoResponseDto userInfo) {

        String id = userInfo.getResponse().getId();
        String naverEmail = userInfo.getResponse().getEmail();
        String name = userInfo.getResponse().getName();

        Optional<UserEntity> naverUser = userRepository.findByEmail(naverEmail);

        if (naverUser.isEmpty()) { //회원가입

            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encodedPassword = passwordEncoder.encode(id);

            ArrayList<String> roles = new ArrayList<>();
            roles.add("USER");

            UserEntity userEntity = UserEntity.builder()
                    .account(naverEmail) //account 를 email 로 설정
                    .password(encodedPassword) //password 를 id로 설정
                    .name(name)
                    .phoneNumber(null)
                    .email(naverEmail)
                    .loginType("naver")
                    .userRank(UserRank.MEMBER)
                    .address(null)
                    .roles(roles)
                    .build();
            userRepository.save(userEntity);
        }
        return new NaverUserLoginResponseDto(naverEmail, id);
    }

    /* 4. 자체 토큰 발급 */
    private LoginResponseDto generateTokenByNaver(NaverUserLoginResponseDto naverUserLoginResponseDto) {
        String email = naverUserLoginResponseDto.getNaverEmail();
        String id = naverUserLoginResponseDto.getId();

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
