package project.shop1.global.util.reponse;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {
    private int code;
    private String message;

    public static ErrorResponse of(int code, String message) {
        return ErrorResponse.builder()
                .code(code)
                .message(message)
                .build();
    }
}