package project.shop1.domain.login.naver.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import project.shop1.domain.login.naver.dto.NaverLoginResponseDto;
import project.shop1.domain.login.naver.service.NaverLoginService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class NaverLoginController {

    private final NaverLoginService naverLoginService;

    /*  로그인 - 네이버
     *  return
     *  naverEmail, accessToken, refreshToken, grantType;
     */
    @GetMapping("/login/naver-callback")
    @PreAuthorize("permitAll()")
    public NaverLoginResponseDto naverLogin(@RequestParam("code") String code){
        NaverLoginResponseDto result = naverLoginService.naverLogin(code);

        return result; // String accessToken, String refreshToken
    }

}
