package project.shop1.feature.login.naver.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NaverUserInfoResponseDto {

    private String resultcode;
    private String message;
    private ResponseData response;

    // 생성자, getter, setter 등 필요한 메서드들을 추가할 수 있습니다.

    public String getResultcode() {
        return resultcode;
    }

    public void setResultcode(String resultcode) {
        this.resultcode = resultcode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ResponseData getResponse() {
        return response;
    }

    public void setResponse(ResponseData response) {
        this.response = response;
    }

    // response 필드의 데이터를 포함하는 내부 클래스 정의
    @Data
    public static class ResponseData {
        private String id;
        private String email;
        private String name;

        // 생성자, getter, setter 등 필요한 메서드들을 추가할 수 있습니다.

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
