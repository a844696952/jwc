package com.yice.edu.cn.osp.service.jw.appIndex;

import com.yice.edu.cn.common.pojo.jw.appIndex.AppIndex;
import com.yice.edu.cn.osp.feignClient.jw.appIndex.AppIndexFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppIndexService {
    @Autowired
    private AppIndexFeign appIndexFeign;

    public AppIndex findAppIndexById(String id) {
        return appIndexFeign.findAppIndexById(id);
    }

    public AppIndex saveAppIndex(AppIndex appIndex) {
        return appIndexFeign.saveAppIndex(appIndex);
    }

    public List<AppIndex> findAppIndexListByCondition(AppIndex appIndex) {
        return appIndexFeign.findAppIndexListByCondition(appIndex);
    }

    public AppIndex findOneAppIndexByCondition(AppIndex appIndex) {
        return appIndexFeign.findOneAppIndexByCondition(appIndex);
    }

    public long findAppIndexCountByCondition(AppIndex appIndex) {
        return appIndexFeign.findAppIndexCountByCondition(appIndex);
    }

    public void updateAppIndex(AppIndex appIndex) {
        appIndexFeign.updateAppIndex(appIndex);
    }

    public void deleteAppIndex(String id) {
        appIndexFeign.deleteAppIndex(id);
    }

    public void deleteAppIndexByCondition(AppIndex appIndex) {
        appIndexFeign.deleteAppIndexByCondition(appIndex);
    }

    public List<AppIndex> findAppIndexListForSchool(String schoolId) {
        return appIndexFeign.findAppIndexListForSchool(schoolId);
    }

}
