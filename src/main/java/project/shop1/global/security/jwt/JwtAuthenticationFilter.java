package project.shop1.global.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;
//    private final RefreshTokenRepository refreshTokenRepository;
//    private final UserRepository userRepository;
//    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    /* JWT 토큰 검증 필터 수행 */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        //Request Header 에서 JWT 토큰 추출
        String token = resolveToken((HttpServletRequest) request);

        //validationToken 으로 토큰 유효성 검사
        if (token != null && jwtTokenProvider.validateToken(token)) { //Token 이 유효
            //토큰이 유효할 경우 토큰에서 Authentication 객체 가지고 와 SecurityContext 에 저장
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
//        } else if (token != null && jwtTokenProvider.getExpiration(token) < 0){ //Token != null 이고 만료시간이 지남 ********************** 삭제해야될 수도
//            logger.error("Access Token 이 만료되었습니다.");
//
//            //redis 에 저장되어 있는 토큰 정보를 만료된 access token 으로 찾아온다
//            RefreshToken foundTokenInfo = refreshTokenRepository.findByAccessToken(token)
//                    .orElseThrow(()-> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "토큰이 존재하지 않습니다."));
//
//            String refreshToken = foundTokenInfo.getRefreshToken();
//
//            //만약 refresh 토큰도 만료되었다면, ExceptionHandlerFilter에서 처리된다.
////            JwtTokenUtil.isExpired(refreshToken, secreKey);
//
//            // refresh token 이 유효 -> redis 에 함께 저장해둔 employeeId 가져온다
//            String userEntityAccount = foundTokenInfo.getAccount();
//
//            UserEntity found = userRepository.findByAccount(userEntityAccount)
//                    .orElseThrow(()-> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "존재하지 않는 회원입니다."));
//
//            //위 사용자 정보로 다시 Access Token 을 만들어 발급
//            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(found.getAccount(), found.getPassword());
//            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
//            token = jwtTokenProvider.generateToken(authentication).getAccessToken();
//
//            //새로 발급한 Access Token 을 Redis 에도 업데이트
//            refreshTokenRepository.save(new RefreshToken(userEntityAccount, refreshToken, token));
        //클라이언트 측 쿠키의 Access Token 업데이트
//            CookieGenerator cookieGenerator = new CookieGenerator();
//            cookieGenerator.setCookieName("token");
//            cookieGenerator.setCookieHttpOnly(true);
//            cookieGenerator.addCookie(response, token);
//            cookieGenerator.setCookieMaxAge(60 * 60);//1시간
        chain.doFilter(request, response);
    }

    /* Request Header 에서 토큰 정보 추출 */
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
