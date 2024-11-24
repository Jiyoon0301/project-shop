package project.shop1.domain.login.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import project.shop1.domain.login.service.LoginService;
import project.shop1.domain.user.repository.UserRepository;
import project.shop1.global.security.jwt.JwtTokenProvider;
import project.shop1.global.security.jwt.dto.JwtToken;
import project.shop1.global.security.redis.dto.RefreshToken;
import project.shop1.global.security.redis.repository.RefreshTokenRepository;
import project.shop1.domain.user.entity.UserEntity;
import project.shop1.domain.user.enums.UserRank;
import project.shop1.domain.login.google.dto.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GoogleLoginServiceImpl implements LoginService {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;
    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String clientSecret;
    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String redirectUri;
    @Value("${spring.security.oauth2.client.registration.google.authorization-grant-type}")
    private String authorizationCode;
    @Value("${url.access-token}")
    private String accessTokenUrl;
    @Value("${url.profile}")
    private String profileUrl;

    @Transactional
    @Override
    public GoogleLoginResponseDto googleLogin(final String code) {

        // 1. Google Access Token 요청
        final String accessToken = requestGoogleAccessToken(code);

        // 2. 액세스 토큰으로 google 계정 프로필 정보 요청
        GoogleAccountProfileResponseDto googleAccountProfileResponseDto = requestGoogleAccountProfile(accessToken);

        // 3. google ID로 회원가입 & 로그인 처리
        GoogleUserLoginResponseDto googleUserLoginResponseDto = googleUserLogin(googleAccountProfileResponseDto);

        // 4. 자체 jwt 토큰 발급
        GoogleLoginResponseDto googleLoginResponseDto = generateTokenByGoogle(googleUserLoginResponseDto);

        return googleLoginResponseDto;
    }

    /* 1. Google Access Token 요청 */
    private String requestGoogleAccessToken(final String code) {
        RestTemplate restTemplate = new RestTemplate();
        GoogleAccessTokenRequestDto googleOAuthRequestParam = GoogleAccessTokenRequestDto.builder()
                .clientId(clientId)
                .clientSecret(clientSecret)
                .code(code)
                .redirectUri(redirectUri)
                .grantType(authorizationCode).build();
        ResponseEntity<GoogleAccessTokenResponseDto> resultEntity = restTemplate.postForEntity(accessTokenUrl,
                googleOAuthRequestParam, GoogleAccessTokenResponseDto.class);

        return resultEntity.getBody().getId_token(); //Id_token = accessToken
    }

    /* 2. 액세스 토큰으로 Google 계정 프로필 정보 요청 */
    private GoogleAccountProfileResponseDto requestGoogleAccountProfile(final String accessToken) {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> map=new HashMap<>();
        map.put("id_token", accessToken);
        ResponseEntity<GoogleAccountProfileResponseDto> result = restTemplate.postForEntity(profileUrl,
                map, GoogleAccountProfileResponseDto.class);

        return result.getBody();
    }

    /* 3. google ID로 회원가입 & 로그인 처리
    * Account = Email
    * Password = Email (바꾸기)
    */
    @Transactional
    public GoogleUserLoginResponseDto googleUserLogin(GoogleAccountProfileResponseDto userInfo) {

        String googleEmail = userInfo.getEmail();
        String name = userInfo.getName();

        Optional<UserEntity> googleUser = userRepository.findByEmail(googleEmail);

        if (googleUser.isEmpty()) { //회원가입

            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encodedPassword = passwordEncoder.encode(googleEmail);

            UserEntity userEntity = UserEntity.builder()
                    .account(googleEmail) //account 를 email 로 설정
                    .password(encodedPassword) //password 를 email 로 설정 (추후 수정 ...)
                    .name(name)
                    .phoneNumber(null)
                    .email(googleEmail)
                    .loginType("google")
                    .userRank(UserRank.MEMBER)
                    .address(null)
                    .build();
            userEntity.addRole("USER");
            userRepository.save(userEntity);
        }
        return new GoogleUserLoginResponseDto(googleEmail);
    }

    /* 4. 자체 토큰 발급 */
    private GoogleLoginResponseDto generateTokenByGoogle(GoogleUserLoginResponseDto googleUserLoginResponseDto){
        String googleEmail = googleUserLoginResponseDto.getGoogleEmail();

        //토큰 생성 (account = email, password = 회원 id)
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(googleEmail, googleEmail);
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        JwtToken jwtToken = jwtTokenProvider.generateToken(authentication);

        //redis 에 토큰 저장
        refreshTokenRepository.save(new RefreshToken(authentication.getName(), jwtToken.getRefreshToken(), jwtToken.getAccessToken())); //account, refreshToken, accessToken

        return new GoogleLoginResponseDto(jwtToken.getAccessToken(), jwtToken.getRefreshToken());
    }
}
