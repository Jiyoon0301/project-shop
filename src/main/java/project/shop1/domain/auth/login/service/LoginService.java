package project.shop1.domain.auth.login.service;

import project.shop1.domain.auth.login.dto.response.LoginResponseDto;

public interface LoginService<T> {

    LoginResponseDto login(T loginRequest);
}
