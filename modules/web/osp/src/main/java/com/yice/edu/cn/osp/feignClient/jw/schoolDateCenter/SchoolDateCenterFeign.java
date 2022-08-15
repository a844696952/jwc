package com.yice.edu.cn.osp.feignClient.jw.schoolDateCenter;

import com.yice.edu.cn.common.pojo.jw.SchoolDateCenter.SchoolDateCenter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jw",contextId = "schoolDateCenterFeign",path = "/schoolDateCenter")
public interface SchoolDateCenterFeign {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findSchoolDateCenterById/{id}")
    SchoolDateCenter findSchoolDateCenterById(@PathVariable("id") String id);
    @PostMapping("/saveSchoolDateCenter")
    SchoolDateCenter saveSchoolDateCenter(SchoolDateCenter schoolDateCenter);
    @PostMapping("/batchSaveSchoolDateCenter")
    void batchSaveSchoolDateCenter(List<SchoolDateCenter> schoolDateCenters);
    @PostMapping("/findSchoolDateCenterListByCondition")
    List<SchoolDateCenter> findSchoolDateCenterListByCondition(SchoolDateCenter schoolDateCenter);
    @PostMapping("/findOneSchoolDateCenterByCondition")
    SchoolDateCenter findOneSchoolDateCenterByCondition(SchoolDateCenter schoolDateCenter);
    @PostMapping("/findSchoolDateCenterCountByCondition")
    long findSchoolDateCenterCountByCondition(SchoolDateCenter schoolDateCenter);
    @PostMapping("/updateSchoolDateCenter")
    void updateSchoolDateCenter(SchoolDateCenter schoolDateCenter);
    @PostMapping("/updateSchoolDateCenterForNotNull")
    void updateSchoolDateCenterForAll(SchoolDateCenter schoolDateCenter);
    @GetMapping("/deleteSchoolDateCenter/{id}")
    void deleteSchoolDateCenter(@PathVariable("id") String id);
    @PostMapping("/deleteSchoolDateCenterByCondition")
    void deleteSchoolDateCenterByCondition(SchoolDateCenter schoolDateCenter);
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    @PostMapping("/updateSchoolDateCenterByType/{type}")
    void updateSchoolDateCenterByType(@RequestBody SchoolDateCenter schoolDateCenter,@PathVariable("type") Integer integer);

    @GetMapping("/findSchoolCompusCenterBySchoolId/{schoolId}")
    SchoolDateCenter findSchoolCompusCenterBySchoolId(@PathVariable("schoolId")String schoolId);

    @GetMapping("/getTruesSchoolDateCenter/{schoolId}")
    SchoolDateCenter getTruesSchoolDateCenter(@PathVariable("schoolId")String schoolId);
}
