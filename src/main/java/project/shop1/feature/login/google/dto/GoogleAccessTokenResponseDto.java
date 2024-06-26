package project.shop1.feature.login.google.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GoogleAccessTokenResponseDto {

    private String expires_in;   // Access Token의 남은 수명
    private String refresh_token;    // 새 액세스 토큰을 얻는 데 사용할 수 있는 토큰
    private String scope;
    private String token_type;   // 반환된 토큰 유형(Bearer 고정)
    private String id_token;
}
