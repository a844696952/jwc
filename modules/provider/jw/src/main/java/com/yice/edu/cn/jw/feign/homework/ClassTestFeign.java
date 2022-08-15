package com.yice.edu.cn.jw.feign.homework;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;


/**
 * 课堂检测
 */
@FeignClient(value="jy",contextId = "classTestFeign",path = "/classTest")
public interface ClassTestFeign {
    @PostMapping("/deleteClassTest")
    void deleteClassTest(List<String> classId);
}
