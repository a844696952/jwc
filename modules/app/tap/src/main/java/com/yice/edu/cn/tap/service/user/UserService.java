package com.yice.edu.cn.tap.service.user;

import com.yice.edu.cn.common.pojo.general.user.User;
import com.yice.edu.cn.tap.feignClient.user.UserFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserFeign userFeign;

    public User findUserById(String id) {
        return userFeign.findUserById(id);
    }
}
