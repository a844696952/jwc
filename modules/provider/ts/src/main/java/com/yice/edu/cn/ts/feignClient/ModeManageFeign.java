package com.yice.edu.cn.ts.feignClient;


import com.yice.edu.cn.common.pojo.ResponseJson;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value="dm",contextId = "modeManageFeign",path = "/modeTask")
public interface ModeManageFeign {

    @GetMapping("/modeTaskSchedule")
     ResponseJson modeTaskSchedule();

    @GetMapping("/classModeTaskSchedule")
    ResponseJson classModeTaskSchedule();
}
