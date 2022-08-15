package com.yice.edu.cn.dm.service.schoolActive;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.dm.schoolActive.DmActivityInfo;
import com.yice.edu.cn.common.pojo.dm.schoolActive.DmActivitySiginUp;
import com.yice.edu.cn.common.util.date.DateUtils;
import com.yice.edu.cn.dm.dao.schoolActive.IDmActivityInfoDao;
import com.yice.edu.cn.dm.dao.schoolActive.IDmActivityItemDao;
import com.yice.edu.cn.dm.dao.schoolActive.IDmActivitySiginUpDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DmActivitySiginUpService {
    @Autowired
    private IDmActivitySiginUpDao dmActivitySiginUpDao;
    @Autowired
    private IDmActivityInfoDao dmActivityInfoDao;
    @Autowired
    private IDmActivityItemDao dmActivityItemDao;
    @Autowired
    private SequenceId sequenceId;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @Transactional(readOnly = true)
    public DmActivitySiginUp findDmActivitySiginUpById(String id) {
        return dmActivitySiginUpDao.findDmActivitySiginUpById(id);
    }
    @Transactional
    public void saveDmActivitySiginUp(DmActivitySiginUp dmActivitySiginUp) {
        dmActivitySiginUp.setId(sequenceId.nextId());
        dmActivitySiginUpDao.saveDmActivitySiginUp(dmActivitySiginUp);
    }
    @Transactional(readOnly = true)
    public List<DmActivitySiginUp> findDmActivitySiginUpListByCondition(DmActivitySiginUp dmActivitySiginUp) {
        return dmActivitySiginUpDao.findDmActivitySiginUpListByCondition(dmActivitySiginUp);
    }
    @Transactional(readOnly = true)
    public DmActivitySiginUp findOneDmActivitySiginUpByCondition(DmActivitySiginUp dmActivitySiginUp) {
        return dmActivitySiginUpDao.findOneDmActivitySiginUpByCondition(dmActivitySiginUp);
    }
    @Transactional(readOnly = true)
    public long findDmActivitySiginUpCountByCondition(DmActivitySiginUp dmActivitySiginUp) {
        return dmActivitySiginUpDao.findDmActivitySiginUpCountByCondition(dmActivitySiginUp);
    }
    @Transactional
    public void updateDmActivitySiginUp(DmActivitySiginUp dmActivitySiginUp) {
        dmActivitySiginUpDao.updateDmActivitySiginUp(dmActivitySiginUp);
    }
    @Transactional
    public void deleteDmActivitySiginUp(String id) {
        dmActivitySiginUpDao.deleteDmActivitySiginUp(id);
    }
    @Transactional
    public void deleteDmActivitySiginUpByCondition(DmActivitySiginUp dmActivitySiginUp) {
        DmActivityInfo dmActivityInfo = dmActivityInfoDao.findOneDmActivityInfoByActivityId(dmActivitySiginUp.getActivityId());
        if(dmActivityInfo.getIsCustomize()==1){
            dmActivityItemDao.deleteDmActivityItem(dmActivitySiginUp.getItemId());
            dmActivitySiginUpDao.deleteDmActivitySiginUpByItemId(dmActivitySiginUp.getItemId(),dmActivitySiginUp.getClassesId());
        }else{
            dmActivitySiginUpDao.deleteDmActivitySiginUpByItemId(dmActivitySiginUp.getItemId(),dmActivitySiginUp.getClassesId());
        }
    }
    @Transactional
    public void batchSaveDmActivitySiginUp(List<DmActivitySiginUp> dmActivitySiginUps){
        dmActivitySiginUps.forEach(d ->{
            d.setId(sequenceId.nextId());
            d.setCreateTime(DateUtils.Nowss());
        });
        dmActivitySiginUpDao.batchSaveDmActivitySiginUp(dmActivitySiginUps);
    }

    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
