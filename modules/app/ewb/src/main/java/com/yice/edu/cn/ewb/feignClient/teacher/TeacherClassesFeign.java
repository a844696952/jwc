package com.yice.edu.cn.ewb.feignClient.teacher;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import com.yice.edu.cn.common.pojo.jw.teacher.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(value = "jw",contextId = "teacherClassesFeign",path = "/teacherClasses")
public interface TeacherClassesFeign {
    @GetMapping("/findTeacherClassesById/{id}")
    TeacherClasses findTeacherClassesById(@PathVariable("id") String id);

    @GetMapping("/findTeacherClassesByTeacherId/{id}")
    List<TeacherClasses> findTeacherClassesByTeacherId(@PathVariable("id") String id);

    @PostMapping("/saveTeacherClasses")
    TeacherClasses saveTeacherClasses(TeacherClasses teacherClasses);

    @PostMapping("/findTeacherClassesListByCondition")
    List<TeacherClasses> findTeacherClassesListByCondition(TeacherClasses teacherClasses);

    @PostMapping("/findTeacherClassesCountByCondition")
    long findTeacherClassesCountByCondition(TeacherClasses teacherClasses);

    @PostMapping("/updateTeacherClasses")
    void updateTeacherClasses(TeacherClasses teacherClasses);

    @GetMapping("/deleteTeacherClasses/{id}")
    void deleteTeacherClasses(@PathVariable("id") String id);

    @PostMapping("/editTeacherPostCourse")
    ResponseJson editTeacherPostCourse(TeacherClasses teacherClasses);

    @PostMapping("/findTeacherClassPostCourseList")
    List<Map<String, Object>> findTeacherClassPostCourseList(
            @RequestBody TeacherClasses teacherClasses);

    @PostMapping("/findClassTeacherListByClasses")
    List<Teacher4Classes> findClassTeacherListByClasses(TeacherClasses teacherClasses);

    @GetMapping(value = "/findTeacherClassesByTeacher/{teacherId}")
    List<TeacherClasses> findTeacherClassesByTeacher(@PathVariable("teacherId") String teacherId);

    @GetMapping("/findGradeByTeacher/{teacherId}")
    List<TeacherClasses> findGradeByTeacher(@PathVariable("teacherId") String teacherId);

    @PostMapping("/findClassesByTeacherCourse")
    List<TeacherClasses> findClassesByTeacherCourse(TeacherClassVo teacherClassVo);

    @GetMapping("/findCourseNameList4Teacher/{teacherId}")
    List<String> findCourseNameList4Teacher(@PathVariable("teacherId") String teacherId);

    @PostMapping("/findClassesByTeacherInfo")
    List<TeacherClassesForQusSurvey> findClassesByTeacherInfo(@RequestBody TeacherClassesForQusSurvey teacher5Classes);

    @PostMapping("/findTeacherClassPostCourseListXq")
    List<Map<String, Object>> findTeacherClassPostCourseListXq(TeacherClasses teacherClasses);

    /**
     * 作业分析
     */
    @PostMapping("/findTeacherClassPostCourseListHomeworkAnalyse")
    List<Map<String,Object>>  findTeacherClassPostCourseListHomeworkAnalyse(@RequestBody TeacherClasses teacherClasses);
    @PostMapping("/findClassTeacherListHomeworkAnalyseByClasses")
    List<TeacherHomeworkAnalyseClasses> findClassTeacherListHomeworkAnalyseByClasses(@RequestBody TeacherClasses teacherClasses);
    
    @GetMapping("/findTeacherClassByTeacherId/{teacherId}")
    List<JwClasses> findTeacherClassByTeacherId(@PathVariable("teacherId") String teacherId);

    @PostMapping("/findTeacherClassByTeacherIdAndPost/{teacherId}")
    TeacherClasses findTeacherClassByTeacherIdAndPost(@PathVariable("teacherId") String teacherId, @RequestParam("postName") String postName);
    @PostMapping("/findCourse4TeacherClasses")
    List<TeacherClassesCourse> findCourse4TeacherClasses(TeacherClasses teacherClasses);

    @GetMapping("/findTeacherClassCourseByTeacherId/{teacherId}")
    List<Map<String,String>> findTeacherClassCourseByTeacherId(
            @PathVariable("teacherId") String teacherId);
}