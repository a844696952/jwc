package com.yice.edu.cn.ts.feignClient;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;



@FeignClient(value="jy",contextId = "cloudSubCourseFeign",path = "/cloudSubCourse")
public interface CloudSubCourseFeign {
    @PostMapping("/pushCloudSubCourse10")
    void pushCloudSubCourse10();
    @PostMapping("/pushCloudSubCourse21")
    void pushCloudSubCourse21();
}
