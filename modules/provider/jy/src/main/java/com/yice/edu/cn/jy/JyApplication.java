package com.yice.edu.cn.jy;

import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication(scanBasePackages = {"com.yice.edu.cn.common", "com.yice.edu.cn.jy"})
@Configuration
@EnableCaching
@EnableFeignClients
@EnableCreateCacheAnnotation
public class JyApplication {
    public static void main(String[] args) {
        SpringApplication.run(JyApplication.class, args);
    }
}
