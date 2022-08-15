package com.yice.edu.cn.osp.controller.jw.qusSurvey;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.qusSurvey.QusSurveyQuestion;
import com.yice.edu.cn.osp.service.jw.qusSurvey.QusSurveyQuestionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/qusSurveyQuestion")
@Api(value = "/qusSurveyQuestion",description = "问卷题目表模块")
public class QusSurveyQuestionController {
    @Autowired
    private QusSurveyQuestionService qusSurveyQuestionService;

    @PostMapping("/saveQusSurveyQuestion")
    @ApiOperation(value = "保存问卷题目表对象", notes = "返回响应对象")
    public ResponseJson saveQusSurveyQuestion(
            @ApiParam(value = "问卷题目表对象", required = true)
            @RequestBody ArrayList<QusSurveyQuestion> qusSurveyQuestionList){
        QusSurveyQuestion s=qusSurveyQuestionService.saveQusSurveyQuestion(qusSurveyQuestionList);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findQusSurveyQuestionById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找问卷题目表", notes = "返回响应对象")
    public ResponseJson findQusSurveyQuestionById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        QusSurveyQuestion qusSurveyQuestion=qusSurveyQuestionService.findQusSurveyQuestionById(id);
        return new ResponseJson(qusSurveyQuestion);
    }

    @PostMapping("/update/updateQusSurveyQuestion")
    @ApiOperation(value = "修改问卷题目表对象", notes = "返回响应对象")
    public ResponseJson updateQusSurveyQuestion(
            @ApiParam(value = "被修改的问卷题目表对象,对象属性不为空则修改", required = true)
            @RequestBody QusSurveyQuestion qusSurveyQuestion){
        qusSurveyQuestionService.updateQusSurveyQuestion(qusSurveyQuestion);
        return new ResponseJson();
    }

    @GetMapping("/look/lookQusSurveyQuestionById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找问卷题目表", notes = "返回响应对象")
    public ResponseJson lookQusSurveyQuestionById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        QusSurveyQuestion qusSurveyQuestion=qusSurveyQuestionService.findQusSurveyQuestionById(id);
        return new ResponseJson(qusSurveyQuestion);
    }

    @PostMapping("/findQusSurveyQuestionsByCondition")
    @ApiOperation(value = "根据条件查找问卷题目表", notes = "返回响应对象")
    public ResponseJson findQusSurveyQuestionsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody QusSurveyQuestion qusSurveyQuestion){
        List<QusSurveyQuestion> data=qusSurveyQuestionService.findQusSurveyQuestionListByCondition(qusSurveyQuestion);
        long count=qusSurveyQuestionService.findQusSurveyQuestionCountByCondition(qusSurveyQuestion);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneQusSurveyQuestionByCondition")
    @ApiOperation(value = "根据条件查找单个问卷题目表,结果必须为单条数据", notes = "没有时返回空")
    public ResponseJson findOneQusSurveyQuestionByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody QusSurveyQuestion qusSurveyQuestion){
        QusSurveyQuestion one=qusSurveyQuestionService.findOneQusSurveyQuestionByCondition(qusSurveyQuestion);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteQusSurveyQuestion/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteQusSurveyQuestion(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        qusSurveyQuestionService.deleteQusSurveyQuestion(id);
        return new ResponseJson();
    }


    @PostMapping("/findQusSurveyQuestionListByCondition")
    @ApiOperation(value = "根据条件查找问卷题目表列表", notes = "返回响应对象,不包含总条数")
    public ResponseJson findQusSurveyQuestionListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody QusSurveyQuestion qusSurveyQuestion){
        List<QusSurveyQuestion> data=qusSurveyQuestionService.findQusSurveyQuestionListByCondition(qusSurveyQuestion);
        return new ResponseJson(data);
    }



}
