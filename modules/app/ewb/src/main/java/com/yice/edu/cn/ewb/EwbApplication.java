package com.yice.edu.cn.ewb;

import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import com.yice.edu.cn.ewb.upload.CustomCommonsMultipartResolver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.multipart.MultipartResolver;

@SpringBootApplication(scanBasePackages = {"com.yice.edu.cn.common","com.yice.edu.cn.ewb"})
@EnableFeignClients
@EnableHystrix
@Configuration
@EnableMethodCache(basePackages = "com.yice.edu.cn.ewb")
@EnableCreateCacheAnnotation
@EnableAspectJAutoProxy
public class EwbApplication {
    public static void main(String[] args) {

        SpringApplication.run(EwbApplication.class, args);
    }

    @Bean(value="multipartResolver")
    public MultipartResolver multipartResolver(){
        return new CustomCommonsMultipartResolver();
    }

}
