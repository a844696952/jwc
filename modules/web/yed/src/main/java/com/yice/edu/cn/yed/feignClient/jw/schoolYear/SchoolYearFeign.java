package com.yice.edu.cn.yed.feignClient.jw.schoolYear;

import com.yice.edu.cn.common.pojo.jw.schoolYear.SchoolYear;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jw",contextId = "schoolYearFeign",path = "/schoolYear")
public interface SchoolYearFeign {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findSchoolYearById/{id}")
    SchoolYear findSchoolYearById(@PathVariable("id") String id);
    @PostMapping("/saveSchoolYear")
    SchoolYear saveSchoolYear(SchoolYear schoolYear);
    @PostMapping("/batchSaveSchoolYear")
    void batchSaveSchoolYear(List<SchoolYear> schoolYears);
    @PostMapping("/findSchoolYearListByCondition")
    List<SchoolYear> findSchoolYearListByCondition(SchoolYear schoolYear);
    @PostMapping("/findOneSchoolYearByCondition")
    SchoolYear findOneSchoolYearByCondition(SchoolYear schoolYear);
    @PostMapping("/findSchoolYearCountByCondition")
    long findSchoolYearCountByCondition(SchoolYear schoolYear);
    @PostMapping("/updateSchoolYear")
    void updateSchoolYear(SchoolYear schoolYear);
    @PostMapping("/updateSchoolYearForAll")
    void updateSchoolYearForAll(SchoolYear schoolYear);
    @GetMapping("/deleteSchoolYear/{id}")
    void deleteSchoolYear(@PathVariable("id") String id);
    @PostMapping("/deleteSchoolYearByCondition")
    void deleteSchoolYearByCondition(SchoolYear schoolYear);
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
