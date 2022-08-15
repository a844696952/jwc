package com.yice.edu.cn.ts.task;

import com.netflix.hystrix.exception.HystrixRuntimeException;
import com.yice.edu.cn.ts.feignClient.KqWorkerDailyFeign;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @BelongsProject: yep
 * @BelongsPackage: com.yice.edu.cn.ts.task
 * @Author: Administrator
 * @CreateTime: 2019-03-08 15:05
 * @Description: 创建当天教职工考勤日统计原始数据
 */
@Component
@EnableAsync
public class CreateWorkerKqDailyRecordTask {

    @Autowired
    KqWorkerDailyFeign kqWorkerDailyFeign;



    private final static Logger logger = LoggerFactory.getLogger(CreateWorkerKqDailyRecordTask.class);

    @Scheduled(cron = "0 1 3 * * ?")//每天凌晨03:01
    //@Scheduled(cron = "0 0/1 * * * ?")
    @Async
    public void createWorkerKqDailyRecord() {
        try {
            logger.info("开始创建职工考勤每日汇总记录");
            kqWorkerDailyFeign.createWorkerKqDailyRecord();
        } catch (HystrixRuntimeException hystrixRuntimeException) {
            // 调用为服务可能失败，在此记录一下日志
            logger.error("微服务调用失败", hystrixRuntimeException);
        } catch (Exception ex) {
            logger.error("服务器错误", ex);
        }
    }
}
