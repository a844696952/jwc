package com.yice.edu.cn.xw.dao.dutyFeedback;

import java.util.List;

import com.yice.edu.cn.common.pojo.xw.dutyFeedback.DutyFeedback;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IDutyFeedbackDao {
    List<DutyFeedback> findDutyFeedbackListByCondition(DutyFeedback dutyFeedback);

    DutyFeedback findOneDutyFeedbackByCondition(DutyFeedback dutyFeedback);

    long findDutyFeedbackCountByCondition(DutyFeedback dutyFeedback);

    DutyFeedback findDutyFeedbackById(@Param("id") String id);

    void saveDutyFeedback(DutyFeedback dutyFeedback);

    void updateDutyFeedback(DutyFeedback dutyFeedback);

    void deleteDutyFeedback(@Param("id") String id);

    void deleteDutyFeedbackByCondition(DutyFeedback dutyFeedback);

    void batchSaveDutyFeedback(List<DutyFeedback> dutyFeedbacks);

    List<DutyFeedback> findDutyFeedbackListByTimeCondition(DutyFeedback dutyFeedback);

    long findDutyFeedbackCountByTimeCondition(DutyFeedback dutyFeedback);
}
