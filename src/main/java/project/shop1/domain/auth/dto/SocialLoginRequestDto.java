package project.shop1.domain.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SocialLoginRequestDto {
    private String code; // Authorization Code
}
