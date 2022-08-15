package com.yice.edu.cn.xw.service.dormManage.dorm;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.DormPersonOut;
import com.yice.edu.cn.xw.dao.dormManage.dorm.IDormPersonOutDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DormPersonOutService {
    @Autowired
    private IDormPersonOutDao dormPersonOutDao;
    @Autowired
    private SequenceId sequenceId;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @Transactional(readOnly = true)
    public DormPersonOut findDormPersonOutById(String id) {
        return dormPersonOutDao.findDormPersonOutById(id);
    }
    @Transactional
    public void saveDormPersonOut(DormPersonOut dormPersonOut) {
        dormPersonOut.setId(sequenceId.nextId());
        dormPersonOutDao.saveDormPersonOut(dormPersonOut);
    }
    @Transactional(readOnly = true)
    public List<DormPersonOut> findDormPersonOutListByCondition(DormPersonOut dormPersonOut) {
        return dormPersonOutDao.findDormPersonOutListByCondition(dormPersonOut);
    }
    @Transactional(readOnly = true)
    public DormPersonOut findOneDormPersonOutByCondition(DormPersonOut dormPersonOut) {
        return dormPersonOutDao.findOneDormPersonOutByCondition(dormPersonOut);
    }
    @Transactional(readOnly = true)
    public long findDormPersonOutCountByCondition(DormPersonOut dormPersonOut) {
        return dormPersonOutDao.findDormPersonOutCountByCondition(dormPersonOut);
    }
    @Transactional
    public void updateDormPersonOut(DormPersonOut dormPersonOut) {
        dormPersonOutDao.updateDormPersonOut(dormPersonOut);
    }
    @Transactional
    public void deleteDormPersonOut(String id) {
        dormPersonOutDao.deleteDormPersonOut(id);
    }
    @Transactional
    public void deleteDormPersonOutByCondition(DormPersonOut dormPersonOut) {
        dormPersonOutDao.deleteDormPersonOutByCondition(dormPersonOut);
    }
    @Transactional
    public void batchSaveDormPersonOut(List<DormPersonOut> dormPersonOuts){
        dormPersonOuts.forEach(dormPersonOut -> dormPersonOut.setId(sequenceId.nextId()));
        dormPersonOutDao.batchSaveDormPersonOut(dormPersonOuts);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    @Transactional
    public List<DormPersonOut> findDormPersonOutListByConditionAndPersonType(DormPersonOut dormPersonOut){
        return dormPersonOutDao.findDormPersonOutListByConditionAndPersonType(dormPersonOut);
    }
    @Transactional
    public long findDormPersonOutCountByConditionAndPersonType(DormPersonOut dormPersonOut){
        return dormPersonOutDao.findDormPersonOutCountByConditionAndPersonType(dormPersonOut);
    }
    @Transactional
    public void deleteDormPersonOutForStudentByTime(){
        dormPersonOutDao.deleteDormPersonOutForStudentByTime();
    }
    @Transactional
    public void deleteDormPersonLogForStudentByTime(){
        dormPersonOutDao.deleteDormPersonLogForStudentByTime();
    }
}
