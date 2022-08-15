package com.yice.edu.cn.pcd;

import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication(scanBasePackages = {"com.yice.edu.cn.common","com.yice.edu.cn.pcd"})
@EnableFeignClients
@EnableHystrix
@Configuration
@EnableMethodCache(basePackages = "com.yice.edu.cn.pcd")
@EnableCreateCacheAnnotation
public class PcdApplication {
    public static void main(String[] args) {

        SpringApplication.run(PcdApplication.class, args);
    }
}
