package com.yice.edu.cn.dm.service.apk;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.dm.classCard.Apk;
import com.yice.edu.cn.dm.dao.apk.IApkDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ApkService {
    @Autowired
    private IApkDao apkDao;
    @Autowired
    private SequenceId sequenceId;

    @Transactional(readOnly = true)
    public Apk findApkById(String id) {
        return apkDao.findApkById(id);
    }
    @Transactional
    public void saveApk(Apk apk) {
        apk.setId(sequenceId.nextId());
        apkDao.saveApk(apk);
    }
    @Transactional(readOnly = true)
    public List<Apk> findApkListByCondition(Apk apk) {
        return apkDao.findApkListByCondition(apk);
    }
    @Transactional(readOnly = true)
    public Apk findOneApkByCondition(Apk apk) {
        return apkDao.findOneApkByCondition(apk);
    }
    @Transactional(readOnly = true)
    public long findApkCountByCondition(Apk apk) {
        return apkDao.findApkCountByCondition(apk);
    }
    @Transactional
    public void updateApk(Apk apk) {
        apkDao.updateApk(apk);
    }
    @Transactional
    public void deleteApk(String id) {
        apkDao.deleteApk(id);
    }
    @Transactional
    public void deleteApkByCondition(Apk apk) {
        apkDao.deleteApkByCondition(apk);
    }
    @Transactional
    public void batchSaveApk(List<Apk> apks){
        apks.forEach(apk -> apk.setId(sequenceId.nextId()));
        apkDao.batchSaveApk(apks);
    }

}
