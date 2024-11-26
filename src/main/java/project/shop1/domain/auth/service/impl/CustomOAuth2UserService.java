package project.shop1.domain.auth.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.shop1.domain.auth.service.common.OAuthAttributes;
import project.shop1.domain.user.entity.UserEntity;
import project.shop1.domain.user.repository.UserRepository;
import project.shop1.global.security.jwt.JwtTokenProvider;
import project.shop1.global.security.redis.repository.RefreshTokenRepository;

import java.util.Collections;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final RefreshTokenRepository refreshTokenRepository;
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // 1. 기본 서비스 호출
        OAuth2User oauth2User = new DefaultOAuth2UserService().loadUser(userRequest);

        // 2. 클라이언트 이름(Google, Kakao, Naver) 확인
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();

        // 3. 사용자 정보 추출
        Map<String, Object> attributes = oauth2User.getAttributes();
        OAuthAttributes extractedAttributes = OAuthAttributes.of(registrationId, userNameAttributeName, attributes);

        // 4. 데이터베이스 저장 또는 업데이트
        UserEntity userEntity = saveOrUpdate(extractedAttributes);

        // 5. 사용자 정보 반환
        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("USER")),
                attributes,
                userNameAttributeName);
    }

    private UserEntity saveOrUpdate(OAuthAttributes attributes) {
        UserEntity user = userRepository.findByEmail(attributes.getEmail())
                .orElse(attributes.toEntity());
        return userRepository.save(user);
    }
}
