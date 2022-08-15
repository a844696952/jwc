package com.yice.edu.cn.cc.feignClient.cloudCourse;

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
    @PostMapping("/findCloudCourseListForTeacher")
    List<CloudCourse> findCloudCourseListForTeacher(CloudCourse cloudCourse);
    @PostMapping("/findCloudCourseCountForTeacher")
    long findCloudCourseCountForTeacher(CloudCourse cloudCourse);
    @PostMapping("/findCloudCourseListForOther")
    List<CloudCourse> findCloudCourseListForOther(CloudCourse cloudCourse);
    @PostMapping("/findCloudCourseCountForOther")
    long findCloudCourseCountForOther(CloudCourse cloudCourse);
    @PostMapping("/findCloudCourseListForAll")
    List<CloudCourse> findCloudCourseListForAll(CloudCourse cloudCourse);
    @PostMapping("/findCloudCourseCountForAll")
    long findCloudCourseCountForAll(CloudCourse cloudCourse);
    @PostMapping("/findCloudCourseValid")
    Integer findCloudCourseValid(CloudCourse cloudCourse);

}
