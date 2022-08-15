package com.yice.edu.cn.xw.feignClient.jw.schoolddatecenter;

import com.yice.edu.cn.common.pojo.jw.SchoolDateCenter.SchoolDateCenter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value="jw",contextId = "schoolDateCenterFeign",path = "/schoolDateCenter")
public interface SchoolDateCenterFeign {

    @GetMapping("/findSchoolCompusCenterBySchoolId/{schoolId}")
    SchoolDateCenter findSchoolCompusCenterBySchoolId(@PathVariable("schoolId")String schoolId);
}
