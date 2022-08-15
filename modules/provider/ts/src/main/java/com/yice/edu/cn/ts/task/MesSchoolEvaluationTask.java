package com.yice.edu.cn.ts.task;

import com.yice.edu.cn.ts.feignClient.MesSchoolEvaluationFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 电子班牌任务模式
 */
@Component
@EnableAsync
public class MesSchoolEvaluationTask {

    @Autowired
    private MesSchoolEvaluationFeign mesSchoolEvaluationFeign;

    /**
     * 保存德育日评比
     */
    @Scheduled(cron = "0 1 0 * * ? ")
    @Async
    public void saveMesSchoolEvaluation(){
        System.out.println("保存德育日评比"+new Date());
        mesSchoolEvaluationFeign.saveMesSchoolEvaluation();
    }

    /**
     * 保存德育评比荣誉
     */
    @Scheduled(cron = "0 30 0 * * ? ")
    @Async
    public void saveMesSchoolEvaluationHonor(){
        System.out.println("保存德育评比荣誉"+new Date());
        mesSchoolEvaluationFeign.saveMesSchoolEvaluationHonor();
    }

}
