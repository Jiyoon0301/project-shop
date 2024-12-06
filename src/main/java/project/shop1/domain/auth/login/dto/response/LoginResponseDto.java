package project.shop1.domain.auth.login.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class LoginResponseDto {
    private String accessToken;
    private String refreshToken;
    private String grantType;
    private String userName; // 예: 사용자 이름
    private String email; // 예: 사용자 이메일
    private List<String> role; // 예: 사용자 역할 (권한)

    public LoginResponseDto(String accessToken, String refreshToken, String grantType, String userName, String email, List<String> role) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.grantType = grantType;
        this.userName = userName;
        this.email = email;
        this.role = role;
    }
}
