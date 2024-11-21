package project.shop1.domain.logout.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import project.shop1.common.reponse.BooleanResponse;
import project.shop1.common.security.SecurityUtil;
import project.shop1.common.security.redis.dto.RefreshToken;
import project.shop1.common.security.redis.service.RefreshTokenService;

@RestController
@RequiredArgsConstructor
public class LogoutController {

    private final RefreshTokenService refreshTokenService;


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

    /* 토큰 로그아웃 - redis 에서 토큰 삭제 */
    @PostMapping("/logout/delete-token") // "/logout"으로 했을 때 "Method Not Allowed" 에러 해결해야함
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BooleanResponse> logout(){
        String account = SecurityUtil.getCurrentUsername();
        RefreshToken refreshToken = refreshTokenService.findByAccount(account);
        // redis 에서 refreshToken 삭제
        refreshTokenService.removeRefreshToken(refreshToken.getAccessToken());

        return ResponseEntity.ok(BooleanResponse.of(true));
    }


}
