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
import com.yice.edu.cn.common.pojo.xq.AnalyseClassScore.AnalyseExamTopicStu;
import com.yice.edu.cn.osp.service.xq.analysisClassScore.AnalyseExamTopicStuService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/analyseExamTopicStu")
@Api(value = "/analyseExamTopicStu",description = "学生个人的小题分析模块")
public class AnalyseExamTopicStuController {
    @Autowired
    private AnalyseExamTopicStuService analyseExamTopicStuService;

    @PostMapping("/saveAnalyseExamTopicStu")
    @ApiOperation(value = "保存学生个人的小题分析对象", notes = "返回保存好的学生个人的小题分析对象", response=AnalyseExamTopicStu.class)
    public ResponseJson saveAnalyseExamTopicStu(
            @ApiParam(value = "学生个人的小题分析对象", required = true)
            @RequestBody AnalyseExamTopicStu analyseExamTopicStu){
        AnalyseExamTopicStu s=analyseExamTopicStuService.saveAnalyseExamTopicStu(analyseExamTopicStu);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findAnalyseExamTopicStuById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找学生个人的小题分析", notes = "返回响应对象", response=AnalyseExamTopicStu.class)
    public ResponseJson findAnalyseExamTopicStuById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        AnalyseExamTopicStu analyseExamTopicStu=analyseExamTopicStuService.findAnalyseExamTopicStuById(id);
        return new ResponseJson(analyseExamTopicStu);
    }

    @PostMapping("/update/updateAnalyseExamTopicStu")
    @ApiOperation(value = "修改学生个人的小题分析对象", notes = "返回响应对象")
    public ResponseJson updateAnalyseExamTopicStu(
            @ApiParam(value = "被修改的学生个人的小题分析对象,对象属性不为空则修改", required = true)
            @RequestBody AnalyseExamTopicStu analyseExamTopicStu){
        analyseExamTopicStuService.updateAnalyseExamTopicStu(analyseExamTopicStu);
        return new ResponseJson();
    }

    @GetMapping("/look/lookAnalyseExamTopicStuById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找学生个人的小题分析", notes = "返回响应对象", response=AnalyseExamTopicStu.class)
    public ResponseJson lookAnalyseExamTopicStuById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        AnalyseExamTopicStu analyseExamTopicStu=analyseExamTopicStuService.findAnalyseExamTopicStuById(id);
        return new ResponseJson(analyseExamTopicStu);
    }

    @PostMapping("/findAnalyseExamTopicStusByCondition")
    @ApiOperation(value = "根据条件查找学生个人的小题分析", notes = "返回响应对象", response=AnalyseExamTopicStu.class)
    public ResponseJson findAnalyseExamTopicStusByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody AnalyseExamTopicStu analyseExamTopicStu){
        List<AnalyseExamTopicStu> data=analyseExamTopicStuService.findAnalyseExamTopicStuListByCondition(analyseExamTopicStu);
        long count=analyseExamTopicStuService.findAnalyseExamTopicStuCountByCondition(analyseExamTopicStu);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneAnalyseExamTopicStuByCondition")
    @ApiOperation(value = "根据条件查找单个学生个人的小题分析,结果必须为单条数据", notes = "没有时返回空", response=AnalyseExamTopicStu.class)
    public ResponseJson findOneAnalyseExamTopicStuByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody AnalyseExamTopicStu analyseExamTopicStu){
        AnalyseExamTopicStu one=analyseExamTopicStuService.findOneAnalyseExamTopicStuByCondition(analyseExamTopicStu);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteAnalyseExamTopicStu/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteAnalyseExamTopicStu(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        analyseExamTopicStuService.deleteAnalyseExamTopicStu(id);
        return new ResponseJson();
    }


    @PostMapping("/findAnalyseExamTopicStuListByCondition")
    @ApiOperation(value = "根据条件查找学生个人的小题分析列表", notes = "返回响应对象,不包含总条数", response=AnalyseExamTopicStu.class)
    public ResponseJson findAnalyseExamTopicStuListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody AnalyseExamTopicStu analyseExamTopicStu){
        List<AnalyseExamTopicStu> data=analyseExamTopicStuService.findAnalyseExamTopicStuListByCondition(analyseExamTopicStu);
        return new ResponseJson(data);
    }



}
