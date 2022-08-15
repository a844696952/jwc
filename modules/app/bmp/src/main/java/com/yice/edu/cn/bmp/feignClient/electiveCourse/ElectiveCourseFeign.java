package com.yice.edu.cn.bmp.feignClient.electiveCourse;

import com.yice.edu.cn.common.pojo.jw.electiveCourse.ElectiveCourse;
import com.yice.edu.cn.common.pojo.jw.electiveCourse.StudentPojo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jw",contextId = "electiveCourseFeign",path = "/electiveCourse")
public interface ElectiveCourseFeign {
    @GetMapping("/findElectiveCourseWithClassInfoById/{id}")
    ElectiveCourse findElectiveCourseById(@PathVariable("id") String id);
    @PostMapping("/saveElectiveCourse")
    ElectiveCourse saveElectiveCourse(ElectiveCourse electiveCourse);
    @PostMapping("/findElectiveCourseListByCondition")
    List<ElectiveCourse> findElectiveCourseListByCondition(ElectiveCourse electiveCourse);
    @PostMapping("/findOneElectiveCourseByCondition")
    ElectiveCourse findOneElectiveCourseByCondition(ElectiveCourse electiveCourse);
    @PostMapping("/findElectiveCourseCountByCondition")
    long findElectiveCourseCountByCondition(ElectiveCourse electiveCourse);
    @PostMapping("/updateElectiveCourse")
    void updateElectiveCourse(ElectiveCourse electiveCourse);
    @GetMapping("/deleteElectiveCourse/{id}")
    void deleteElectiveCourse(@PathVariable("id") String id);
    @PostMapping("/deleteElectiveCourseByCondition")
    void deleteElectiveCourseByCondition(ElectiveCourse electiveCourse);
    @PostMapping("/findStudentListByClassIdList")
    List<StudentPojo> findStudentListByClassIdList(List<String> classIdList);



    @PostMapping("/findElectiveCoursesByConditionForStu")
    List<ElectiveCourse> findElectiveCoursesByConditionForStu(ElectiveCourse electiveCourse);
    @PostMapping("/findElectiveCourseCountByConditionForStu")
    long findElectiveCourseCountByConditionForStu(ElectiveCourse electiveCourse);
}
