package com.yice.edu.cn.osp.controller.xq.analysisClassScore;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.xq.AnalyseClassScore.AnalyseExamTopic;
import com.yice.edu.cn.osp.service.xq.analysisClassScore.AnalyseExamTopicService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/analyseExamTopic")
@Api(value = "/analyseExamTopic",description = "考试的小题分析模块")
public class AnalyseExamTopicController {
    @Autowired
    private AnalyseExamTopicService analyseExamTopicService;

    @PostMapping("/saveAnalyseExamTopic")
    @ApiOperation(value = "保存考试的小题分析对象", notes = "返回保存好的考试的小题分析对象", response=AnalyseExamTopic.class)
    public ResponseJson saveAnalyseExamTopic(
            @ApiParam(value = "考试的小题分析对象", required = true)
            @RequestBody AnalyseExamTopic analyseExamTopic){
        AnalyseExamTopic s=analyseExamTopicService.saveAnalyseExamTopic(analyseExamTopic);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findAnalyseExamTopicById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找考试的小题分析", notes = "返回响应对象", response=AnalyseExamTopic.class)
    public ResponseJson findAnalyseExamTopicById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        AnalyseExamTopic analyseExamTopic=analyseExamTopicService.findAnalyseExamTopicById(id);
        return new ResponseJson(analyseExamTopic);
    }

    @PostMapping("/update/updateAnalyseExamTopic")
    @ApiOperation(value = "修改考试的小题分析对象", notes = "返回响应对象")
    public ResponseJson updateAnalyseExamTopic(
            @ApiParam(value = "被修改的考试的小题分析对象,对象属性不为空则修改", required = true)
            @RequestBody AnalyseExamTopic analyseExamTopic){
        analyseExamTopicService.updateAnalyseExamTopic(analyseExamTopic);
        return new ResponseJson();
    }

    @GetMapping("/look/lookAnalyseExamTopicById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找考试的小题分析", notes = "返回响应对象", response=AnalyseExamTopic.class)
    public ResponseJson lookAnalyseExamTopicById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        AnalyseExamTopic analyseExamTopic=analyseExamTopicService.findAnalyseExamTopicById(id);
        return new ResponseJson(analyseExamTopic);
    }

    @PostMapping("/findAnalyseExamTopicsByCondition")
    @ApiOperation(value = "根据条件查找考试的小题分析", notes = "返回响应对象", response=AnalyseExamTopic.class)
    public ResponseJson findAnalyseExamTopicsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody AnalyseExamTopic analyseExamTopic){
        List<AnalyseExamTopic> data=analyseExamTopicService.findAnalyseExamTopicListByCondition(analyseExamTopic);
        long count=analyseExamTopicService.findAnalyseExamTopicCountByCondition(analyseExamTopic);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneAnalyseExamTopicByCondition")
    @ApiOperation(value = "根据条件查找单个考试的小题分析,结果必须为单条数据", notes = "没有时返回空", response=AnalyseExamTopic.class)
    public ResponseJson findOneAnalyseExamTopicByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody AnalyseExamTopic analyseExamTopic){
        AnalyseExamTopic one=analyseExamTopicService.findOneAnalyseExamTopicByCondition(analyseExamTopic);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteAnalyseExamTopic/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteAnalyseExamTopic(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        analyseExamTopicService.deleteAnalyseExamTopic(id);
        return new ResponseJson();
    }


    @PostMapping("/findAnalyseExamTopicListByCondition")
    @ApiOperation(value = "根据条件查找考试的小题分析列表", notes = "返回响应对象,不包含总条数", response=AnalyseExamTopic.class)
    public ResponseJson findAnalyseExamTopicListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody AnalyseExamTopic analyseExamTopic){
        List<AnalyseExamTopic> data=analyseExamTopicService.findAnalyseExamTopicListByCondition(analyseExamTopic);
        return new ResponseJson(data);
    }



}
