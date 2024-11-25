package project.shop1.domain.auth.emailAuth_needRefactor.dto;

import lombok.Data;

@Data
public class AuthCodeRequestDto {
    private String authcode;
    private String email;
}
