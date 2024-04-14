package project.shop1.common.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException{

        // 1. Request Header에서 JWT 토큰 추출
        String token = resolveToken((HttpServletRequest) request);

        // 2. validateToken으로 토큰 유효성 검사
        if (token != null && jwtTokenProvider.validateToken(token)) { //토큰의 유효성 검증
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication); //인증 객체를 SecurityContext에 저장 -> 요청을 처리하는 동안 인증 정보가 유지된다
        }
        chain.doFilter(request, response); //다음 필터로 요청을 전달
    }

    // Request Header(주어진 HttpServletRequest)에서 토큰 정보 추출
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization"); //"Authorization" 헤더에서
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) { //"Bearer" 접두사로 시작하는 토큰을 추출하여 반환
            return bearerToken.substring(7);
        }
        return null;
    }

}
