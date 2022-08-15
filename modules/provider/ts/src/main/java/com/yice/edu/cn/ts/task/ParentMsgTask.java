package com.yice.edu.cn.ts.task;

import com.yice.edu.cn.ts.feignClient.ParentMsgFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class ParentMsgTask {
    @Autowired
    private ParentMsgFeign parentMsgFeign;
    @Scheduled(cron = "0 2 0 * * ?")
    @Async
    public void deleteParentMsgTwoDayBefore(){
        parentMsgFeign.deleteParentMsgTwoDayBefore();
    }
}
