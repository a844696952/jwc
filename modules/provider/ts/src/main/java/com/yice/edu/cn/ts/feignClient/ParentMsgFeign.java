package com.yice.edu.cn.ts.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value="dm",contextId = "parentMsgFeign",path = "/parentmsg")
public interface ParentMsgFeign {
    @PostMapping("/deleteParentMsgTwoDayBefore")
    public void deleteParentMsgTwoDayBefore();
}
