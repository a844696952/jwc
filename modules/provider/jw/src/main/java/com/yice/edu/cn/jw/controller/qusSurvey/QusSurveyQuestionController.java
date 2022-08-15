package com.yice.edu.cn.jw.controller.qusSurvey;

import com.yice.edu.cn.common.pojo.jw.qusSurvey.QusSurveyQuestion;
import com.yice.edu.cn.jw.service.qusSurvey.QusSurveyQuestionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/qusSurveyQuestion")
@Api(value = "/qusSurveyQuestion",description = "问卷题目表模块")
public class QusSurveyQuestionController {
    @Autowired
    private QusSurveyQuestionService qusSurveyQuestionService;

    @GetMapping("/findQusSurveyQuestionById/{id}")
    @ApiOperation(value = "通过id查找问卷题目表", notes = "返回问卷题目表对象")
    public QusSurveyQuestion findQusSurveyQuestionById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return qusSurveyQuestionService.findQusSurveyQuestionById(id);
    }

    @PostMapping("/saveQusSurveyQuestion")
    @ApiOperation(value = "保存问卷题目表", notes = "返回问卷题目表对象")
    public ArrayList<QusSurveyQuestion> saveQusSurveyQuestion(
            @ApiParam(value = "问卷题目表对象", required = true)
            @RequestBody ArrayList<QusSurveyQuestion> qusSurveyQuestionList){
        qusSurveyQuestionService.saveQusSurveyQuestion(qusSurveyQuestionList);
        return qusSurveyQuestionList;
    }

    @PostMapping("/findQusSurveyQuestionListByCondition")
    @ApiOperation(value = "根据条件查找问卷题目表列表", notes = "返回问卷题目表列表")
    public List<QusSurveyQuestion> findQusSurveyQuestionListByCondition(
            @ApiParam(value = "问卷题目表对象")
            @RequestBody QusSurveyQuestion qusSurveyQuestion){
        return qusSurveyQuestionService.findQusSurveyQuestionListByCondition(qusSurveyQuestion);
    }
    @PostMapping("/findQusSurveyQuestionCountByCondition")
    @ApiOperation(value = "根据条件查找问卷题目表列表个数", notes = "返回问卷题目表总个数")
    public long findQusSurveyQuestionCountByCondition(
            @ApiParam(value = "问卷题目表对象")
            @RequestBody QusSurveyQuestion qusSurveyQuestion){
        return qusSurveyQuestionService.findQusSurveyQuestionCountByCondition(qusSurveyQuestion);
    }

    @PostMapping("/updateQusSurveyQuestion")
    @ApiOperation(value = "修改问卷题目表", notes = "问卷题目表对象必传")
    public void updateQusSurveyQuestion(
            @ApiParam(value = "问卷题目表对象,对象属性不为空则修改", required = true)
            @RequestBody QusSurveyQuestion qusSurveyQuestion){
        qusSurveyQuestionService.updateQusSurveyQuestion(qusSurveyQuestion);
    }

    @GetMapping("/deleteQusSurveyQuestion/{id}")
    @ApiOperation(value = "通过id删除问卷题目表")
    public void deleteQusSurveyQuestion(
            @ApiParam(value = "问卷题目表对象", required = true)
            @PathVariable String id){
        qusSurveyQuestionService.deleteQusSurveyQuestion(id);
    }
    @PostMapping("/deleteQusSurveyQuestionByCondition")
    @ApiOperation(value = "根据条件删除问卷题目表")
    public void deleteQusSurveyQuestionByCondition(
            @ApiParam(value = "问卷题目表对象")
            @RequestBody QusSurveyQuestion qusSurveyQuestion){
        qusSurveyQuestionService.deleteQusSurveyQuestionByCondition(qusSurveyQuestion);
    }
    @PostMapping("/findOneQusSurveyQuestionByCondition")
    @ApiOperation(value = "根据条件查找单个问卷题目表,结果必须为单条数据", notes = "返回单个问卷题目表,没有时为空")
    public QusSurveyQuestion findOneQusSurveyQuestionByCondition(
            @ApiParam(value = "问卷题目表对象")
            @RequestBody QusSurveyQuestion qusSurveyQuestion){
        return qusSurveyQuestionService.findOneQusSurveyQuestionByCondition(qusSurveyQuestion);
    }
}
