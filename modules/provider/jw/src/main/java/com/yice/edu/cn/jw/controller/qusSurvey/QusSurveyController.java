package com.yice.edu.cn.jw.controller.qusSurvey;

import cn.hutool.json.JSONUtil;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.jw.qusSurvey.*;
import com.yice.edu.cn.common.pojo.jw.student.StudentQusSurvey;
import com.yice.edu.cn.common.pojo.ts.jpush.Extras;
import com.yice.edu.cn.common.pojo.ts.jpush.JpushApp;
import com.yice.edu.cn.common.pojo.ts.jpush.Push;
import com.yice.edu.cn.jw.service.qusSurvey.QusSurveySendObjectService;
import com.yice.edu.cn.jw.service.qusSurvey.QusSurveyService;
import com.yice.edu.cn.jw.service.qusSurvey.QusSurveySubmitService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/qusSurvey")
@Api(value = "/qusSurvey",description = "模块")
public class QusSurveyController {
    @Autowired
    private QusSurveyService qusSurveyService;

    @Autowired
    private QusSurveySendObjectService qusSurveySendObjectService;

    @Autowired
    private QusSurveySubmitService qusSurveySubmitService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @GetMapping("/findQusSurveyById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public QusSurvey findQusSurveyById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return qusSurveyService.findQusSurveyById(id);
    }

    @PostMapping("/saveQusSurvey")
    @ApiOperation(value = "保存", notes = "返回对象")
    public QusSurvey saveQusSurvey(
            @ApiParam(value = "对象", required = true)
            @RequestBody QusSurvey qusSurvey){
        qusSurveyService.saveQusSurvey(qusSurvey);
        return qusSurvey;
    }

    @PostMapping("/findQusSurveyListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<QusSurvey> findQusSurveyListByCondition(
            @ApiParam(value = "对象")
            @RequestBody QusSurvey qusSurvey){
        return qusSurveyService.findQusSurveyListByCondition(qusSurvey);
    }
    @PostMapping("/findQusSurveyCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findQusSurveyCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody QusSurvey qusSurvey){
        return qusSurveyService.findQusSurveyCountByCondition(qusSurvey);
    }

    @PostMapping("/updateQusSurvey")
    @ApiOperation(value = "修改", notes = "对象必传")
    public QusSurvey updateQusSurvey(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody QusSurvey qusSurvey){
      return   qusSurveyService.updateQusSurvey(qusSurvey);
    }

    @GetMapping("/deleteQusSurvey/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteQusSurvey(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        qusSurveyService.deleteQusSurvey(id);
    }
    @PostMapping("/deleteQusSurveyByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deleteQusSurveyByCondition(
            @ApiParam(value = "对象")
            @RequestBody QusSurvey qusSurvey){
        qusSurveyService.deleteQusSurveyByCondition(qusSurvey);
    }
    @PostMapping("/findOneQusSurveyByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public QusSurvey findOneQusSurveyByCondition(
            @ApiParam(value = "对象")
            @RequestBody QusSurvey qusSurvey){
        return qusSurveyService.findOneQusSurveyByCondition(qusSurvey);
    }

    @PostMapping("/ConfirmSend")
    @ApiOperation(value = "确认下发", notes = "返回响应对象")
    public void ConfirmSend(
            @ApiParam(value = "对象", required = true)
            @RequestBody QusSurveySendObject1 qusSurveySendObject1){
        qusSurveyService.ConfirmSend(qusSurveySendObject1);
    }

    @PostMapping("/findSendObjectQusSurveyCountByCondition")
    @ApiOperation(value = "根据条件查询存储问卷的发送对象", notes = "返回响应对象")
    public long findSendObjectQusSurveyCountByCondition(
            @ApiParam(value = "对象", required = true)
            @RequestBody SendObjectQusSurvey sendObjectQusSurvey){
    return  qusSurveyService.findSendObjectQusSurveyCountByCondition(sendObjectQusSurvey);

    }

    @PostMapping("/findSendObjectQusSurveyListByCondition")
    @ApiOperation(value = "根据条件查询存储问卷的发送对象", notes = "返回响应对象")
    public List<SendObjectQusSurvey> findSendObjectQusSurveyListByCondition(
            @ApiParam(value = "对象", required = true)
            @RequestBody SendObjectQusSurvey sendObjectQusSurvey){
        return  qusSurveyService.findSendObjectQusSurveyListByCondition(sendObjectQusSurvey);

    }


