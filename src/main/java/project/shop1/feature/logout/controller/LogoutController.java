package project.shop1.feature.logout.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import project.shop1.common.reponse.BooleanResponse;

@RestController
@RequiredArgsConstructor
public class LogoutController {

    @PostMapping("/logout")
    public ResponseEntity<BooleanResponse> logout(HttpServletRequest httpServletRequest){
        HttpSession session = httpServletRequest.getSession();
        session.invalidate();
        if (session == null || !httpServletRequest.isRequestedSessionIdValid()) {
            System.out.println("세션이 무효화 상태입니다.");
        }

        return ResponseEntity.ok(BooleanResponse.of(true));
    }

//    @PostMapping("/logout")
//    public ResponseEntity<ResponseDto> logoutUser(@RequestHeader("Authorization") String token) throws Exception {
//        // 토큰을 이용한 로그아웃 처리
//    }

}
