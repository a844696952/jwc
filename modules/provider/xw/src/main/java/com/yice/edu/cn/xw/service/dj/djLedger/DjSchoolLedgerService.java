package com.yice.edu.cn.xw.service.dj.djLedger;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.xw.dj.djLedgerTemplate.DjSchoolLedger;
import com.yice.edu.cn.xw.dao.dj.djLedger.IDjSchoolLedgerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DjSchoolLedgerService {
    @Autowired
    private IDjSchoolLedgerDao djSchoolLedgerDao;
    @Autowired
    private SequenceId sequenceId;

    @Transactional(readOnly = true)
    public DjSchoolLedger findDjSchoolLedgerById(String id) {
        return djSchoolLedgerDao.findDjSchoolLedgerById(id);
    }
    @Transactional
    public void saveDjSchoolLedger(DjSchoolLedger djSchoolLedger) {
        djSchoolLedger.setId(sequenceId.nextId());
        djSchoolLedgerDao.saveDjSchoolLedger(djSchoolLedger);
    }
    @Transactional(readOnly = true)
    public List<DjSchoolLedger> findDjSchoolLedgerListByCondition(DjSchoolLedger djSchoolLedger) {
        return djSchoolLedgerDao.findDjSchoolLedgerListByCondition(djSchoolLedger);
    }
    @Transactional(readOnly = true)
    public DjSchoolLedger findOneDjSchoolLedgerByCondition(DjSchoolLedger djSchoolLedger) {
        return djSchoolLedgerDao.findOneDjSchoolLedgerByCondition(djSchoolLedger);
    }
    @Transactional(readOnly = true)
    public long findDjSchoolLedgerCountByCondition(DjSchoolLedger djSchoolLedger) {
        return djSchoolLedgerDao.findDjSchoolLedgerCountByCondition(djSchoolLedger);
    }
    @Transactional
    public void updateDjSchoolLedger(DjSchoolLedger djSchoolLedger) {
        djSchoolLedgerDao.updateDjSchoolLedger(djSchoolLedger);
    }
    @Transactional
    public void deleteDjSchoolLedger(String id) {
        djSchoolLedgerDao.deleteDjSchoolLedger(id);
    }
    @Transactional
    public void deleteDjSchoolLedgerByCondition(DjSchoolLedger djSchoolLedger) {
        djSchoolLedgerDao.deleteDjSchoolLedgerByCondition(djSchoolLedger);
    }
    @Transactional
    public void batchSaveDjSchoolLedger(List<DjSchoolLedger> djSchoolLedgers){
        djSchoolLedgers.forEach(djSchoolLedger -> djSchoolLedger.setId(sequenceId.nextId()));
        djSchoolLedgerDao.batchSaveDjSchoolLedger(djSchoolLedgers);
    }

}
