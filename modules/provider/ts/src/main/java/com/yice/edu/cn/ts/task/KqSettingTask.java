package com.yice.edu.cn.ts.task;

import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CreateCache;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.dm.kq.EccStudentKqSetting;
import com.yice.edu.cn.ts.feignClient.EccStudentKqSettingFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@EnableAsync
public class KqSettingTask {

    @Autowired
    private EccStudentKqSettingFeign eccStudentKqSettingFeign;

    @CreateCache(name= Constant.Redis.ECC_CHECKIN_SETTING2,timeUnit= TimeUnit.DAYS,expire=1)
    private Cache<String, EccStudentKqSetting> settingCache;


    /**
     * 每天凌晨30分刷新考勤设置
     */
    @Scheduled(cron = "0 30 0 * * ?")
    @Async
    public void refreshKqSetting(){
        try {
            List<EccStudentKqSetting> settingList = eccStudentKqSettingFeign.findEccStudentKqSettingList();
            settingList.forEach(sl->{
                settingCache.put(sl.getSchoolId(),sl);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
