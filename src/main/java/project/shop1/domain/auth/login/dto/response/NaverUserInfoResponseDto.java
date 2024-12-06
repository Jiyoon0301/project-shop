package project.shop1.domain.auth.login.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NaverUserInfoResponseDto {

    private String resultCode;
    private String message;
    private ResponseData response;

    // response 필드의 데이터를 포함하는 내부 클래스 정의
    @Data
    @AllArgsConstructor
    public static class ResponseData {
        private String id;
        private String email;
        private String name;
    }
}