    @PostMapping("/findQusSurveySendObjectListByClassId")
    @ApiOperation(value = "根据班级id查找发送列表", notes = "返回列表")
    public List<SendObjectQusSurvey> findQusSurveySendObjectListByClassId(
            @ApiParam(value = "对象")
            @RequestBody StudentQusSurvey student){
        return qusSurveySendObjectService.findQusSurveySendObjectListByClassId(student);
    }

    @PostMapping("/findQusSurveySendObjectListCountByClassId")
    @ApiOperation(value = "根据班级id查找发送列表", notes = "返回列表")
    public long findQusSurveySendObjectListCountByClassId(
            @ApiParam(value = "对象")
            @RequestBody StudentQusSurvey student){
        return qusSurveySendObjectService.findQusSurveySendObjectListCountByClassId(student);
    }

    @PostMapping("/findQusSurveyTeacherSendClassesListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<QusSurveyTeacherSendClass> findQusSurveyTeacherSendClassesListByCondition(
            @ApiParam(value = "对象")
            @RequestBody QusSurveyTeacherSendClass qusSurveyTeacherSendClasses){
        QusSurveySubmit qusSurveySubmit=new QusSurveySubmit();
        qusSurveySubmit.setTeacherId(qusSurveyTeacherSendClasses.getTeacherId());
        qusSurveySubmit.setSurveyId(qusSurveyTeacherSendClasses.getSurveyId());
        if(qusSurveyTeacherSendClasses.getStudentId()!=null) {
            qusSurveySubmit.setStudentId(qusSurveyTeacherSendClasses.getStudentId());
        }
        qusSurveyTeacherSendClasses.setStudentId(null);
        List<QusSurveyTeacherSendClass> qusSurveyTeacherSendClassList= qusSurveyService.findQusSurveyTeacherSendClassesListByCondition(qusSurveyTeacherSendClasses);

        List<QusSurveySubmit> qusSurveySubmitList=qusSurveySubmitService.findQusSurveySubmitListByCondition(qusSurveySubmit);
        qusSurveyTeacherSendClassList.forEach( qusSurveyTeacherSendClass-> {
            qusSurveyTeacherSendClass.setState("1");
            qusSurveySubmitList.forEach(qusSurveySubmit1 -> {
                       if(qusSurveySubmit1.getTeacherId().equals(qusSurveyTeacherSendClass.getTeacherId())){
                           qusSurveyTeacherSendClass.setState("2");
                       }
                    });
        });

        return  qusSurveyTeacherSendClassList;
    }
    @PostMapping("/findQusSurveyTeacherSendClassesCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findQusSurveyTeacherSendClassesCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody QusSurveyTeacherSendClass qusSurveyTeacherSendClasses){
        return qusSurveyService.findQusSurveyTeacherSendClassesCountByCondition(qusSurveyTeacherSendClasses);
    }

    @PostMapping("/getQuestionTypeCountList")
    @ApiOperation(value = " 获取题型统计的结果集", notes = "返回每道题的选项选择次数")
    public List<QusSurveyQuestion> getQuestionTypeCountList(
            @ApiParam(value = "问卷id、老师id")
            @RequestBody  QusSurveySubmit qusSurveySubmit){
        return qusSurveyService.getQuestionTypeCountList(qusSurveySubmit);
    }

    @PostMapping("/getTeacherSendClassTopSum")
    @ApiOperation(value = " 取得老师问卷各个班级一起的平均分、最高分、最低分、总提交人数", notes = "统计结果")
    public QusSurveyTeacherSendClass getTeacherSendClassTopSum(
            @ApiParam(value = "问卷id、老师id")
            @RequestBody  QusSurveyTeacherSendClass qusSurveyTeacherSendClass){
        return qusSurveyService.getTeacherSendClassTopSum(qusSurveyTeacherSendClass);
    }


    @PostMapping("/findSendObjectQusSurveyListByTeacherId")
    @ApiOperation(value = "根据教师id查找发送列表", notes = "发送给老师的问卷信息")
    public List<SendObjectQusSurvey> findSendObjectQusSurveyListByTeacherId(
            @ApiParam(value = "教师id")
            @RequestBody SendObjectQusSurvey sendObjectQusSurvey){
        return qusSurveySendObjectService.findSendObjectQusSurveyListByTeacherId(sendObjectQusSurvey);
    }

