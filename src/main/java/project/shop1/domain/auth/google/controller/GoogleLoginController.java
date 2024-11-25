package project.shop1.domain.auth.google.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import project.shop1.domain.auth.google.dto.GoogleLoginResponseDto;
import project.shop1.domain.auth.google.service.GoogleLoginService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class GoogleLoginController {

    private final GoogleLoginService googleLoginService;

//    @PostMapping("/login/google-callback")
//    @PreAuthorize("permitAll()")
//    public String googleLogin(){
//        String reqUrl = "https://accounts.google.com/o/oauth2/v2/auth?client_id="
//         + clientId +
//                "&redirect_uri="
//        + redirectUri + "&response_type=code&scope=email profile";
//        return reqUrl;
//    }

    /*  로그인 - 구글
    *  return
    *  googleEmail, accessToken, refreshToken, grantType;
    */
    @PostMapping("/login/google-callback")
    @PreAuthorize("permitAll()")
    public GoogleLoginResponseDto googleLogin(@RequestParam("code") String code){
        GoogleLoginResponseDto googleLoginResponseDto = googleLoginService.googleLogin(code);

        return googleLoginResponseDto; // String accessToken, String refreshToken
    }
}
