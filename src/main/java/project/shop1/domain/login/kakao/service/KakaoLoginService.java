package project.shop1.domain.login.kakao.service;


import project.shop1.domain.login.kakao.dto.KakaoLoginResponseDto;

public interface KakaoLoginService {

    /* 카카오 로그인 전체 서비스 로직 */
    KakaoLoginResponseDto kakaoLogin(String code);
}
