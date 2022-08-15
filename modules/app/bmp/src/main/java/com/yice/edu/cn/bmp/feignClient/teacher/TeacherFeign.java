package com.yice.edu.cn.bmp.feignClient.teacher;

import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jw",contextId = "teacherFeign",path = "teacher")
public interface TeacherFeign {

    @PostMapping("/login")
    Teacher login(Teacher teacher);

    @PostMapping("/findTeacherCountByCondition")
    long findTeacherCountByCondition(Teacher teacher);


    @PostMapping("/updateTeacherAdmin")
     void updateTeacherAdmin(Teacher teacher);

    @GetMapping("/findTeacherById/{id}")
     Teacher findTeacherById(@PathVariable("id") String id);

    @PostMapping("/findCorrespondencesByTeacher")
    List<Teacher> findCorrespondencesByTeacher(Teacher teacher);

    @PostMapping("/findTeacherListByCondition")
     List<Teacher> findTeacherListByCondition(Teacher teacher);

    @PostMapping("/findTeacherCountByCondition4Like")
    long findTeacherCountByCondition4Like(Teacher teacher);
    @PostMapping("/findStudentTeachers")
    List<Teacher> findStudentTeachers(Teacher teacher);
}
