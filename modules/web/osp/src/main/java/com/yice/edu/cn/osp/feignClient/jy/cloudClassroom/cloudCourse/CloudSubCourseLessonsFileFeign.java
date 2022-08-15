package com.yice.edu.cn.osp.feignClient.jy.cloudClassroom.cloudCourse;

import com.yice.edu.cn.common.pojo.cc.cloudCourse.CloudCoursewareVo;
import com.yice.edu.cn.common.pojo.cc.cloudCourse.CloudSubCourse;
import com.yice.edu.cn.common.pojo.cc.cloudCourse.CloudSubCourseLessonsFile;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value="jy",contextId = "cloudSubCourseLessonsFileFeign",path = "/cloudSubCourseLessonsFile")
public interface CloudSubCourseLessonsFileFeign {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findCloudSubCourseLessonsFileById/{id}")
    CloudSubCourseLessonsFile findCloudSubCourseLessonsFileById(@PathVariable("id") String id);
    @PostMapping("/saveCloudSubCourseLessonsFile")
    CloudSubCourseLessonsFile saveCloudSubCourseLessonsFile(CloudSubCourseLessonsFile cloudSubCourseLessonsFile);
    @PostMapping("/batchSaveCloudSubCourseLessonsFile")
    void batchSaveCloudSubCourseLessonsFile(List<CloudSubCourseLessonsFile> cloudSubCourseLessonsFiles);
    @PostMapping("/findCloudSubCourseLessonsFileListByCondition")
    List<CloudSubCourseLessonsFile> findCloudSubCourseLessonsFileListByCondition(CloudSubCourseLessonsFile cloudSubCourseLessonsFile);
    @PostMapping("/findOneCloudSubCourseLessonsFileByCondition")
    CloudSubCourseLessonsFile findOneCloudSubCourseLessonsFileByCondition(CloudSubCourseLessonsFile cloudSubCourseLessonsFile);
    @PostMapping("/findCloudSubCourseLessonsFileCountByCondition")
    long findCloudSubCourseLessonsFileCountByCondition(CloudSubCourseLessonsFile cloudSubCourseLessonsFile);
    @PostMapping("/updateCloudSubCourseLessonsFile")
    void updateCloudSubCourseLessonsFile(CloudSubCourseLessonsFile cloudSubCourseLessonsFile);
    @PostMapping("/updateCloudSubCourseLessonsFileForNotNull")
    void updateCloudSubCourseLessonsFileForAll(CloudSubCourseLessonsFile cloudSubCourseLessonsFile);
    @GetMapping("/deleteCloudSubCourseLessonsFile/{id}")
    void deleteCloudSubCourseLessonsFile(@PathVariable("id") String id);
    @PostMapping("/deleteCloudSubCourseLessonsFileByCondition")
    void deleteCloudSubCourseLessonsFileByCondition(CloudSubCourseLessonsFile cloudSubCourseLessonsFile);
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    @GetMapping("/findCloudSubCourseListByCloudCourseId/{id}")
    List<CloudSubCourse> findCloudSubCourseListByCloudCourseId(@PathVariable("id") String id);
    @PostMapping("/unifiedLessons")
    void unifiedLessons(@RequestBody List<CloudSubCourseLessonsFile>lessonsFileList);
    @PostMapping("/saveCoursewareListByCondition")
    void saveCoursewareListByCondition(@RequestBody CloudCoursewareVo cloudCoursewareVo);
}
