package project.shop1.feature.login.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import project.shop1.common.ResponseDto;
import project.shop1.common.validation.ValidationSequence;
import project.shop1.feature.join.dto.JoinRequestDto;
import project.shop1.feature.login.dto.LoginRequestDto;
import project.shop1.feature.login.service.LoginService;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<ResponseDto> loginUser(@Validated(value= ValidationSequence.class) LoginRequestDto loginRequestDto, HttpServletRequest httpServletRequest){
        loginService.loginUser(loginRequestDto,httpServletRequest);
        return new ResponseEntity<>(new ResponseDto("로그인이 성공적으로 완료되었습니다."), new HttpHeaders(), HttpStatus.OK);
    }

}
