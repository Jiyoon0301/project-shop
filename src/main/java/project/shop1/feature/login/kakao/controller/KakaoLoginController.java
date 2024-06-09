package project.shop1.feature.login.kakao.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import project.shop1.feature.login.kakao.dto.KakaoLoginResponseDto;
import project.shop1.feature.login.kakao.service.KakaoLoginService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class KakaoLoginController {

    private final KakaoLoginService kakaoLoginService;

    /* 로그인 - 카카오 */
    // token 과 email 리턴(프런트에서 현재 유저 저장할 때 필요)
    @GetMapping("/login/kakao-callback")
    @PreAuthorize("permitAll()")
    public KakaoLoginResponseDto kakaoLogin(@RequestParam("code") String code){ //return KakaoLoginResponseDto - id, nickname, email, access/refreshToken, expiresIn
        KakaoLoginResponseDto kakaoLoginResponseDto = kakaoLoginService.kakaoLogin(code);

        return kakaoLoginResponseDto; // String accessToken, String refreshToken
    }
}
