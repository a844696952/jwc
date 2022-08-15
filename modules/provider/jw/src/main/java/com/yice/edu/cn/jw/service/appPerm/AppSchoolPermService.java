package com.yice.edu.cn.jw.service.appPerm;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.yedAdmin.AppSchoolPerm;
import com.yice.edu.cn.jw.dao.appPerm.AppSchoolPermDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AppSchoolPermService {
    @Autowired
    private AppSchoolPermDao appSchoolPermDao;
    @Autowired
    private SequenceId sequenceId;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @Transactional(readOnly = true)
    public AppSchoolPerm findAppSchoolPermById(String id) {
        return appSchoolPermDao.findAppSchoolPermById(id);
    }
    @Transactional
    public void saveAppSchoolPerm(AppSchoolPerm appSchoolPerm) {
        appSchoolPerm.setId(sequenceId.nextId());
        appSchoolPermDao.saveAppSchoolPerm(appSchoolPerm);
    }
    @Transactional(readOnly = true)
    public List<AppSchoolPerm> findAppSchoolPermListByCondition(AppSchoolPerm appSchoolPerm) {
        return appSchoolPermDao.findAppSchoolPermListByCondition(appSchoolPerm);
    }
    @Transactional(readOnly = true)
    public AppSchoolPerm findOneAppSchoolPermByCondition(AppSchoolPerm appSchoolPerm) {
        return appSchoolPermDao.findOneAppSchoolPermByCondition(appSchoolPerm);
    }
    @Transactional(readOnly = true)
    public long findAppSchoolPermCountByCondition(AppSchoolPerm appSchoolPerm) {
        return appSchoolPermDao.findAppSchoolPermCountByCondition(appSchoolPerm);
    }
    @Transactional
    public void updateAppSchoolPerm(AppSchoolPerm appSchoolPerm) {
        appSchoolPermDao.updateAppSchoolPerm(appSchoolPerm);
    }
    @Transactional
    public void deleteAppSchoolPerm(String id) {
        appSchoolPermDao.deleteAppSchoolPerm(id);
    }
    @Transactional
    public void deleteAppSchoolPermByCondition(AppSchoolPerm appSchoolPerm) {
        appSchoolPermDao.deleteAppSchoolPermByCondition(appSchoolPerm);
    }
    @Transactional
    public void batchSaveAppSchoolPerm(List<AppSchoolPerm> appSchoolPerms){
        appSchoolPerms.forEach(appSchoolPerm -> appSchoolPerm.setId(sequenceId.nextId()));
        appSchoolPermDao.batchSaveAppSchoolPerm(appSchoolPerms);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
