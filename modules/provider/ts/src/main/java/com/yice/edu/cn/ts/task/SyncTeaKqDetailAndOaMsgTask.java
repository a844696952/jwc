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
 * @Description: 教职工考勤同步OA
 */
@Component
@EnableAsync
public class SyncTeaKqDetailAndOaMsgTask {

    @Autowired
    KqWorkerDailyFeign kqWorkerDailyFeign;



    private final static Logger logger = LoggerFactory.getLogger(SyncTeaKqDetailAndOaMsgTask.class);


    @Scheduled(cron = "0 0/5 * * * ? ")//每五分钟执行一次同步
    @Async
    public void createWorkerKqDailyRecord() {
        try {
            logger.info("调用OA同步职工考勤");
            kqWorkerDailyFeign.syncStatusDetailAndOaMsg();
        } catch (HystrixRuntimeException hystrixRuntimeException) {
            // 调用为服务可能失败，在此记录一下日志
            logger.error("微服务调用失败", hystrixRuntimeException);
        } catch (Exception ex) {
            logger.error("服务器错误", ex);
        }
    }
}
