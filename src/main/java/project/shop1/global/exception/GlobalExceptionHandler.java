package project.shop1.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import project.shop1.global.util.reponse.ErrorResponse;

import java.util.Optional;


@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponse> joinUserValidationException(MethodArgumentNotValidException ex){
        ErrorCode errorCode = ErrorCode.PARAMETER_VALIDATION_FAIL;
        BindingResult bindingResult = ex.getBindingResult();

        String exceptionMessage =
                Optional.ofNullable(bindingResult.getFieldError())
                        .orElseGet(() -> {
                            log.error("Get Binding Result Error - Field Error is null.");
                            throw new NullPointerException("Get Binding Result Error - Field Error is null.");
                        })
                        .getDefaultMessage();

        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(ErrorResponse.of(errorCode.getCode(), exceptionMessage));
    }

    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<ErrorResponse> BusinessExceptionHandler(BusinessException ex){
        ErrorCode errorCode = ex.getErrorCode();

        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(ErrorResponse.of(
                        errorCode.getCode(), ex.getMessage()));
    }
}
