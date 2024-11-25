package project.shop1.domain.auth.local.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import project.shop1.global.security.SecurityUtils;
import project.shop1.global.security.jwt.dto.JwtToken;
import project.shop1.global.util.validation.ValidationSequence;
import project.shop1.domain.auth.local.service.LocalLoginService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class LocalLoginController {

    private final LocalLoginService localLoginService;

    /* 로그인 - jwt */
    @PostMapping("/login") // JwtLoginRequestDto : String account, String password
    @PreAuthorize("permitAll()")
    public JwtToken login(@Validated(value = ValidationSequence.class) @RequestBody LocalLoginRequestDto localLoginRequestDto){
        JwtToken jwtToken = localLoginService.login(localLoginRequestDto);
        log.info("accessToken = {}, refreshToken = {}", jwtToken.getAccessToken(), jwtToken.getRefreshToken());

        return jwtToken; // String accessToken, String refreshToken, String grantType
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
