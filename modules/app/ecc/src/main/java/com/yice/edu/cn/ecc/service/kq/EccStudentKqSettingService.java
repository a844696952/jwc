package com.yice.edu.cn.ecc.service.kq;

import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CreateCache;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.dm.kq.EccStudentKqSetting;
import com.yice.edu.cn.ecc.feignClient.kq.EccStudentKqSettingFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class EccStudentKqSettingService {

    @Autowired
    private EccStudentKqSettingFeign eccStudentKqSettingFeign;

    @CreateCache(name= Constant.Redis.ECC_CHECKIN_SETTING2,timeUnit= TimeUnit.DAYS,expire=1)
    private Cache<String, EccStudentKqSetting> settingCache;


    public EccStudentKqSetting saveEccStudentKqSetting(EccStudentKqSetting setting) {
        return eccStudentKqSettingFeign.saveEccStudentKqSetting(setting);
    }

    public void updateEccStudentKqSetting(EccStudentKqSetting setting) {
        eccStudentKqSettingFeign.updateEccStudentKqSetting(setting);
    }

    public List<EccStudentKqSetting> findEccStudentKqSettingListByCondition(EccStudentKqSetting setting) {

        return eccStudentKqSettingFeign.findEccStudentKqSettingListByCondition(setting);

    }

    public EccStudentKqSetting currentKqSetting(EccStudentKqSetting setting) {
        return eccStudentKqSettingFeign.currentKqSetting(setting);
    }
}
