package com.yice.edu.cn.osp.feignClient.jw.practice;

import com.yice.edu.cn.common.pojo.jw.practice.PracticeFile;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jw",contextId = "practiceFileFeign",path = "/practiceFile")
public interface PracticeFileFeign {
    @GetMapping("/findPracticeFileById/{id}")
    PracticeFile findPracticeFileById(@PathVariable("id") String id);
    @PostMapping("/savePracticeFile")
    PracticeFile savePracticeFile(PracticeFile practiceFile);
    @PostMapping("/findPracticeFileListByCondition")
    List<PracticeFile> findPracticeFileListByCondition(PracticeFile practiceFile);
    @PostMapping("/findOnePracticeFileByCondition")
    PracticeFile findOnePracticeFileByCondition(PracticeFile practiceFile);
    @PostMapping("/findPracticeFileCountByCondition")
    long findPracticeFileCountByCondition(PracticeFile practiceFile);
    @PostMapping("/updatePracticeFile")
    void updatePracticeFile(PracticeFile practiceFile);
    @GetMapping("/deletePracticeFile/{id}")
    void deletePracticeFile(@PathVariable("id") String id);
    @PostMapping("/deletePracticeFileByCondition")
    void deletePracticeFileByCondition(PracticeFile practiceFile);
    @GetMapping("/findPracticeFileListById/{id}")
    List<PracticeFile> findPracticeFileListById(@PathVariable("id") String id);
}
