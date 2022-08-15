package com.yice.edu.cn.bmp.config;

import com.yice.edu.cn.bmp.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Bean
    public  LoginInterceptor loginInterceptor(){
        return  new LoginInterceptor();
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor()).addPathPatterns("/**")
                .excludePathPatterns("/login/**")
                .excludePathPatterns("/register/**")
                .excludePathPatterns("/error/**")
                .excludePathPatterns("/**/*.*")
                .excludePathPatterns("/swagger-resources/**")
                .excludePathPatterns("/*/v2/api-docs")
                .excludePathPatterns("/swagger/api-docs")
                .excludePathPatterns("/webjars/**")
                .excludePathPatterns("/");


    }
}
//    excludePathPatterns方法是排除访问路径,但是当你排除的url路径在项目中并不存在的时候,springboot会将路径编程/error,从而无法进行排除.