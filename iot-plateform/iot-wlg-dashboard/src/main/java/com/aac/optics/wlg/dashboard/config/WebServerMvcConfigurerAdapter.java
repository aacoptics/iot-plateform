package com.aac.optics.wlg.dashboard.config;

import com.aac.optics.common.web.interceptor.UserInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebServerMvcConfigurerAdapter implements WebMvcConfigurer {

    @Bean
    public HandlerInterceptor userInterceptor() {
        return new UserInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userInterceptor());
    }
}
