package com.yice.edu.cn.mes;

import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = {"com.yice.edu.cn.common", "com.yice.edu.cn.mes"})
@EnableFeignClients
@EnableHystrix
@EnableCreateCacheAnnotation
@EnableMethodCache(basePackages = "com.yice.edu.cn.mes")
public class MesApplication {

    public static void main(String[] args) {
        SpringApplication.run(MesApplication.class, args);

    }


}
