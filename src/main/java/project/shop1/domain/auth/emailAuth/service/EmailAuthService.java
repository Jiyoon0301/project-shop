package project.shop1.domain.auth.emailAuth.service;

import project.shop1.domain.auth.emailAuth.dto.request.AuthCodeRequestDto;
import project.shop1.domain.auth.emailAuth.dto.request.EmailAuthRequestDto;
import project.shop1.domain.auth.emailAuth.entity.EmailAuth;

public interface EmailAuthService {

    void sendEmail(EmailAuthRequestDto emailAuthRequestDto);

    Boolean validationAuthCode(AuthCodeRequestDto authCodeRequestDto);

    EmailAuth findEmailAuthByEmail(String email);
}
