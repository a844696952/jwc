package com.yice.edu.cn.osp.controller.jw.exam.buildPaper.paper;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.paper.Paper;
import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.paper.PaperQuestion;
import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.paper.PaperTopics;
import com.yice.edu.cn.osp.interceptor.LoginInterceptor;
import com.yice.edu.cn.osp.service.jw.exam.buildPaper.paper.PaperQuestionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@RestController
@RequestMapping("/paperQuestion")
@Api(value = "/paperQuestion",description = "大题模块")
public class PaperQuestionController {
    @Autowired
    private PaperQuestionService paperQuestionService;

    @PostMapping("/savePaperQuestion")
    @ApiOperation(value = "保存试题篮对象", notes = "返回响应对象")
    public ResponseJson savePaperQuestion(
            @ApiParam(value = "试题篮对象", required = true)
            @RequestBody PaperQuestion paperQuestion){

        paperQuestion.setCreateUserId(LoginInterceptor.myId());
        PaperQuestion s= paperQuestionService.savePaperQuestion(paperQuestion);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findPaperQuestionById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找试题篮", notes = "返回响应对象")
    public ResponseJson findPaperQuestionById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        PaperQuestion paperQuestion = paperQuestionService.findPaperQuestionById(id);
        return new ResponseJson(paperQuestion);
    }

    @PostMapping("/update/updatePaperQuestion")
    @ApiOperation(value = "修改试题篮对象", notes = "返回响应对象")
    public ResponseJson updatePaperQuestion(
            @ApiParam(value = "被修改的试题篮对象,对象属性不为空则修改", required = true)
            @RequestBody PaperQuestion paperQuestion){
        paperQuestionService.updatePaperQuestion(paperQuestion);
        return new ResponseJson();
    }

    @GetMapping("/look/lookPaperQuestionById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找试题篮", notes = "返回响应对象")
    public ResponseJson lookPaperQuestionById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        PaperQuestion paperQuestion = paperQuestionService.findPaperQuestionById(id);
        return new ResponseJson(paperQuestion);
    }

    @PostMapping("/findPaperQuestionsByCondition")
    @ApiOperation(value = "根据条件查找试题篮", notes = "返回响应对象")
    public ResponseJson findPaperQuestionsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody PaperQuestion paperQuestion){
        Pager paper = paperQuestion.getPager();
        paper.setSortField("sort");
        paper.setSortOrder(Pager.ASC);
        List<PaperQuestion> data= paperQuestionService.findPaperQuestionListByCondition(paperQuestion);
        long count= paperQuestionService.findPaperQuestionCountByCondition(paperQuestion);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOnePaperQuestionByCondition")
    @ApiOperation(value = "根据条件查找单个试题篮,结果必须为单条数据", notes = "没有时返回空")
    public ResponseJson findOnePaperQuestionByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody PaperQuestion paperQuestion){
        PaperQuestion one= paperQuestionService.findOnePaperQuestionByCondition(paperQuestion);
        return new ResponseJson(one);
    }
    @GetMapping("/ignore/deletePaperQuestion/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deletePaperQuestion(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        paperQuestionService.deletePaperQuestion(id);
        return new ResponseJson();
    }


    @PostMapping("/findPaperQuestionListByCondition")
    @ApiOperation(value = "根据条件查找试题篮列表", notes = "返回响应对象,不包含总条数")
    public ResponseJson findPaperQuestionListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody PaperQuestion paperQuestion){
        List<PaperQuestion> data= paperQuestionService.findPaperQuestionListByCondition(paperQuestion);
        return new ResponseJson(data);
    }




    @PostMapping("/drop/dragSortBigPaperQuestionKong")
    @ApiOperation(value = "根据列表大题排序",notes = "修改数据顺序")
    public ResponseJson dragSortBigPaperQuestionKong(
            @ApiParam(value = "排序的列表题目对象")
            @RequestBody List<PaperQuestion> paperQuestionList
    ){
        paperQuestionService.dragSortBigPaperQuestionKong(paperQuestionList);
        return  new ResponseJson();
    }

    @PostMapping("/drop/dragSortMinPaperQuestionKong")
    @ApiOperation(value = "根据小题排序(覆盖)",notes = "无返回")
    public  ResponseJson dragSortMinPaperQuestionKong(
            @ApiParam(value = "排序的小题对象")
            @RequestBody PaperQuestion paperQuestion
    ){
        paperQuestionService.dragSortMinPaperQuestionKong(paperQuestion);
        return  new ResponseJson();
    }


    @GetMapping("/findStandardPaperQuestionKong/{testPaperId}")
    @ApiOperation(value = "传递试卷id查询对应试卷")
    public  ResponseJson findStandardPaperQuestionKong(
            @ApiParam(value = "试卷id")
            @PathVariable String testPaperId
    ){
        return paperQuestionService.findStandardPaperQuestionKong(testPaperId);
    }

    @PostMapping("update/updatePaperQuestionKong")
    @ApiOperation(value = "传递修改的小题对象")
    public ResponseJson updatePaperQuestionKong(
            @ApiParam(value = "小题对象")
            @RequestBody PaperTopics paperTopics
    ){
        paperQuestionService.updatePaperQuestionKong(paperTopics);
        return  new ResponseJson();
    }

    @PostMapping("/look/findListQuestionListKong")
    @ApiOperation(value = "传递试卷对象，获取解开后的所有小题数据")
    public ResponseJson findListQuestionListKong(
            @ApiParam(value = "小题对象")
            @RequestBody Paper paper
            ){
        List<PaperQuestion> paperQuestions = paperQuestionService.findListQuestionListKong(paper);
        long count = paperQuestionService.findCountQuestionCountKong(paper);
        return  new ResponseJson(paperQuestions,count);
    }

    @GetMapping("/look/findOnePaperTopicsOneKong/{paperId}/{topicsId}")
    @ApiOperation(value = "传递试卷Id和小题Id，获取单个小题数据")
    public ResponseJson findOnePaperTopicsOneKong(
            @PathVariable("paperId") String paperId,
            @PathVariable("topicsId") String topicsId
    ){
        PaperTopics paperTopics = paperQuestionService.findOnePaperTopicsOneKong(paperId,topicsId);
        return new ResponseJson(paperTopics);
    }
}
