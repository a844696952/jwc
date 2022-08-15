package com.yice.edu.cn.osp.feignClient.jw.teacher;

import com.yice.edu.cn.common.pojo.general.dd.Dd;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherPost;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value="jw",contextId = "teacherPostFeign",path = "/teacherPost")
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
    @PostMapping("/findGradeTeacherBySchool")
    List<TeacherPost> findGradeTeacherBySchool(@RequestParam("schoolId")String schoolId,List<Dd> gradeList);
    @PostMapping("/findTeachersByPost")
    List<Teacher> findTeachersByPost(TeacherPost teacherPost);
    @PostMapping("/findTeacherListByPost")
    List<TeacherPost> findTeacherListByPost(TeacherPost teacherPost);
    @GetMapping("/findTeachers4Grade/{schoolId}")
    List<TeacherPost> findTeachers4Grade(@PathVariable("schoolId") String schoolId);
    @PostMapping("/findTeachers4Class")
    List<TeacherPost> findTeachers4Class(TeacherPost teacherPost);

}
