package project.shop1.feature.login.kakao.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KakaoLoginResponseDto {
    /* 카카오 로그인 후 최종적으로 프론트에 보내질 response Dto */
    private String id;
    private String nickname;
    private String email;
    private String accessToken;
    private String refreshToken;
    private String grantType;
//    private Long expiresIn;
}
