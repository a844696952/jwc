package com.yice.edu.cn.bmp.feignClient.qusSurvey;

import com.yice.edu.cn.common.pojo.jw.qusSurvey.QusSurvey;
import com.yice.edu.cn.common.pojo.jw.qusSurvey.QusSurveyTeacherSendClass;
import com.yice.edu.cn.common.pojo.jw.qusSurvey.SendObjectQusSurvey;
import com.yice.edu.cn.common.pojo.jw.student.StudentQusSurvey;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jw",contextId = "qusSurveyFeign",path = "/qusSurvey")
public interface QusSurveyFeign {
    @PostMapping("/findQusSurveySendObjectListByClassId")
    List<SendObjectQusSurvey> findQusSurveySendObjectListByClassId(StudentQusSurvey student);
    @PostMapping("/findQusSurveyTeacherSendClassesListByCondition")
    List<QusSurveyTeacherSendClass> findQusSurveyTeacherSendClassesListByCondition(QusSurveyTeacherSendClass qusSurveyTeacherSendClasses);
    @PostMapping("/findOneQusSurveyByCondition")
    QusSurvey findOneQusSurveyByCondition(QusSurvey qusSurvey);
    @PostMapping("/findQusSurveySendObjectListCountByClassId")
    long findQusSurveySendObjectListCountByClassId(StudentQusSurvey student);
    @PostMapping("/findQusSurveyTeacherSendClassesCountByCondition")
    long findQusSurveyTeacherSendClassesCountByCondition(QusSurveyTeacherSendClass qusSurveyTeacherSendClasses);
}
