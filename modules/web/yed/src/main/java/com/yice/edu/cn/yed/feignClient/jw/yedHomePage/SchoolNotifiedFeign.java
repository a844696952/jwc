package com.yice.edu.cn.yed.feignClient.jw.yedHomePage;

import com.yice.edu.cn.common.pojo.jw.yed.SchoolNotified;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jw",contextId = "schoolNotifiedFeign",path = "/schoolNotified")
public interface SchoolNotifiedFeign {
    @GetMapping("/findSchoolNotifiedById/{id}")
    SchoolNotified findSchoolNotifiedById(@PathVariable("id") String id);
    @PostMapping("/saveSchoolNotified")
    SchoolNotified saveSchoolNotified(SchoolNotified schoolNotified);
    @PostMapping("/findSchoolNotifiedListByCondition")
    List<SchoolNotified> findSchoolNotifiedListByCondition(SchoolNotified schoolNotified);
    @PostMapping("/findOneSchoolNotifiedByCondition")
    SchoolNotified findOneSchoolNotifiedByCondition(SchoolNotified schoolNotified);
    @PostMapping("/findSchoolNotifiedCountByCondition")
    long findSchoolNotifiedCountByCondition(SchoolNotified schoolNotified);
    @PostMapping("/updateSchoolNotified")
    void updateSchoolNotified(SchoolNotified schoolNotified);
    @GetMapping("/deleteSchoolNotified/{id}")
    void deleteSchoolNotified(@PathVariable("id") String id);
    @PostMapping("/deleteSchoolNotifiedByCondition")
    void deleteSchoolNotifiedByCondition(SchoolNotified schoolNotified);
}
