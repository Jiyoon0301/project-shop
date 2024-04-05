package project.shop1.common.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import project.shop1.common.interceptor.AdminInterceptor;
import project.shop1.common.interceptor.CartInterceptor;
import project.shop1.common.interceptor.LoginInterceptor;
import project.shop1.feature.join.repository.JoinRepository;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final JoinRepository joinRepository;

    @Override
    public void addInterceptors (InterceptorRegistry registry) {
        // CartInterceptor를 "/cart/**" URL 패턴에 적용
//        registry.addInterceptor(new CartInterceptor())
//                .addPathPatterns("/cart/**");
//                .addPathPatterns("/order/**");

        // LoginInterceptor를 "/login/**" URL 패턴에 적용
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/login/**");

        // AdminInterceptor를 "/admin/**" URL 패턴에 적용
//        registry.addInterceptor(new AdminInterceptor(joinRepository))
//                .addPathPatterns("/admin/**");
    }

}