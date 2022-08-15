package com.yice.edu.cn.tap.service.qusSurvey;

import com.yice.edu.cn.common.pojo.jw.qusSurvey.*;
import com.yice.edu.cn.common.pojo.jw.student.StudentQusSurvey;
import com.yice.edu.cn.tap.feignClient.qusSurvey.QusSurveyFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QusSurveyService {
    @Autowired
    private QusSurveyFeign qusSurveyFeign;
    public List<SendObjectQusSurvey> findQusSurveySendObjectListByClassId(
            StudentQusSurvey student){
        return qusSurveyFeign.findQusSurveySendObjectListByClassId(student);
    }

    public List<QusSurveyTeacherSendClass> findQusSurveyTeacherSendClassesListByCondition(QusSurveyTeacherSendClass qusSurveyTeacherSendClasses) {
        return qusSurveyFeign.findQusSurveyTeacherSendClassesListByCondition(qusSurveyTeacherSendClasses);
    }

    public QusSurvey findOneQusSurveyByCondition(QusSurvey qusSurvey) {
        return qusSurveyFeign.findOneQusSurveyByCondition(qusSurvey);
    }

    public List<SendObjectQusSurvey> findSendObjectQusSurveyListByTeacherId(SendObjectQusSurvey sendObjectQusSurvey) {
        return qusSurveyFeign.findSendObjectQusSurveyListByTeacherId(sendObjectQusSurvey);
    }
    public long findSendObjectQusSurveyListCountByTeacherId(SendObjectQusSurvey sendObjectQusSurvey) {
        return qusSurveyFeign.findSendObjectQusSurveyListCountByTeacherId(sendObjectQusSurvey);
    }

    public QusSurveyTeacherSendClass getTeacherSendClassTopSum(QusSurveyTeacherSendClass qusSurveyTeacherSendClass) {
        return qusSurveyFeign.getTeacherSendClassTopSum(qusSurveyTeacherSendClass);
    }

    public List<QusSurveyQuestion> getQuestionTypeCountList(QusSurveySubmit qusSurveySubmit) {
        return qusSurveyFeign.getQuestionTypeCountList(qusSurveySubmit);
    }
}
