package com.yice.edu.cn.xw.service.searchOwner;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.xw.searchOwner.XwSearchOwner;
import com.yice.edu.cn.xw.dao.searchOwner.IXwSearchOwnerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class XwSearchOwnerService {
    @Autowired
    private IXwSearchOwnerDao xwSearchOwnerDao;
    @Autowired
    private SequenceId sequenceId;

    @Transactional(readOnly = true)
    public XwSearchOwner findXwSearchOwnerById(String id) {
        return xwSearchOwnerDao.findXwSearchOwnerById(id);
    }
    @Transactional
    public void saveXwSearchOwner(XwSearchOwner xwSearchOwner) {
        xwSearchOwner.setId(sequenceId.nextId());
        xwSearchOwnerDao.saveXwSearchOwner(xwSearchOwner);
    }
    @Transactional(readOnly = true)
    public List<XwSearchOwner> findXwSearchOwnerListByCondition(XwSearchOwner xwSearchOwner) {
        return xwSearchOwnerDao.findXwSearchOwnerListByCondition(xwSearchOwner);
    }
    @Transactional(readOnly = true)
    public XwSearchOwner findOneXwSearchOwnerByCondition(XwSearchOwner xwSearchOwner) {
        return xwSearchOwnerDao.findOneXwSearchOwnerByCondition(xwSearchOwner);
    }
    @Transactional(readOnly = true)
    public long findXwSearchOwnerCountByCondition(XwSearchOwner xwSearchOwner) {
        return xwSearchOwnerDao.findXwSearchOwnerCountByCondition(xwSearchOwner);
    }
    @Transactional
    public void updateXwSearchOwner(XwSearchOwner xwSearchOwner) {
        xwSearchOwnerDao.updateXwSearchOwner(xwSearchOwner);
    }
    @Transactional
    public void deleteXwSearchOwner(String id) {
        xwSearchOwnerDao.deleteXwSearchOwner(id);
    }
    @Transactional
    public void deleteXwSearchOwnerByCondition(XwSearchOwner xwSearchOwner) {
        xwSearchOwnerDao.deleteXwSearchOwnerByCondition(xwSearchOwner);
    }
    @Transactional
    public void batchSaveXwSearchOwner(List<XwSearchOwner> xwSearchOwners){
        xwSearchOwners.forEach(xwSearchOwner -> xwSearchOwner.setId(sequenceId.nextId()));
        xwSearchOwnerDao.batchSaveXwSearchOwner(xwSearchOwners);
    }

}
