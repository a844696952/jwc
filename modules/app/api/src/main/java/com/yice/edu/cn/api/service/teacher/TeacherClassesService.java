package com.yice.edu.cn.api.service.teacher;

import com.yice.edu.cn.common.pojo.jw.teacher.TeacherClasses;
import com.yice.edu.cn.api.feign.teacher.TeacherClassesFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@Service
public class TeacherClassesService {
    @Autowired
    private TeacherClassesFeign teacherClassesFeign;

    public List<Map<String, Object>> findTeacherClassPostCourseListBySchoolId(
            @RequestBody TeacherClasses teacherClasses){
        return teacherClassesFeign.findTeacherClassPostCourseListBySchoolId(teacherClasses);
    }


}
