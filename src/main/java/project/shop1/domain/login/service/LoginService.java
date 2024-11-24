package project.shop1.domain.login.service;

import project.shop1.domain.login.dto.LoginResponseDto;

public interface LoginService<T> {

    LoginResponseDto login(T loginRequest);
}
