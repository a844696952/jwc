package com.yice.edu.cn.osp.controller.jw.qusSurvey;

import cn.hutool.core.bean.BeanUtil;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.qusSurvey.*;
import com.yice.edu.cn.common.pojo.jw.schoolYear.CurSchoolYear;
import com.yice.edu.cn.common.pojo.jw.stuEvaluate.StuEvaluate;
import com.yice.edu.cn.common.pojo.jw.stuEvaluate.StuEvaluateContent;
import com.yice.edu.cn.common.pojo.jw.stuEvaluate.StuEvaluateSendObject;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherClassesForQusSurvey;
import com.yice.edu.cn.osp.service.jw.qusSurvey.QusSurveyQuestionService;
import com.yice.edu.cn.osp.service.jw.qusSurvey.QusSurveySendObjectService;
import com.yice.edu.cn.osp.service.jw.qusSurvey.QusSurveyService;
import com.yice.edu.cn.osp.service.jw.qusSurvey.QusSurveySubmitService;
import com.yice.edu.cn.osp.service.jw.schoolYear.SchoolYearService;
import com.yice.edu.cn.osp.service.jw.stuEvaluate.StuEvaluateContentService;
import com.yice.edu.cn.osp.service.jw.stuEvaluate.StuEvaluateSendObjectService;
import com.yice.edu.cn.osp.service.jw.stuEvaluate.StuEvaluateService;
import com.yice.edu.cn.osp.service.jw.student.StudentService;
import com.yice.edu.cn.osp.service.jw.teacher.TeacherClassesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.myId;
import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/qusSurvey")
@Api(value = "/qusSurvey",description = "填写问卷调查信息模块")
public class QusSurveyController {
    @Autowired
    private QusSurveyService qusSurveyService;
    @Autowired
    private TeacherClassesService teacherClassesService;
    @Autowired
    private QusSurveyQuestionService qusSurveyQuestionService;
    @Autowired
    private QusSurveySendObjectService qusSurveySendObjectService;
    @Autowired
    private QusSurveySubmitService qusSurveySubmitService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private StuEvaluateService stuEvaluateService;
    @Autowired
    private StuEvaluateContentService stuEvaluateContentService;
    @Autowired
    private StuEvaluateSendObjectService stuEvaluateSendObjectService;
    @Autowired
    private SchoolYearService schoolYearService;


    @PostMapping("/save/saveQusSurvey")
    @ApiOperation(value = "保存问卷对象", notes = "返回响应对象")
    public ResponseJson saveQusSurvey(
            @ApiParam(value = "对象", required = true)
            @RequestBody QusSurvey qusSurvey){
        qusSurvey.setSchoolId(mySchoolId());
        QusSurvey s=qusSurveyService.saveQusSurvey(qusSurvey);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findQusSurveyById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象")
    public ResponseJson findQusSurveyById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
        @PathVariable String id){
        QusSurvey qusSurvey=qusSurveyService.findQusSurveyById(id);
        return new ResponseJson(qusSurvey);
    }

    @PostMapping("/update/updateQusSurvey")
    @ApiOperation(value = "修改对象", notes = "返回响应对象")
    public ResponseJson updateQusSurvey(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody QusSurvey qusSurvey){
        QusSurvey s= qusSurveyService.updateQusSurvey(qusSurvey);
        return new ResponseJson(s);
    }

