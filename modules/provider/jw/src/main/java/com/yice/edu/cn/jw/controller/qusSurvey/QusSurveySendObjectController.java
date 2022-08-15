package com.yice.edu.cn.jw.controller.qusSurvey;

import cn.hutool.core.date.DateUtil;
import com.yice.edu.cn.common.pojo.jw.qusSurvey.QusSurvey;
import com.yice.edu.cn.common.pojo.jw.qusSurvey.QusSurveyClass;
import com.yice.edu.cn.common.pojo.jw.qusSurvey.QusSurveySendObject;
import com.yice.edu.cn.common.pojo.jw.qusSurvey.SendObjectQusSurvey;
import com.yice.edu.cn.common.pojo.jw.student.StudentQusSurvey;
import com.yice.edu.cn.jw.service.qusSurvey.QusSurveySendObjectService;
import com.yice.edu.cn.jw.service.qusSurvey.QusSurveyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/qusSurveySendObject")
@Api(value = "/qusSurveySendObject",description = "模块")
public class QusSurveySendObjectController {
    @Autowired
    private QusSurveyService qusSurveyService;
    @Autowired
    private QusSurveySendObjectService qusSurveySendObjectService;

    @GetMapping("/findQusSurveySendObjectById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public QusSurveySendObject findQusSurveySendObjectById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return qusSurveySendObjectService.findQusSurveySendObjectById(id);
    }

    @PostMapping("/saveQusSurveySendObject")
    @ApiOperation(value = "保存", notes = "返回对象")
    public QusSurveySendObject saveQusSurveySendObject(
            @ApiParam(value = "对象", required = true)
            @RequestBody QusSurveySendObject qusSurveySendObject){
        qusSurveySendObject.setCreateTime(DateUtil.now());
        qusSurveySendObjectService.saveQusSurveySendObject(qusSurveySendObject);
        return qusSurveySendObject;
    }

    @PostMapping("/findQusSurveySendObjectListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<QusSurveySendObject> findQusSurveySendObjectListByCondition(
            @ApiParam(value = "对象")
            @RequestBody QusSurveySendObject qusSurveySendObject){
        return qusSurveySendObjectService.findQusSurveySendObjectListByCondition(qusSurveySendObject);
    }
    @PostMapping("/findQusSurveySendObjectCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findQusSurveySendObjectCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody QusSurveySendObject qusSurveySendObject){
        return qusSurveySendObjectService.findQusSurveySendObjectCountByCondition(qusSurveySendObject);
    }

    @PostMapping("/updateQusSurveySendObject")
    @ApiOperation(value = "修改", notes = "对象必传")
    public void updateQusSurveySendObject(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody QusSurveySendObject qusSurveySendObject){
        qusSurveySendObjectService.updateQusSurveySendObject(qusSurveySendObject);
    }

    @GetMapping("/deleteQusSurveySendObject/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteQusSurveySendObject(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        qusSurveySendObjectService.deleteQusSurveySendObject(id);
    }
    @PostMapping("/deleteQusSurveySendObjectByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deleteQusSurveySendObjectByCondition(
            @ApiParam(value = "对象")
            @RequestBody QusSurveySendObject qusSurveySendObject){
        qusSurveySendObjectService.deleteQusSurveySendObjectByCondition(qusSurveySendObject);
    }
    @PostMapping("/findOneQusSurveySendObjectByCondition")
    @ApiOperation(value = "查询问卷发送对象，传入问卷id(surveyId)", notes = "发送对象的集合")
    public QusSurveySendObject findOneQusSurveySendObjectByCondition(
            @ApiParam(value = "surveyId")
            @RequestBody QusSurveySendObject qusSurveySendObject){
        QusSurveySendObject qusSurveySendObject1= qusSurveySendObjectService.findOneQusSurveySendObjectByCondition(qusSurveySendObject);

        if(qusSurveySendObject1!=null&&qusSurveySendObject1.getTeacherList()!=null){

            qusSurveySendObject1.getTeacherList().forEach(qusSurveyTeachers -> {
                Map<Integer,List<QusSurveyClass>> m=qusSurveyTeachers.getAlreadyQusSurveyClassList().stream().collect(Collectors.groupingBy(QusSurveyClass::getGradeId));
                ArrayList<QusSurveyClass> qList=new ArrayList<>();
                m.forEach((k,v)->{
                    QusSurveyClass qusSurveyClass=new QusSurveyClass();
                    qusSurveyClass.setGradeId(v.get(0).getGradeId());
                    qusSurveyClass.setClassesName(v.get(0).getGradeName());
                    qusSurveyClass.setClassList(v.stream().collect(Collectors.toList()));
                    qList.add(qusSurveyClass);
                });
                qusSurveyTeachers.setAlreadyQusSurveyClassList(qList);
            });
        }


        if(qusSurveySendObject1==null){
            qusSurveySendObject1=new QusSurveySendObject();
            QusSurvey qusSurvey=new QusSurvey();
            qusSurvey.setId(qusSurveySendObject.getSurveyId());
            QusSurvey qusSurvey1= qusSurveyService.findOneQusSurveyByCondition(qusSurvey);
            qusSurveySendObject1.setSurveyId(qusSurvey1.getId());
            qusSurveySendObject1.setSurveyTitle(qusSurvey1.getTitle());
            qusSurveySendObject1.setTeacherList(new ArrayList<>());

        }
        return  qusSurveySendObject1;
    }



    @PostMapping("/findQusSurveySendObjectListByClassId")
    @ApiOperation(value = "根据班级id查找发送列表", notes = "返回列表")
    public List<SendObjectQusSurvey> findQusSurveySendObjectListByClassId(
            @ApiParam(value = "对象")
            @RequestBody StudentQusSurvey student){
        return qusSurveySendObjectService.findQusSurveySendObjectListByClassId(student);
    }


}
