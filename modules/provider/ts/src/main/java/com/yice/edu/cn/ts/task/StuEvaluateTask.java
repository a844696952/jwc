package com.yice.edu.cn.ts.task;

import com.yice.edu.cn.ts.feignClient.StuEvaluateFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableAsync
public class StuEvaluateTask {
    @Value("${server.port}")
    private String port;
    @Autowired
    private StuEvaluateFeign stuEvaluateFeign;
   @Scheduled(cron = "0 0 9,12,15,18 * * ?")
//    @Scheduled(cron = "0 * 16 * * ?")
    @Async
    public void homeworkComplete(){
        try {
            stuEvaluateFeign.pushByTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
