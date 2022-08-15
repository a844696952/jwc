package com.yice.edu.cn.osp.feignClient.jw.teacher;

import com.yice.edu.cn.common.pojo.jw.teacher.TeacherQuit;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jw",contextId = "teacherQuitFeign",path = "/teacherQuit")
public interface TeacherQuitFeign {
    @GetMapping("/findTeacherQuitById/{id}")
    TeacherQuit findTeacherQuitById(@PathVariable("id") String id);
    @PostMapping("/saveTeacherQuit")
    TeacherQuit saveTeacherQuit(TeacherQuit teacherQuit);
    @PostMapping("/findTeacherQuitListByCondition")
    List<TeacherQuit> findTeacherQuitListByCondition(TeacherQuit teacherQuit);
    @PostMapping("/findTeacherQuitCountByCondition")
    long findTeacherQuitCountByCondition(TeacherQuit teacherQuit);
    @PostMapping("/updateTeacherQuit")
    void updateTeacherQuit(TeacherQuit teacherQuit);
    @GetMapping("/deleteTeacherQuit/{id}")
    void deleteTeacherQuit(@PathVariable("id") String id);
    @PostMapping("/deleteTeacherQuitByCondition")
    void deleteTeacherQuitByCondition(TeacherQuit teacherQuit);
}
