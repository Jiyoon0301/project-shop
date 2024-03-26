package project.shop1.common.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import project.shop1.common.interceptor.CartInterceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // CartInterceptor를 "/cart/**" URL 패턴에 적용
        registry.addInterceptor(new CartInterceptor())
                .addPathPatterns("/cart/**");
    }
}