package com.yice.edu.cn.ws.feignClient.cc;


import com.yice.edu.cn.common.pojo.cc.cloudCourse.CloudCourseShareFile;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jy",contextId = "cloudCourseShareFileFeign",path = "/cloudCourseShareFile")
public interface CloudCourseShareFileFeign {
    @GetMapping("/findCloudCourseShareFileById/{id}")
    CloudCourseShareFile findCloudCourseShareFileById(@PathVariable("id") String id);
    @PostMapping("/saveCloudCourseShareFile")
    CloudCourseShareFile saveCloudCourseShareFile(CloudCourseShareFile cloudCourseShareFile);
    @PostMapping("/findCloudCourseShareFileListByCondition")
    List<CloudCourseShareFile> findCloudCourseShareFileListByCondition(CloudCourseShareFile cloudCourseShareFile);
    @PostMapping("/findOneCloudCourseShareFileByCondition")
    CloudCourseShareFile findOneCloudCourseShareFileByCondition(CloudCourseShareFile cloudCourseShareFile);
    @PostMapping("/findCloudCourseShareFileCountByCondition")
    long findCloudCourseShareFileCountByCondition(CloudCourseShareFile cloudCourseShareFile);
    @PostMapping("/updateCloudCourseShareFile")
    void updateCloudCourseShareFile(CloudCourseShareFile cloudCourseShareFile);
    @GetMapping("/deleteCloudCourseShareFile/{id}")
    void deleteCloudCourseShareFile(@PathVariable("id") String id);
    @PostMapping("/deleteCloudCourseShareFileByCondition")
    void deleteCloudCourseShareFileByCondition(CloudCourseShareFile cloudCourseShareFile);
}
