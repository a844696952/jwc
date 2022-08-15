package com.yice.edu.cn.common;

import com.yice.edu.cn.common.aspect.SysLogAspect;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@Configuration
@AllArgsConstructor
@ConditionalOnWebApplication
@EnableFeignClients({"com.yice.edu.cn.common.feign"})
public class CommonApplication {

    @Bean
    public SysLogAspect sysLogAspect() {
        return new SysLogAspect();
    }
}
