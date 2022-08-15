package com.yice.edu.cn.bmp.feignClient.teacher;

import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherPost;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jw",path = "/teacherPost")
public interface TeacherPostFeign {
    @GetMapping("/findTeacherPostById/{id}")
    TeacherPost findTeacherPostById(@PathVariable("id") String id);
    @PostMapping("/saveTeacherPost")
    TeacherPost saveTeacherPost(TeacherPost teacherPost);
    @PostMapping("/findTeacherPostListByCondition")
    List<TeacherPost> findTeacherPostListByCondition(TeacherPost teacherPost);
    @PostMapping("/findTeacherPostCountByCondition")
    long findTeacherPostCountByCondition(TeacherPost teacherPost);
    @PostMapping("/updateTeacherPost")
    void updateTeacherPost(TeacherPost teacherPost);
    @GetMapping("/deleteTeacherPost/{id}")
    void deleteTeacherPost(@PathVariable("id") String id);
    @PostMapping("/editTeacherPost")
    int editTeacherPost(TeacherPost teacherPost);
    @PostMapping("/findOneTeacherPostByCondition")
    TeacherPost findOneTeacherPostByCondition(TeacherPost teacherPost);
    @PostMapping("/findTeachersByPost")
    List<Teacher> findTeachersByPost(TeacherPost teacherPost);
}
