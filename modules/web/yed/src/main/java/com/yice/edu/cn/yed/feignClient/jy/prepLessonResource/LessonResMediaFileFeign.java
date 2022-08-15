package com.yice.edu.cn.yed.feignClient.jy.prepLessonResource;

import com.yice.edu.cn.common.pojo.jy.prepLessonResource.LessonResMediaFile;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jy",contextId = "lessonResMediaFileFeign",path = "/lessonResMediaFile")
public interface LessonResMediaFileFeign {
    @GetMapping("/findLessonResMediaFileById/{id}")
    LessonResMediaFile findLessonResMediaFileById(@PathVariable("id") String id);
    @PostMapping("/saveLessonResMediaFile")
    LessonResMediaFile saveLessonResMediaFile(LessonResMediaFile lessonResMediaFile);
    @PostMapping("/find/findLessonResMediaFileListByCondition")
    List<LessonResMediaFile> findLessonResMediaFileListByCondition(LessonResMediaFile lessonResMediaFile);
    @PostMapping("/find/findLessonResMediaFileListByCondition2")
    List<LessonResMediaFile> findLessonResMediaFileListByCondition2(LessonResMediaFile lessonResMediaFile);
    @PostMapping("/findOneLessonResMediaFileByCondition")
    LessonResMediaFile findOneLessonResMediaFileByCondition(LessonResMediaFile lessonResMediaFile);
    @PostMapping("/find/findLessonResMediaFileCountByCondition")
    long findLessonResMediaFileCountByCondition(LessonResMediaFile lessonResMediaFile);
    @PostMapping("/find/findLessonResMediaFileCountByCondition2")
    long findLessonResMediaFileCountByCondition2(LessonResMediaFile lessonResMediaFile);
    @PostMapping("/updateLessonResMediaFile")
    void updateLessonResMediaFile(LessonResMediaFile lessonResMediaFile);
    @GetMapping("/deleteLessonResMediaFile/{id}")
    void deleteLessonResMediaFile(@PathVariable("id") String id);
    @PostMapping("/deleteLessonResMediaFileByCondition")
    void deleteLessonResMediaFileByCondition(LessonResMediaFile lessonResMediaFile);
    //下载次数+1
    @GetMapping("/update/downloadCountChange/{id}")
    void downloadCountChange(@PathVariable("id") String id);
   //查看次数+1
    @GetMapping("/update/numLookChange/{id}")
    void numLookChange(@PathVariable("id") String id);
}
