package com.yice.edu.cn.jw.controller.exam.buildPaper.paper;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.paper.Paper;
import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.paper.PaperQuestion;
import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.paper.PaperTopics;
import com.yice.edu.cn.jw.service.exam.buildPaper.paper.PaperQuestionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.mvel2.ast.RegExMatchNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/paperQuestion")
@Api(value = "/paperQuestion",description = "试题篮模块")
public class PaperQuestionController {
    @Autowired
    private PaperQuestionService paperQuestionService;

    @GetMapping("/findPaperQuestionById/{id}")
    @ApiOperation(value = "通过id查找试题篮", notes = "返回试题篮对象")
    public PaperQuestion findPaperQuestionById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return paperQuestionService.findPaperQuestionById(id);
    }

    @PostMapping("/savePaperQuestion")
    @ApiOperation(value = "保存试题篮", notes = "返回试题篮对象")
    public PaperQuestion savePaperQuestion(
            @ApiParam(value = "试题篮对象", required = true)
            @RequestBody PaperQuestion paperQuestion){
        paperQuestionService.savePaperQuestion(paperQuestion);
        return paperQuestion;
    }

    @PostMapping("/findPaperQuestionListByCondition")
    @ApiOperation(value = "根据条件查找试题篮列表", notes = "返回试题篮列表")
    public List<PaperQuestion> findPaperQuestionListByCondition(
            @ApiParam(value = "试题篮对象")
            @RequestBody PaperQuestion paperQuestion){
        return paperQuestionService.findPaperQuestionListByCondition(paperQuestion);
    }
    @PostMapping("/findPaperQuestionCountByCondition")
    @ApiOperation(value = "根据条件查找试题篮列表个数", notes = "返回试题篮总个数")
    public long findPaperQuestionCountByCondition(
            @ApiParam(value = "试题篮对象")
            @RequestBody PaperQuestion paperQuestion){
        return paperQuestionService.findPaperQuestionCountByCondition(paperQuestion);
    }

    @PostMapping("/updatePaperQuestion")
    @ApiOperation(value = "修改试题篮", notes = "试题篮对象必传")
    public void updatePaperQuestion(
            @ApiParam(value = "试题篮对象,对象属性不为空则修改", required = true)
            @RequestBody PaperQuestion paperQuestion){
        paperQuestionService.updatePaperQuestion(paperQuestion);
    }

    @GetMapping("/deletePaperQuestion/{id}")
    @ApiOperation(value = "通过id删除试题篮")
    public void deletePaperQuestion(
            @ApiParam(value = "试题篮对象", required = true)
            @PathVariable String id){
        paperQuestionService.deletePaperQuestion(id);
    }
    @PostMapping("/deletePaperQuestionByCondition")
    @ApiOperation(value = "根据条件删除试题篮")
    public void deletePaperQuestionByCondition(
            @ApiParam(value = "试题篮对象")
            @RequestBody PaperQuestion paperQuestion){
        paperQuestionService.deletePaperQuestionByCondition(paperQuestion);
    }
    @PostMapping("/findOnePaperQuestionByCondition")
    @ApiOperation(value = "根据条件查找单个试题篮,结果必须为单条数据", notes = "返回单个试题篮,没有时为空")
    public PaperQuestion findOnePaperQuestionByCondition(
            @ApiParam(value = "试题篮对象")
            @RequestBody PaperQuestion paperQuestion){
        return paperQuestionService.findOnePaperQuestionByCondition(paperQuestion);
    }



    @PostMapping("/dragSortBigPaperQuestionKong")
    @ApiOperation(value = "根据对象大题排序",notes = "无返回")
    public void dragSortPagerBasketKong(
            @ApiParam(value = "试题篮顺序")
            @RequestBody List<PaperQuestion> paperQuestionList
    ){
        paperQuestionService.dragSortBigPaperQuestionKong(paperQuestionList);
    }

    @PostMapping("/dragSortMinPaperQuestionKong")
    @ApiOperation(value = "根据对象小题排序(覆盖)",notes = "无返回")
    public void dragSortMinPagerBasketKong(
            @ApiParam(value = "同一类型的小题排序")
            @RequestBody PaperQuestion paperQuestion
    ){
        paperQuestionService.dragSortMinPaperQuestionKong(paperQuestion);
    }

    @GetMapping("/findStandardPaperQuestionKong/{testPaperId}")
    @ApiOperation(value = "通过试卷id查看看你详情")
    public ResponseJson findStandardPaperQuestionKong(
            @ApiParam(value = "试卷id")
            @PathVariable String testPaperId
    ){
        return paperQuestionService.findStandardPaperQuestionKong(testPaperId);
    }

    @PostMapping("/updatePaperQuestionKong")
    @ApiOperation(value = "传递小题对象",notes = "无返回值")
    public void updatePaperQuestionKong(
            @ApiParam(value = "小题对象")
            @RequestBody PaperTopics paperTopics
    ){
        paperQuestionService.updatePaperQuestionKong(paperTopics);
    }


    @PostMapping("/findListQuestionListKong")
    @ApiModelProperty(value = "传递试卷Id",notes = "返回所有小题")
    public List<PaperQuestion> findListQuestionListKong(
            @ApiParam(value = "试卷对象")
            @RequestBody Paper paper
            ){
      return   paperQuestionService.findListQuestionListKong(paper);
    }


    @PostMapping("/findCountQuestionCountKong")
    @ApiModelProperty(value = "传递试卷Id",notes = "返回所有小题")
    public long findCountQuestionCountKong(
            @ApiParam(value = "试卷对象")
            @RequestBody Paper paper
    ){
        return paperQuestionService.findCountQuestionCountKong(paper);
    }

    @GetMapping("/findOnePaperTopicsOneKong/{paperId}/{topicsId}")
    @ApiModelProperty(value = "试卷Id和小题Id")
    public PaperTopics findOnePaperTopicsOneKong(
            @ApiParam(value = "试卷Id和小题Id")
            @PathVariable("paperId") String paperId,
            @PathVariable("topicsId") String topicsId
    ){
        return paperQuestionService.findOnePaperTopicsOneKong(paperId,topicsId);
    }
}
