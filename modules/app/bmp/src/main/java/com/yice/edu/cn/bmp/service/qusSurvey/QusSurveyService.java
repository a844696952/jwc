package com.yice.edu.cn.bmp.service.qusSurvey;

import com.yice.edu.cn.bmp.feignClient.qusSurvey.QusSurveyFeign;
import com.yice.edu.cn.common.pojo.jw.qusSurvey.QusSurvey;
import com.yice.edu.cn.common.pojo.jw.qusSurvey.QusSurveyTeacherSendClass;
import com.yice.edu.cn.common.pojo.jw.qusSurvey.SendObjectQusSurvey;
import com.yice.edu.cn.common.pojo.jw.student.StudentQusSurvey;
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

    public long findQusSurveySendObjectListCountByClassId(StudentQusSurvey student) {
        return  qusSurveyFeign.findQusSurveySendObjectListCountByClassId(student);
    }

    public long findQusSurveyTeacherSendClassesCountByCondition(QusSurveyTeacherSendClass qusSurveyTeacherSendClasses) {
        return  qusSurveyFeign.findQusSurveyTeacherSendClassesCountByCondition(qusSurveyTeacherSendClasses);
    }
}
