package com.yice.edu.cn.ts.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value="jy",contextId = "homeworkFeign",path = "/homework")
public interface HomeworkFeign {
    @GetMapping("/scheduleEndTime/{interval}")
    void scheduleEndTime(@PathVariable("interval") int interval);
}
