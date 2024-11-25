package project.shop1.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import project.shop1.domain.auth.enums.ProviderType;
import project.shop1.domain.auth.service.impl.GoogleLoginServiceImpl;
import project.shop1.domain.auth.service.impl.KakaoLoginServiceImpl;
import project.shop1.domain.auth.service.impl.LocalLoginServiceImpl;
import project.shop1.domain.auth.service.impl.NaverLoginServiceImpl;

@RequiredArgsConstructor
@Component
public class LoginServiceFactory {

    private final GoogleLoginServiceImpl googleLoginService;
    private final KakaoLoginServiceImpl kakaoLoginService;
    private final NaverLoginServiceImpl naverLoginService;
    private final LocalLoginServiceImpl localLoginService;

    public LoginService getLoginService(ProviderType providerType) {
        return switch (providerType) {
            case GOOGLE -> googleLoginService;
            case KAKAO -> kakaoLoginService;
            case NAVER -> naverLoginService;
            case LOCAL -> localLoginService;
        };
    }
}
