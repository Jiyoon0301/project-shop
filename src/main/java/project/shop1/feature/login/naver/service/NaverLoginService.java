package project.shop1.feature.login.naver.service;

import project.shop1.feature.login.naver.dto.NaverLoginResponseDto;

public interface NaverLoginService {

    /* naver 로그인 전체 서비스 로직 */
    NaverLoginResponseDto naverLogin(final String code);

}
