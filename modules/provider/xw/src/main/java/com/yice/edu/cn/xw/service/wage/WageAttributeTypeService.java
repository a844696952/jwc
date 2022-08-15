package com.yice.edu.cn.xw.service.wage;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.xw.wage.WageAttributeType;
import com.yice.edu.cn.xw.dao.wage.IWageAttributeTypeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WageAttributeTypeService {
    @Autowired
    private IWageAttributeTypeDao wageAttributeTypeDao;
    @Autowired
    private SequenceId sequenceId;

    @Transactional(readOnly = true)
    public WageAttributeType findWageAttributeTypeById(String id) {
        return wageAttributeTypeDao.findWageAttributeTypeById(id);
    }
    @Transactional
    public void saveWageAttributeType(WageAttributeType wageAttributeType) {
        wageAttributeType.setId(sequenceId.nextId());
        wageAttributeTypeDao.saveWageAttributeType(wageAttributeType);
    }
    @Transactional(readOnly = true)
    public List<WageAttributeType> findWageAttributeTypeListByCondition(WageAttributeType wageAttributeType) {
        return wageAttributeTypeDao.findWageAttributeTypeListByCondition(wageAttributeType);
    }
    @Transactional(readOnly = true)
    public WageAttributeType findOneWageAttributeTypeByCondition(WageAttributeType wageAttributeType) {
        return wageAttributeTypeDao.findOneWageAttributeTypeByCondition(wageAttributeType);
    }
    @Transactional(readOnly = true)
    public long findWageAttributeTypeCountByCondition(WageAttributeType wageAttributeType) {
        return wageAttributeTypeDao.findWageAttributeTypeCountByCondition(wageAttributeType);
    }
    @Transactional
    public void updateWageAttributeType(WageAttributeType wageAttributeType) {
        wageAttributeTypeDao.updateWageAttributeType(wageAttributeType);
    }
    @Transactional
    public void deleteWageAttributeType(String id) {
        wageAttributeTypeDao.deleteWageAttributeType(id);
    }
    @Transactional
    public void deleteWageAttributeTypeByCondition(WageAttributeType wageAttributeType) {
        wageAttributeTypeDao.deleteWageAttributeTypeByCondition(wageAttributeType);
    }
    @Transactional
    public void batchSaveWageAttributeType(List<WageAttributeType> wageAttributeTypes){
        wageAttributeTypes.forEach(wageAttributeType -> wageAttributeType.setId(sequenceId.nextId()));
        wageAttributeTypeDao.batchSaveWageAttributeType(wageAttributeTypes);
    }
    @Transactional(readOnly = true)
    public List<WageAttributeType> findWageAttributeTypeByTypeId(String id) {
        return wageAttributeTypeDao.findWageAttributeTypeByTypeId(id);
    }

}
