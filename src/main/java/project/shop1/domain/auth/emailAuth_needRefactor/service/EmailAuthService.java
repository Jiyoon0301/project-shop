package project.shop1.domain.auth.emailAuth_needRefactor.service;

import project.shop1.domain.auth.emailAuth_needRefactor.dto.AuthCodeRequestDto;
import project.shop1.domain.auth.emailAuth_needRefactor.dto.EmailAuthRequestDto;
import project.shop1.domain.auth.emailAuth_needRefactor.entity.EmailAuth;

public interface EmailAuthService {

    void sendEmail(EmailAuthRequestDto emailAuthRequestDto);

    Boolean validationAuthCode(AuthCodeRequestDto authCodeRequestDto);

    EmailAuth findEmailAuthByEmail(String email);
}
