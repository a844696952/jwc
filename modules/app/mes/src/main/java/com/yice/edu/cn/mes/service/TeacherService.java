package com.yice.edu.cn.mes.service;

import com.alicp.jetcache.anno.CacheInvalidate;
import com.yice.edu.cn.common.pojo.Constant;
import org.springframework.stereotype.Service;

@Service
public class TeacherService {

    @CacheInvalidate(name = Constant.Redis.MES_TEACHER_LOGIN_ERROR, key = "#key")
    public void clearLoginErrorInfoCache(String key) {

    }
}
