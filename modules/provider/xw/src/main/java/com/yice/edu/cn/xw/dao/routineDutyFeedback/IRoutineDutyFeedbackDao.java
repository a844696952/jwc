package com.yice.edu.cn.xw.dao.routineDutyFeedback;

import java.util.List;

import com.yice.edu.cn.common.pojo.xw.routineDutyFeedback.RoutineDutyFeedback;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IRoutineDutyFeedbackDao {
    List<RoutineDutyFeedback> findRoutineDutyFeedbackListByCondition(RoutineDutyFeedback routineDutyFeedback);

    long findRoutineDutyFeedbackCountByCondition(RoutineDutyFeedback routineDutyFeedback);

    RoutineDutyFeedback findOneRoutineDutyFeedbackByCondition(RoutineDutyFeedback routineDutyFeedback);

    RoutineDutyFeedback findRoutineDutyFeedbackById(@Param("id") String id);

    void saveRoutineDutyFeedback(RoutineDutyFeedback routineDutyFeedback);

    void updateRoutineDutyFeedback(RoutineDutyFeedback routineDutyFeedback);

    void deleteRoutineDutyFeedback(@Param("id") String id);

    void deleteRoutineDutyFeedbackByCondition(RoutineDutyFeedback routineDutyFeedback);

    void batchSaveRoutineDutyFeedback(List<RoutineDutyFeedback> routineDutyFeedbacks);
}
