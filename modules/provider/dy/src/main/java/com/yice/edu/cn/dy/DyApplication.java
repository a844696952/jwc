package com.yice.edu.cn.dy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@SpringBootApplication(scanBasePackages = {"com.yice.edu.cn.common", "com.yice.edu.cn.dy"})
@Configuration
@EnableFeignClients
public class DyApplication {

    public static void main(String[] args) {
        SpringApplication.run(DyApplication.class, args);
    }

    @Configuration
    public static class SecurityPermitAllConfig extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests().anyRequest().permitAll()
                    .and().headers().frameOptions().disable()
                    .and().csrf().disable();

        }
    }

}
