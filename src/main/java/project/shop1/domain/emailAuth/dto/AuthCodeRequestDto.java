package project.shop1.domain.emailAuth.dto;

import lombok.Data;

@Data
public class AuthCodeRequestDto {
    private String authcode;
    private String email;
}
