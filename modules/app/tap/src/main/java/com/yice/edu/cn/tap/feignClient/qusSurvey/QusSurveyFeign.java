package com.yice.edu.cn.tap.feignClient.qusSurvey;

import com.yice.edu.cn.common.pojo.jw.qusSurvey.*;
import com.yice.edu.cn.common.pojo.jw.student.StudentQusSurvey;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jw",path = "/qusSurvey")
public interface QusSurveyFeign {
    @PostMapping("/findQusSurveySendObjectListByClassId")
    List<SendObjectQusSurvey> findQusSurveySendObjectListByClassId(StudentQusSurvey student);
    @PostMapping("/findQusSurveyTeacherSendClassesListByCondition")
    List<QusSurveyTeacherSendClass> findQusSurveyTeacherSendClassesListByCondition(QusSurveyTeacherSendClass qusSurveyTeacherSendClasses);
    @PostMapping("/findOneQusSurveyByCondition")
    QusSurvey findOneQusSurveyByCondition(QusSurvey qusSurvey);
    @PostMapping("/findSendObjectQusSurveyListByTeacherId")
    List<SendObjectQusSurvey> findSendObjectQusSurveyListByTeacherId(SendObjectQusSurvey sendObjectQusSurvey);
    @PostMapping("/findSendObjectQusSurveyListCountByTeacherId")
    long findSendObjectQusSurveyListCountByTeacherId(SendObjectQusSurvey sendObjectQusSurvey);
    @PostMapping("/getTeacherSendClassTopSum")
    QusSurveyTeacherSendClass getTeacherSendClassTopSum(QusSurveyTeacherSendClass qusSurveyTeacherSendClass);
    @PostMapping("/getQuestionTypeCountList")
    List<QusSurveyQuestion> getQuestionTypeCountList(QusSurveySubmit qusSurveySubmit);
}
