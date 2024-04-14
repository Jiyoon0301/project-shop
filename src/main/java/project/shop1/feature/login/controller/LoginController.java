package project.shop1.feature.login.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project.shop1.common.reponse.BooleanResponse;
import project.shop1.common.validation.ValidationSequence;
import project.shop1.feature.login.dto.LoginRequestDto;
import project.shop1.feature.login.service.LoginService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final LoginService loginService;

//    @Value("${kakao.client_id}")
    private final String client_id = "8e1195f51d733edc8a79e51967b3065d";

//    @Value("${kakao.redirect_uri}")
    private final String redirect_uri = "http://localhost:8080/login/kakao-callback";

    private final String uri = "https://kauth.kakao.com/oauth/authorize?response_type=code&client_id="+client_id+"redirect_uri="+redirect_uri;


    @PostMapping("/login")
    public ResponseEntity<BooleanResponse> login(@Validated(value = ValidationSequence.class) @RequestBody LoginRequestDto loginRequestDto, HttpServletRequest request){
        loginService.loginUser(loginRequestDto, request);

        return ResponseEntity.ok(BooleanResponse.of(true));
    }

//    @GetMapping("/login/kakao-auth")
//    public ResponseEntity<String> kakaoAuth(Model model){
//        String location = uri;
//
//        return ResponseEntity.status(HttpStatus.FOUND).header("Location", location).build();
//    }
//
//    @GetMapping("/login/kakao-callback")
//    public String kakaoCallBack(@RequestParam("code") String code){
//        log.info("code : "+ code);
//        return null;
//    }

}
