package com.yice.edu.cn.ts.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value="jw",contextId = "stuEvaluateFeign",path = "/stuEvaluate")
public interface StuEvaluateFeign {
    @GetMapping("/StuEvaluatePushByTime")
    void pushByTime();
}
