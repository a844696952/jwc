package com.yice.edu.cn.osp.service.xw.routineDutyFeedback;

import com.yice.edu.cn.common.pojo.xw.routineDutyFeedback.RoutineDutyFeedback;
import com.yice.edu.cn.osp.feignClient.xw.routineDutyFeedback.RoutineDutyFeedbackFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoutineDutyFeedbackService {
    @Autowired
    private RoutineDutyFeedbackFeign routineDutyFeedbackFeign;

    public RoutineDutyFeedback findRoutineDutyFeedbackById(String id) {
        return routineDutyFeedbackFeign.findRoutineDutyFeedbackById(id);
    }

    public RoutineDutyFeedback saveRoutineDutyFeedback(RoutineDutyFeedback routineDutyFeedback) {
        return routineDutyFeedbackFeign.saveRoutineDutyFeedback(routineDutyFeedback);
    }

    public List<RoutineDutyFeedback> findRoutineDutyFeedbackListByCondition(RoutineDutyFeedback routineDutyFeedback) {
        return routineDutyFeedbackFeign.findRoutineDutyFeedbackListByCondition(routineDutyFeedback);
    }

    public RoutineDutyFeedback findOneRoutineDutyFeedbackByCondition(RoutineDutyFeedback routineDutyFeedback) {
        return routineDutyFeedbackFeign.findOneRoutineDutyFeedbackByCondition(routineDutyFeedback);
    }

    public long findRoutineDutyFeedbackCountByCondition(RoutineDutyFeedback routineDutyFeedback) {
        return routineDutyFeedbackFeign.findRoutineDutyFeedbackCountByCondition(routineDutyFeedback);
    }

    public void updateRoutineDutyFeedback(RoutineDutyFeedback routineDutyFeedback) {
        routineDutyFeedbackFeign.updateRoutineDutyFeedback(routineDutyFeedback);
    }

    public void deleteRoutineDutyFeedback(String id) {
        routineDutyFeedbackFeign.deleteRoutineDutyFeedback(id);
    }

    public void deleteRoutineDutyFeedbackByCondition(RoutineDutyFeedback routineDutyFeedback) {
        routineDutyFeedbackFeign.deleteRoutineDutyFeedbackByCondition(routineDutyFeedback);
    }
}
