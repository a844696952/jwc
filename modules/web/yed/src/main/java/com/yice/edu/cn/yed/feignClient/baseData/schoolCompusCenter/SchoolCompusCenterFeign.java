package com.yice.edu.cn.yed.feignClient.baseData.schoolCompusCenter;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.schoolCompusCenter.SchoolCompusCenter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value="jw",contextId = "schoolCompusCenterFeign",path = "/schoolCompusCenter")
public interface SchoolCompusCenterFeign {
    @GetMapping("/findSchoolCompusCenterById/{id}")
    SchoolCompusCenter findSchoolCompusCenterById(@PathVariable("id") String id);
    @PostMapping("/saveSchoolCompusCenter")
    SchoolCompusCenter saveSchoolCompusCenter(SchoolCompusCenter schoolCompusCenter);
    @PostMapping("/findSchoolCompusCenterListByCondition")
    List<SchoolCompusCenter> findSchoolCompusCenterListByCondition(SchoolCompusCenter schoolCompusCenter);
    @PostMapping("/findOneSchoolCompusCenterByCondition")
    SchoolCompusCenter findOneSchoolCompusCenterByCondition(SchoolCompusCenter schoolCompusCenter);
    @PostMapping("/findSchoolCompusCenterCountByCondition")
    long findSchoolCompusCenterCountByCondition(SchoolCompusCenter schoolCompusCenter);
    @PostMapping("/updateSchoolCompusCenter")
    void updateSchoolCompusCenter(SchoolCompusCenter schoolCompusCenter);
    @GetMapping("/deleteSchoolCompusCenter/{id}")
    void deleteSchoolCompusCenter(@PathVariable("id") String id);
    @PostMapping("/deleteSchoolCompusCenterByCondition")
    void deleteSchoolCompusCenterByCondition(SchoolCompusCenter schoolCompusCenter);


    @GetMapping("/findSchoolCompusCenter/{schoolId}")
    ResponseJson findSchoolCompusCenter(@PathVariable("schoolId") String schoolId);
    @PostMapping("/saveSchoolCompusCenterData")
    void saveSchoolCompusCenterData(@RequestBody SchoolCompusCenter schoolCompusCenter);
}
