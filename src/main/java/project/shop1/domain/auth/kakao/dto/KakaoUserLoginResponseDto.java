package project.shop1.domain.auth.kakao.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KakaoUserLoginResponseDto {
    private String id;
    private String nickname;
    private String email;

}
