package com.yice.edu.cn.ts.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value="xw",contextId = "dormPersonOutFeign",path = "/dormPersonOut")
public interface DormPersonOutFeign {

    @GetMapping("/deleteDormPersonOutForStudentByTime")
    void deleteDormPersonOutForStudentByTime();

    @GetMapping("/deleteDormPersonLogForStudentByTime")
    void deleteDormPersonLogForStudentByTime();
}
