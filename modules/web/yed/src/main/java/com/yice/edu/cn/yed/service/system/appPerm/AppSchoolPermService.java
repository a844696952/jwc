package com.yice.edu.cn.yed.service.system.appPerm;

import com.yice.edu.cn.common.pojo.yedAdmin.AppSchoolPerm;
import com.yice.edu.cn.yed.feignClient.system.appPerm.AppSchoolPermFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppSchoolPermService {
    @Autowired
    private AppSchoolPermFeign appSchoolPermFeign;

    public AppSchoolPerm findAppSchoolPermById(String id) {
        return appSchoolPermFeign.findAppSchoolPermById(id);
    }

    public AppSchoolPerm saveAppSchoolPerm(AppSchoolPerm appSchoolPerm) {
        return appSchoolPermFeign.saveAppSchoolPerm(appSchoolPerm);
    }

    public List<AppSchoolPerm> findAppSchoolPermListByCondition(AppSchoolPerm appSchoolPerm) {
        return appSchoolPermFeign.findAppSchoolPermListByCondition(appSchoolPerm);
    }

    public AppSchoolPerm findOneAppSchoolPermByCondition(AppSchoolPerm appSchoolPerm) {
        return appSchoolPermFeign.findOneAppSchoolPermByCondition(appSchoolPerm);
    }

    public long findAppSchoolPermCountByCondition(AppSchoolPerm appSchoolPerm) {
        return appSchoolPermFeign.findAppSchoolPermCountByCondition(appSchoolPerm);
    }

    public void updateAppSchoolPerm(AppSchoolPerm appSchoolPerm) {
        appSchoolPermFeign.updateAppSchoolPerm(appSchoolPerm);
    }

    public void deleteAppSchoolPerm(String id) {
        appSchoolPermFeign.deleteAppSchoolPerm(id);
    }

    public void deleteAppSchoolPermByCondition(AppSchoolPerm appSchoolPerm) {
        appSchoolPermFeign.deleteAppSchoolPermByCondition(appSchoolPerm);
    }
}
