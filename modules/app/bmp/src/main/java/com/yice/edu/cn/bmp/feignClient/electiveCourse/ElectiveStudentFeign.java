package com.yice.edu.cn.bmp.feignClient.electiveCourse;

import com.yice.edu.cn.common.pojo.jw.electiveCourse.ElectiveCourse;
import com.yice.edu.cn.common.pojo.jw.electiveCourse.ElectiveStudent;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

@FeignClient(value="jw",contextId = "electiveStudentFeign",path = "/electiveStudent")
public interface ElectiveStudentFeign {
    @GetMapping("/findElectiveStudentById/{id}")
    ElectiveStudent findElectiveStudentById(@PathVariable("id") String id);
    @PostMapping("/saveElectiveStudent")
    Map saveElectiveStudent(ElectiveStudent electiveStudent);
    @PostMapping("/findElectiveStudentListByCondition")
    List<ElectiveStudent> findElectiveStudentListByCondition(ElectiveStudent electiveStudent);
    @PostMapping("/findOneElectiveStudentByCondition")
    ElectiveStudent findOneElectiveStudentByCondition(ElectiveStudent electiveStudent);
    @PostMapping("/findElectiveStudentCountByCondition")
    long findElectiveStudentCountByCondition(ElectiveStudent electiveStudent);
    @PostMapping("/updateElectiveStudent")
    void updateElectiveStudent(ElectiveStudent electiveStudent);
    @GetMapping("/deleteElectiveStudent/{id}")
    void deleteElectiveStudent(@PathVariable("id") String id);
    @PostMapping("/deleteElectiveStudentByCondition")
    void deleteElectiveStudentByCondition(ElectiveStudent electiveStudent);
    @PostMapping("/batchSaveElectiveStudent")
    ElectiveCourse batchSaveElectiveStudent(List<ElectiveStudent> electiveStudents);
    @PostMapping("/uploadElectiveStudent")
    Map<String,Object> uploadElectiveStudent(List<ElectiveStudent> list1);


    @PostMapping("/findMyElectiveStudentListByCondition")
    List<ElectiveStudent> findMyElectiveStudentListByCondition(ElectiveStudent electiveStudent);
    @PostMapping("/findMyElectiveStudentCountByCondition")
    long findMyElectiveStudentCountByCondition(ElectiveStudent electiveStudent);
    @PostMapping("/getCanSelectCourseList")
    List<ElectiveCourse> getCanSelectCourseList(ElectiveStudent electiveStudent);
    @PostMapping("/findSchoolYearElectiveStudentsByCondition")
    List<ElectiveStudent> findSchoolYearElectiveStudentsByCondition(ElectiveStudent electiveStudent);
    @PostMapping("/findSchoolYearElectiveStudentsCountByCondition")
    long findSchoolYearElectiveStudentsCountByCondition(ElectiveStudent electiveStudent);
    @PostMapping("/getAlreadySelectCourseList")
    List<ElectiveCourse> getAlreadySelectCourseList(ElectiveStudent electiveStudent);
    @PostMapping("/findSchoolYearStudentScoreListByCondition")
    List<ElectiveStudent> findSchoolYearStudentScoreListByCondition(ElectiveStudent electiveStudent);
    @PostMapping("/findSchoolYearStudentScoreCountByCondition")
    long findSchoolYearStudentScoreCountByCondition(ElectiveStudent electiveStudent);
    @PostMapping("/getAlreadySelectCourseCount")
    long getAlreadySelectCourseCount(ElectiveStudent electiveStudent);
    @PostMapping("/checkTimeRepeatCount")
    long checkTimeRepeatCount(ElectiveStudent es);
}
