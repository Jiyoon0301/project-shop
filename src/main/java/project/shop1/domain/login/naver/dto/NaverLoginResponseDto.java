package project.shop1.domain.login.naver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NaverLoginResponseDto {

    private String accessToken;
    private String refreshToken;

}
