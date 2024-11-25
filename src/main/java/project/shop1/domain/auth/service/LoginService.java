package project.shop1.domain.auth.service;

import project.shop1.domain.auth.dto.LoginResponseDto;

public interface LoginService<T> {

    LoginResponseDto login(T loginRequest);
}
