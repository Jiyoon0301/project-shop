//package project.shop1.common.configuration;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//@RequiredArgsConstructor
//public class SecurityConfig {
//
////    @Bean
////    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
////        return httpSecurity
////                .httpBasic().disable()
////                .csrf().disable()
////                .cors().and()
////                .authorizeRequests()
////                .requestMatchers("/api/users/login", "/api/users/join").permitAll()
////                .and()
////                .build();
////    }
//
//    // securityFilterChain 다시 채우기, 암호화된 비밀번호로 바꿔서 저장하기, login 메서드 손 보기
//}
