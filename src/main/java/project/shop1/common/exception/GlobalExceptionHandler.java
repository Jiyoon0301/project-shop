package project.shop1.common.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import project.shop1.common.ResponseDto;
import project.shop1.feature.login.exception.NotExistUserEntity;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({Exception.class})
    protected ResponseEntity<ResponseDto> joinUserException(){
        return new ResponseEntity<>(new ResponseDto("이미 사용 중인 아이디입니다."), new HttpHeaders(), HttpStatus.CONFLICT); //충돌 에러
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ResponseDto> joinUserValidationException(MethodArgumentNotValidException ex){

        BindingResult bindingResult = ex.getBindingResult();
        String message = bindingResult.getFieldError().getDefaultMessage(); //에러 받아와서 그 에러에 맞는 message 받아오기

        return new ResponseEntity<>(new ResponseDto(message), new HttpHeaders(), HttpStatus.CONFLICT); //return에 에러 메세지 담기
    }

    @ExceptionHandler(NotExistUserEntity.class)
    protected ResponseEntity<ResponseDto> NotExistUserEntity(MethodArgumentNotValidException ex){

        BindingResult bindingResult = ex.getBindingResult();
        String message = bindingResult.getFieldError().getDefaultMessage(); //에러 받아와서 그 에러에 맞는 message 받아오기

        return new ResponseEntity<>(new ResponseDto(message), new HttpHeaders(), HttpStatus.CONFLICT); //return에 에러 메세지 담기
    }

}
