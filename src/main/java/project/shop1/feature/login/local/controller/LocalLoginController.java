package project.shop1.feature.login.local.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import project.shop1.common.security.SecurityUtil;
import project.shop1.common.security.jwt.dto.JwtToken;
import project.shop1.common.validation.ValidationSequence;
import project.shop1.feature.login.local.dto.LocalLoginRequestDto;
import project.shop1.feature.login.local.service.LocalLoginService;

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

        return jwtToken;
    }

    /* 로그인 테스트 - jwt */
    @PostMapping("/test")
    @PreAuthorize("hasRole('USER')")
    public String test() {
        return SecurityUtil.getCurrentUsername();
    }


    /* 로그인 - 세션 */
//    @PostMapping("/login")
//    public ResponseEntity<BooleanResponse> login(@Validated(value = ValidationSequence.class) @RequestBody LocalLoginRequestDto localLoginRequestDto, HttpServletRequest request){
//        loginService.loginUser(loginRequestDto, request);
//
//        return ResponseEntity.ok(BooleanResponse.of(true));
//    }
}
