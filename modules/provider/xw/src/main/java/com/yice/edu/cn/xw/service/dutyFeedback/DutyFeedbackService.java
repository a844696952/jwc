package com.yice.edu.cn.xw.service.dutyFeedback;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.xw.dutyFeedback.DutyFeedback;
import com.yice.edu.cn.xw.dao.dutyFeedback.IDutyFeedbackDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DutyFeedbackService {
    @Autowired
    private IDutyFeedbackDao dutyFeedbackDao;
    @Autowired
    private SequenceId sequenceId;

    @Transactional(readOnly = true)
    public DutyFeedback findDutyFeedbackById(String id) {
        return dutyFeedbackDao.findDutyFeedbackById(id);
    }
    @Transactional
    public void saveDutyFeedback(DutyFeedback dutyFeedback) {
        dutyFeedback.setId(sequenceId.nextId());
        String handlingInstructions = dutyFeedback.getHandlingInstructions();
        if(handlingInstructions!=null && !"".equals(handlingInstructions.trim())){
            dutyFeedback.setStatus("1");//0为立即处理，1为查看处理情况
        }else{
            dutyFeedback.setStatus("0");
        }
        dutyFeedbackDao.saveDutyFeedback(dutyFeedback);
    }
    @Transactional(readOnly = true)
    public List<DutyFeedback> findDutyFeedbackListByCondition(DutyFeedback dutyFeedback) {
        if(dutyFeedback.getRangeTime()!=null){
            dutyFeedback.setStartdate(dutyFeedback.getRangeTime()[0]);
            dutyFeedback.setEnddate(dutyFeedback.getRangeTime()[1]);
        }
        return dutyFeedbackDao.findDutyFeedbackListByTimeCondition(dutyFeedback);

        //return dutyFeedbackDao.findDutyFeedbackListByCondition(dutyFeedback);
    }
    @Transactional(readOnly = true)
    public DutyFeedback findOneDutyFeedbackByCondition(DutyFeedback dutyFeedback) {
        return dutyFeedbackDao.findOneDutyFeedbackByCondition(dutyFeedback);
    }
    @Transactional(readOnly = true)
    public long findDutyFeedbackCountByCondition(DutyFeedback dutyFeedback) {
        if(dutyFeedback.getRangeTime()!=null){
            dutyFeedback.setStartdate(dutyFeedback.getRangeTime()[0]);
            dutyFeedback.setEnddate(dutyFeedback.getRangeTime()[1]);
        }
        return dutyFeedbackDao.findDutyFeedbackCountByTimeCondition(dutyFeedback);
        //return dutyFeedbackDao.findDutyFeedbackCountByCondition(dutyFeedback);
    }
    @Transactional
    public void updateDutyFeedback(DutyFeedback dutyFeedback) {
        String handlingInstructions = dutyFeedback.getHandlingInstructions();
        if(handlingInstructions!=null && !"".equals(handlingInstructions.trim())){
            dutyFeedback.setStatus("1");//0为立即处理，1为查看处理情况
        }
        dutyFeedbackDao.updateDutyFeedback(dutyFeedback);
    }
    @Transactional
    public void deleteDutyFeedback(String id) {
        dutyFeedbackDao.deleteDutyFeedback(id);
    }
    @Transactional
    public void deleteDutyFeedbackByCondition(DutyFeedback dutyFeedback) {
        dutyFeedbackDao.deleteDutyFeedbackByCondition(dutyFeedback);
    }
    @Transactional
    public void batchSaveDutyFeedback(List<DutyFeedback> dutyFeedbacks){
        dutyFeedbacks.forEach(dutyFeedback -> dutyFeedback.setId(sequenceId.nextId()));
        dutyFeedbackDao.batchSaveDutyFeedback(dutyFeedbacks);
    }

}
