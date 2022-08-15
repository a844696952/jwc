package com.yice.edu.cn.osp.feignClient.jy.cloudClassroom.cloudCourse;


import com.yice.edu.cn.common.pojo.cc.cloudCourse.CloudCourse;
import com.yice.edu.cn.common.pojo.cc.cloudCourse.CloudCourseResource;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jy",contextId = "cloudCourseResourceFeign", path = "/cloudCourseResource")
public interface CloudCourseResourceFeign {
    @GetMapping("/findCloudCourseResourceById/{id}")
    CloudCourseResource findCloudCourseResourceById(@PathVariable("id") String id);
    @PostMapping("/saveCloudCourseResource")
    CloudCourseResource saveCloudCourseResource(CloudCourseResource cloudCourseResource);
    @PostMapping("/findCloudCourseResourceListByCondition")
    List<CloudCourseResource> findCloudCourseResourceListByCondition(CloudCourseResource cloudCourseResource);
    @PostMapping("/findRecordingAndBroadcastingResources")
    List<CloudCourseResource> findRecordingAndBroadcastingResources(CloudCourseResource cloudCourseResource);
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

    @GetMapping("/deleteCloudCourseResourceRe/{id}")
    void deleteCloudCourseResourceRe(@PathVariable("id") String id);

    @PostMapping("/findResourceRecordByCondition")
    List<CloudCourseResource> findResourceRecordByCondition(CloudCourseResource cloudCourseResource);

    @GetMapping("/deleteResourceMsg/{id}")
    void deleteResourceMsg(@PathVariable("id") String id);
}
