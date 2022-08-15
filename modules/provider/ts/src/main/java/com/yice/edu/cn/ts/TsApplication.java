package com.yice.edu.cn.ts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;

@SpringBootApplication(scanBasePackages = {"com.yice.edu.cn.common", "com.yice.edu.cn.ts"})
@EnableHystrix
@Configuration
@EnableFeignClients
@EnableMethodCache(basePackages = "com.yice.edu.cn.ts")
@EnableCreateCacheAnnotation
@EnableAspectJAutoProxy
@EnableScheduling
public class TsApplication {
    public static void main(String[] args) {
        SpringApplication.run(TsApplication.class, args);
    }
    
}
