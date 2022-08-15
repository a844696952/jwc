package com.yice.edu.cn.xw.feignClient.resetDynamicTask;

import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "ts",path = "/resetDynamicTask")
public interface ResetDynamicTaskFeign {
    @PostMapping("/reset")
    void resetDynamicTask();
}
