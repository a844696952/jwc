package com.yice.edu.cn.jw.service.appGuidance;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.jw.appGuidance.AppGuidance;
import com.yice.edu.cn.jw.dao.appGuidance.IAppGuidanceDao;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class AppGuidanceService {
    @Autowired
    private IAppGuidanceDao appGuidanceDao;
    @Autowired
    private SequenceId sequenceId;

    @Transactional(readOnly = true)
    public AppGuidance findAppGuidanceById(String id) {
        return appGuidanceDao.findAppGuidanceById(id);
    }
    @Transactional
    public void saveAppGuidance(AppGuidance appGuidance) {
        appGuidance.setId(sequenceId.nextId());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date();
        appGuidance.setUpdateTime(sdf.format(date));
        appGuidanceDao.saveAppGuidance(appGuidance);
    }
    @Transactional(readOnly = true)
    public List<AppGuidance> findAppGuidanceListByCondition(AppGuidance appGuidance) {
        if(!StringUtils.isEmpty(appGuidance.getName())){
            appGuidance.setName(appGuidance.getName().trim());
        }
        return appGuidanceDao.findAppGuidanceListByCondition4Like(appGuidance);
        //return appGuidanceDao.findAppGuidanceListByCondition(appGuidance);
    }
    @Transactional(readOnly = true)
    public AppGuidance findOneAppGuidanceByCondition(AppGuidance appGuidance) {
        return appGuidanceDao.findOneAppGuidanceByCondition(appGuidance);
    }
    @Transactional(readOnly = true)
    public long findAppGuidanceCountByCondition(AppGuidance appGuidance) {
        if(!StringUtils.isEmpty(appGuidance.getName())){
            appGuidance.setName(appGuidance.getName().trim());
        }
        return appGuidanceDao.findAppGuidanceCountByCondition4Like((appGuidance));
        //return appGuidanceDao.findAppGuidanceCountByCondition(appGuidance);
    }
    @Transactional
    public void updateAppGuidance(AppGuidance appGuidance) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date();
        appGuidance.setUpdateTime(sdf.format(date));
        appGuidanceDao.updateAppGuidance(appGuidance);
    }
    @Transactional
    public void deleteAppGuidance(String id) {
        appGuidanceDao.deleteAppGuidance(id);
    }
    @Transactional
    public void deleteAppGuidanceByCondition(AppGuidance appGuidance) {
        appGuidanceDao.deleteAppGuidanceByCondition(appGuidance);
    }
    @Transactional
    public void batchSaveAppGuidance(List<AppGuidance> appGuidances){
        appGuidances.forEach(appGuidance -> appGuidance.setId(sequenceId.nextId()));
        appGuidanceDao.batchSaveAppGuidance(appGuidances);
    }

}
