package com.yice.edu.cn.ewb.feignClient.wisdomclassroom;

import com.yice.edu.cn.common.pojo.jy.wisdomclassroom.ClassTest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jy",contextId = "classTestFeign",path = "/classTest")
public interface ClassTestFeign {
    @GetMapping("/findClassTestById/{id}")
    ClassTest findClassTestById(@PathVariable("id") String id);
    @PostMapping("/saveClassTest")
    boolean saveClassTest(ClassTest classTest);
    @PostMapping("/findClassTestListByCondition")
    List<ClassTest> findClassTestByCondition(ClassTest classTest);
}
