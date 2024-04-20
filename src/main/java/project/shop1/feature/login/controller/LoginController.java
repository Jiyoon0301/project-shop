package project.shop1.feature.login.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project.shop1.common.repository.UserRepository;
import project.shop1.common.security.SecurityUtil;
import project.shop1.common.security.jwt.JwtTokenProvider;
import project.shop1.common.security.jwt.configuration.SecurityConfig;
import project.shop1.common.security.jwt.dto.JwtToken;
import project.shop1.common.validation.ValidationSequence;
import project.shop1.feature.login.dto.JwtLoginRequestDto;
import project.shop1.feature.login.service.LoginService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final LoginService loginService;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final SecurityConfig securityConfig;

//    @Value("${kakao.client_id}")
    private final String client_id = "8e1195f51d733edc8a79e51967b3065d";

//    @Value("${kakao.redirect_uri}")
    private final String redirect_uri = "http://localhost:8080/login/kakao-callback";

    private final String uri = "https://kauth.kakao.com/oauth/authorize?response_type=code&client_id="+client_id+"redirect_uri="+redirect_uri;


    /* 로그인 메서드 */
//    @PostMapping("/login")
//    public ResponseEntity<BooleanResponse> login(@Validated(value = ValidationSequence.class) @RequestBody LoginRequestDto loginRequestDto, HttpServletRequest request){
//        loginService.loginUser(loginRequestDto, request);
//
//        return ResponseEntity.ok(BooleanResponse.of(true));
//    }

    /* Jwt 인증 로그인 */
    @PostMapping("/login") // JwtLoginRequestDto : String account, String password
    public JwtToken login(@Validated(value = ValidationSequence.class) @RequestBody JwtLoginRequestDto jwtLoginRequestDto){
        JwtToken jwtToken = loginService.login(jwtLoginRequestDto);
        log.info("accessToken = {}, refreshToken = {}", jwtToken.getAccessToken(), jwtToken.getRefreshToken());

        return jwtToken;
    }

    /* 로그인 확인 */
    @PostMapping("/test")
    @PreAuthorize("hasRole('USER')")
    public String test() {
        return SecurityUtil.getCurrentUsername();
    }

//    @GetMapping("/login/kakao-auth")
//    public ResponseEntity<String> kakaoAuth(Model model){
//        String location = uri;
//
//        return ResponseEntity.status(HttpStatus.FOUND).header("Location", location).build();
//    }

    /* 카카오 로그인 - 프론트로부터 인가코드 전달받는 메서드 */
    @GetMapping("/login/kakao-callback")
    public String kakaoCallBack(@RequestParam("code") String code){
        log.info("code : "+ code);
        return null;
    }

}
