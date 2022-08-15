package com.yice.edu.cn.jw.service.appVersionControl;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.jw.appVersionControl.AppVersionControl;
import com.yice.edu.cn.jw.dao.appVersionControl.AppVersionControlDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AppVersionControlService {
    @Autowired
    private AppVersionControlDao appVersionControlDao;
    @Autowired
    private SequenceId sequenceId;

    @Transactional(readOnly = true)
    public AppVersionControl findAppVersionControlById(String id) {
        return appVersionControlDao.findAppVersionControlById(id);
    }
    @Transactional
    public String saveAppVersionControl(AppVersionControl appVersionControl) {
        List<AppVersionControl> appVersionControls =  appVersionControlDao.findSaveVersionControlNumber(appVersionControl);
        if(appVersionControls!=null&&appVersionControls.size()!=0){
            return "版本号不能低于已有版本" ;
        }
        appVersionControl.setId(sequenceId.nextId());
        appVersionControlDao.saveAppVersionControl(appVersionControl);
        return null;
    }
    @Transactional(readOnly = true)
    public List<AppVersionControl> findAppVersionControlListByCondition(AppVersionControl appVersionControl) {
        return appVersionControlDao.findAppVersionControlListByCondition(appVersionControl);
    }
    @Transactional(readOnly = true)
    public AppVersionControl findOneAppVersionControlByCondition(AppVersionControl appVersionControl) {
        return appVersionControlDao.findOneAppVersionControlByCondition(appVersionControl);
    }
    @Transactional(readOnly = true)
    public long findAppVersionControlCountByCondition(AppVersionControl appVersionControl) {
        return appVersionControlDao.findAppVersionControlCountByCondition(appVersionControl);
    }
    @Transactional
    public void updateAppVersionControl(AppVersionControl appVersionControl) {
        appVersionControlDao.updateAppVersionControl(appVersionControl);
    }
    @Transactional
    public void deleteAppVersionControl(String id) {
        appVersionControlDao.deleteAppVersionControl(id);
    }
    @Transactional
    public void deleteAppVersionControlByCondition(AppVersionControl appVersionControl) {
        appVersionControlDao.deleteAppVersionControlByCondition(appVersionControl);
    }
    @Transactional
    public void batchSaveAppVersionControl(List<AppVersionControl> appVersionControls){
        appVersionControls.forEach(appVersionControl -> appVersionControl.setId(sequenceId.nextId()));
        appVersionControlDao.batchSaveAppVersionControl(appVersionControls);
    }


    @Transactional
    public AppVersionControl findVersionControlUptodate(AppVersionControl appVersionControl){
        List<AppVersionControl> appVersionControls = appVersionControlDao.findVersionControlUptodate(appVersionControl);
        if(appVersionControls!=null&&appVersionControls.size()!=0){
            AppVersionControl appVersionControl1 = appVersionControls.get(0);
            for (AppVersionControl f :appVersionControls){
                    if(appVersionControl1.getVersionNumber()<f.getVersionNumber()){
                        appVersionControl1 = f;
                    }
            }
            return appVersionControl1;
        }
        return null;
    }

}
