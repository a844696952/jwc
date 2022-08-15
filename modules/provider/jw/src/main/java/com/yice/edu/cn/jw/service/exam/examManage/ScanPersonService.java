package com.yice.edu.cn.jw.service.exam.examManage;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.ScanPerson;
import com.yice.edu.cn.jw.dao.exam.examManage.IScanPersonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ScanPersonService {
    @Autowired
    private IScanPersonDao scanPersonDao;
    @Autowired
    private SequenceId sequenceId;

    @Transactional(readOnly = true)
    public ScanPerson findScanPersonById(String id) {
        return scanPersonDao.findScanPersonById(id);
    }
    @Transactional
    public void saveScanPerson(ScanPerson scanPerson) {
        scanPerson.setId(sequenceId.nextId());
        scanPersonDao.saveScanPerson(scanPerson);
    }
    @Transactional(readOnly = true)
    public List<ScanPerson> findScanPersonListByCondition(ScanPerson scanPerson) {
        return scanPersonDao.findScanPersonListByCondition(scanPerson);
    }
    @Transactional(readOnly = true)
    public ScanPerson findOneScanPersonByCondition(ScanPerson scanPerson) {
        return scanPersonDao.findOneScanPersonByCondition(scanPerson);
    }
    @Transactional(readOnly = true)
    public long findScanPersonCountByCondition(ScanPerson scanPerson) {
        return scanPersonDao.findScanPersonCountByCondition(scanPerson);
    }
    @Transactional
    public void updateScanPerson(ScanPerson scanPerson) {
        scanPersonDao.updateScanPerson(scanPerson);
    }
    @Transactional
    public void deleteScanPerson(String id) {
        scanPersonDao.deleteScanPerson(id);
    }
    @Transactional
    public void deleteScanPersonByCondition(ScanPerson scanPerson) {
        scanPersonDao.deleteScanPersonByCondition(scanPerson);
    }
    @Transactional
    public void batchSaveScanPerson(List<ScanPerson> scanPersons){
        scanPersons.forEach(scanPerson -> scanPerson.setId(sequenceId.nextId()));
        scanPersonDao.batchSaveScanPerson(scanPersons);
    }

    @Transactional
    public List<ScanPerson>  findAllScanPerson(ScanPerson scanPerson){
        return scanPersonDao.findAllScanPerson(scanPerson);
    }


    @Transactional
    public List<ScanPerson>  findTeacherByIds(ScanPerson scanPerson){
        return scanPersonDao.findTeacherByIds(scanPerson);
    }

}
