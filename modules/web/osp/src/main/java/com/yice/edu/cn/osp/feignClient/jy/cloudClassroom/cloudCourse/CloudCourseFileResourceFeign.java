package com.yice.edu.cn.osp.feignClient.jy.cloudClassroom.cloudCourse;


import com.yice.edu.cn.common.pojo.cc.cloudCourse.CloudCourse;
import com.yice.edu.cn.common.pojo.cc.cloudCourse.CloudCourseFileResource;
import com.yice.edu.cn.common.pojo.cc.cloudCourse.CloudCourseResource;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


@FeignClient(value="jy",contextId = "cloudCourseFileResourceFeign",path = "/cloudCourseFileResource")
public interface CloudCourseFileResourceFeign {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findCloudCourseFileResourceById/{id}")
    CloudCourseFileResource findCloudCourseFileResourceById(@PathVariable("id") String id);
    @PostMapping("/saveCloudCourseFileResource")
    CloudCourseFileResource saveCloudCourseFileResource(CloudCourseFileResource CloudCourseFileResource);
    @PostMapping("/batchSaveCloudCourseFileResource")
    void batchSaveCloudCourseFileResource(List<CloudCourseFileResource> CloudCourseFileResources);
    @PostMapping("/findCloudCourseFileResourceListByCondition")
    List<CloudCourseFileResource> findCloudCourseFileResourceListByCondition(CloudCourseFileResource CloudCourseFileResource);
    @PostMapping("/findOneCloudCourseFileResourceByCondition")
    CloudCourseFileResource findOneCloudCourseFileResourceByCondition(CloudCourseFileResource CloudCourseFileResource);
    @PostMapping("/findCloudCourseFileResourceCountByCondition")
    long findCloudCourseFileResourceCountByCondition(CloudCourseFileResource CloudCourseFileResource);
    @PostMapping("/updateCloudCourseFileResource")
    void updateCloudCourseFileResource(CloudCourseFileResource CloudCourseFileResource);
    @PostMapping("/updateCloudCourseFileResourceForNotNull")
    void updateCloudCourseFileResourceForAll(CloudCourseFileResource CloudCourseFileResource);
    @GetMapping("/deleteCloudCourseFileResource/{id}")
    void deleteCloudCourseFileResource(@PathVariable("id") String id);
    @PostMapping("/deleteCloudCourseFileResourceByCondition")
    void deleteCloudCourseFileResourceByCondition(CloudCourseFileResource CloudCourseFileResource);
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
   @GetMapping("/findCloudCourseFileResourceByCloudCourseId/{id}")
   CloudCourseResource findCloudCourseFileResourceByCloudCourseId(@PathVariable("id") String id);

    @PostMapping("/deleteCloudCourseFileResourceByList")
    void deleteCloudCourseFileResourceByList(@RequestBody List<String> ids);
}
