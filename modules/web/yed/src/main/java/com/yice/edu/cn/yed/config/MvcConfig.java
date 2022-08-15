package com.yice.edu.cn.yed.config;

import com.yice.edu.cn.yed.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Bean
    public LoginInterceptor loginInterceptor(){
        return new LoginInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor()).addPathPatterns("/**")
                .excludePathPatterns("/login/**","/error/**")
                .excludePathPatterns("/**/*.*")
                .excludePathPatterns("/swagger-resources/**")
                .excludePathPatterns("/*/v2/api-docs")
                .excludePathPatterns("/swagger/api-docs")
                .excludePathPatterns("/webjars/**")
//                .excludePathPatterns("/**/*.html","/**/*.js","/**.css","/**.woff","/**.ttf","/**.eot","/**.woff2","/**.css")
//                .excludePathPatterns("/**.jpg","/**.jpeg","/**.png","/**.gif","/**.bmp","/**.svg","/**.ico")
                .excludePathPatterns("/");


    }
}
