package com.yice.edu.cn.osp.service.dm.kq;

import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CreateCache;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.dm.kq.EccStudentKqSetting;
import com.yice.edu.cn.osp.feignClient.dm.kq.EccStudentKqSettingFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

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

    public EccStudentKqSetting cacheKqSetting(String mySchoolId) {
        return settingCache.get(mySchoolId);
    }

    public void refresh(EccStudentKqSetting setting) {
        settingCache.put(mySchoolId(),setting);
    }
}
