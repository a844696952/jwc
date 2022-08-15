package com.yice.edu.cn.tap.feignClient.course;

import com.yice.edu.cn.common.pojo.general.dd.Dd;
import com.yice.edu.cn.common.pojo.jw.JwCourse.JwCourse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Repository
@FeignClient(value = "jw", path = "/jwCourse")
public interface JwCourseFeign {
    @GetMapping("/findJwCourseById/{id}")
    JwCourse findJwCourseById(@PathVariable("id") String id);

    @PostMapping("/saveJwCourses")
    long saveJwCourse(JwCourse jwCourse);

    @PostMapping("/findJwCourseListByCondition")
    List<JwCourse> findJwCourseListByCondition(JwCourse jwCourse);

    @PostMapping("/findJwCourseCountByCondition")
    long findJwCourseCountByCondition(JwCourse jwCourse);

    @PostMapping("/updateJwCourse")
    void updateJwCourse(JwCourse jwCourse);

    @GetMapping("/deleteJwCourse/{id}")
    void deleteJwCourse(@PathVariable("id") String id);

    @PostMapping("/deleteJwCourseByCondition")
    void deleteJwCourseByCondition(JwCourse jwCourse);

    @PostMapping("/queryAllByTypeIdGrade")
    List<Dd> queryAllByTypeIdGrade();

    @PostMapping("/queryAllByTypeIdCourse")
    List<Dd> queryAllByTypeIdCourse();

    @PostMapping("/queryAllByTypeIdType")
    List<Dd> queryAllByTypeIdType();

    @PostMapping("/queryAllByTypeIdBuilding")
    List<Dd> queryAllByTypeIdBuilding();

    @PostMapping("/queryOneById")
    Dd queryOneById(String id);

    @PostMapping("/distinctJwCourse")
    long distinctJwCourse(JwCourse jwCourse);


    @GetMapping("/deleteJwCourseTeacherClassesCurr")
    void deleteJwCourseTeacherClassesCurr(String id);

    @PostMapping("/deleteTeacherClassesCourseByCondition")
    void deleteTeacherClassesCourseByCondition(@RequestBody String id);

    @PostMapping("/updateTeacherClassesCourseByCondition")
    void  updateTeacherClassesCourseByCondition(@RequestBody JwCourse jwCourse);

    @GetMapping("/findSchoolEaxmCourseList/{schoolId}")
    List<JwCourse> findSchoolEaxmCourseList(@PathVariable("schoolId")String schoolId);

}

