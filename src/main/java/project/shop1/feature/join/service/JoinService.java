package project.shop1.feature.join.service;

import project.shop1.feature.join.dto.AuthCodeRequestDto;
import project.shop1.feature.join.dto.EmailAuthRequestDto;
import project.shop1.feature.join.dto.JoinRequestDto;

public interface JoinService {

    void join(JoinRequestDto joinRequestDto);

    void authEmail(EmailAuthRequestDto emailAuthRequestDto);

    void validationAuthCode(AuthCodeRequestDto authCodeRequestDto);
}
