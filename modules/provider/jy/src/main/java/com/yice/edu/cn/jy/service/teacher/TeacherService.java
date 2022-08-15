package com.yice.edu.cn.jy.service.teacher;

import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.jy.feignClient.teacher.TeacherFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeacherService {
    @Autowired
    private TeacherFeign teacherFeign;
    public Teacher findTeacherById(String id) {
        return teacherFeign.findTeacherById(id);
    }

}
