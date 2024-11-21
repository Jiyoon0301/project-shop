package project.shop1.domain.login.google.service;

import project.shop1.domain.login.google.dto.GoogleLoginResponseDto;

public interface GoogleLoginService {

    GoogleLoginResponseDto googleLogin(final String code);

    }
