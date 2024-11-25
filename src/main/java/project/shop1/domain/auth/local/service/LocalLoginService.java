package project.shop1.domain.auth.local.service;

import jakarta.servlet.http.HttpServletRequest;
import project.shop1.global.security.jwt.dto.JwtToken;

public interface LocalLoginService {

    /* jwt 로그인 */
    JwtToken login(LocalLoginRequestDto localLoginRequestDto);

    /* 세션 로그인 */
    void loginUser(LocalLoginRequestDto localLoginRequestDto, HttpServletRequest request);


}
