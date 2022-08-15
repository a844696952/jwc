package com.yice.edu.cn.yed.service.system.appPerm;

import com.yice.edu.cn.common.pojo.yedAdmin.AppPerm;
import com.yice.edu.cn.yed.feignClient.system.appPerm.AppPermFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppPermService {
    @Autowired
    private AppPermFeign appPermFeign;

    public AppPerm findAppPermById(String id) {
        return appPermFeign.findAppPermById(id);
    }

    public AppPerm saveAppPerm(AppPerm appPerm) {
        return appPermFeign.saveAppPerm(appPerm);
    }

    public List<AppPerm> findAppPermListByCondition(AppPerm appPerm) {
        return appPermFeign.findAppPermListByCondition(appPerm);
    }

    public AppPerm findOneAppPermByCondition(AppPerm appPerm) {
        return appPermFeign.findOneAppPermByCondition(appPerm);
    }

    public long findAppPermCountByCondition(AppPerm appPerm) {
        return appPermFeign.findAppPermCountByCondition(appPerm);
    }

    public void updateAppPerm(AppPerm appPerm) {
        appPermFeign.updateAppPerm(appPerm);
    }

    public void deleteAppPerm(String id) {
        appPermFeign.deleteAppPerm(id);
    }

    public void deleteAppPermByCondition(AppPerm appPerm) {
        appPermFeign.deleteAppPermByCondition(appPerm);
    }

    public List<AppPerm> findAppPermAndSchoolPermKong(Integer type,String schoolId){
        return appPermFeign.findAppPermAndSchoolPermKong(type,schoolId);
    }

    public void updatesAppSchoolPerm(Integer type,String schoolId,List<AppPerm> appPerms){
        appPermFeign.updatesAppSchoolPerm(type,schoolId,appPerms);
    }

    public void updateAppPermModel(Integer type,List<AppPerm> appPermList){
        appPermFeign.updateAppPermModel(type,appPermList);
    }

}