    @GetMapping("/look/lookQusSurveyById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象")
    public ResponseJson lookQusSurveyById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        QusSurvey qusSurvey=qusSurveyService.findQusSurveyById(id);
        return new ResponseJson(qusSurvey);
    }

    @PostMapping("/find/findQusSurveysByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象")
    public ResponseJson findQusSurveysByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody QusSurvey qusSurvey){
        qusSurvey.setSchoolId(mySchoolId());
        Pager pager=qusSurvey.getPager();
        if(pager!=null){
            pager.setLike("title");
        }
        List<QusSurvey> data=qusSurveyService.findQusSurveyListByCondition(qusSurvey);
        long count=qusSurveyService.findQusSurveyCountByCondition(qusSurvey);
        return new ResponseJson(data,count);
    }
    @PostMapping("ignore/findOneQusSurveyByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空")
    public ResponseJson findOneQusSurveyByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody QusSurvey qusSurvey){
        qusSurvey.setSchoolId(mySchoolId());
        QusSurvey one=qusSurveyService.findOneQusSurveyByCondition(qusSurvey);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteQusSurvey/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteQusSurvey(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        qusSurveyService.deleteQusSurvey(id);
        return new ResponseJson();
    }


    @PostMapping("/findQusSurveyListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数")
    public ResponseJson findQusSurveyListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody QusSurvey qusSurvey){
        qusSurvey.setSchoolId(mySchoolId());
        List<QusSurvey> data=qusSurveyService.findQusSurveyListByCondition(qusSurvey);
        return new ResponseJson(data);
    }


    @PostMapping("/saveQusSurveyQuestion")
    @ApiOperation(value = "保存问卷题目表对象", notes = "返回响应对象")
    public ResponseJson saveQusSurveyQuestion(
            @ApiParam(value = "问卷题目表对象", required = true)
            @RequestBody ArrayList<QusSurveyQuestion> qusSurveyQuestionList){
        QusSurveyQuestion s=qusSurveyQuestionService.saveQusSurveyQuestion(qusSurveyQuestionList);
        return new ResponseJson(s);
    }

    @PostMapping("ignore/findClassesByTeacherInfo")
    @ApiOperation(value = "通过教师信息获取教师及对应所教的班级", notes = "返回教师班级列表")
    public ResponseJson findClassesByTeacherInfo(@ApiParam(value = "教师姓名", required = true)
                                                          @RequestBody TeacherClassesForQusSurvey teacher5Classes){
        List<TeacherClassesForQusSurvey> data= teacherClassesService.findClassesByTeacherInfo(teacher5Classes);
        return new ResponseJson(data);
    }

    @PostMapping("/save/saveQusSurveySendObject")
    @ApiOperation(value = "保存发送对象", notes = "返回响应对象")
    public ResponseJson saveQusSurveySendObject(
            @ApiParam(value = "对象", required = true)
            @RequestBody QusSurveySendObject qusSurveySendObject){
        qusSurveySendObject.setSchoolId(mySchoolId());
        QusSurveySendObject s=qusSurveySendObjectService.saveQusSurveySendObject(qusSurveySendObject);
        return new ResponseJson(s);
    }

    @PostMapping("ignore/findOneQusSurveySendObjectByCondition")
    @ApiOperation(value = "查询问卷发送对象，传入问卷id(surveyId)", notes = "发送对象的集合")
    public ResponseJson findOneQusSurveySendObjectByCondition(
            @ApiParam(value = "surveyId")
            @RequestBody QusSurveySendObject qusSurveySendObject){
        QusSurveySendObject one=qusSurveySendObjectService.findOneQusSurveySendObjectByCondition(qusSurveySendObject);
        return new ResponseJson(one);
    }


    @PostMapping("/confirmSend")
    @ApiOperation(value = "确认下发", notes = "返回响应对象")
    public ResponseJson confirmSend(
            @ApiParam(value = "对象", required = true)
            @RequestBody QusSurveySendObject1 qusSurveySendObject1){
        qusSurveyService.ConfirmSend(qusSurveySendObject1);
        return new ResponseJson();
    }

    @PostMapping("/findSendObjectQusSurveyListByCondition")
    @ApiOperation(value = "根据条件查询存储问卷的发送对象", notes = "返回响应对象")
    public ResponseJson findSendObjectQusSurveyListByCondition(
            @ApiParam(value = "对象", required = true)
            @RequestBody SendObjectQusSurvey sendObjectQusSurvey){
        List<SendObjectQusSurvey> data=  qusSurveyService.findSendObjectQusSurveyListByCondition(sendObjectQusSurvey);
        return new ResponseJson(data);
    }

    @PostMapping("ignore/findQusSurveyTeacherSendClassesByCondition")
    @ApiOperation(value = "取得每个老师问卷的平均分、最高分、最低分（问卷id(surveyId)、老师id(teacherId)）", notes = "返回响应对象")
    public ResponseJson findQusSurveyTeacherSendClassesByCondition(
            @ApiParam(value = "问卷id(surveyId)、老师id(teacherId)")
            @Validated
            @RequestBody QusSurveyTeacherSendClass qusSurveyTeacherSendClasses){
        List<QusSurveyTeacherSendClass> data=qusSurveyService.findQusSurveyTeacherSendClassesListByCondition(qusSurveyTeacherSendClasses);
        long count=qusSurveyService.findQusSurveyTeacherSendClassesCountByCondition(qusSurveyTeacherSendClasses);
        return new ResponseJson(data,count);
    }

    @PostMapping("ignore/getQuestionTypeCountList")
    @ApiOperation(value = " 获取题型统计的结果集（问卷id(surveyId)、老师id(teacherId)）", notes = "返回每道题的选项选择次数")
    public ResponseJson getQuestionTypeCountList(
            @ApiParam(value = "问卷id(surveyId)、老师id(teacherId)")
            @RequestBody  QusSurveySubmit qusSurveySubmit){
        List<QusSurveyQuestion> data= qusSurveyService.getQuestionTypeCountList(qusSurveySubmit);
        return new ResponseJson(data);
    }

    @PostMapping("ignore/getTeacherSendClassTopSum")
    @ApiOperation(value = " 取得老师问卷各个班级一起的平均分、最高分、最低分（问卷id(surveyId)、老师id(teacherId)）", notes = "统计结果")
    public ResponseJson getTeacherSendClassTopSum(
            @ApiParam(value = "问卷id、老师id")
            @RequestBody  QusSurveyTeacherSendClass qusSurveyTeacherSendClass){
        QusSurveyTeacherSendClass data= qusSurveyService.getTeacherSendClassTopSum(qusSurveyTeacherSendClass);
        return  new ResponseJson(data);
    }




    @PostMapping("/look/getQuestionAnswerList")
    @ApiOperation(value = "获取问答题答案:传入班级id:classId，问卷id:surveyId,老师id:teacherId", notes = "返回响应对象")
    public ResponseJson getQuestionAnswerList(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody QuestionAnswer questionAnswer){
        //获取班级总人数
        Student student=new Student();
        student.setClassesId(questionAnswer.getClassId());
        long num =studentService.findStudentCountByCondition(student);
        questionAnswer.setClassStuNum(String.valueOf(num));
        //获取问答题问题
        QusSurvey qusSurvey=qusSurveyService.findQusSurveyById(questionAnswer.getSurveyId());
       String s= qusSurvey.getQusSurveyQuestions().stream().filter(qusSurveyQuestion -> qusSurveyQuestion.getQusTypeId().equals("3"))
                  .filter(qusSurveyQuestion -> Objects.nonNull(qusSurveyQuestion)).map(v -> v.getQusContent()).findAny().get();
       questionAnswer.setQuestion(s);


       //获取问答题答案集
        QusSurveySubmit qusSurveySubmit=new QusSurveySubmit();
        qusSurveySubmit.setSurveyId(questionAnswer.getSurveyId());
        qusSurveySubmit.setClassId(questionAnswer.getClassId());
        qusSurveySubmit.setTeacherId(questionAnswer.getTeacherId());
        List<QusSurveySubmit> qusSurveySubmitList=qusSurveySubmitService.findQusSurveySubmitListByCondition(qusSurveySubmit);

        List<String> answer=new ArrayList<>();
        qusSurveySubmitList.forEach(qusSurveySubmit1 -> {
            answer.add(qusSurveySubmit1.getQuestionAnswer());
        });
        questionAnswer.setQuestionAnswer(answer);
        return new ResponseJson(questionAnswer);
    }


    /**
     * 问卷调查与学生评价合并了，所以以下将评价的权限与问卷权限归为一类
     */

    @PostMapping("/find/findStuEvaluatesByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象")
    public ResponseJson findStuEvaluatesByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody StuEvaluate stuEvaluate){
        stuEvaluate.setSchoolId(mySchoolId());
        Pager pager=stuEvaluate.getPager();
        if(pager!=null){
            pager.setLike("title");
        }
        List<StuEvaluate> data=stuEvaluateService.findStuEvaluateListByCondition(stuEvaluate);
        long count=stuEvaluateService.findStuEvaluateCountByCondition(stuEvaluate);
        return new ResponseJson(data,count);
    }
    @PostMapping("look/findStuEvaluateContentsByCondition")
    @ApiOperation(value = "根据评价evaluateId,和班级id：classId，返回学生评价列表", notes = "返回响应对象")
    public ResponseJson findStuEvaluateContentsByCondition(
            @ApiParam(value = "传入评价evaluateId,和班级id：classId")
            @Validated
            @RequestBody StuEvaluateContent stuEvaluateContent){
        stuEvaluateContent.setEvaluateState("2");
        List<StuEvaluateContent> data=stuEvaluateContentService.findStuEvaluateContentListByCondition(stuEvaluateContent);
        long count=stuEvaluateContentService.findStuEvaluateContentCountByCondition(stuEvaluateContent);
        return new ResponseJson(data,count);
    }

    @PostMapping("/confirmSend/saveStuEvaluateSendObject")
    @ApiOperation(value = "保存发送对象", notes = "返回对象")
    public ResponseJson  saveStuEvaluateSendObject(
            @ApiParam(value = "对象", required = true)
            @RequestBody ArrayList<StuEvaluateSendObject> sendObjectList){
        sendObjectList.get(0).getStuEvaluate().setSchoolId(mySchoolId());
        CurSchoolYear curSchoolYear=schoolYearService.findCurSchoolYear(mySchoolId());
        sendObjectList.forEach(s->{
            s.setSchoolYearId(curSchoolYear.getSchoolYearId());
            s.setFromTo(curSchoolYear.getFromTo());
            s.setTerm(curSchoolYear.getTerm());
        });
        stuEvaluateService.saveStuEvaluateSendObject(sendObjectList);
        return new ResponseJson();
    }


    @GetMapping("look/findStuEvaluateSendObjectListByEvaluateId/{evaluateId}")
    @ApiOperation(value = "学生评价查看", notes = "没有时返回空")
    public ResponseJson findStuEvaluateSendObjectListByEvaluateId(
            @ApiParam(value = "传入evaluateId")
            @PathVariable String evaluateId){
        StuEvaluateSendObject stuEvaluateSendObject=new StuEvaluateSendObject();
        StuEvaluate stuEvaluate=new StuEvaluate();
        stuEvaluate.setId(evaluateId);
        stuEvaluateSendObject.setStuEvaluate(stuEvaluate);
        List<StuEvaluateSendObject> data=stuEvaluateSendObjectService.findStuEvaluateSendObjectListByCondition(stuEvaluateSendObject);
        return new ResponseJson(data);
    }


    /**
     * 以下为教师互评接口
     */

    @PostMapping("/findSendObjectQusSurveysByCondition")
    @ApiOperation(value = "教师互评任务接口", notes = "返回响应对象")
    public ResponseJson findSendObjectQusSurveysByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody SendObjectQusSurvey sendObjectQusSurvey){
        sendObjectQusSurvey.setSchoolId(mySchoolId());
        sendObjectQusSurvey.setTeacherId(myId());
        SendObjectQusSurvey sendObjectQusSurvey1=new SendObjectQusSurvey();
        BeanUtil.copyProperties(sendObjectQusSurvey,sendObjectQusSurvey1);
        List<SendObjectQusSurvey> data=qusSurveyService.findSendObjectQusSurveyListByCondition(sendObjectQusSurvey);
        long count=qusSurveyService.findSendObjectQusSurveyCountByCondition(sendObjectQusSurvey1);
        return new ResponseJson(data,count);
    }

    @PostMapping("/getTeacherQuestionTypeCountList")
    @ApiOperation(value = " 教师互评获取题型统计的结果集（问卷id(surveyId)、被评老师id(beEvaluatedTeacherId)）", notes = "返回每道题的选项选择次数")
    public ResponseJson getTeacherQuestionTypeCountList(
            @ApiParam(value = "问卷id(surveyId)、被评老师id(beEvaluatedTeacherId)")
            @RequestBody QusSurveySubmit qusSurveySubmit){
        List<QusSurveyQuestion> data= qusSurveyService.getTeacherQuestionTypeCountList(qusSurveySubmit);
        return new ResponseJson(data);
    }


    @PostMapping("/getTeacherEvaluateTopSum")
    @ApiOperation(value = " 获取教师互评的平均分、最高分、最低分（问卷id(surveyId)、被评老师id(beEvaluatedTeacherId)）", notes = "统计结果")
    public ResponseJson getTeacherEvaluateTopSum(
            @ApiParam(value = "问卷id、老师id")
            @RequestBody QusSurveySubmit qusSurveySubmit){
        qusSurveySubmit.setSubmitStatus("2");
        QusSurveySubmit data= qusSurveyService.getTeacherEvaluateTopSum(qusSurveySubmit);
        return  new ResponseJson(data);
    }

    @PostMapping("/findOneSendObjectQusSurveyByCondition")
    @ApiOperation(value = "查询已发送对象，传入问卷id(surveyId)", notes = "发送对象的集合")
    public ResponseJson findOneQusSurveySendObjectByCondition(
            @ApiParam(value = "surveyId")
            @RequestBody SendObjectQusSurvey sendObjectQusSurvey){
        SendObjectQusSurvey one=qusSurveyService.findOneSendObjectQusSurveyByCondition(sendObjectQusSurvey);
        return new ResponseJson(one);
    }


    @PostMapping("/lookAlreadyQusSurvey")
    @ApiOperation(value = "教师互评，评价任务已评价去查看页面，传入beEvaluatedTeacherId，surveyId", notes = "返回响应对象")
    public ResponseJson lookAlreadyQusSurvey(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @RequestBody QusSurveySubmit qusSurveySubmit){
        qusSurveySubmit.setEvaluateTeacherId(myId());
        QusSurvey qusSurvey=qusSurveyService.lookAlreadyQusSurvey(qusSurveySubmit);
        return new ResponseJson(qusSurvey);
    }
}


