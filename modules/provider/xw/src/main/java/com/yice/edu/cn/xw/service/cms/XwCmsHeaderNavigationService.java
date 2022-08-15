package com.yice.edu.cn.xw.service.cms;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.xw.cms.XwCmsHeaderNavigation;
import com.yice.edu.cn.xw.dao.cms.IXwCmsHeaderNavigationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class XwCmsHeaderNavigationService {
    @Autowired
    private IXwCmsHeaderNavigationDao xwCmsHeaderNavigationDao;
    @Autowired
    private SequenceId sequenceId;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @Transactional(readOnly = true)
    public XwCmsHeaderNavigation findXwCmsHeaderNavigationById(String id) {
        return xwCmsHeaderNavigationDao.findXwCmsHeaderNavigationById(id);
    }
    @Transactional
    public void saveXwCmsHeaderNavigation(XwCmsHeaderNavigation xwCmsHeaderNavigation) {
        xwCmsHeaderNavigation.setId(sequenceId.nextId());
        xwCmsHeaderNavigationDao.saveXwCmsHeaderNavigation(xwCmsHeaderNavigation);
    }
    @Transactional(readOnly = true)
    public List<XwCmsHeaderNavigation> findXwCmsHeaderNavigationListByCondition(XwCmsHeaderNavigation xwCmsHeaderNavigation) {
        return xwCmsHeaderNavigationDao.findXwCmsHeaderNavigationListByCondition(xwCmsHeaderNavigation);
    }
    @Transactional(readOnly = true)
    public XwCmsHeaderNavigation findOneXwCmsHeaderNavigationByCondition(XwCmsHeaderNavigation xwCmsHeaderNavigation) {
        return xwCmsHeaderNavigationDao.findOneXwCmsHeaderNavigationByCondition(xwCmsHeaderNavigation);
    }
    @Transactional(readOnly = true)
    public long findXwCmsHeaderNavigationCountByCondition(XwCmsHeaderNavigation xwCmsHeaderNavigation) {
        return xwCmsHeaderNavigationDao.findXwCmsHeaderNavigationCountByCondition(xwCmsHeaderNavigation);
    }
    @Transactional
    public void updateXwCmsHeaderNavigation(XwCmsHeaderNavigation xwCmsHeaderNavigation) {
        xwCmsHeaderNavigationDao.updateXwCmsHeaderNavigation(xwCmsHeaderNavigation);
    }
    @Transactional
    public void updateXwCmsHeaderNavigationForAll(XwCmsHeaderNavigation xwCmsHeaderNavigation) {
        xwCmsHeaderNavigationDao.updateXwCmsHeaderNavigationForAll(xwCmsHeaderNavigation);
    }
    @Transactional
    public void deleteXwCmsHeaderNavigation(String id) {
        xwCmsHeaderNavigationDao.deleteXwCmsHeaderNavigation(id);
    }
    @Transactional
    public void deleteXwCmsHeaderNavigationByCondition(XwCmsHeaderNavigation xwCmsHeaderNavigation) {
        xwCmsHeaderNavigationDao.deleteXwCmsHeaderNavigationByCondition(xwCmsHeaderNavigation);
    }
    @Transactional
    public void batchSaveXwCmsHeaderNavigation(List<XwCmsHeaderNavigation> xwCmsHeaderNavigations){
        xwCmsHeaderNavigations.forEach(xwCmsHeaderNavigation -> xwCmsHeaderNavigation.setId(sequenceId.nextId()));
        xwCmsHeaderNavigationDao.batchSaveXwCmsHeaderNavigation(xwCmsHeaderNavigations);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/


    @Transactional
    public void deleteXwCmsHeaderNavigationAll() {
        xwCmsHeaderNavigationDao.deleteXwCmsHeaderNavigationAll();
    }


    @Transactional
    public List<XwCmsHeaderNavigation>  saveXwCmsHeaderNavigationList(List<XwCmsHeaderNavigation> xwCmsHeaderNavigations){
        //不管是新增还是编辑都会传全部数据
        deleteXwCmsHeaderNavigationAll();
        xwCmsHeaderNavigations.forEach(xwCmsHeaderNavigation -> xwCmsHeaderNavigation.setId(sequenceId.nextId()));
        xwCmsHeaderNavigationDao.batchSaveXwCmsHeaderNavigation(xwCmsHeaderNavigations);
        return xwCmsHeaderNavigations;
    }

}
