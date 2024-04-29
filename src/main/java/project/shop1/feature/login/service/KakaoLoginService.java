package project.shop1.feature.login.service;

import project.shop1.feature.login.dto.kakaoLogin.KakaoLoginResponseDto;
import project.shop1.feature.login.dto.kakaoLogin.KakaoUserLoginResponseDto;

import java.util.HashMap;

public interface KakaoLoginService {

    /* 카카오 로그인 전체 서비스 로직 */
    KakaoLoginResponseDto kakaoLogin(String code);

    /* KakaoAccessToken을 통해 카카오에서 필요한 해당 유저의 정보를 받는 메서드 */
//    HashMap<String, Object> getUserKakaoInfo(String accessToken);

    /* kakao 로그인 시 이미 회원가입(카카오 로그인 동의)이 진행된 유저인지 확인, userEmail 을 이용해 일반
    유저처럼 JWT 토큰 발급하여 return */
//    UserKakaoLoginResponseDto kakaoLogin(String accessToken);







    }
