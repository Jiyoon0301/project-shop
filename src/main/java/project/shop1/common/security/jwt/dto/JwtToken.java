package project.shop1.common.security.jwt.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class JwtToken {

    /* "Bearer" 인증 방식 : Access Token 을 HTTP 요청의 Authorization 헤더에 포함하여 전송 */
    private String grantType; //JWT 에 대한 인증 타입 = "Bearer"
    private String accessToken;
    private String refreshToken;
}
