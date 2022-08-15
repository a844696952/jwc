package com.yice.edu.cn.osp.feignClient.jy.cloudClassroom.cloudCourse;

import com.yice.edu.cn.common.pojo.cc.cloudCourse.CloudCourse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jy",contextId = "cloudCourseFeign",path = "/cloudCourse")
public interface CloudCourseFeign {
    @GetMapping("/findCloudCourseById/{id}")
    CloudCourse findCloudCourseById(@PathVariable("id") String id);
    @PostMapping("/saveCloudCourse")
    CloudCourse saveCloudCourse(CloudCourse cloudCourse);
    @PostMapping("/findCloudCourseListByCondition")
    List<CloudCourse> findCloudCourseListByCondition(CloudCourse cloudCourse);
    @PostMapping("/findOneCloudCourseByCondition")
    CloudCourse findOneCloudCourseByCondition(CloudCourse cloudCourse);
    @PostMapping("/findCloudCourseCountByCondition")
    long findCloudCourseCountByCondition(CloudCourse cloudCourse);
    @PostMapping("/updateCloudCourse")
    void updateCloudCourse(CloudCourse cloudCourse);
    @GetMapping("/deleteCloudCourse/{id}")
    void deleteCloudCourse(@PathVariable("id") String id);
    @PostMapping("/deleteCloudCourseByCondition")
    void deleteCloudCourseByCondition(CloudCourse cloudCourse);
    @PostMapping("/findCloudCoursesByConditionOther")
    List<CloudCourse> findCloudCoursesByConditionOther(CloudCourse cloudCourse);
    @PostMapping("/findCloudCourseCountByConditionOther")
    long findCloudCourseCountByConditionOther(CloudCourse cloudCourse);
    @PostMapping("/findCloudCoursesByConditionMine")
    List<CloudCourse> findCloudCoursesByConditionMine(CloudCourse cloudCourse);
    @PostMapping("/findCloudCourseCountByConditionMine")
    long findCloudCourseCountByConditionMine(CloudCourse cloudCourse);

}
