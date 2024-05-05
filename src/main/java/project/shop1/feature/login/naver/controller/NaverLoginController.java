package project.shop1.feature.login.naver.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import project.shop1.feature.login.naver.dto.NaverLoginResponseDto;
import project.shop1.feature.login.naver.service.NaverLoginService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class NaverLoginController {

    private final NaverLoginService naverLoginService;

    /* 로그인 - 네이버 */
    // token 과 email 리턴(프런트에서 현재 유저 저장할 때 필요)
    @GetMapping("/login/naver-callback")
    @PreAuthorize("permitAll()")
    public NaverLoginResponseDto naverCallback(@RequestParam("code") String code){ //return KakaoLoginResponseDto - id, nickname, email, access/refreshToken, expiresIn
//        NaverLoginResponseDto naverLoginResponseDto = naverLoginService.naverLogin(code);

        return null;
    }

}
