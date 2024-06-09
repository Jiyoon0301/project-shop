package project.shop1.feature.login.kakao.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KakaoLoginResponseDto {

    private String accessToken;
    private String refreshToken;
//    private Long expiresIn;
}
