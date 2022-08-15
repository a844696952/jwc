package com.yice.edu.cn.ts.task;

import com.netflix.hystrix.exception.HystrixRuntimeException;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.dm.dmMobileRedBanner.DmCountDownManage;
import com.yice.edu.cn.ts.feignClient.DmCountDownManageFeign;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * 倒计时对终止日期的刷新
 *
 * @Author: keyusong
 * @Date: 2019/6/29 17:38
 */
@Component
@EnableAsync
public class DmCountDownManageTask {

    @Autowired
    private DmCountDownManageFeign dmCountDownManageFeign;

    private final static Logger logger = LoggerFactory.getLogger(DmCountDownManageTask.class);

    @Scheduled(cron = "0 0 1 * * ?")//每天凌晨01:00
    @Async
    public void updateAwardRedCountDown() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long currentTime = System.currentTimeMillis();
        try {
            DmCountDownManage dmCountDownManage = new DmCountDownManage();
            dmCountDownManage.setStatus(Constant.DM_COUNT_DOWN_MANAGE.MANAGE_VALID);
            List<DmCountDownManage> dmList = dmCountDownManageFeign.findDmCountDownManageListByCondition(dmCountDownManage);
            //防止空指针异常
            if (dmList != null && !dmList.isEmpty()) {
                //过滤出正在展示，日期过期的数据
                dmList.stream().filter(skt -> {
                    try {
                        if (skt.getStatus() == Constant.DM_COUNT_DOWN_MANAGE.MANAGE_VALID && df.parse(skt.getBreakTime()).getTime() < currentTime) {
                            return true;
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return false;
                }).forEach(skt -> {
                    //更改状态为已结束
                    skt.setStatus(Constant.DM_COUNT_DOWN_MANAGE.MANAGE_DISABLE);
                    dmCountDownManageFeign.updateDmCountDownManage(skt);
                });
            }
        } catch (HystrixRuntimeException hystrixRuntimeException) {
            // 调用为服务可能失败，在此记录一下日志
            logger.error("微服务调用失败", hystrixRuntimeException);
        } catch (Exception ex) {
            logger.error("服务器错误", ex);
        }
    }
}
