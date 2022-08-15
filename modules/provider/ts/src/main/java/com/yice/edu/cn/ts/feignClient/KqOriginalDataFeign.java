package com.yice.edu.cn.ts.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value="xw",contextId = "kqOriginalDataFeign",path = "/kqOriginalData")
public interface KqOriginalDataFeign {
    @GetMapping("/xwStuKqDailyStatis")
    void xwStuKqDailyStatis();
    @GetMapping("/changeStuNowStatus")
    void changeStuNowStatus();
    @GetMapping("/deleteThreeMonthsAgoAllData")
    void deleteThreeMonthsAgoAllData();
}
