package project.shop1.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import project.shop1.domain.auth.service.impl.CustomOAuth2UserService;
import project.shop1.global.security.jwt.JwtAuthenticationFilter;
import project.shop1.global.security.jwt.JwtTokenProvider;
import project.shop1.global.security.jwt.handler.CustomAccessDeniedHandler;
import project.shop1.global.security.jwt.handler.CustomAuthenticationEntryPoint;
import project.shop1.domain.auth.local.common.CustomUserDetailsService;

@Configuration
@EnableWebSecurity //Spring Security 설정 활성화
@EnableMethodSecurity(securedEnabled = true) //@PreAuthorize 활성화, EnableGlobalMethodSecurity deprecated
@AllArgsConstructor
public class SecurityConfig {
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtTokenProvider jwtTokenProvider;
    private final CustomAccessDeniedHandler accessDeniedHandler;
    private final CustomAuthenticationEntryPoint authenticationEntryPoint;
    private final CustomOAuth2UserService customOAuth2UserService; // OAuth2UserService 주입
    private static final String[] AUTH_WHITELIST = {
            "/join/**", "/login/**"
    };

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(jwtTokenProvider);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //CSRF, CORS
        http.csrf((csrf) -> csrf.disable()) //CSRF 보호 비활성화 : CSRF 토큰을 사용하지 않을 것이므로 확인하지 않도록 설정
            .cors(Customizer.withDefaults()) //CORS 설정을 적용 : 다른 도메인의 웹 페이지에서 리소스에 접근할 수 있도록 허용

        //JWT 를 사용, 세션 사용하지 않음
            .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(
                SessionCreationPolicy.STATELESS))

        //FormLogin, BasicHttp 비활성화
            .formLogin((form) -> form.disable()) //폼 로그인과 HTTP 기본 인증을 비활성화 : Spring 웹 페이제엇 제공되는 로그인 폼을 통해 사용자를 인증하는 메커니즘과 HTTP 기반 기본 인증 비활성화
            .httpBasic(AbstractHttpConfigurer::disable)

        //JwtAuthFilter 를 UsernamePasswordAuthenticationFilter 앞에 추가
        //JwtAuthFilter 를 통과하여 Authentication 을 획득하였다면 인증 필요(Authenticated)한 자원의 사용이 가능
            .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)

            .exceptionHandling((exceptionHandling) -> exceptionHandling
                .authenticationEntryPoint(authenticationEntryPoint) //인증되지 않은 사용자에 대해 처리
                .accessDeniedHandler(accessDeniedHandler)) //인증되었지만, 특정 리소스에 대한 권한이 없을 경우 처리


        //권한 규칙 작성
        //anyRequest()에 대해 permitAll() -> 기본적으로는 모두 허용, EnableGlobalMethodSecurity: Annotation으로 접근을 제한, 메서드 단위로 보안 수준을 설정
            .authorizeHttpRequests(authorize -> authorize.requestMatchers(AUTH_WHITELIST).permitAll()
                        //@PreAuthorization을 사용할 것이기 때문에 모든 경로에 대한 인증처리 Pass
                        .anyRequest().permitAll());
//                      .anyRequest().authenticated()); // 나머지 요청은 인증 필요

        // OAuth2 로그인 설정
//        http.oauth2Login(oauth2Login -> oauth2Login
//                .userInfoEndpoint(userInfoEndpoint -> userInfoEndpoint
//                        .userService(customOAuth2UserService)) // OAuth2UserService 설정
//        );

        return http.build();
    }
}
