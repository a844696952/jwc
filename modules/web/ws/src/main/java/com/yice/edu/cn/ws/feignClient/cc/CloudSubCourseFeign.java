package com.yice.edu.cn.ws.feignClient.cc;

import com.yice.edu.cn.common.pojo.cc.cloudCourse.CloudSubCourse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jy",contextId = "cloudSubCourseFeign",path = "/cloudSubCourse")
public interface CloudSubCourseFeign {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findCloudSubCourseById/{id}")
    CloudSubCourse findCloudSubCourseById(@PathVariable("id") String id);
    @PostMapping("/saveCloudSubCourse")
    CloudSubCourse saveCloudSubCourse(CloudSubCourse cloudSubCourse);
    @PostMapping("/batchSaveCloudSubCourse")
    void batchSaveCloudSubCourse(List<CloudSubCourse> cloudSubCourses);
    @PostMapping("/findCloudSubCourseListByCondition")
    List<CloudSubCourse> findCloudSubCourseListByCondition(CloudSubCourse cloudSubCourse);
    @PostMapping("/findOneCloudSubCourseByCondition")
    CloudSubCourse findOneCloudSubCourseByCondition(CloudSubCourse cloudSubCourse);
    @PostMapping("/findCloudSubCourseCountByCondition")
    long findCloudSubCourseCountByCondition(CloudSubCourse cloudSubCourse);
    @PostMapping("/updateCloudSubCourse")
    void updateCloudSubCourse(CloudSubCourse cloudSubCourse);
    @PostMapping("/updateCloudSubCourseForNotNull")
    void updateCloudSubCourseForAll(CloudSubCourse cloudSubCourse);
    @GetMapping("/deleteCloudSubCourse/{id}")
    void deleteCloudSubCourse(@PathVariable("id") String id);
    @PostMapping("/deleteCloudSubCourseByCondition")
    void deleteCloudSubCourseByCondition(CloudSubCourse cloudSubCourse);
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    @PostMapping("/findCloudSubCourseListByLoginIdAndNow")
    List<CloudSubCourse> findCloudSubCourseListByLoginIdAndNow(CloudSubCourse cloudSubCourse);
    @PostMapping("/endCourse")
    CloudSubCourse endCourse(CloudSubCourse cloudSubCourse);
    @PostMapping("/startCourse")
    CloudSubCourse startCourse(CloudSubCourse cloudSubCourse);


}
