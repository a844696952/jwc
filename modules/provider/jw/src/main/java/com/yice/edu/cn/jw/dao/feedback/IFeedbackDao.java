package com.yice.edu.cn.jw.dao.feedback;

import java.util.List;

import com.yice.edu.cn.common.pojo.jw.feedback.Feedback;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IFeedbackDao {
    List<Feedback> findFeedbackListByCondition(Feedback feedback);

    Feedback findOneFeedbackByCondition(Feedback feedback);

    long findFeedbackCountByCondition(Feedback feedback);

    Feedback findFeedbackById(@Param("id") String id);

    void saveFeedback(Feedback feedback);

    void updateFeedback(Feedback feedback);

    void deleteFeedback(@Param("id") String id);

    void deleteFeedbackByCondition(Feedback feedback);

    void batchSaveFeedback(List<Feedback> feedbacks);

    List<Feedback> findFeedbackListByCondition4Like(Feedback feedback);

    long findFeedbackCountByCondition4Like(Feedback feedback);
}
