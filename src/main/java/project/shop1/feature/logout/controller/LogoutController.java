package project.shop1.feature.logout.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import project.shop1.common.ResponseDto;
import project.shop1.common.validation.ValidationSequence;
import project.shop1.feature.login.dto.LoginRequestDto;

@RestController
@RequiredArgsConstructor
public class LogoutController {

    @PostMapping("/logout")
    public ResponseEntity<ResponseDto> logoutUser(HttpServletRequest httpServletRequest) throws Exception{

        HttpSession session = httpServletRequest.getSession();

        session.invalidate();

        return new ResponseEntity<>(new ResponseDto("로그아웃되었습니다."), new HttpHeaders(), HttpStatus.OK);
    }

//    @PostMapping("/logout")
//    public ResponseEntity<ResponseDto> logoutUser(@RequestHeader("Authorization") String token) throws Exception {
//        // 토큰을 이용한 로그아웃 처리
//    }

}
