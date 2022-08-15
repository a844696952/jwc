package com.yice.edu.cn.jw.service.user;

import com.yice.edu.cn.common.pojo.general.user.User;
import com.yice.edu.cn.jw.dao.user.IUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private IUserDao userDao;

    public User findUserById(String id) {
        return userDao.findUserById(id);
    }
}
