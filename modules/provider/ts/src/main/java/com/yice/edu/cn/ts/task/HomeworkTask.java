package com.yice.edu.cn.ts.task;

import com.yice.edu.cn.ts.feignClient.HomeworkFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableAsync
public class HomeworkTask {
    @Value("${server.port}")
    private String port;
    @Autowired
    private HomeworkFeign homeworkFeign;

    @Scheduled(cron = "0 0/5 * * * ?")
    @Async
    public void homeworkComplete(){
        try {
            homeworkFeign.scheduleEndTime(5);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
