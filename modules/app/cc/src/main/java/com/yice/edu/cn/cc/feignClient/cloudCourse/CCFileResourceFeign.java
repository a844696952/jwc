package com.yice.edu.cn.cc.feignClient.cloudCourse;

import com.yice.edu.cn.common.pojo.cc.cloudCourse.CloudCourseFileResource;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jy",contextId = "cCFileResourceFeign",path = "/cCFileResource")
public interface CCFileResourceFeign {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findCCFileResourceById/{id}")
    CloudCourseFileResource findCCFileResourceById(@PathVariable("id") String id);
    @PostMapping("/saveCCFileResource")
    CloudCourseFileResource saveCCFileResource(CloudCourseFileResource cCFileResource);
    @PostMapping("/batchSaveCCFileResource")
    void batchSaveCCFileResource(List<CloudCourseFileResource> cCFileResources);
    @PostMapping("/findCCFileResourceListByCondition")
    List<CloudCourseFileResource> findCCFileResourceListByCondition(CloudCourseFileResource cCFileResource);
    @PostMapping("/findOneCCFileResourceByCondition")
    CloudCourseFileResource findOneCCFileResourceByCondition(CloudCourseFileResource cCFileResource);
    @PostMapping("/findCCFileResourceCountByCondition")
    long findCCFileResourceCountByCondition(CloudCourseFileResource cCFileResource);
    @PostMapping("/updateCCFileResource")
    void updateCCFileResource(CloudCourseFileResource cCFileResource);
    @PostMapping("/updateCCFileResourceForNotNull")
    void updateCCFileResourceForAll(CloudCourseFileResource cCFileResource);
    @GetMapping("/deleteCCFileResource/{id}")
    void deleteCCFileResource(@PathVariable("id") String id);
    @PostMapping("/deleteCCFileResourceByCondition")
    void deleteCCFileResourceByCondition(CloudCourseFileResource cCFileResource);
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
