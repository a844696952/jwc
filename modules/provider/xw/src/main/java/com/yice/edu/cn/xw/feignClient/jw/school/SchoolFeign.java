package com.yice.edu.cn.xw.feignClient.jw.school;

import com.yice.edu.cn.common.pojo.jw.school.School;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jw",contextId = "schoolFeign",path = "/school")
public interface SchoolFeign {
    @GetMapping("/findSchoolById/{id}")
    School findSchoolById(@PathVariable("id") String id);
    @PostMapping("/saveSchool")
    School saveSchool(School school);
    @PostMapping("/findSchoolListByCondition")
    List<School> findSchoolListByCondition(School school);
    @PostMapping("/findSchoolCountByCondition")
    long findSchoolCountByCondition(School school);
    @PostMapping("/updateSchool")
    void updateSchool(School school);
    @GetMapping("/deleteSchool/{id}")
    void deleteSchool(@PathVariable("id") String id);
    @PostMapping("/deleteSchoolByCondition")
    void deleteSchoolByCondition(School school);
    @GetMapping("/validateSchoolAccount/{schoolId}")
    boolean validateSchoolAccount(@PathVariable("schoolId") String schoolId);
}