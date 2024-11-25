package project.shop1.domain.auth.google.service;

import project.shop1.domain.auth.google.dto.GoogleLoginResponseDto;

public interface GoogleLoginService {

    GoogleLoginResponseDto googleLogin(final String code);

    }
