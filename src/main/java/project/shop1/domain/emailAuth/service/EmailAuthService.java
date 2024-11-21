package project.shop1.domain.emailAuth.service;

import project.shop1.domain.emailAuth.dto.AuthCodeRequestDto;
import project.shop1.domain.emailAuth.dto.EmailAuthRequestDto;
import project.shop1.domain.emailAuth.entity.EmailAuth;

public interface EmailAuthService {

    void sendEmail(EmailAuthRequestDto emailAuthRequestDto);

    Boolean validationAuthCode(AuthCodeRequestDto authCodeRequestDto);

    EmailAuth findEmailAuthByEmail(String email);
}
