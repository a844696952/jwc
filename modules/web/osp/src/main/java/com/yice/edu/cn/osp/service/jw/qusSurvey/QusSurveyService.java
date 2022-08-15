package com.yice.edu.cn.osp.service.jw.qusSurvey;

import com.yice.edu.cn.common.pojo.jw.qusSurvey.*;
import com.yice.edu.cn.osp.feignClient.jw.qusSurvey.QusSurveyFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QusSurveyService {
    @Autowired
    private QusSurveyFeign qusSurveyFeign;

    public QusSurvey findQusSurveyById(String id) {
        return qusSurveyFeign.findQusSurveyById(id);
    }

    public QusSurvey saveQusSurvey(QusSurvey qusSurvey) {
        return qusSurveyFeign.saveQusSurvey(qusSurvey);
    }

    public List<QusSurvey> findQusSurveyListByCondition(QusSurvey qusSurvey) {
        return qusSurveyFeign.findQusSurveyListByCondition(qusSurvey);
    }

    public QusSurvey findOneQusSurveyByCondition(QusSurvey qusSurvey) {
        return qusSurveyFeign.findOneQusSurveyByCondition(qusSurvey);
    }

    public long findQusSurveyCountByCondition(QusSurvey qusSurvey) {
        return qusSurveyFeign.findQusSurveyCountByCondition(qusSurvey);
    }

    public QusSurvey updateQusSurvey(QusSurvey qusSurvey) {
        return qusSurveyFeign.updateQusSurvey(qusSurvey);
    }

    public void deleteQusSurvey(String id) {
        qusSurveyFeign.deleteQusSurvey(id);
    }

    public void deleteQusSurveyByCondition(QusSurvey qusSurvey) {
        qusSurveyFeign.deleteQusSurveyByCondition(qusSurvey);
    }
    public void ConfirmSend( QusSurveySendObject1 qusSurveySendObject1) {
    qusSurveyFeign.ConfirmSend(qusSurveySendObject1);
}
    public List<SendObjectQusSurvey> findSendObjectQusSurveyListByCondition(SendObjectQusSurvey sendObjectQusSurvey){
     return    qusSurveyFeign.findSendObjectQusSurveyListByCondition(sendObjectQusSurvey);
    }

    public List<QusSurveyTeacherSendClass> findQusSurveyTeacherSendClassesListByCondition(QusSurveyTeacherSendClass qusSurveyTeacherSendClasses) {
        return qusSurveyFeign.findQusSurveyTeacherSendClassesListByCondition(qusSurveyTeacherSendClasses);
    }

    public long findQusSurveyTeacherSendClassesCountByCondition(QusSurveyTeacherSendClass qusSurveyTeacherSendClasses) {
        return qusSurveyFeign.findQusSurveyTeacherSendClassesCountByCondition(qusSurveyTeacherSendClasses);
    }


    public List<QusSurveyQuestion> getQuestionTypeCountList(QusSurveySubmit qusSurveySubmit) {
        return qusSurveyFeign.getQuestionTypeCountList(qusSurveySubmit);
    }

    public QusSurveyTeacherSendClass getTeacherSendClassTopSum(QusSurveyTeacherSendClass qusSurveyTeacherSendClass) {
        return qusSurveyFeign.getTeacherSendClassTopSum(qusSurveyTeacherSendClass);
    }

    public long findSendObjectQusSurveyCountByCondition(SendObjectQusSurvey sendObjectQusSurvey1) {
        return qusSurveyFeign.findSendObjectQusSurveyCountByCondition(sendObjectQusSurvey1);
    }

    public List<QusSurveyQuestion> getTeacherQuestionTypeCountList(QusSurveySubmit qusSurveySubmit) {
        return qusSurveyFeign.getTeacherQuestionTypeCountList(qusSurveySubmit);
    }

    public QusSurveySubmit getTeacherEvaluateTopSum(QusSurveySubmit qusSurveySubmit) {
        return qusSurveyFeign.getTeacherEvaluateTopSum(qusSurveySubmit);
    }

    public SendObjectQusSurvey findOneSendObjectQusSurveyByCondition(SendObjectQusSurvey sendObjectQusSurvey) {
        return qusSurveyFeign.findOneSendObjectQusSurveyByCondition(sendObjectQusSurvey);
    }

    public QusSurvey lookAlreadyQusSurvey(QusSurveySubmit qusSurveySubmit) {
        return qusSurveyFeign.lookAlreadyQusSurvey(qusSurveySubmit);
    }
}
