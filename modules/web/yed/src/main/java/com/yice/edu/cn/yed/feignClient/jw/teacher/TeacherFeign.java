package com.yice.edu.cn.yed.feignClient.jw.teacher;

import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

@FeignClient(value="jw",contextId = "teacherFeign",path = "/teacher")
public interface TeacherFeign {
    @GetMapping("/findTeacherById/{id}")
    Teacher findTeacherById(@PathVariable("id") String id);
    @PostMapping("/saveTeacher")
    Teacher saveTeacher(Teacher teacher);
    @PostMapping("/findTeacherListByCondition")
    List<Teacher> findTeacherListByCondition(Teacher teacher);
    @PostMapping("/findTeacherCountByCondition")
    long findTeacherCountByCondition(Teacher teacher);
    @PostMapping("/findTeachersCount4Yed")
    long findTeachersCount4Yed(Teacher teacher);
    @PostMapping("/findTeachers4Yed")
    List<Teacher> findTeachers4Yed(Teacher teacher);
    @PostMapping("/updateTeacher")
    Teacher updateTeacher(Teacher teacher);
    @GetMapping("/deleteTeacher/{id}")
    void deleteTeacher(@PathVariable("id") String id);
    @PostMapping("/batchSaveTeacher")
    Map<String,Object> batchSaveTeacher(List<Teacher> teacherList);
    @PostMapping("/updateTeacherAdmin")
    void updateTeacherAdmin(Teacher teacher);
    @PostMapping("/findAdminBySchool")
    Teacher findAdminBySchool(Teacher teacher);
}
