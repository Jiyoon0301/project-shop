package project.shop1.domain.auth.naver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NaverLoginResponseDto {

    private String accessToken;

    private String refreshToken;
}
