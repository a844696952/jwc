package com.yice.edu.cn.dm.service.dmlog;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.dm.classCard.DmLog;
import com.yice.edu.cn.dm.dao.dmLog.IDmLogDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DmLogService {
    @Autowired
    private IDmLogDao dmLogDao;
    @Autowired
    private SequenceId sequenceId;

    @Transactional(readOnly = true)
    public DmLog findDmLogById(String id) {
        return dmLogDao.findDmLogById(id);
    }
    @Transactional
    public void saveDmLog(DmLog dmLog) {
        dmLog.setId(sequenceId.nextId());
        dmLogDao.saveDmLog(dmLog);
    }
    @Transactional(readOnly = true)
    public List<DmLog> findDmLogListByCondition(DmLog dmLog) {
        return dmLogDao.findDmLogListByCondition(dmLog);
    }
    @Transactional(readOnly = true)
    public DmLog findOneDmLogByCondition(DmLog dmLog) {
        return dmLogDao.findOneDmLogByCondition(dmLog);
    }
    @Transactional(readOnly = true)
    public long findDmLogCountByCondition(DmLog dmLog) {
        return dmLogDao.findDmLogCountByCondition(dmLog);
    }
    @Transactional
    public void updateDmLog(DmLog dmLog) {
        dmLogDao.updateDmLog(dmLog);
    }
    @Transactional
    public void deleteDmLog(String id) {
        dmLogDao.deleteDmLog(id);
    }
    @Transactional
    public void deleteDmLogByCondition(DmLog dmLog) {
        dmLogDao.deleteDmLogByCondition(dmLog);
    }
    @Transactional
    public void batchSaveDmLog(List<DmLog> dmLogs){
        dmLogs.forEach(dmLog -> dmLog.setId(sequenceId.nextId()));
        dmLogDao.batchSaveDmLog(dmLogs);
    }
    @Transactional(readOnly = true)
    public List<String> findParentsByStudentId(String studentId){
        return dmLogDao.findParentsByStudentId(studentId);
    }
    @Transactional(readOnly = true)
    public List<String> findTeacherPostBySid(String studentId){
        return dmLogDao.findTeacherPostBySid(studentId);
    }


}
