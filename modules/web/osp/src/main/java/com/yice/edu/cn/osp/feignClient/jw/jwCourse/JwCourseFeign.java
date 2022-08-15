package com.yice.edu.cn.osp.feignClient.jw.jwCourse;

import com.yice.edu.cn.common.pojo.jw.JwCourse.JwCourse;
import com.yice.edu.cn.common.pojo.general.dd.Dd;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherClassesCourse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Repository
@FeignClient(value = "jw",contextId = "jwCourseFeign",path = "/jwCourse")
public interface JwCourseFeign {
    @GetMapping("/findJwCourseById/{id}")
    JwCourse findJwCourseById(@PathVariable("id") String id);

    @PostMapping("/saveJwCourses")
    long saveJwCourse(JwCourse jwCourse);

    @PostMapping("/findJwCourseListByCondition")
    List<JwCourse> findJwCourseListByCondition(JwCourse jwCourse);

    @PostMapping("/findJwCourseCountByCondition")
    long findJwCourseCountByCondition(JwCourse jwCourse);

    @PostMapping("/findOneJwCourseByCondition")
    JwCourse findOneJwCourseByCondition(JwCourse jwCourse);

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

    @PostMapping("/deleteTeacherClassesCourseByCondition")
    void deleteTeacherClassesCourseByCondition(JwCourse jwCourse);

    @PostMapping("/updateTeacherClassesCourseByCondition")
    void  updateTeacherClassesCourseByCondition(@RequestBody JwCourse jwCourse);
    @GetMapping("/findTeachersByNameId/{nameId}/{schoolId}")
    List<Teacher> findTeachersByNameId(@PathVariable("nameId") String typeId, @PathVariable("schoolId") String schoolId);

    @PostMapping("/findJwCourseListByConditionKong")
    List<JwCourse> findJwCourseListByConditionKong(JwCourse jwCourse);
    @PostMapping("/findJwCourseCountByConditionKong")
    long findJwCourseCountByConditionKong(JwCourse jwCourse);
    @GetMapping("/findSchoolEaxmCourseList/{schoolId}")
    List<JwCourse> findSchoolEaxmCourseList(@PathVariable("schoolId")String schoolId);
    @PostMapping("/findFiltrationJwCouserBySchoolId")
    List<Dd> findFiltrationJwCouserBySchoolId(Dd dd);
}

