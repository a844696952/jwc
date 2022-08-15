package com.yice.edu.cn.ts.task;

import com.yice.edu.cn.ts.feignClient.ModeManageFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 电子班牌任务模式
 */
@Component
@EnableAsync
public class ModeTask {

    @Autowired
    private ModeManageFeign modeManageFeign;

    /**
     * 模式任务
     */
    @Scheduled(cron = "0 0/1 * * * ? ")
    @Async
    public void modeChangeTask(){
        modeManageFeign.modeTaskSchedule();
        modeManageFeign.classModeTaskSchedule();
    }







}
