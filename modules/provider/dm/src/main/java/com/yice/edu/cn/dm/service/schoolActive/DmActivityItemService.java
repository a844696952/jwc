package com.yice.edu.cn.dm.service.schoolActive;

import cn.hutool.core.collection.CollUtil;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.dm.schoolActive.DmActivityInfo;
import com.yice.edu.cn.common.pojo.dm.schoolActive.DmActivityItem;
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
public class DmActivityItemService {
    @Autowired
    private IDmActivityItemDao dmActivityItemDao;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private IDmActivitySiginUpDao dmActivitySiginUpDao;
    @Autowired
    private IDmActivityInfoDao dmActivityInfoDao;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @Transactional(readOnly = true)
    public DmActivityItem findDmActivityItemById(String id) {
        return dmActivityItemDao.findDmActivityItemById(id);
    }
    @Transactional
    public void saveDmActivityItem(DmActivityItem dmActivityItem) {
        dmActivityItem.setId(sequenceId.nextId());
        dmActivityItemDao.saveDmActivityItem(dmActivityItem);
    }
    @Transactional(readOnly = true)
    public List<DmActivityItem> findDmActivityItemListByCondition(DmActivityItem dmActivityItem) {
        return dmActivityItemDao.findDmActivityItemListByCondition(dmActivityItem);
    }
    @Transactional(readOnly = true)
    public DmActivityItem findOneDmActivityItemByCondition(DmActivityItem dmActivityItem) {
        return dmActivityItemDao.findOneDmActivityItemByCondition(dmActivityItem);
    }
    @Transactional(readOnly = true)
    public long findDmActivityItemCountByCondition(DmActivityItem dmActivityItem) {
        return dmActivityItemDao.findDmActivityItemCountByCondition(dmActivityItem);
    }
    @Transactional
    public void updateDmActivityItem(DmActivityItem dmActivityItem) {
        dmActivityItemDao.updateDmActivityItem(dmActivityItem);
    }
    @Transactional
    public void deleteDmActivityItem(String id) {
        dmActivityItemDao.deleteDmActivityItem(id);
    }
    @Transactional
    public void deleteDmActivityItemByCondition(DmActivityItem dmActivityItem) {
        dmActivityItemDao.deleteDmActivityItemByCondition(dmActivityItem);
    }
    @Transactional
    public void batchSaveDmActivityItem(List<DmActivityItem> dmActivityItems){
        dmActivityItems.forEach(dmActivityItem -> dmActivityItem.setId(sequenceId.nextId()));
        dmActivityItemDao.batchSaveDmActivityItem(dmActivityItems);
    }

    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

    @Transactional(readOnly = true)
    public List<DmActivityItem> findDmActivityItemsByCondition(DmActivityItem dmActivityItem) {
        DmActivityInfo dmActivityInfo = dmActivityInfoDao.findOneDmActivityInfoByActivityId(dmActivityItem.getActivityId());
        List<DmActivityItem> dmActivityItems;
        if(dmActivityInfo.getIsCustomize()==0){
             dmActivityItems = dmActivityItemDao.findDmActivityItemListByActivityId(dmActivityItem.getActivityId());

        }else{
            dmActivityItems = dmActivityItemDao.findDmActivityItemListByActivityIdAndClassesId(dmActivityItem);
        }
        dmActivityItems.forEach(d->{
            List<DmActivitySiginUp> dmActivitySiginUps = dmActivitySiginUpDao.findDmActivitySiginUpListByItemId(d.getId(),dmActivityItem.getClassesId());
            d.setDmActivitySiginUps(dmActivitySiginUps);
        });
        return dmActivityItems;
    }

    @Transactional
    public void saveDmActivityItemAndPeople(DmActivityItem dmActivityItem) {
        dmActivityItem.setId(sequenceId.nextId());
        dmActivityItem.setCreateTime(DateUtils.Nowss());
        dmActivityItemDao.saveDmActivityItem(dmActivityItem);
        List<DmActivitySiginUp> dmActivitySiginUps = dmActivityItem.getDmActivitySiginUps();
        dmActivitySiginUps.forEach(d->{
            d.setId(sequenceId.nextId());
            d.setActivityId(dmActivityItem.getActivityId());
            d.setItemId(dmActivityItem.getId());
            d.setSchoolId(dmActivityItem.getSchoolId());
            d.setCreateTime(DateUtils.Nowss());
            d.setClassesId(dmActivityItem.getClassesId());
        });
        if(CollUtil.isNotEmpty(dmActivitySiginUps)){
            dmActivitySiginUpDao.batchSaveDmActivitySiginUp(dmActivitySiginUps);
        }
    }

    @Transactional
    public void updateDmActivityItemAndPeople(DmActivityItem dmActivityItem) {
        DmActivityInfo dmActivityInfo = dmActivityInfoDao.findOneDmActivityInfoByActivityId(dmActivityItem.getActivityId());
        List<DmActivitySiginUp> dmActivitySiginUps = dmActivityItem.getDmActivitySiginUps();
        dmActivitySiginUps.forEach(d->{
            d.setClassesId(dmActivityItem.getClassesId());
            d.setItemId(dmActivityItem.getId());
            d.setActivityId(dmActivityItem.getActivityId());
            d.setCreateTime(DateUtils.Nowss());
            d.setSchoolId(dmActivityItem.getSchoolId());
            d.setId(sequenceId.nextId());
        });
        if(dmActivityInfo.getIsCustomize()==1){
            //班级可以自定义项目
            dmActivityItemDao.updateDmActivityItemById(dmActivityItem);
        }
        dmActivitySiginUpDao.deleteDmActivitySiginUpByItemId(dmActivityItem.getId(),dmActivityItem.getClassesId());
        if(CollUtil.isNotEmpty(dmActivitySiginUps)){
            dmActivitySiginUpDao.batchSaveDmActivitySiginUp(dmActivitySiginUps);
        }
    }
}
