package com.yice.edu.cn.osp.feignClient.jy.homework;

import com.yice.edu.cn.common.pojo.jy.wisdomclassroom.ClassTest;
import com.yice.edu.cn.common.pojo.jy.wisdomclassroom.TopicDetail;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


/**
 * 课堂检测
 */
@FeignClient(value="jy",contextId = "classTestFeign",path = "/classTest")
public interface ClassTestFeign {

    @GetMapping("/findClassTestById/{id}")
    ClassTest findClassTestById(@PathVariable("id") String id);

    @PostMapping("/saveClassTest")
    Boolean saveClassTest(@RequestBody ClassTest classTest);

    @PostMapping("/findTopDetailListByCondition")
    List<TopicDetail> findTopDetailListByCondition(@RequestBody TopicDetail topicDetail);

    @PostMapping("/findTopDetailCountByCondition")
    long findTopDetailCountByCondition(@RequestBody TopicDetail topicDetail);

    @PostMapping("/findClassTestListByCondition")
    List<ClassTest> findClassTestListByCondition(@RequestBody ClassTest classTest);

    @PostMapping("/findListByCondition")
    List<ClassTest>  findListByCondition(@RequestBody ClassTest classTest);


    @PostMapping("/findClassTestCountByCondition")
    Long findClassTestCountByCondition(@RequestBody ClassTest classTest);

    @PostMapping("/deleteClassTest")
    void deleteClassTest(List<String> classId);
}
