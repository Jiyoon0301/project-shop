package project.shop1.domain.auth.login.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project.shop1.domain.auth.login.dto.request.LocalLoginRequestDto;
import project.shop1.domain.auth.login.dto.response.LoginResponseDto;
import project.shop1.domain.auth.login.dto.request.SocialLoginRequestDto;
import project.shop1.domain.auth.login.enums.ProviderType;
import project.shop1.domain.auth.login.service.LoginService;
import project.shop1.domain.auth.login.service.LoginServiceFactory;
import project.shop1.global.security.SecurityUtils;

@RestController
@RequiredArgsConstructor
@RequestMapping("/login")
@Slf4j
public class LoginController {

    private final LoginServiceFactory loginServiceFactory;

    @PostMapping("/{provider}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<LoginResponseDto> login(@PathVariable ProviderType provider,
                                          @Validated @RequestBody Object loginRequest) {
        Object requestDto = convertToDto(loginRequest, provider);
        
        LoginService loginService = loginServiceFactory.getLoginService(provider);
        LoginResponseDto loginResponseDto = loginService.login(requestDto);

        log.debug("Login successful for provider: {}", provider);
        return ResponseEntity.ok(loginResponseDto);
    }

    private Object convertToDto(Object loginRequest, ProviderType provider) {
        return switch (provider) {
            case LOCAL -> (LocalLoginRequestDto) loginRequest;
            case GOOGLE, KAKAO, NAVER -> (SocialLoginRequestDto) loginRequest;
            default -> throw new IllegalArgumentException("Invalid provider type: " + provider);
        };
    }

    /* 로그인 테스트 - jwt */
    @PostMapping("/test")
    @PreAuthorize("hasRole('USER')")
    public String test() {
        return SecurityUtils.getCurrentUsername();
    }

    /* 로그인 - 세션 */
//    @PostMapping("/login")
//    public ResponseEntity<BooleanResponse> login(@Validated(value = ValidationSequence.class) @RequestBody LocalLoginRequestDto localLoginRequestDto, HttpServletRequest request){
//        loginService.loginUser(loginRequestDto, request);
//
//        return ResponseEntity.ok(BooleanResponse.of(true));
//    }
}
