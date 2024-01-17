package project.shop1.common.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import project.shop1.common.ResponseDto;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({Exception.class})
    protected ResponseEntity<ResponseDto> joinUserException(){
        return new ResponseEntity<>(new ResponseDto("이미 사용 중인 아이디입니다."), new HttpHeaders(), HttpStatus.CONFLICT); //충돌 에러
    }

}
