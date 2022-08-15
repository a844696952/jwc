package com.yice.edu.cn.ts.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value="jw",contextId = "qusSurveyFeign",path = "/qusSurvey")
public interface QusSurveyFeign {
    @GetMapping("/pushToTeacherByEndTime")
    void pushToTeacherByEndTime();
}
