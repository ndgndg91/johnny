package com.johnny.cs.core.configuration;

import com.johnny.cs.core.interceptor.AuthorizationInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthorizationInterceptor())
                .addPathPatterns("/alarm/me")
                .addPathPatterns("/line/test")
                .addPathPatterns("/get/**")
                .addPathPatterns("/holiday/**");
    }
}
