package com.yice.edu.cn.bmp.service.teacher;

import com.yice.edu.cn.bmp.feignClient.teacher.TeacherFeign;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class TeacherService {
    @Autowired
    private TeacherFeign teacherFeign;


    public List<Teacher> findCorrespondencesByTeacher(Teacher teacher) {
        return teacherFeign.findCorrespondencesByTeacher(teacher);
    }

    public long findTeacherCountByCondition(
            @ApiParam(value = "教师信息对象")
            @RequestBody Teacher teacher){
        return teacherFeign.findTeacherCountByCondition(teacher);
    }

    public long findTeacherCountByCondition4Like(Teacher teacher) {
        return teacherFeign.findTeacherCountByCondition4Like(teacher);
    }

    public List<Teacher> findStudentTeachers(Teacher teacher) {
        return teacherFeign.findStudentTeachers(teacher);
    }

    public Teacher findTeacherById(String id) {
        return teacherFeign.findTeacherById(id);
    }
}
