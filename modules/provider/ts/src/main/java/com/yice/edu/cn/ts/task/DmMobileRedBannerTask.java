package com.yice.edu.cn.ts.task;

import com.netflix.hystrix.exception.HystrixRuntimeException;
import com.yice.edu.cn.common.pojo.dm.dmMobileRedBanner.DmMobileRedBanner;
import com.yice.edu.cn.ts.feignClient.DmMobileRedBannerFeign;
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
 * @Author: yb
 * @CreateTime: 2019-06-24 15:05
 * @Description: 云班牌流动红旗，每天凌晨1点更新最新流动红旗
 */
@Component
@EnableAsync
public class DmMobileRedBannerTask {
    @Autowired
    private DmMobileRedBannerFeign dmMobileRedBannerFeign;

    private final static Logger logger = LoggerFactory.getLogger(DmMobileRedBannerTask.class);

    /**
     * 1.查询出今天待颁发状态的流动红旗；2.更改红旗状态
     */
    @Scheduled(cron = "0 0 1 * * ?")//每天凌晨01:00
    //@Scheduled(cron = "0 0/1 * * * ?")
    @Async
    public void updateAwardRedBanner() {
        try {
            DmMobileRedBanner todayAwardRedBanner = dmMobileRedBannerFeign.findTodayAwardRedBanner();
            if(todayAwardRedBanner == null){
                return;
            }
            //将之前的流动红旗状态改为已结束
            dmMobileRedBannerFeign.updateStatus();
            //将待颁发红旗状态改为已生效
            todayAwardRedBanner.setStatus(1);
            dmMobileRedBannerFeign.updateStatusShowById(todayAwardRedBanner.getId());

        } catch (HystrixRuntimeException hystrixRuntimeException) {
            // 调用为服务可能失败，在此记录一下日志
            logger.error("微服务调用失败", hystrixRuntimeException);
        } catch (Exception ex) {
            logger.error("服务器错误", ex);
        }
    }
}
