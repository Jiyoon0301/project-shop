package project.shop1.domain.login.dto;

import lombok.Data;

@Data
public class LocalLoginRequestDto {
    private String account;
    private String password;
}
