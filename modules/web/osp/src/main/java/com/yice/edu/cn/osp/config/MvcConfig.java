package com.yice.edu.cn.osp.config;

import com.yice.edu.cn.osp.interceptor.LoginInterceptor;
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
                .excludePathPatterns("/login/**","/error/**","/kqMachine/**","/kqOriginDataReceive/**","/h5nl/**")
                .excludePathPatterns("/**/*.*")
                .excludePathPatterns("/swagger-resources/**")
                .excludePathPatterns("/*/v2/api-docs")
                .excludePathPatterns("/swagger/api-docs")
                .excludePathPatterns("/webjars/**")
                .excludePathPatterns("/datasource/teacherHeadImg/**")
//                .excludePathPatterns("/**/*.html","/**/*.js","/**.css","/**.woff","/**.ttf","/**.eot","/**.woff2","/**.css")
//                .excludePathPatterns("/**.jpg","/**.jpeg","/**.png","/**.gif","/**.bmp","/**.svg","/**.ico")
                .excludePathPatterns("/").excludePathPatterns("/21/**");


    }


}
