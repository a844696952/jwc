package com.yice.edu.cn.ts.task;

import com.yice.edu.cn.ts.feignClient.ParentMsgFeign;
import com.yice.edu.cn.ts.feignClient.SturespmsgFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class SturespmsgTask {
    @Autowired
    private SturespmsgFeign sturespmsgFeign;

    @Scheduled(cron = "0 2 0 * * ?")
    @Async
    public void deleteSturespmsgBeforeTwoDay(){
        sturespmsgFeign.deleteSturespmsgBeforeTwoDay();
    }
}
