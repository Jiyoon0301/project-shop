package project.shop1.domain.auth.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project.shop1.domain.auth.dto.LocalLoginRequestDto;
import project.shop1.domain.auth.dto.LoginResponseDto;
import project.shop1.domain.auth.dto.SocialLoginRequestDto;
import project.shop1.domain.auth.enums.ProviderType;
import project.shop1.domain.auth.service.LoginService;
import project.shop1.domain.auth.service.LoginServiceFactory;
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


//    /**
//     * @param localLoginRequestDto: String account, String password
//     * @return jwtToken: String accessToken, String refreshToken, String grantType
//     */
//    @PostMapping("/local")
//    @PreAuthorize("permitAll()")
//    public JwtToken localLogin(@Validated(value = ValidationSequence.class) @RequestBody LocalLoginRequestDto localLoginRequestDto) {
//        JwtToken jwtToken = localLoginService.login(localLoginRequestDto);
//        log.debug("accessToken = {}, refreshToken = {}", jwtToken.getAccessToken(), jwtToken.getRefreshToken());
//
//        return jwtToken; // String accessToken, String refreshToken, String grantType
//    }
//
//    /**
//     * @param code: Authorization Code
//     * @return googleEmail, accessToken, refreshToken, //grantType
//     */
//    @PostMapping("/google")
//    @PreAuthorize("permitAll()")
//    public ResponseEntity<GoogleLoginResponseDto> googleLogin(@RequestParam("code") String code) {
//        try {
//            GoogleLoginResponseDto googleLoginResponseDto = googleLoginService.googleLogin(code);
//            return ResponseEntity.ok(googleLoginResponseDto);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(new GoogleLoginResponseDto("error", "예기치 못한 서버 오류가 발생했습니다."));
//        }
//    }
//
//    /**
//     * @param code
//     * @return String accessToken, String refreshToken
//     */
//    // token 과 email 리턴(프런트에서 현재 유저 저장할 때 필요)
//    @PostMapping("/kakao")
//    @PreAuthorize("permitAll()")
//    public ResponseEntity<KakaoLoginResponseDto> kakaoLogin(@RequestParam("code") String code) {
//        try {
//            KakaoLoginResponseDto kakaoLoginResponseDto = kakaoLoginService.kakaoLogin(code);
//            return ResponseEntity.ok(kakaoLoginResponseDto);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(new KakaoLoginResponseDto("error", "예기치 못한 서버 오류가 발생했습니다."));
//        }
//    }
//
//    /**
//     * @param code
//     * @return String accessToken, String refreshToken
//     */
//    @PostMapping("/naver")
//    @PreAuthorize("permitAll()")
//    public ResponseEntity<NaverLoginResponseDto> naverLogin(@RequestParam("code") String code) {
//        try {
//            NaverLoginResponseDto naverLoginResponseDto = naverLoginService.naverLogin(code);
//            return ResponseEntity.ok(naverLoginResponseDto);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(new NaverLoginResponseDto("error", "예기치 못한 서버 오류가 발생했습니다."));
//        }
//    }

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
