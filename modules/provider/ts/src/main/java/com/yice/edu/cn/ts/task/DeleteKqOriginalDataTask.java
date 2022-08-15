package com.yice.edu.cn.ts.task;

import com.netflix.hystrix.exception.HystrixRuntimeException;
import com.yice.edu.cn.ts.feignClient.KqOriginalDataFeign;
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
public class DeleteKqOriginalDataTask {

    @Autowired
    private KqOriginalDataFeign kqOriginalDataFeign;

    private final static Logger logger = LoggerFactory.getLogger(DeleteKqOriginalDataTask.class);

    @Scheduled(cron = "0 42 3 1 * ?")//每月1号凌晨03:42
    //@Scheduled(cron = "0 0/1 * * * ?")
    @Async
    public void createWorkerKqDailyRecord() {
        try {
            kqOriginalDataFeign.deleteThreeMonthsAgoAllData();
        } catch (HystrixRuntimeException hystrixRuntimeException) {
            // 调用为服务可能失败，在此记录一下日志
            logger.error("微服务调用失败", hystrixRuntimeException);
        } catch (Exception ex) {
            logger.error("服务器错误", ex);
        }
    }
}
