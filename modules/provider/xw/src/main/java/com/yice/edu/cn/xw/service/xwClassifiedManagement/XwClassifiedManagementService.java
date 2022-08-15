package com.yice.edu.cn.xw.service.xwClassifiedManagement;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.xw.xwClassifiedManagement.XwClassifiedManagement;
import com.yice.edu.cn.common.pojo.xw.xwRegulatoryFramework.XwRegulatoryFramework;
import com.yice.edu.cn.xw.dao.xwClassifiedManagement.IXwClassifiedManagementDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class XwClassifiedManagementService {
    @Autowired
    private IXwClassifiedManagementDao xwClassifiedManagementDao;
    @Autowired
    private SequenceId sequenceId;

    @Transactional(readOnly = true)
    public XwClassifiedManagement findXwClassifiedManagementById(String id) {
        return xwClassifiedManagementDao.findXwClassifiedManagementById(id);
    }
    @Transactional
    public void saveXwClassifiedManagement(XwClassifiedManagement xwClassifiedManagement) {
        xwClassifiedManagement.setId(sequenceId.nextId());
        xwClassifiedManagementDao.saveXwClassifiedManagement(xwClassifiedManagement);
    }
    @Transactional(readOnly = true)
    public List<XwClassifiedManagement> findXwClassifiedManagementListByCondition(XwClassifiedManagement xwClassifiedManagement) {
        return xwClassifiedManagementDao.findXwClassifiedManagementListByCondition(xwClassifiedManagement);
    }
    @Transactional(readOnly = true)
    public XwClassifiedManagement findOneXwClassifiedManagementByCondition(XwClassifiedManagement xwClassifiedManagement) {
        return xwClassifiedManagementDao.findOneXwClassifiedManagementByCondition(xwClassifiedManagement);
    }
    @Transactional(readOnly = true)
    public long findXwClassifiedManagementCountByCondition(XwClassifiedManagement xwClassifiedManagement) {
        return xwClassifiedManagementDao.findXwClassifiedManagementCountByCondition(xwClassifiedManagement);
    }
    @Transactional
    public void updateXwClassifiedManagement(XwClassifiedManagement xwClassifiedManagement) {
        if (xwClassifiedManagement.getRemark()==null){
            xwClassifiedManagement.setRemark("");
        }
        xwClassifiedManagementDao.updateXwClassifiedManagement(xwClassifiedManagement);
    }
    @Transactional
    public void deleteXwClassifiedManagement(String id) {
        xwClassifiedManagementDao.deleteXwClassifiedManagement(id);
    }
    @Transactional
    public void deleteXwClassifiedManagementByCondition(XwClassifiedManagement xwClassifiedManagement) {
        xwClassifiedManagementDao.deleteXwClassifiedManagementByCondition(xwClassifiedManagement);
    }
    @Transactional
    public void batchSaveXwClassifiedManagement(List<XwClassifiedManagement> xwClassifiedManagements){
        xwClassifiedManagements.forEach(xwClassifiedManagement -> xwClassifiedManagement.setId(sequenceId.nextId()));
        xwClassifiedManagementDao.batchSaveXwClassifiedManagement(xwClassifiedManagements);
    }



    @Transactional(readOnly = true)
    public List<XwClassifiedManagement> findXwClassifiedManagementListByCondition2(XwClassifiedManagement xwClassifiedManagement) {
        return xwClassifiedManagementDao.findXwClassifiedManagementListByCondition2(xwClassifiedManagement);
    }
    @Transactional(readOnly = true)
    public long findXwClassifiedManagementCountByCondition2(XwClassifiedManagement xwClassifiedManagement) {
        return xwClassifiedManagementDao.findXwClassifiedManagementCountByCondition2(xwClassifiedManagement);
    }
    @Transactional(readOnly = true)
    public  long findCountByIdForDelete(XwRegulatoryFramework xwRegulatoryFramework){
        return  xwClassifiedManagementDao.findCountByIdForDelete(xwRegulatoryFramework);
    }


}
