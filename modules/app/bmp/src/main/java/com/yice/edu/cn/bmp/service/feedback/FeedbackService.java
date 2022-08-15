package com.yice.edu.cn.bmp.service.feedback;

import com.yice.edu.cn.bmp.feignClient.feedback.FeedbackFeign;
import com.yice.edu.cn.common.pojo.jw.feedback.Feedback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackService {
    @Autowired
    private FeedbackFeign feedbackFeign;

    public Feedback findFeedbackById(String id) {
        return feedbackFeign.findFeedbackById(id);
    }

    public Feedback saveFeedback(Feedback feedback) {
        return feedbackFeign.saveFeedback(feedback);
    }

    public List<Feedback> findFeedbackListByCondition(Feedback feedback) {
        return feedbackFeign.findFeedbackListByCondition(feedback);
    }

    public Feedback findOneFeedbackByCondition(Feedback feedback) {
        return feedbackFeign.findOneFeedbackByCondition(feedback);
    }

    public long findFeedbackCountByCondition(Feedback feedback) {
        return feedbackFeign.findFeedbackCountByCondition(feedback);
    }

    public void updateFeedback(Feedback feedback) {
        feedbackFeign.updateFeedback(feedback);
    }

    public void deleteFeedback(String id) {
        feedbackFeign.deleteFeedback(id);
    }

    public void deleteFeedbackByCondition(Feedback feedback) {
        feedbackFeign.deleteFeedbackByCondition(feedback);
    }
}
