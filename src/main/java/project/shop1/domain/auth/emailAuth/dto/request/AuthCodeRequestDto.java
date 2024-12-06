package project.shop1.domain.auth.emailAuth.dto.request;

import lombok.Data;

@Data
public class AuthCodeRequestDto {
    private String authcode;
    private String email;
}
