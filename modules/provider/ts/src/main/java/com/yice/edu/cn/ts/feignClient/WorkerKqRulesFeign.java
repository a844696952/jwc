package com.yice.edu.cn.ts.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(value="xw",contextId = "workerKqRulesFeign",path = "/workerKqRules")
public interface WorkerKqRulesFeign {
    @PostMapping("/sendToKqManager")
    void sendToKqManager( @RequestBody String taskId );
}
