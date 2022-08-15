package com.yice.edu.cn.ecc.config;

import com.yice.edu.cn.ecc.interceptor.LoginInterceptor;
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
    
    
    /**
     * @author dengfengfeng
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor()).addPathPatterns("/**")
                .excludePathPatterns("/dmClassCard/dmClassCardLog")
                .excludePathPatterns("/**/*.*")
                .excludePathPatterns("/swagger-resources/**")
                .excludePathPatterns("/*/v2/api-docs")
                .excludePathPatterns("/swagger/api-docs")
                .excludePathPatterns("/webjars/**")
                .excludePathPatterns("/");


    }
}
