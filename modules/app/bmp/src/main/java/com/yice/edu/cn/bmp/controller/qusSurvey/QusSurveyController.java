package com.yice.edu.cn.bmp.controller.qusSurvey;

import com.yice.edu.cn.bmp.service.qusSurvey.QusSurveyOptionService;
import com.yice.edu.cn.bmp.service.qusSurvey.QusSurveyService;
import com.yice.edu.cn.bmp.service.qusSurvey.QusSurveySubmitService;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.qusSurvey.*;
import com.yice.edu.cn.common.pojo.jw.student.StudentQusSurvey;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.yice.edu.cn.bmp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/qusSurvey")
public class QusSurveyController {
    @Autowired
    private QusSurveyService qusSurveyService;
    @Autowired
    private QusSurveySubmitService qusSurveySubmitService;
    @Autowired
    private QusSurveyOptionService qusSurveyOptionService;

    @PostMapping("/findQusSurveySendObjectListByClassId")
    @ApiOperation(value = "根据班级id查找发送列表", notes = "返回列表")
    public ResponseJson findQusSurveySendObjectListByClassId(
            @ApiParam(value = "对象")
            @RequestBody StudentQusSurvey student){
        List<SendObjectQusSurvey> data=qusSurveyService.findQusSurveySendObjectListByClassId(student);
        long count=qusSurveyService.findQusSurveySendObjectListCountByClassId(student);
        return  new ResponseJson(data,count);
    }


    @PostMapping("/saveQusSurveySubmit")
    @ApiOperation(value = "保存提交整个问卷记录的表对象", notes = "返回响应对象")
    public ResponseJson saveQusSurveySubmit(
            @ApiParam(value = "提交整个问卷记录的表对象", required = true)
            @RequestBody QusSurveySubmit qusSurveySubmit){
        qusSurveySubmit.setSchoolId(mySchoolId());
        QusSurveySubmit s=qusSurveySubmitService.saveQusSurveySubmit(qusSurveySubmit);
        return new ResponseJson(s);
    }

    @PostMapping("/findQusSurveyTeacherSendClassesListByCondition")
    @ApiOperation(value = "查询单个问卷的涉及老师的列表", notes = "返回响应对象,不包含总条数")
    public ResponseJson findQusSurveyTeacherSendClassesListByCondition(
            @ApiParam(value = "传入surveyId、classId、studentId")
            @Validated
            @RequestBody QusSurveyTeacherSendClass qusSurveyTeacherSendClass){
        List<QusSurveyTeacherSendClass> data=qusSurveyService.findQusSurveyTeacherSendClassesListByCondition(qusSurveyTeacherSendClass);
        long count=qusSurveyService.findQusSurveyTeacherSendClassesCountByCondition(qusSurveyTeacherSendClass);
        return new ResponseJson(data,count);
    }


//    @PostMapping("/saveQusSurveyOption")
//    @ApiOperation(value = "保存学生提交选项保存的记录的表对象", notes = "返回响应对象")
//    public ResponseJson saveQusSurveyOption(
//            @ApiParam(value = "学生提交选项保存的记录的表对象", required = true)
//            @RequestBody QusSurveyOption qusSurveyOption){
//        QusSurveyOption s=qusSurveyOptionService.saveQusSurveyOption(qusSurveyOption);
//        return new ResponseJson(s);
//    }

    @PostMapping("ignore/findOneQusSurveyByCondition")
    @ApiOperation(value = "传入问卷id，查询单个问卷对象", notes = "没有时返回空")
    public ResponseJson findOneQusSurveyByCondition(
            @ApiParam(value = "id")
            @RequestBody QusSurvey qusSurvey){
        QusSurvey one=qusSurveyService.findOneQusSurveyByCondition(qusSurvey);
        return new ResponseJson(one);
    }

}
