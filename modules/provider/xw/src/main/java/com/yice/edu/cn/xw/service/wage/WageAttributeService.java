package com.yice.edu.cn.xw.service.wage;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.xw.wage.WageAttribute;
import com.yice.edu.cn.xw.dao.wage.IWageAttributeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WageAttributeService {
    @Autowired
    private IWageAttributeDao wageAttributeDao;
    @Autowired
    private SequenceId sequenceId;

    @Transactional(readOnly = true)
    public WageAttribute findWageAttributeById(String id) {
        return wageAttributeDao.findWageAttributeById(id);
    }
    @Transactional
    public void saveWageAttribute(WageAttribute wageAttribute) {
        wageAttribute.setId(sequenceId.nextId());
        wageAttributeDao.saveWageAttribute(wageAttribute);
    }
    @Transactional(readOnly = true)
    public List<WageAttribute> findWageAttributeListByCondition(WageAttribute wageAttribute) {
        return wageAttributeDao.findWageAttributeListByCondition(wageAttribute);
    }
    @Transactional(readOnly = true)
    public WageAttribute findOneWageAttributeByCondition(WageAttribute wageAttribute) {
        return wageAttributeDao.findOneWageAttributeByCondition(wageAttribute);
    }
    @Transactional(readOnly = true)
    public long findWageAttributeCountByCondition(WageAttribute wageAttribute) {
        return wageAttributeDao.findWageAttributeCountByCondition(wageAttribute);
    }
    @Transactional
    public void updateWageAttribute(WageAttribute wageAttribute) {
        wageAttributeDao.updateWageAttribute(wageAttribute);
    }
    @Transactional
    public void deleteWageAttribute(String id) {
        wageAttributeDao.deleteWageAttribute(id);
    }
    @Transactional
    public void deleteWageAttributeByCondition(WageAttribute wageAttribute) {
        wageAttributeDao.deleteWageAttributeByCondition(wageAttribute);
    }
    @Transactional
    public void batchSaveWageAttribute(List<WageAttribute> wageAttributes){
        wageAttributes.forEach(wageAttribute -> wageAttribute.setId(sequenceId.nextId()));
        wageAttributeDao.batchSaveWageAttribute(wageAttributes);
    }

}
