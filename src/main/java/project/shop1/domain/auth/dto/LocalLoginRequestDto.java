package project.shop1.domain.auth.dto;

import lombok.Data;

@Data
public class LocalLoginRequestDto {
    private String account;
    private String password;
}
