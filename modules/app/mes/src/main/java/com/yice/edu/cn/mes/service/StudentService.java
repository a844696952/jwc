package com.yice.edu.cn.mes.service;

import com.alicp.jetcache.anno.CacheInvalidate;
import com.alicp.jetcache.anno.CacheUpdate;
import com.alicp.jetcache.anno.Cached;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.mes.feign.StudentFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class StudentService {
    @Autowired
    private StudentFeign studentFeign;

    @Cached(name = Constant.Redis.MES_STUDENT_LOGIN_ERROR, key = "#key", expire = 1, timeUnit = TimeUnit.DAYS)
    public String saveLoginErrorInfoToRedis(String errorInfo, String key) {
        return errorInfo;
    }

    @CacheUpdate(name = Constant.Redis.MES_STUDENT_LOGIN_ERROR, key = "#key", value = "#errorInfo")
    public void updateLoginErrorInfoToRedis(String errorInfo, String key) {

    }

    @CacheInvalidate(name = Constant.Redis.MES_STUDENT_LOGIN_ERROR, key = "#key")
    public void clearLoginErrorInfoCache(String key) {

    }

    @Cached(name = Constant.Redis.MES_STUDENT_CACHE, key = "#student.id", expire = Constant.Redis.EWB_STUDENT__TIMEOUT, timeUnit = TimeUnit.DAYS)
    public Student saveStudentToRedis(Student student) {
        return student;
    }

    public List<Student> findStudentLogin(Student student) {
        return studentFeign.findStudentListByCondition(student);
    }

    public Student getStudentLoginInfo(String id) {
        return studentFeign.getStudentLoginInfo(id);
    }

    public List<Student> findStudentsByCondition(Student student) {
        return studentFeign.findStudentsByCondition(student);
    }

    public Student findStudentById(String id) {
        return studentFeign.findStudentById(id);
    }
}
