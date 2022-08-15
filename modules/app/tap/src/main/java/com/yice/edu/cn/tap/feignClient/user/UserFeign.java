package com.yice.edu.cn.tap.feignClient.user;

import com.yice.edu.cn.common.pojo.general.user.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "jw",path = "/user")
public interface UserFeign {
    @GetMapping("/findUserById/{id}")
    User findUserById(@PathVariable("id") String id);
}
