package com.yice.edu.cn.yed.feignClient.baseData.school;

import com.yice.edu.cn.common.pojo.jw.school.School;
import com.yice.edu.cn.common.pojo.jw.school.SchoolExt;
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
    @PostMapping("/saveSchoolAndSchoolYear")
    void saveSchoolAndSchoolYear(SchoolExt schoolExt);

    @PostMapping("/findSchoolNoRepetitionSchoolName")
    long findSchoolNoRepetitionSchoolName(School school);
    @PostMapping("/queryClockInRange")
    public List<Integer> queryClockInRange();
}
