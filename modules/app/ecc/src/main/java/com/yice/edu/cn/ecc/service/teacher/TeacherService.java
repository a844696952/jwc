package com.yice.edu.cn.ecc.service.teacher;

import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher4Classes;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherClasses;
import com.yice.edu.cn.ecc.feignClient.teacher.TeacherFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService {

    @Autowired
    private TeacherFeign teacherFeign;

    public List<Teacher4Classes> findTeacherListByCondition(TeacherClasses teacherClasses) {
        return teacherFeign.findClassTeacherListByClasses(teacherClasses);
    }

}
