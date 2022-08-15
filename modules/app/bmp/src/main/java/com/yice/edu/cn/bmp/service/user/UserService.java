package com.yice.edu.cn.bmp.service.user;

import com.yice.edu.cn.bmp.feignClient.user.UserFeign;
import com.yice.edu.cn.common.pojo.general.user.User;
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
