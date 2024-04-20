package project.shop1.feature.login.service;

import jakarta.servlet.http.HttpServletRequest;
import project.shop1.common.security.jwt.dto.JwtToken;
import project.shop1.feature.login.dto.JwtLoginRequestDto;
import project.shop1.feature.login.dto.LoginRequestDto;

public interface LoginService {

    /* 로그인 */
    void loginUser(LoginRequestDto loginRequestDto, HttpServletRequest request);

    /* jwt 로그인 */
    JwtToken login(JwtLoginRequestDto jwtLoginRequestDto);
    }
