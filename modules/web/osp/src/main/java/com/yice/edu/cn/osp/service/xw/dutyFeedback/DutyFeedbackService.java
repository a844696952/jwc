package com.yice.edu.cn.osp.service.xw.dutyFeedback;

import com.yice.edu.cn.common.pojo.xw.dutyFeedback.DutyFeedback;
import com.yice.edu.cn.osp.feignClient.xw.dutyFeedback.DutyFeedbackFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DutyFeedbackService {
    @Autowired
    private DutyFeedbackFeign dutyFeedbackFeign;

    public DutyFeedback findDutyFeedbackById(String id) {
        return dutyFeedbackFeign.findDutyFeedbackById(id);
    }

    public DutyFeedback saveDutyFeedback(DutyFeedback dutyFeedback) {
        return dutyFeedbackFeign.saveDutyFeedback(dutyFeedback);
    }

    public List<DutyFeedback> findDutyFeedbackListByCondition(DutyFeedback dutyFeedback) {
        return dutyFeedbackFeign.findDutyFeedbackListByCondition(dutyFeedback);
    }

    public DutyFeedback findOneDutyFeedbackByCondition(DutyFeedback dutyFeedback) {
        return dutyFeedbackFeign.findOneDutyFeedbackByCondition(dutyFeedback);
    }

    public long findDutyFeedbackCountByCondition(DutyFeedback dutyFeedback) {
        return dutyFeedbackFeign.findDutyFeedbackCountByCondition(dutyFeedback);
    }

    public void updateDutyFeedback(DutyFeedback dutyFeedback) {
        dutyFeedbackFeign.updateDutyFeedback(dutyFeedback);
    }

    public void deleteDutyFeedback(String id) {
        dutyFeedbackFeign.deleteDutyFeedback(id);
    }

    public void deleteDutyFeedbackByCondition(DutyFeedback dutyFeedback) {
        dutyFeedbackFeign.deleteDutyFeedbackByCondition(dutyFeedback);
    }
}
