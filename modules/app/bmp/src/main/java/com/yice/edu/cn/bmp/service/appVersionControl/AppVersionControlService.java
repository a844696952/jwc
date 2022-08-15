package com.yice.edu.cn.bmp.service.appVersionControl;

import com.yice.edu.cn.bmp.feignClient.appVersion.AppVersionControlFeign;
import com.yice.edu.cn.common.pojo.jw.appVersionControl.AppVersionControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppVersionControlService {
    @Autowired
    private AppVersionControlFeign appVersionControlFeign;

    public AppVersionControl findAppVersionControlById(String id) {
        return appVersionControlFeign.findAppVersionControlById(id);
    }

    public AppVersionControl saveAppVersionControl(AppVersionControl appVersionControl) {
        return appVersionControlFeign.saveAppVersionControl(appVersionControl);
    }

    public List<AppVersionControl> findAppVersionControlListByCondition(AppVersionControl appVersionControl) {
        return appVersionControlFeign.findAppVersionControlListByCondition(appVersionControl);
    }

    public AppVersionControl findOneAppVersionControlByCondition(AppVersionControl appVersionControl) {
        return appVersionControlFeign.findOneAppVersionControlByCondition(appVersionControl);
    }

    public long findAppVersionControlCountByCondition(AppVersionControl appVersionControl) {
        return appVersionControlFeign.findAppVersionControlCountByCondition(appVersionControl);
    }

    public void updateAppVersionControl(AppVersionControl appVersionControl) {
        appVersionControlFeign.updateAppVersionControl(appVersionControl);
    }

    public void deleteAppVersionControl(String id) {
        appVersionControlFeign.deleteAppVersionControl(id);
    }

    public void deleteAppVersionControlByCondition(AppVersionControl appVersionControl) {
        appVersionControlFeign.deleteAppVersionControlByCondition(appVersionControl);
    }

    public AppVersionControl findVersionControlUptodate(AppVersionControl appVersionControl){
        return appVersionControlFeign.findVersionControlUptodate(appVersionControl);
    }
}
