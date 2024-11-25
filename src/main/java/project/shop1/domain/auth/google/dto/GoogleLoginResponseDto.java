package project.shop1.domain.auth.google.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GoogleLoginResponseDto {

    private String accessToken;
    private String refreshToken;
}
