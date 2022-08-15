package com.yice.edu.cn.ts.task;

import cn.hutool.core.date.DateUtil;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import com.yice.edu.cn.common.pojo.jw.school.School;
import com.yice.edu.cn.common.pojo.xw.kq.KqDailyStatistical;
import com.yice.edu.cn.common.pojo.xw.kq.KqOriginalData;
import com.yice.edu.cn.common.pojo.xw.kq.KqSchoolInit;
import com.yice.edu.cn.ts.feignClient.KqOriginalDataFeign;
import com.yice.edu.cn.ts.feignClient.KqSchoolInitFeign;
import com.yice.edu.cn.ts.feignClient.SchoolFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @BelongsProject: yep
 * @BelongsPackage: com.yice.edu.cn.ts.task
 * @Author: Administrator
 * @CreateTime: 2019-03-08 15:05
 * @Description: 校务学生考勤日统计
 */
@Component
@EnableAsync
public class StuKqDailyStatisTask {
    @Value("${server.port}")
    private String port;
    @Autowired
    private KqOriginalDataFeign kqOriginalDataFeign;

    private final static Logger logger = LoggerFactory.getLogger(StuKqDailyStatisTask.class);

    @Scheduled(cron = "0 0 1 * * ?")//每天凌晨01:00
    //@Scheduled(cron = "0 0/1 * * * ?")
    @Async
    public void dailyStatis() {
        try {
            kqOriginalDataFeign.xwStuKqDailyStatis();
        } catch (HystrixRuntimeException hystrixRuntimeException) {
            // 调用为服务可能失败，在此记录一下日志
            logger.error("微服务调用失败", hystrixRuntimeException);
        } catch (Exception ex) {
            logger.error("服务器错误", ex);
        }
    }
}
