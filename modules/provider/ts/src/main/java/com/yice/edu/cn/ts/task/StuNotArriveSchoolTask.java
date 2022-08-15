package com.yice.edu.cn.ts.task;

import com.netflix.hystrix.exception.HystrixRuntimeException;
import com.yice.edu.cn.ts.feignClient.KqOriginalDataFeign;
import com.yice.edu.cn.ts.feignClient.StuInAndOutStartTimeFeign;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 学生未到校统计
 */
@Component
@EnableAsync
public class StuNotArriveSchoolTask {

    @Autowired
    private StuInAndOutStartTimeFeign stuInAndOutStartTimeFeign;

    private final static Logger logger = LoggerFactory.getLogger(StuNotArriveSchoolTask.class);
    //@Scheduled(cron = "0 50 15 * * ?")//每天凌晨01:00
    @Scheduled(cron = "0 0 23 * * ?")//每天晚上23:00
    //@Scheduled(cron = "0 0/3 * * * ?")
    @Async
    public void stuNotArriveSchool() {
        try {
            logger.info("学生未到校统计");
            stuInAndOutStartTimeFeign.stuNotArriveSchool();
        } catch (HystrixRuntimeException hystrixRuntimeException) {
            // 调用为服务可能失败，在此记录一下日志
            logger.error("微服务调用失败", hystrixRuntimeException);
        } catch (Exception ex) {
            logger.error("服务器错误", ex);
        }
    }
}
