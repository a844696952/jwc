package com.yice.edu.cn.osp.feignClient.jw.practice;

import com.yice.edu.cn.common.pojo.jw.practice.Practice;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jw",contextId = "practiceFeign",path = "/practice")
public interface PracticeFeign {
    @GetMapping("/findPracticeById/{id}")
    Practice findPracticeById(@PathVariable("id") String id);
    @PostMapping("/savePractice")
    Practice savePractice(Practice practice);
    @PostMapping("/findPracticeListByCondition")
    List<Practice> findPracticeListByCondition(Practice practice);
    @PostMapping("/findPracticeListByCondition1")
    List<Practice> findPracticeListByCondition1(Practice practice);
    @PostMapping("/findPracticeListByTeacherId")
    List<Practice> findPracticeListByTeacherId(Practice practice);
    @PostMapping("/findOnePracticeByCondition")
    Practice findOnePracticeByCondition(Practice practice);
    @PostMapping("/findPracticeCountByCondition")
    long findPracticeCountByCondition(Practice practice);
    @PostMapping("/findPracticeCountByCondition1")
    long findPracticeCountByCondition1(Practice practice);
    @PostMapping("/findPracticeCountByTeacherId")
    long findPracticeCountByTeacherId(Practice practice);
    @PostMapping("/updatePractice")
    void updatePractice(Practice practice);
    @GetMapping("/deletePractice/{id}")
    void deletePractice(@PathVariable("id") String id);
    @PostMapping("/deletePracticeByCondition")
    void deletePracticeByCondition(Practice practice);
}
