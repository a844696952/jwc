package com.yice.edu.cn.jw.feign.dm;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@FeignClient(value="dm",contextId = "dmClassCardFeign",path = "/dmClassCard")
public interface DmClassCardFeign {
    @PostMapping("/deleteDmData")
    void deleteDmData(List<String> clazzIdList);

    @DeleteMapping("/deleteDmKqData/{schoolId}")
    void deleteDmKqData(@PathVariable("schoolId") String schoolId);

    @DeleteMapping("/cacheInvalidateSchoolCourse/{schoolId}")
    void cacheInvalidateSchoolCourse(@PathVariable("schoolId") String schoolId);
}