package com.yice.edu.cn.yed.feignClient.frame.login;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

@Component
public class LoginFeignFallbackFactory /*implements FallbackFactory<LoginFeign>*/ {


    /*@Override
    public LoginFeign create(Throwable throwable) {
        return new LoginFeign() {
            @Override
            public String findAdminById(@PathVariable("id") String id) {
                return throwable.getCause().toString();
            }

        };
    }*/
}
