package com.yice.edu.cn.dm.service.dmIot;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.dm.dmIot.DmIot;
import com.yice.edu.cn.dm.dao.dmIot.DmIotDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DmIotService {
    @Autowired
    private DmIotDao dmIotDao;
    @Autowired
    private SequenceId sequenceId;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @Transactional(readOnly = true)
    public DmIot findDmIotById(String id) {
        return dmIotDao.findDmIotById(id);
    }
    @Transactional
    public void saveDmIot(DmIot dmIot) {
        dmIot.setId(sequenceId.nextId());
        dmIotDao.saveDmIot(dmIot);
    }
    @Transactional(readOnly = true)
    public List<DmIot> findDmIotListByCondition(DmIot dmIot) {
        return dmIotDao.findDmIotListByCondition(dmIot);
    }
    @Transactional(readOnly = true)
    public DmIot findOneDmIotByCondition(DmIot dmIot) {
        return dmIotDao.findOneDmIotByCondition(dmIot);
    }
    @Transactional(readOnly = true)
    public long findDmIotCountByCondition(DmIot dmIot) {
        return dmIotDao.findDmIotCountByCondition(dmIot);
    }
    @Transactional
    public void updateDmIot(DmIot dmIot) {
        dmIotDao.updateDmIot(dmIot);
    }
    @Transactional
    public void updateDmIotForAll(DmIot dmIot) {
        dmIotDao.updateDmIotForAll(dmIot);
    }
    @Transactional
    public void deleteDmIot(String id) {
        dmIotDao.deleteDmIot(id);
    }
    @Transactional
    public void deleteDmIotByCondition(DmIot dmIot) {
        dmIotDao.deleteDmIotByCondition(dmIot);
    }
    @Transactional
    public void batchSaveDmIot(List<DmIot> dmIots){
        dmIots.forEach(dmIot -> dmIot.setId(sequenceId.nextId()));
        dmIotDao.batchSaveDmIot(dmIots);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
