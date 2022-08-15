package com.yice.edu.cn.osp.service.jw.qusSurvey;

import com.yice.edu.cn.common.pojo.jw.qusSurvey.QusSurveyQuestion;
import com.yice.edu.cn.osp.feignClient.jw.qusSurvey.QusSurveyQuestionFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QusSurveyQuestionService {
    @Autowired
    private QusSurveyQuestionFeign qusSurveyQuestionFeign;

    public QusSurveyQuestion findQusSurveyQuestionById(String id) {
        return qusSurveyQuestionFeign.findQusSurveyQuestionById(id);
    }

    public QusSurveyQuestion saveQusSurveyQuestion(ArrayList<QusSurveyQuestion> qusSurveyQuestionList) {
        return qusSurveyQuestionFeign.saveQusSurveyQuestion(qusSurveyQuestionList);
    }

    public List<QusSurveyQuestion> findQusSurveyQuestionListByCondition(QusSurveyQuestion qusSurveyQuestion) {
        return qusSurveyQuestionFeign.findQusSurveyQuestionListByCondition(qusSurveyQuestion);
    }

    public QusSurveyQuestion findOneQusSurveyQuestionByCondition(QusSurveyQuestion qusSurveyQuestion) {
        return qusSurveyQuestionFeign.findOneQusSurveyQuestionByCondition(qusSurveyQuestion);
    }

    public long findQusSurveyQuestionCountByCondition(QusSurveyQuestion qusSurveyQuestion) {
        return qusSurveyQuestionFeign.findQusSurveyQuestionCountByCondition(qusSurveyQuestion);
    }

    public void updateQusSurveyQuestion(QusSurveyQuestion qusSurveyQuestion) {
        qusSurveyQuestionFeign.updateQusSurveyQuestion(qusSurveyQuestion);
    }

    public void deleteQusSurveyQuestion(String id) {
        qusSurveyQuestionFeign.deleteQusSurveyQuestion(id);
    }

    public void deleteQusSurveyQuestionByCondition(QusSurveyQuestion qusSurveyQuestion) {
        qusSurveyQuestionFeign.deleteQusSurveyQuestionByCondition(qusSurveyQuestion);
    }
}
