package com.yice.edu.cn.osp.feignClient.jy.resourceCenter;

import com.yice.edu.cn.common.pojo.jy.resourceCenter.ResourceCenter;
import com.yice.edu.cn.common.pojo.jy.resourceCenter.TeacherCourse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jy",contextId = "resourceCenterFeign",path = "/resourceCenter")
public interface ResourceCenterFeign {
    @GetMapping("/findResourceCenterById/{id}")
    ResourceCenter findResourceCenterById(@PathVariable("id") String id);
    @PostMapping("/saveResourceCenter")
    ResourceCenter saveResourceCenter(ResourceCenter resourceCenter);
    @PostMapping("/findResourceCenterListByCondition")
    List<ResourceCenter> findResourceCenterListByCondition(ResourceCenter resourceCenter);
    @PostMapping("/findOneResourceCenterByCondition")
    ResourceCenter findOneResourceCenterByCondition(ResourceCenter resourceCenter);
    @PostMapping("/findResourceCenterCountByCondition")
    long findResourceCenterCountByCondition(ResourceCenter resourceCenter);
    @PostMapping("/updateResourceCenter")
    void updateResourceCenter(ResourceCenter resourceCenter);
    @GetMapping("/deleteResourceCenter/{id}")
    void deleteResourceCenter(@PathVariable("id") String id);
    @PostMapping("/deleteResourceCenterByCondition")
    void deleteResourceCenterByCondition(ResourceCenter resourceCenter);
    @PostMapping("/findTeacherCourseListBySchoolId")
    List<TeacherCourse> findTeacherCourseListBySchoolId(TeacherCourse teacherCourse);
    @PostMapping("/findTeacherCourseCountBySchoolId")
    long findTeacherCourseCountBySchoolId(TeacherCourse teacherCourse);
    @PostMapping("/findResourceCentersForH5ByCondition")
    List<ResourceCenter> findResourceCentersForH5ByCondition(ResourceCenter resourceCenter);
    @PostMapping("/findResourceCenterCountForH5ByCondition")
    long findResourceCenterCountForH5ByCondition(ResourceCenter resourceCenter);
}
