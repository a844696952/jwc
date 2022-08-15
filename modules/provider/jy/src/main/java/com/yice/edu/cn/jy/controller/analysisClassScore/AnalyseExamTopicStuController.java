package com.yice.edu.cn.jy.controller.analysisClassScore;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yice.edu.cn.common.pojo.xq.AnalyseClassScore.AnalyseExamTopicStu;
import com.yice.edu.cn.jy.service.analysisClassScore.AnalyseExamTopicStuService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/analyseExamTopicStu")
@Api(value = "/analyseExamTopicStu",description = "学生个人的小题分析模块")
public class AnalyseExamTopicStuController {
    @Autowired
    private AnalyseExamTopicStuService analyseExamTopicStuService;

    @GetMapping("/findAnalyseExamTopicStuById/{id}")
    @ApiOperation(value = "通过id查找学生个人的小题分析", notes = "返回学生个人的小题分析对象")
    public AnalyseExamTopicStu findAnalyseExamTopicStuById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return analyseExamTopicStuService.findAnalyseExamTopicStuById(id);
    }

    @PostMapping("/saveAnalyseExamTopicStu")
    @ApiOperation(value = "保存学生个人的小题分析", notes = "返回学生个人的小题分析对象")
    public AnalyseExamTopicStu saveAnalyseExamTopicStu(
            @ApiParam(value = "学生个人的小题分析对象", required = true)
            @RequestBody AnalyseExamTopicStu analyseExamTopicStu){
        analyseExamTopicStuService.saveAnalyseExamTopicStu(analyseExamTopicStu);
        return analyseExamTopicStu;
    }

    @PostMapping("/findAnalyseExamTopicStuListByCondition")
    @ApiOperation(value = "根据条件查找学生个人的小题分析列表", notes = "返回学生个人的小题分析列表")
    public List<AnalyseExamTopicStu> findAnalyseExamTopicStuListByCondition(
            @ApiParam(value = "学生个人的小题分析对象")
            @RequestBody AnalyseExamTopicStu analyseExamTopicStu){
        return analyseExamTopicStuService.findAnalyseExamTopicStuListByCondition(analyseExamTopicStu);
    }
    @PostMapping("/findAnalyseExamTopicStuCountByCondition")
    @ApiOperation(value = "根据条件查找学生个人的小题分析列表个数", notes = "返回学生个人的小题分析总个数")
    public long findAnalyseExamTopicStuCountByCondition(
            @ApiParam(value = "学生个人的小题分析对象")
            @RequestBody AnalyseExamTopicStu analyseExamTopicStu){
        return analyseExamTopicStuService.findAnalyseExamTopicStuCountByCondition(analyseExamTopicStu);
    }

    @PostMapping("/updateAnalyseExamTopicStu")
    @ApiOperation(value = "修改学生个人的小题分析", notes = "学生个人的小题分析对象必传")
    public void updateAnalyseExamTopicStu(
            @ApiParam(value = "学生个人的小题分析对象,对象属性不为空则修改", required = true)
            @RequestBody AnalyseExamTopicStu analyseExamTopicStu){
        analyseExamTopicStuService.updateAnalyseExamTopicStu(analyseExamTopicStu);
    }

    @GetMapping("/deleteAnalyseExamTopicStu/{id}")
    @ApiOperation(value = "通过id删除学生个人的小题分析")
    public void deleteAnalyseExamTopicStu(
            @ApiParam(value = "学生个人的小题分析对象", required = true)
            @PathVariable String id){
        analyseExamTopicStuService.deleteAnalyseExamTopicStu(id);
    }
    @PostMapping("/deleteAnalyseExamTopicStuByCondition")
    @ApiOperation(value = "根据条件删除学生个人的小题分析")
    public void deleteAnalyseExamTopicStuByCondition(
            @ApiParam(value = "学生个人的小题分析对象")
            @RequestBody AnalyseExamTopicStu analyseExamTopicStu){
        analyseExamTopicStuService.deleteAnalyseExamTopicStuByCondition(analyseExamTopicStu);
    }
    @PostMapping("/findOneAnalyseExamTopicStuByCondition")
    @ApiOperation(value = "根据条件查找单个学生个人的小题分析,结果必须为单条数据", notes = "返回单个学生个人的小题分析,没有时为空")
    public AnalyseExamTopicStu findOneAnalyseExamTopicStuByCondition(
            @ApiParam(value = "学生个人的小题分析对象")
            @RequestBody AnalyseExamTopicStu analyseExamTopicStu){
        return analyseExamTopicStuService.findOneAnalyseExamTopicStuByCondition(analyseExamTopicStu);
    }
}
