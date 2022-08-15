package com.yice.edu.cn.ecc;

import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication(scanBasePackages = {"com.yice.edu.cn.common","com.yice.edu.cn.ecc"})
@EnableFeignClients
@EnableHystrix
@Configuration
@EnableMethodCache(basePackages = "com.yice.edu.cn.ecc")
@EnableCreateCacheAnnotation

public class EccApplication {

    @Autowired
    private RestTemplateBuilder builder;

    public static void main(String[] args) {

        SpringApplication.run(EccApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return builder.build();
    }
}
