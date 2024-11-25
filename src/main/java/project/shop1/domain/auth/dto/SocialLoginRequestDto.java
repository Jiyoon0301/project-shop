package project.shop1.domain.auth.dto;

import lombok.Data;

@Data
public class SocialLoginRequestDto {
    private String code; // Authorization Code
}
