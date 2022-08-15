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

@Component
@EnableAsync
public class ReCalculateKqWorkerDailyTask {

    @Autowired
    KqWorkerDailyFeign kqWorkerDailyFeign;



    private final static Logger logger = LoggerFactory.getLogger(ReCalculateKqWorkerDailyTask.class);

    @Scheduled(cron = "0 02 23 * * ? ")//recalculate every day at 23 clock
    //@Scheduled(cron = "0 0/3 * * * ? ")//每五分钟执行一次同步
    @Async
    public void reCalculateKqWorkerDaily() {
        try {
            logger.info("调用断网重连日职工考勤统计任务");
            kqWorkerDailyFeign.reCalculateKqWorkerDaily();
        } catch (HystrixRuntimeException hystrixRuntimeException) {
            // 调用为服务可能失败，在此记录一下日志
            logger.error("微服务调用失败", hystrixRuntimeException);
        } catch (Exception ex) {
            logger.error("服务器错误", ex);
        }
    }
}
