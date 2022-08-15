package com.yice.edu.cn.ts.task;

import com.yice.edu.cn.ts.feignClient.QusSurveyFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableAsync
public class QusSurveyTask {
    @Value("${server.port}")
    private String port;
    @Autowired
    private QusSurveyFeign qusSurveyFeign;
    @Scheduled(cron = "0 0 9,12,15,18 * * ?")
//    @Scheduled(cron = "0 * 16 * * ?")
    @Async
    public void homeworkComplete(){
        try {
            qusSurveyFeign.pushToTeacherByEndTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
