package project.shop1.feature.login.service;

import jakarta.servlet.http.HttpServletRequest;
import project.shop1.feature.login.dto.LoginRequestDto;

public interface LoginService {
    void loginUser(LoginRequestDto loginRequestDto, HttpServletRequest request);
}
