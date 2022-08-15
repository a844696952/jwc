package com.yice.edu.cn.tap;

import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication(scanBasePackages = {"com.yice.edu.cn.common","com.yice.edu.cn.tap"})
@EnableFeignClients
@EnableHystrix
@Configuration
@EnableMethodCache(basePackages = "com.yice.edu.cn.tap")
@EnableCreateCacheAnnotation
public class TapApplication {
    public static void main(String[] args) {

        SpringApplication.run(TapApplication.class, args);
    }

    @Autowired
    private RestTemplateBuilder builder;

    @Bean
    public RestTemplate restTemplate() {
        return builder.build();
    }

}
