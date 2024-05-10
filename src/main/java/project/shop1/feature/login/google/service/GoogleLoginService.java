package project.shop1.feature.login.google.service;

import project.shop1.feature.login.google.dto.GoogleLoginResponseDto;

public interface GoogleLoginService {

    GoogleLoginResponseDto googleLogin(final String code);

    }
