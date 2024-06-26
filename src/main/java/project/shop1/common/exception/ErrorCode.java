package project.shop1.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    PARAMETER_VALIDATION_FAIL(HttpStatus.BAD_REQUEST, 2201, "파라미터 값이 조건에 맞지 않습니다."),

    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, 2202, "리소스를 찾을 수 없습니다."),
    RESOURCE_CONFLICT(HttpStatus.CONFLICT, 2203, "이미 존재하는 리소스입니다."),
    RESOURCE_ACCESS_NOT_ACCEPTABLE(HttpStatus.NOT_ACCEPTABLE, 2204, "접근 권한이 없습니다."),
    AUTHENTICATION_FAIL(HttpStatus.UNAUTHORIZED, 2205, "인증에 실패했습니다."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, 2206, "비밀번호가 일치하지 않습니다."),
    INSUFFICIENT_STOCK(HttpStatus.BAD_REQUEST,2207,"재고가 부족합니다."),


    SEVER_NOT_SUPPORT(HttpStatus.INTERNAL_SERVER_ERROR, 9999, "알 수 없는 예외입니다.");

    private final HttpStatus httpStatus;
    private final int code;
    private final String message;
}
