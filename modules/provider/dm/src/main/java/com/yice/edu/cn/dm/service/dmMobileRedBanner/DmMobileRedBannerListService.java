package com.yice.edu.cn.dm.service.dmMobileRedBanner;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.dm.dmMobileRedBanner.DmMobileRedBannerList;
import com.yice.edu.cn.dm.dao.dmMobileRedBanner.IDmMobileRedBannerListDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DmMobileRedBannerListService {
    @Autowired
    private IDmMobileRedBannerListDao dmMobileRedBannerListDao;
    @Autowired
    private SequenceId sequenceId;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @Transactional(readOnly = true)
    public DmMobileRedBannerList findDmMobileRedBannerListById(String id) {
        return dmMobileRedBannerListDao.findDmMobileRedBannerListById(id);
    }
    @Transactional
    public void saveDmMobileRedBannerList(DmMobileRedBannerList dmMobileRedBannerList) {
        dmMobileRedBannerList.setId(sequenceId.nextId());
        dmMobileRedBannerListDao.saveDmMobileRedBannerList(dmMobileRedBannerList);
    }
    @Transactional(readOnly = true)
    public List<DmMobileRedBannerList> findDmMobileRedBannerListListByCondition(DmMobileRedBannerList dmMobileRedBannerList) {
        return dmMobileRedBannerListDao.findDmMobileRedBannerListListByCondition(dmMobileRedBannerList);
    }
    @Transactional(readOnly = true)
    public DmMobileRedBannerList findOneDmMobileRedBannerListByCondition(DmMobileRedBannerList dmMobileRedBannerList) {
        return dmMobileRedBannerListDao.findOneDmMobileRedBannerListByCondition(dmMobileRedBannerList);
    }
    @Transactional(readOnly = true)
    public long findDmMobileRedBannerListCountByCondition(DmMobileRedBannerList dmMobileRedBannerList) {
        return dmMobileRedBannerListDao.findDmMobileRedBannerListCountByCondition(dmMobileRedBannerList);
    }
    @Transactional
    public void updateDmMobileRedBannerList(DmMobileRedBannerList dmMobileRedBannerList) {
        dmMobileRedBannerListDao.updateDmMobileRedBannerList(dmMobileRedBannerList);
    }
    @Transactional
    public void updateDmMobileRedBannerListForAll(DmMobileRedBannerList dmMobileRedBannerList) {
        dmMobileRedBannerListDao.updateDmMobileRedBannerListForAll(dmMobileRedBannerList);
    }
    @Transactional
    public void deleteDmMobileRedBannerList(String id) {
        dmMobileRedBannerListDao.deleteDmMobileRedBannerList(id);
    }
    @Transactional
    public void deleteDmMobileRedBannerListByCondition(DmMobileRedBannerList dmMobileRedBannerList) {
        dmMobileRedBannerListDao.deleteDmMobileRedBannerListByCondition(dmMobileRedBannerList);
    }
    @Transactional
    public void batchSaveDmMobileRedBannerList(List<DmMobileRedBannerList> dmMobileRedBannerLists){
        dmMobileRedBannerLists.forEach(dmMobileRedBannerList -> dmMobileRedBannerList.setId(sequenceId.nextId()));
        dmMobileRedBannerListDao.batchSaveDmMobileRedBannerList(dmMobileRedBannerLists);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
