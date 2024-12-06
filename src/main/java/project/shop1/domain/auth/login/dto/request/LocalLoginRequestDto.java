package project.shop1.domain.auth.login.dto.request;

import lombok.Data;

@Data
public class LocalLoginRequestDto {
    private String account;
    private String password;
}
