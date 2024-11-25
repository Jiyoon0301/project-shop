package project.shop1.domain.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import project.shop1.global.util.reponse.BooleanResponse;
import project.shop1.global.security.SecurityUtils;
import project.shop1.global.security.redis.dto.RefreshToken;
import project.shop1.global.security.redis.service.RefreshTokenService;

@RestController
@RequiredArgsConstructor
public class LogoutController {

    private final RefreshTokenService refreshTokenService;

    /**
     * redis에서 토큰 삭제함으로써 로그아웃
     * @return
     */
    @PostMapping("/logout/delete-token") // "/logout"으로 했을 때 "Method Not Allowed" 에러 해결해야함
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BooleanResponse> logout() {
        String account = SecurityUtils.getCurrentUsername();
        RefreshToken refreshToken = refreshTokenService.findByAccount(account);
        // redis 에서 refreshToken 삭제
        refreshTokenService.removeRefreshToken(refreshToken.getAccessToken());

        return ResponseEntity.ok(BooleanResponse.of(true));
    }

    // 세션 로그아웃
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
}
