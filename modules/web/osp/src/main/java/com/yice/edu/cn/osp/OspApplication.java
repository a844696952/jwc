package com.yice.edu.cn.osp;

import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication(scanBasePackages = {"com.yice.edu.cn.common","com.yice.edu.cn.osp"})
@EnableFeignClients
@EnableHystrix
@Configuration
@EnableMethodCache(basePackages = "com.yice.edu.cn.osp")
@EnableCreateCacheAnnotation
public class OspApplication {

    public static void main(String[] args) {

        SpringApplication.run(OspApplication.class, args);
    }
}
