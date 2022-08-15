package com.yice.edu.cn.jy.controller.analysisClassScore;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yice.edu.cn.common.pojo.xq.AnalyseClassScore.AnalyseExamTopic;
import com.yice.edu.cn.jy.service.analysisClassScore.AnalyseExamTopicService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/analyseExamTopic")
@Api(value = "/analyseExamTopic",description = "考试的小题分析模块")
public class AnalyseExamTopicController {
    @Autowired
    private AnalyseExamTopicService analyseExamTopicService;

    @GetMapping("/findAnalyseExamTopicById/{id}")
    @ApiOperation(value = "通过id查找考试的小题分析", notes = "返回考试的小题分析对象")
    public AnalyseExamTopic findAnalyseExamTopicById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return analyseExamTopicService.findAnalyseExamTopicById(id);
    }

    @PostMapping("/saveAnalyseExamTopic")
    @ApiOperation(value = "保存考试的小题分析", notes = "返回考试的小题分析对象")
    public AnalyseExamTopic saveAnalyseExamTopic(
            @ApiParam(value = "考试的小题分析对象", required = true)
            @RequestBody AnalyseExamTopic analyseExamTopic){
        analyseExamTopicService.saveAnalyseExamTopic(analyseExamTopic);
        return analyseExamTopic;
    }

    @PostMapping("/findAnalyseExamTopicListByCondition")
    @ApiOperation(value = "根据条件查找考试的小题分析列表", notes = "返回考试的小题分析列表")
    public List<AnalyseExamTopic> findAnalyseExamTopicListByCondition(
            @ApiParam(value = "考试的小题分析对象")
            @RequestBody AnalyseExamTopic analyseExamTopic){
        return analyseExamTopicService.findAnalyseExamTopicListByCondition(analyseExamTopic);
    }
    @PostMapping("/findAnalyseExamTopicCountByCondition")
    @ApiOperation(value = "根据条件查找考试的小题分析列表个数", notes = "返回考试的小题分析总个数")
    public long findAnalyseExamTopicCountByCondition(
            @ApiParam(value = "考试的小题分析对象")
            @RequestBody AnalyseExamTopic analyseExamTopic){
        return analyseExamTopicService.findAnalyseExamTopicCountByCondition(analyseExamTopic);
    }

    @PostMapping("/updateAnalyseExamTopic")
    @ApiOperation(value = "修改考试的小题分析", notes = "考试的小题分析对象必传")
    public void updateAnalyseExamTopic(
            @ApiParam(value = "考试的小题分析对象,对象属性不为空则修改", required = true)
            @RequestBody AnalyseExamTopic analyseExamTopic){
        analyseExamTopicService.updateAnalyseExamTopic(analyseExamTopic);
    }

    @GetMapping("/deleteAnalyseExamTopic/{id}")
    @ApiOperation(value = "通过id删除考试的小题分析")
    public void deleteAnalyseExamTopic(
            @ApiParam(value = "考试的小题分析对象", required = true)
            @PathVariable String id){
        analyseExamTopicService.deleteAnalyseExamTopic(id);
    }
    @PostMapping("/deleteAnalyseExamTopicByCondition")
    @ApiOperation(value = "根据条件删除考试的小题分析")
    public void deleteAnalyseExamTopicByCondition(
            @ApiParam(value = "考试的小题分析对象")
            @RequestBody AnalyseExamTopic analyseExamTopic){
        analyseExamTopicService.deleteAnalyseExamTopicByCondition(analyseExamTopic);
    }
    @PostMapping("/findOneAnalyseExamTopicByCondition")
    @ApiOperation(value = "根据条件查找单个考试的小题分析,结果必须为单条数据", notes = "返回单个考试的小题分析,没有时为空")
    public AnalyseExamTopic findOneAnalyseExamTopicByCondition(
            @ApiParam(value = "考试的小题分析对象")
            @RequestBody AnalyseExamTopic analyseExamTopic){
        return analyseExamTopicService.findOneAnalyseExamTopicByCondition(analyseExamTopic);
    }
}
