package com.yice.edu.cn.ts.task;

import com.netflix.hystrix.exception.HystrixRuntimeException;
import com.yice.edu.cn.ts.feignClient.DormPersonOutFeign;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


/**
 * 对退宿人员记录进项清理
 */
@Component
@EnableAsync
public class DormPersonOutTask {

    @Autowired
    private DormPersonOutFeign dormPersonOutFeign;

    private final static Logger logger = LoggerFactory.getLogger(DormPersonOutTask.class);

    @Scheduled(cron = "0 0 0 1 10 * ")//每年10月1日凌晨00:00
    @Async
    public void updateAwardRedBanner() {
        try {
            dormPersonOutFeign.deleteDormPersonOutForStudentByTime();
            dormPersonOutFeign.deleteDormPersonLogForStudentByTime();
        } catch (HystrixRuntimeException hystrixRuntimeException) {
            // 调用为服务可能失败，在此记录一下日志
            logger.error("微服务调用失败", hystrixRuntimeException);
        } catch (Exception ex) {
            logger.error("服务器错误", ex);
        }
    }
}
