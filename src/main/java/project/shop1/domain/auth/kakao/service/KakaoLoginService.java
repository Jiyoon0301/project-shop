package project.shop1.domain.auth.kakao.service;


import project.shop1.domain.auth.kakao.dto.KakaoLoginResponseDto;

public interface KakaoLoginService {

    /* 카카오 로그인 전체 서비스 로직 */
    KakaoLoginResponseDto kakaoLogin(String code);
}
