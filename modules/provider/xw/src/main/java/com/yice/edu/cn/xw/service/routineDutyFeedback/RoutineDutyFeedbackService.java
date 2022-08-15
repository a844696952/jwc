package com.yice.edu.cn.xw.service.routineDutyFeedback;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.xw.routineDutyFeedback.RoutineDutyFeedback;
import com.yice.edu.cn.xw.dao.routineDutyFeedback.IRoutineDutyFeedbackDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoutineDutyFeedbackService {
    @Autowired
    private IRoutineDutyFeedbackDao routineDutyFeedbackDao;
    @Autowired
    private SequenceId sequenceId;

    @Transactional(readOnly = true)
    public RoutineDutyFeedback findRoutineDutyFeedbackById(String id) {
        return routineDutyFeedbackDao.findRoutineDutyFeedbackById(id);
    }
    @Transactional
    public void saveRoutineDutyFeedback(RoutineDutyFeedback routineDutyFeedback) {
        routineDutyFeedback.setId(sequenceId.nextId());
        routineDutyFeedbackDao.saveRoutineDutyFeedback(routineDutyFeedback);
    }
    @Transactional(readOnly = true)
    public List<RoutineDutyFeedback> findRoutineDutyFeedbackListByCondition(RoutineDutyFeedback routineDutyFeedback) {
        return routineDutyFeedbackDao.findRoutineDutyFeedbackListByCondition(routineDutyFeedback);
    }
    @Transactional(readOnly = true)
    public RoutineDutyFeedback findOneRoutineDutyFeedbackByCondition(RoutineDutyFeedback routineDutyFeedback) {
        return routineDutyFeedbackDao.findOneRoutineDutyFeedbackByCondition(routineDutyFeedback);
    }
    @Transactional(readOnly = true)
    public long findRoutineDutyFeedbackCountByCondition(RoutineDutyFeedback routineDutyFeedback) {
        return routineDutyFeedbackDao.findRoutineDutyFeedbackCountByCondition(routineDutyFeedback);
    }
    @Transactional
    public void updateRoutineDutyFeedback(RoutineDutyFeedback routineDutyFeedback) {
        routineDutyFeedbackDao.updateRoutineDutyFeedback(routineDutyFeedback);
    }
    @Transactional
    public void deleteRoutineDutyFeedback(String id) {
        routineDutyFeedbackDao.deleteRoutineDutyFeedback(id);
    }
    @Transactional
    public void deleteRoutineDutyFeedbackByCondition(RoutineDutyFeedback routineDutyFeedback) {
        routineDutyFeedbackDao.deleteRoutineDutyFeedbackByCondition(routineDutyFeedback);
    }
    @Transactional
    public void batchSaveRoutineDutyFeedback(List<RoutineDutyFeedback> routineDutyFeedbacks){
        routineDutyFeedbacks.forEach(routineDutyFeedback -> routineDutyFeedback.setId(sequenceId.nextId()));
        routineDutyFeedbackDao.batchSaveRoutineDutyFeedback(routineDutyFeedbacks);
    }

}
