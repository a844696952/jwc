package com.yice.edu.cn.recording;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication(scanBasePackages = {"com.yice.edu.cn.common", "com.yice.edu.cn"})
@Configuration
@EnableAsync //开启异步调用
@EnableFeignClients
@EnableHystrix
public class RecordingApplication {
    public static void main(String[] args) {
        SpringApplication.run(RecordingApplication.class, args);
    }

}
