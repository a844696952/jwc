package com.yice.edu.cn.tap.service.appPerm;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.yedAdmin.AppPerm;
import com.yice.edu.cn.tap.feignClient.school.SchoolFeign;
import com.yice.edu.cn.tap.feignClient.appPerm.AppPermFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppPermService {
    @Autowired
    private AppPermFeign appPermFeign;
    @Autowired
    private SchoolFeign schoolFeign;


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

    public List<AppPerm> findAppPermListTreeByClass(AppPerm appPerm){
        return appPermFeign.findAppPermListTreeByClass(appPerm);
    }
    public ResponseJson findSchoolExpireOrSchoolYear(String schoolId) {
        return schoolFeign.findSchoolExpireOrSchoolYear(schoolId);
    }
}
