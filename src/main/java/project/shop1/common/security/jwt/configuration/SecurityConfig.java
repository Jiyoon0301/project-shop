package project.shop1.common.security.jwt.configuration;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import project.shop1.domain.user.repository.UserRepository;
import project.shop1.common.security.jwt.JwtAuthenticationFilter;
import project.shop1.common.security.jwt.JwtTokenProvider;
import project.shop1.common.security.jwt.handler.CustomAccessDeniedHandler;
import project.shop1.common.security.jwt.handler.CustomAuthenticationEntryPoint;
import project.shop1.common.security.redis.repository.RefreshTokenRepository;
import project.shop1.domain.login.local.common.CustomUserDetailsService;

/* SecurityContext */
@Configuration
@EnableWebSecurity //Spring Security 설정 활성화
@EnableMethodSecurity(securedEnabled = true) //@PreAuthorize 활성화, EnableGlobalMethodSecurity deprecated 됨
@AllArgsConstructor
//@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtTokenProvider jwtTokenProvider;
    private final CustomAccessDeniedHandler accessDeniedHandler;
    private final CustomAuthenticationEntryPoint authenticationEntryPoint;

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private static final String[] AUTH_WHITELIST={
        "/join/**", "/login/**", "/main/**", "/search/**"
    };


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        //CSRF, CORS
        http.csrf((csrf) -> csrf.disable()); //CSRF 보호 비활성화 : CSRF 토큰을 사용하지 않을 것이므로 확인하지 않도록 설정
        http.cors(Customizer.withDefaults()); //CORS 설정을 적용 : 다른 도메인의 웹 페이지에서 리소스에 접근할 수 있도록 허용

        //JWT 를 사용하기 때문에 세션 사용하지 않음
        http.sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(
                SessionCreationPolicy.STATELESS));

        //FormLogin, BasicHttp 비활성화
        http.formLogin((form) -> form.disable()); //폼 로그인과 HTTP 기본 인증을 비활성화 : Spring 웹 페이제엇 제공되는 로그인 폼을 통해 사용자를 인증하는 메커니즘과 HTTP 기반 기본 인증 비활성화
        http.httpBasic(AbstractHttpConfigurer::disable);

        //JwtAuthFilter 를 UsernamePasswordAuthenticationFilter 앞에 추가
        //JwtAuthFilter 를 통과하여 Authentication 을 획득하였다면 인증 필요(Authenticated)한 자원의 사용이 가능
        http.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider, refreshTokenRepository, userRepository, authenticationManagerBuilder), UsernamePasswordAuthenticationFilter.class);

        http.exceptionHandling((exceptionHandling) -> exceptionHandling
                .authenticationEntryPoint(authenticationEntryPoint) //인증되지 않은 사용자에 대해 처리
                .accessDeniedHandler(accessDeniedHandler)); //인증되었지만, 특정 리소스에 대한 권한이 없을 경우 처리


        //권한 규칙 작성
        //anyRequest()에 대해 permitAll() 해주었는데 기본적으로는 모두 허용해줄 것이고, EnableGlobalMethodSecurity를 설정해둔 이유가 Annotation으로 접근을 제한하려고 했기 때문에 메서드 단위로 보안 수준을 설정해줄 것이다.
        http.authorizeHttpRequests(authorize -> authorize.requestMatchers(AUTH_WHITELIST).permitAll()
//                        .requestMatchers("/my-page/**").hasRole("USER")
//                        .requestMatchers("/admin/**").hasRole("ADMIN")
        //@PreAuthorization을 사용할 것이기 때문에 모든 경로에 대한 인증처리 Pass
                .anyRequest().permitAll()
//                        .anyRequest().authenticated();
        );
        return http.build();

    }

}
