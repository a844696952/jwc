package com.yice.edu.cn.ts.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value="dm",contextId = "sturespmsgFeign",path = "/sturespmsg")
public interface SturespmsgFeign {
    @PostMapping("/deleteSturespmsgBeforeTwoDay")
    public void deleteSturespmsgBeforeTwoDay();
}
