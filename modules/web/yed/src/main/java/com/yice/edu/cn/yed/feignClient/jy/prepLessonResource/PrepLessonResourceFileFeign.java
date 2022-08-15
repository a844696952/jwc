package com.yice.edu.cn.yed.feignClient.jy.prepLessonResource;

import com.yice.edu.cn.common.pojo.jy.prepLessonResource.PrepLessonResourceFile;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jy",contextId = "prepLessonResourceFileFeign",path = "/prepLessonResourceFile")
public interface PrepLessonResourceFileFeign {
    @GetMapping("/findPrepLessonResourceFileById/{id}")
    PrepLessonResourceFile findPrepLessonResourceFileById(@PathVariable("id") String id);
    @PostMapping("/savePrepLessonResourceFile")
    PrepLessonResourceFile savePrepLessonResourceFile(PrepLessonResourceFile prepLessonResourceFile);
    @PostMapping("/find/findPrepLessonResourceFileListByCondition")
    List<PrepLessonResourceFile> findPrepLessonResourceFileListByCondition(PrepLessonResourceFile prepLessonResourceFile);
    @PostMapping("/find/findPrepLessonResourceFileListByCondition2")
    List<PrepLessonResourceFile> findPrepLessonResourceFileListByCondition2(PrepLessonResourceFile prepLessonResourceFile);
    @PostMapping("/findOnePrepLessonResourceFileByCondition")
    PrepLessonResourceFile findOnePrepLessonResourceFileByCondition(PrepLessonResourceFile prepLessonResourceFile);
    @PostMapping("/findPrepLessonResourceFileCountByCondition")
    long findPrepLessonResourceFileCountByCondition(PrepLessonResourceFile prepLessonResourceFile);
    @PostMapping("/findPrepLessonResourceFileCountByCondition2")
    long findPrepLessonResourceFileCountByCondition2(PrepLessonResourceFile prepLessonResourceFile);
    @PostMapping("/updatePrepLessonResourceFile")
    void updatePrepLessonResourceFile(PrepLessonResourceFile prepLessonResourceFile);
    @GetMapping("/deletePrepLessonResourceFile/{id}")
    void deletePrepLessonResourceFile(@PathVariable("id") String id);
    @PostMapping("/deletePrepLessonResourceFileByCondition")
    void deletePrepLessonResourceFileByCondition(PrepLessonResourceFile prepLessonResourceFile);

    @GetMapping("/update/downloadCountChange/{id}")
    void downloadCountChange(@PathVariable("id") String id);


    @GetMapping("/update/numLookChange/{id}")
    void numLookChange(@PathVariable("id") String id);



    //通过章节id查
    @PostMapping("/find/findMatFilesCountByMatItemid")
    long findMatFilesCountByMatItemid(PrepLessonResourceFile prepLessonResourceFile);
    @PostMapping("/find/findMatFileListByMatItemid")
    List<PrepLessonResourceFile> findMatFileListByMatItemid(PrepLessonResourceFile prepLessonResourceFile);
    //通过文件名或文件类型模糊查
    @PostMapping("/find/findMatFilesCountByCondition3")
    long findMatFilesCountByCondition3(PrepLessonResourceFile prepLessonResourceFile);
    @PostMapping("/find/findMatFileListByCondition3")
    List<PrepLessonResourceFile> findMatFileListByCondition3(PrepLessonResourceFile prepLessonResourceFile);

}
