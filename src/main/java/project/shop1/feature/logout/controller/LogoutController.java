package project.shop1.feature.logout.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import project.shop1.common.exception.BusinessException;
import project.shop1.common.exception.ErrorCode;
import project.shop1.common.reponse.BooleanResponse;
import project.shop1.common.security.jwt.JwtAuthenticationFilter;
import project.shop1.common.security.jwt.JwtTokenProvider;
import project.shop1.feature.login.common.CustomUserDetails;
import project.shop1.feature.logout.service.LogoutService;

@RestController
@RequiredArgsConstructor
public class LogoutController {

    private final LogoutService logoutService;
    private final JwtTokenProvider jwtTokenProvider;


    /* 세션 로그아웃 */
//    @PostMapping("/logout")
//    @PreAuthorize("hasRole('USER')")
//    public ResponseEntity<BooleanResponse> logout(HttpServletRequest httpServletRequest){
//        HttpSession session = httpServletRequest.getSession();
//        session.invalidate();
//        if (session == null || !httpServletRequest.isRequestedSessionIdValid()) {
//            System.out.println("세션이 무효화 상태입니다.");
//        }
//        return ResponseEntity.ok(BooleanResponse.of(true));
//    }

    /* 토큰 로그아웃 */
    @PostMapping("/logout") // Post->Delete
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BooleanResponse> logout(@AuthenticationPrincipal CustomUserDetails userDetails, HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        String accessToken = null;
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            accessToken =  bearerToken.substring(7);
        }

        if (accessToken == null) {
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "이미 로그아웃된 회원입니다.");
        } else{
            logoutService.logout(accessToken, userDetails.getUsername()); // userName = account
        }

        return ResponseEntity.ok(BooleanResponse.of(true));
    }

}