    @PostMapping("/findSendObjectQusSurveyListCountByTeacherId")
    @ApiOperation(value = "根据教师id查找发送列表数量", notes = "发送给老师的问卷信息")
    public long  findSendObjectQusSurveyListCountByTeacherId(
            @ApiParam(value = "教师id")
            @RequestBody SendObjectQusSurvey sendObjectQusSurvey){
        return qusSurveySendObjectService.findSendObjectQusSurveyListCountByTeacherId(sendObjectQusSurvey);
    }

    /**
     * 时间截止之后，推送给老师评价的结果
     * @param
     * @return
     */
    @GetMapping("/pushToTeacherByEndTime")
    @ApiOperation(value = "时间截止之后，推送给老师评价的结果", notes = "发送给老师的问卷信息")
    public void pushToTeacherByEndTime(){
        SendObjectQusSurvey sendObjectQusSurvey=new SendObjectQusSurvey();
        sendObjectQusSurvey.setPushState("1");
        List<SendObjectQusSurvey> sendObjectQusSurveyList=qusSurveyService.findSendObjectQusSurveyListToPushByCondition(sendObjectQusSurvey);

        sendObjectQusSurveyList.forEach(sendObject -> {
            List<String> teacherIdList=new ArrayList<>();
            sendObject.getTeacherList().forEach(qusSurveyTeachers -> {
                teacherIdList.add(qusSurveyTeachers.getTeacherId());

            });
            pushToTeacher(teacherIdList,sendObject.getSurveyTitle());
            //更新为已推送
            sendObject.setPushState("2");
            qusSurveyService.updateSaveSendObjectQusSurvey(sendObject);
        });

    }

    public  void pushToTeacher(List<String>teacherIdList ,String title ){
        if(teacherIdList.size()>0){
            //处理推送
            final Push push = Push.newBuilder(JpushApp.TAP).getSimplePush(teacherIdList.toArray(new String[teacherIdList.size()]),"教师评价",title+"结果已出，点击查看详情。",Extras.TAP_SURVEY_TO_TEACHER);
            stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH, JSONUtil.toJsonStr(push));
        }

    }



    @PostMapping("/getTeacherQuestionTypeCountList")
    @ApiOperation(value = " 教师互评获取题型统计的结果集（问卷id(surveyId)、被评老师id(beEvaluatedTeacherId)）", notes = "返回每道题的选项选择次数")
    public List<QusSurveyQuestion>  getTeacherQuestionTypeCountList(
            @ApiParam(value = "问卷id(surveyId)、被评老师id(beEvaluatedTeacherId)")
            @RequestBody QusSurveySubmit qusSurveySubmit){
        return   qusSurveyService.getTeacherQuestionTypeCountList(qusSurveySubmit);

    }


    @PostMapping("/getTeacherEvaluateTopSum")
    @ApiOperation(value = " 获取教师互评的平均分、最高分、最低分（问卷id(surveyId)、被评老师id(beEvaluatedTeacherId)）", notes = "统计结果")
    public  QusSurveySubmit getTeacherEvaluateTopSum(
            @ApiParam(value = "问卷id、老师id")
            @RequestBody QusSurveySubmit qusSurveySubmit){
        return  qusSurveyService.getTeacherEvaluateTopSum(qusSurveySubmit);

    }


    @PostMapping("/findOneSendObjectQusSurveyByCondition")
    @ApiOperation(value = "查询已发送对象，传入问卷id(surveyId)", notes = "发送对象的集合")
    public SendObjectQusSurvey findOneQusSurveySendObjectByCondition(
            @ApiParam(value = "surveyId")
            @RequestBody SendObjectQusSurvey sendObjectQusSurvey){
       return  qusSurveyService.findOneSendObjectQusSurveyByCondition(sendObjectQusSurvey);

    }

    @PostMapping("/lookAlreadyQusSurvey")
    @ApiOperation(value = "教师互评，评价任务已评价去查看页面，传入beEvaluatedTeacherId，surveyId", notes = "返回响应对象")
    public QusSurvey lookAlreadyQusSurvey(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @RequestBody QusSurveySubmit qusSurveySubmit){
        return  qusSurveyService.lookAlreadyQusSurvey(qusSurveySubmit);

    }

}
