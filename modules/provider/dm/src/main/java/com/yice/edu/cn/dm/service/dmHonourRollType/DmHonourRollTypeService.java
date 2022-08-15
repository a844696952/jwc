package com.yice.edu.cn.dm.service.dmHonourRollType;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.dm.dmHonourRollType.DmHonourRollType;
import com.yice.edu.cn.dm.dao.dmHonourRollType.DmHonourRollTypeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DmHonourRollTypeService {
    @Autowired
    private DmHonourRollTypeDao dmHonourRollTypeDao;
    @Autowired
    private SequenceId sequenceId;

    @Transactional(readOnly = true)
    public DmHonourRollType findDmHonourRollTypeById(String id) {
        return dmHonourRollTypeDao.findDmHonourRollTypeById(id);
    }
    @Transactional
    public void saveDmHonourRollType(DmHonourRollType dmHonourRollType) {
        dmHonourRollType.setId(sequenceId.nextId());
        dmHonourRollTypeDao.saveDmHonourRollType(dmHonourRollType);
    }
    @Transactional(readOnly = true)
    public List<DmHonourRollType> findDmHonourRollTypeListByCondition(DmHonourRollType dmHonourRollType) {
        return dmHonourRollTypeDao.findDmHonourRollTypeListByCondition(dmHonourRollType);
    }
    @Transactional(readOnly = true)
    public DmHonourRollType findOneDmHonourRollTypeByCondition(DmHonourRollType dmHonourRollType) {
        return dmHonourRollTypeDao.findOneDmHonourRollTypeByCondition(dmHonourRollType);
    }
    @Transactional(readOnly = true)
    public long findDmHonourRollTypeCountByCondition(DmHonourRollType dmHonourRollType) {
        return dmHonourRollTypeDao.findDmHonourRollTypeCountByCondition(dmHonourRollType);
    }
    @Transactional
    public void updateDmHonourRollType(DmHonourRollType dmHonourRollType) {
        dmHonourRollTypeDao.updateDmHonourRollType(dmHonourRollType);
    }
    @Transactional
    public void deleteDmHonourRollType(String id) {
        dmHonourRollTypeDao.deleteDmHonourRollType(id);
    }
    @Transactional
    public void deleteDmHonourRollTypeByCondition(DmHonourRollType dmHonourRollType) {
        dmHonourRollTypeDao.deleteDmHonourRollTypeByCondition(dmHonourRollType);
    }
    @Transactional
    public void batchSaveDmHonourRollType(List<DmHonourRollType> dmHonourRollTypes){
        dmHonourRollTypes.forEach(dmHonourRollType -> dmHonourRollType.setId(sequenceId.nextId()));
        dmHonourRollTypeDao.batchSaveDmHonourRollType(dmHonourRollTypes);
    }

}
