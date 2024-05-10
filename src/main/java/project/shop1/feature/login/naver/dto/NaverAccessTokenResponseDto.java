package project.shop1.feature.login.naver.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NaverAccessTokenResponseDto {

    private String access_token;
    private String refresh_token;
    private String token_type;
    private String expires_in;

    private String error;
    private String error_description;
}
