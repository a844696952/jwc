package com.yice.edu.cn.xw.service.dormManage.dorm;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.DormPersonLog;
import com.yice.edu.cn.xw.dao.dormManage.dorm.IDormPersonLogDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DormPersonLogService {
    @Autowired
    private IDormPersonLogDao dormPersonLogDao;
    @Autowired
    private SequenceId sequenceId;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @Transactional(readOnly = true)
    public DormPersonLog findDormPersonLogById(String id) {
        return dormPersonLogDao.findDormPersonLogById(id);
    }
    @Transactional
    public void saveDormPersonLog(DormPersonLog dormPersonLog) {
        dormPersonLog.setId(sequenceId.nextId());
        dormPersonLogDao.saveDormPersonLog(dormPersonLog);
    }
    @Transactional(readOnly = true)
    public List<DormPersonLog> findDormPersonLogListByCondition(DormPersonLog dormPersonLog) {
        return dormPersonLogDao.findDormPersonLogListByCondition(dormPersonLog);
    }
    @Transactional(readOnly = true)
    public DormPersonLog findOneDormPersonLogByCondition(DormPersonLog dormPersonLog) {
        return dormPersonLogDao.findOneDormPersonLogByCondition(dormPersonLog);
    }
    @Transactional(readOnly = true)
    public long findDormPersonLogCountByCondition(DormPersonLog dormPersonLog) {
        return dormPersonLogDao.findDormPersonLogCountByCondition(dormPersonLog);
    }
    @Transactional
    public void updateDormPersonLog(DormPersonLog dormPersonLog) {
        dormPersonLogDao.updateDormPersonLog(dormPersonLog);
    }
    @Transactional
    public void deleteDormPersonLog(String id) {
        dormPersonLogDao.deleteDormPersonLog(id);
    }
    @Transactional
    public void deleteDormPersonLogByCondition(DormPersonLog dormPersonLog) {
        dormPersonLogDao.deleteDormPersonLogByCondition(dormPersonLog);
    }
    @Transactional
    public void batchSaveDormPersonLog(List<DormPersonLog> dormPersonLogs){
        dormPersonLogs.forEach(dormPersonLog -> dormPersonLog.setId(sequenceId.nextId()));
        dormPersonLogDao.batchSaveDormPersonLog(dormPersonLogs);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
