package project.shop1.domain.login.google.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GoogleLoginResponseDto {

    private String accessToken;
    private String refreshToken;
}
