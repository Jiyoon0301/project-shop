package project.shop1.feature.login.local.service;

import jakarta.servlet.http.HttpServletRequest;
import project.shop1.common.security.jwt.dto.JwtToken;
import project.shop1.feature.login.local.dto.LocalLoginRequestDto;

public interface LocalLoginService {

    /* jwt 로그인 */
    JwtToken login(LocalLoginRequestDto localLoginRequestDto);

    /* 세션 로그인 */
    void loginUser(LocalLoginRequestDto localLoginRequestDto, HttpServletRequest request);


}
