package com.yice.edu.cn.osp.feignClient.jw.teacher;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import com.yice.edu.cn.common.pojo.jw.teacher.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(value = "jw",contextId = "teacherClassesFeign",path = "/teacherClasses")
public interface TeacherClassesFeign {
    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findTeacherClassesById/{id}")
    TeacherClasses findTeacherClassesById(@PathVariable("id") String id);
    @PostMapping("/saveTeacherClasses")
    TeacherClasses saveTeacherClasses(TeacherClasses teacherClasses);
    @PostMapping("/batchSaveTeacherClasses")
    void batchSaveTeacherClasses(List<TeacherClasses> teacherClassess);
    @PostMapping("/findTeacherClassesListByCondition")
    List<TeacherClasses> findTeacherClassesListByCondition(TeacherClasses teacherClasses);
    @PostMapping("/findOneTeacherClassesByCondition")
    TeacherClasses findOneTeacherClassesByCondition(TeacherClasses teacherClasses);
    @PostMapping("/findTeacherClassesCountByCondition")
    long findTeacherClassesCountByCondition(TeacherClasses teacherClasses);
    @PostMapping("/updateTeacherClasses")
    void updateTeacherClasses(TeacherClasses teacherClasses);
    @PostMapping("/updateTeacherClassesForNotNull")
    void updateTeacherClassesForAll(TeacherClasses teacherClasses);
    @GetMapping("/deleteTeacherClasses/{id}")
    void deleteTeacherClasses(@PathVariable("id") String id);
    @PostMapping("/deleteTeacherClassesByCondition")
    void deleteTeacherClassesByCondition(TeacherClasses teacherClasses);
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

    @PostMapping("/editTeacherPostCourse")
    ResponseJson editTeacherPostCourse(TeacherClasses teacherClasses);

    @PostMapping("/findTeacherClassPostCourseList")
    List<Map<String, Object>> findTeacherClassPostCourseList(
            @RequestBody TeacherClasses teacherClasses);

    @PostMapping("/findTeacherClassPostCourseListBySchoolId")
    List<Map<String, Object>> findTeacherClassPostCourseListBySchoolId(
            @RequestBody TeacherClasses teacherClasses);

    @PostMapping("/findClassTeacherListByClasses")
    List<Teacher4Classes> findClassTeacherListByClasses(TeacherClasses teacherClasses);

    @GetMapping(value = "/findTeacherClassesByTeacher/{teacherId}")
    List<TeacherClasses> findTeacherClassesByTeacher(@PathVariable("teacherId") String teacherId);

    @GetMapping("/findGradeByTeacher/{teacherId}")
    List<TeacherClasses> findGradeByTeacher(@PathVariable("teacherId") String teacherId);

    @PostMapping("/findCourseByTeacherGrade")
    List<TeacherClassesCourse> findCourseByTeacherGrade(TeacherClasses teacherClasses);

    @PostMapping("/findCourseByTeacherGrade2")
    List<TeacherClassesCourse> findCourseByTeacherGrade2(TeacherClasses teacherClasses);

    @PostMapping("/findClassesByTeacherCourse")
    List<TeacherClasses> findClassesByTeacherCourse(TeacherClassVo teacherClassVo);

    @GetMapping("/findCourseNameList4Teacher/{teacherId}")
    List<String> findCourseNameList4Teacher(@PathVariable("teacherId") String teacherId);

    @PostMapping("/findClassesByTeacherInfo")
    List<TeacherClassesForQusSurvey> findClassesByTeacherInfo(@RequestBody TeacherClassesForQusSurvey teacher5Classes);

    @PostMapping("/findTeacherClassPostCourseListXq")
    List<Map<String,String>> findTeacherClassPostCourseListXq(TeacherClasses teacherClasses);

    /**
     * 作业分析
     */
    @GetMapping("/findTeacherClassPostCourseListHomeworkAnalyse/{teacherId}")
    List<Map<String,String>>  findTeacherClassPostCourseListHomeworkAnalyse(@PathVariable("teacherId") String teacherId);

    @GetMapping("/findTeacherClassesHomeworkAnalyseByTeacherId/{teacherId}")
    List<Map<String,String>>  findTeacherClassesHomeworkAnalyseByTeacherId(@PathVariable("teacherId") String teacherId);
    @GetMapping("/findTeacherClassCourseListHomeworkAnalyse/{teacherClassesId}")
    List<TeacherClassesCourse> findTeacherClassCourseListHomeworkAnalyse(@PathVariable("teacherClassesId") String teacherClassesId);

    @GetMapping("/findClassTeacherListHomeworkAnalyseByClasses/{classesId}")
    List<TeacherHomeworkAnalyseClasses> findClassTeacherListHomeworkAnalyseByClasses(@PathVariable("classesId") String classesId);
    @PostMapping("/findClassesCourseListHomeworkAnalyseByTeacherClassesIds")
    List<Map<String,Object>> findClassesCourseListHomeworkAnalyseByTeacherClassesIds(Map map);
    
    @GetMapping("/findTeacherClassByTeacherId/{teacherId}")
    List<JwClasses> findTeacherClassByTeacherId(@PathVariable("teacherId") String teacherId);

    @PostMapping("/findTeacherClassByTeacherIdAndPost/{teacherId}")
    TeacherClasses findTeacherClassByTeacherIdAndPost(@PathVariable("teacherId")String teacherId,@RequestParam("postName") String postName);

    @PostMapping("/findCourse4TeacherClasses")
    List<TeacherClassesCourse> findCourse4TeacherClasses(TeacherClasses teacherClasses);

    @PostMapping("/findHeadmasterByClasses")
    Teacher findHeadmasterByClasses(@RequestBody TeacherClasses teacherClasses );

    @GetMapping("/findTeacherClasses/{id}")
    List<TeacherClasses> findTeacherClasses(@PathVariable("id") String id);

    @PostMapping("/batchSaveTeaching")
    Map<String, Object> batchSaveTeaching(@RequestParam("schoolId") String schoolId,@RequestBody List<TeachingInfo> teachingInfos);

}