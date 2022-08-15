package com.yice.edu.cn.cc.feignClient.cloudCourse;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.yice.edu.cn.common.pojo.cc.cloudCourse.CloudCourseResource;

@FeignClient(value="jy",contextId = "cloudCourseResourceFeign",path = "/cloudCourseResource")
public interface CloudCourseResourceFeign {
    @GetMapping("/findCloudCourseResourceById/{id}")
    CloudCourseResource findCloudCourseResourceById(@PathVariable("id") String id);
    @PostMapping("/saveCloudCourseResource")
    CloudCourseResource saveCloudCourseResource(CloudCourseResource cloudCourseResource);
    @PostMapping("/findCloudCourseResourceListByCondition")
    List<CloudCourseResource> findCloudCourseResourceListByCondition(CloudCourseResource cloudCourseResource);
    @PostMapping("/findOneCloudCourseResourceByCondition")
    CloudCourseResource findOneCloudCourseResourceByCondition(CloudCourseResource cloudCourseResource);
    @PostMapping("/findCloudCourseResourceCountByCondition")
    long findCloudCourseResourceCountByCondition(CloudCourseResource cloudCourseResource);
    @PostMapping("/updateCloudCourseResource")
    void updateCloudCourseResource(CloudCourseResource cloudCourseResource);
    @GetMapping("/deleteCloudCourseResource/{id}")
    void deleteCloudCourseResource(@PathVariable("id") String id);
    @PostMapping("/deleteCloudCourseResourceByCondition")
    void deleteCloudCourseResourceByCondition(CloudCourseResource cloudCourseResource);
}
