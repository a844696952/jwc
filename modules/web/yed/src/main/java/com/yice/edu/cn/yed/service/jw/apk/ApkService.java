package com.yice.edu.cn.yed.service.jw.apk;

import com.yice.edu.cn.common.pojo.dm.classCard.Apk;
import com.yice.edu.cn.yed.feignClient.jw.apk.ApkFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApkService {
    @Autowired
    private ApkFeign apkFeign;

    public Apk findApkById(String id) {
        return apkFeign.findApkById(id);
    }

    public Apk saveApk(Apk apk) {
        return apkFeign.saveApk(apk);
    }

    public List<Apk> findApkListByCondition(Apk apk) {
        return apkFeign.findApkListByCondition(apk);
    }

    public Apk findOneApkByCondition(Apk apk) {
        return apkFeign.findOneApkByCondition(apk);
    }

    public long findApkCountByCondition(Apk apk) {
        return apkFeign.findApkCountByCondition(apk);
    }

    public void updateApk(Apk apk) {
        apkFeign.updateApk(apk);
    }

    public void deleteApk(String id) {
        apkFeign.deleteApk(id);
    }

    public void deleteApkByCondition(Apk apk) {
        apkFeign.deleteApkByCondition(apk);
    }
}
