package com.yice.edu.cn.tap.controller.qusSurvey;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.qusSurvey.*;
import com.yice.edu.cn.tap.service.qusSurvey.QusSurveyService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

import static com.yice.edu.cn.tap.interceptor.LoginInterceptor.myId;

@RestController
@RequestMapping("/qusSurvey")
public class QusSurveyController {
    @Autowired
    private QusSurveyService qusSurveyService;
    @PostMapping("ignore/findOneQusSurveyByCondition")
    @ApiOperation(value = "传入问卷id，查询单个问卷对象", notes = "没有时返回空")
    public ResponseJson findOneQusSurveyByCondition(
            @ApiParam(value = "问卷id(surveyId)")
            @RequestBody QusSurvey qusSurvey){
        QusSurvey one=qusSurveyService.findOneQusSurveyByCondition(qusSurvey);
        return new ResponseJson(one);
    }



    @PostMapping("/findSendObjectQusSurveyListByTeacherId")
    @ApiOperation(value = "根据教师id查找发送列表", notes = "发送给老师的问卷信息")
    public ResponseJson findSendObjectQusSurveyListByTeacherId(@RequestBody SendObjectQusSurvey sendObjectQusSurvey){
        String teacherId=myId();
        sendObjectQusSurvey.setTeacherId(teacherId);
        List<SendObjectQusSurvey> data=  qusSurveyService.findSendObjectQusSurveyListByTeacherId(sendObjectQusSurvey);
        data.forEach(sendObjectQusSurvey1 -> {
            sendObjectQusSurvey1.setTeacherList(null);
        });
        long count=qusSurveyService.findSendObjectQusSurveyListCountByTeacherId(sendObjectQusSurvey);
        return new ResponseJson(data,count);
    }


    @PostMapping("/getTeacherSendClassTopSum")
    @ApiOperation(value = " 取得老师问卷各个班级一起的平均分、最高分、最低分、总提交人数", notes = "统计结果")
    public ResponseJson getTeacherSendClassTopSum(
            @ApiParam(value = "问卷id、老师id")
            @RequestBody  QusSurveyTeacherSendClass qusSurveyTeacherSendClass){
        QusSurveyTeacherSendClass data= qusSurveyService.getTeacherSendClassTopSum(qusSurveyTeacherSendClass);
        return  new ResponseJson(data);
    }

    @PostMapping("ignore/getQuestionTypeCountList")
    @ApiOperation(value = " 获取题型统计的结果集（问卷id(surveyId)", notes = "返回每道题的选项选择次数")
    public ResponseJson getQuestionTypeCountList(
            @ApiParam(value = "问卷id(surveyId)")
            @RequestBody  QusSurveySubmit qusSurveySubmit){
        String teacherId=myId();
        qusSurveySubmit.setTeacherId(teacherId);
        List<QusSurveyQuestion> data= qusSurveyService.getQuestionTypeCountList(qusSurveySubmit);
        List<QusSurveyQuestion> data1=data.stream().filter(q -> !q.getQusTypeId().equals("3")).collect(Collectors.toList());
        return new ResponseJson(data1);
    }

}
