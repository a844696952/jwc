package com.yice.edu.cn.dm.service.active;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.dm.active.DmActive;
import com.yice.edu.cn.dm.dao.active.IDmActiveDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DmActiveService {
    @Autowired
    private IDmActiveDao dmActiveDao;
    @Autowired
    private SequenceId sequenceId;

    @Transactional(readOnly = true)
    public DmActive findDmActiveById(String id) {
        return dmActiveDao.findDmActiveById(id);
    }
    @Transactional
    public void saveDmActive(DmActive dmActive) {
        dmActive.setId(sequenceId.nextId());
        dmActiveDao.saveDmActive(dmActive);
    }
    @Transactional(readOnly = true)
    public List<DmActive> findDmActiveListByCondition(DmActive dmActive) {
        return dmActiveDao.findDmActiveListByCondition(dmActive);
    }
    @Transactional(readOnly = true)
    public DmActive findOneDmActiveByCondition(DmActive dmActive) {
        return dmActiveDao.findOneDmActiveByCondition(dmActive);
    }
    @Transactional(readOnly = true)
    public long findDmActiveCountByCondition(DmActive dmActive) {
        return dmActiveDao.findDmActiveCountByCondition(dmActive);
    }
    @Transactional
    public void updateDmActive(DmActive dmActive) {
        dmActiveDao.updateDmActive(dmActive);
    }
    @Transactional
    public void deleteDmActive(String id) {
        dmActiveDao.deleteDmActive(id);
    }
    @Transactional
    public void deleteDmActiveByCondition(DmActive dmActive) {
        dmActiveDao.deleteDmActiveByCondition(dmActive);
    }
    @Transactional
    public void batchSaveDmActive(List<DmActive> dmActives){
        dmActiveDao.batchSaveDmActive(dmActives);
    }

    @Transactional
    public void deleteManyDmActive(String[] rowData) {
        dmActiveDao.deleteManyDmActive(rowData);
    }

    @Transactional(readOnly = true)
    public List<DmActive> findDmActiveListByConditionLike(DmActive dmActive) {
        return dmActiveDao.findDmActiveListByConditionLike(dmActive);
    }

    @Transactional(readOnly = true)
    public long findDmActiveCountByConditionLike(DmActive dmActive) {
        return dmActiveDao.findDmActiveCountByConditionLike(dmActive);
    }

    @Transactional(readOnly = true)
    public List<DmActive> findDmActiveListByConditionVue(DmActive dmActive) {
        return dmActiveDao.findDmActiveListByConditionVue(dmActive);
    }
}
