package project.shop1.feature.login.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project.shop1.common.repository.UserRepository;
import project.shop1.common.security.SecurityUtil;
import project.shop1.common.security.jwt.JwtTokenProvider;
import project.shop1.common.security.jwt.configuration.SecurityConfig;
import project.shop1.common.security.jwt.dto.JwtToken;
import project.shop1.common.validation.ValidationSequence;
import project.shop1.feature.login.dto.JwtLoginRequestDto;
import project.shop1.feature.login.dto.kakaoLogin.KakaoLoginResponseDto;
import project.shop1.feature.login.dto.kakaoLogin.KakaoUserLoginResponseDto;
import project.shop1.feature.login.service.KakaoLoginService;
import project.shop1.feature.login.service.LoginService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final LoginService loginService;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final SecurityConfig securityConfig;
    private final KakaoLoginService kakaoLoginService;

//    @Value("${kakao.client_id}")
    private final String client_id = "8e1195f51d733edc8a79e51967b3065d";

//    @Value("${kakao.redirect_uri}")
    private final String redirect_uri = "http://localhost:8080/login/kakao-callback";

    private final String uri = "https://kauth.kakao.com/oauth/authorize?response_type=code&client_id="+client_id+"redirect_uri="+redirect_uri;


    /* 로그인 - 세션 */
//    @PostMapping("/login")
//    public ResponseEntity<BooleanResponse> login(@Validated(value = ValidationSequence.class) @RequestBody LoginRequestDto loginRequestDto, HttpServletRequest request){
//        loginService.loginUser(loginRequestDto, request);
//
//        return ResponseEntity.ok(BooleanResponse.of(true));
//    }

    /* 로그인 - jwt */
    @PostMapping("/login") // JwtLoginRequestDto : String account, String password
    public JwtToken login(@Validated(value = ValidationSequence.class) @RequestBody JwtLoginRequestDto jwtLoginRequestDto){
        JwtToken jwtToken = loginService.login(jwtLoginRequestDto);
        log.info("accessToken = {}, refreshToken = {}", jwtToken.getAccessToken(), jwtToken.getRefreshToken());

        return jwtToken;
    }

    /* 로그인 테스트 - jwt  */
    @PostMapping("/test")
    @PreAuthorize("hasRole('USER')")
    public String test() {
        return SecurityUtil.getCurrentUsername();
    }

    /* 로그인 - 카카오 */
    // token 과 email 리턴(프런트에서 현재 유저 저장할 때 필요)
    @GetMapping("/login/kakao-callback")
    @PreAuthorize("permitAll()")
    public KakaoLoginResponseDto kakaoCallBack(@RequestParam("code") String code){ //return KakaoLoginResponseDto - id, nickname, email, access/refreshToken, expiresIn
        KakaoLoginResponseDto kakaoLoginResponseDto = kakaoLoginService.kakaoLogin(code);

        return kakaoLoginResponseDto;
    }


}
