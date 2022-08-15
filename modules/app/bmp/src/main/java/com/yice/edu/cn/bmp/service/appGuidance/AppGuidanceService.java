package com.yice.edu.cn.bmp.service.appGuidance;

import com.yice.edu.cn.bmp.feignClient.appGuidance.AppGuidanceFeign;
import com.yice.edu.cn.common.pojo.jw.appGuidance.AppGuidance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppGuidanceService {
    @Autowired
    private AppGuidanceFeign appGuidanceFeign;

    public AppGuidance findAppGuidanceById(String id) {
        return appGuidanceFeign.findAppGuidanceById(id);
    }

    public AppGuidance saveAppGuidance(AppGuidance appGuidance) {
        return appGuidanceFeign.saveAppGuidance(appGuidance);
    }

    public List<AppGuidance> findAppGuidanceListByCondition(AppGuidance appGuidance) {
        return appGuidanceFeign.findAppGuidanceListByCondition(appGuidance);
    }

    public AppGuidance findOneAppGuidanceByCondition(AppGuidance appGuidance) {
        return appGuidanceFeign.findOneAppGuidanceByCondition(appGuidance);
    }

    public long findAppGuidanceCountByCondition(AppGuidance appGuidance) {
        return appGuidanceFeign.findAppGuidanceCountByCondition(appGuidance);
    }

    public void updateAppGuidance(AppGuidance appGuidance) {
        appGuidanceFeign.updateAppGuidance(appGuidance);
    }

    public void deleteAppGuidance(String id) {
        appGuidanceFeign.deleteAppGuidance(id);
    }

    public void deleteAppGuidanceByCondition(AppGuidance appGuidance) {
        appGuidanceFeign.deleteAppGuidanceByCondition(appGuidance);
    }
}
