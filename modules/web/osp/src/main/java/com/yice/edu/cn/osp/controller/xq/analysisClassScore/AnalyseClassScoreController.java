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
import com.yice.edu.cn.common.pojo.xq.AnalyseClassScore.AnalyseClassScore;
import com.yice.edu.cn.osp.service.xq.analysisClassScore.AnalyseClassScoreService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/analyseClassScore")
@Api(value = "/analyseClassScore",description = "班级全部科目考试分析模块")
public class AnalyseClassScoreController {
    @Autowired
    private AnalyseClassScoreService analyseClassScoreService;

    @PostMapping("/saveAnalyseClassScore")
    @ApiOperation(value = "保存班级全部科目考试分析对象", notes = "返回保存好的班级全部科目考试分析对象", response=AnalyseClassScore.class)
    public ResponseJson saveAnalyseClassScore(
            @ApiParam(value = "班级全部科目考试分析对象", required = true)
            @RequestBody AnalyseClassScore analyseClassScore){
        AnalyseClassScore s=analyseClassScoreService.saveAnalyseClassScore(analyseClassScore);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findAnalyseClassScoreById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找班级全部科目考试分析", notes = "返回响应对象", response=AnalyseClassScore.class)
    public ResponseJson findAnalyseClassScoreById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        AnalyseClassScore analyseClassScore=analyseClassScoreService.findAnalyseClassScoreById(id);
        return new ResponseJson(analyseClassScore);
    }

    @PostMapping("/update/updateAnalyseClassScore")
    @ApiOperation(value = "修改班级全部科目考试分析对象", notes = "返回响应对象")
    public ResponseJson updateAnalyseClassScore(
            @ApiParam(value = "被修改的班级全部科目考试分析对象,对象属性不为空则修改", required = true)
            @RequestBody AnalyseClassScore analyseClassScore){
        analyseClassScoreService.updateAnalyseClassScore(analyseClassScore);
        return new ResponseJson();
    }

    @GetMapping("/look/lookAnalyseClassScoreById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找班级全部科目考试分析", notes = "返回响应对象", response=AnalyseClassScore.class)
    public ResponseJson lookAnalyseClassScoreById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        AnalyseClassScore analyseClassScore=analyseClassScoreService.findAnalyseClassScoreById(id);
        return new ResponseJson(analyseClassScore);
    }

    @PostMapping("/findAnalyseClassScoresByCondition")
    @ApiOperation(value = "根据条件查找班级全部科目考试分析", notes = "返回响应对象", response=AnalyseClassScore.class)
    public ResponseJson findAnalyseClassScoresByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody AnalyseClassScore analyseClassScore){
        List<AnalyseClassScore> data=analyseClassScoreService.findAnalyseClassScoreListByCondition(analyseClassScore);
        long count=analyseClassScoreService.findAnalyseClassScoreCountByCondition(analyseClassScore);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneAnalyseClassScoreByCondition")
    @ApiOperation(value = "根据条件查找单个班级全部科目考试分析,结果必须为单条数据", notes = "没有时返回空", response=AnalyseClassScore.class)
    public ResponseJson findOneAnalyseClassScoreByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody AnalyseClassScore analyseClassScore){
        AnalyseClassScore one=analyseClassScoreService.findOneAnalyseClassScoreByCondition(analyseClassScore);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteAnalyseClassScore/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteAnalyseClassScore(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        analyseClassScoreService.deleteAnalyseClassScore(id);
        return new ResponseJson();
    }


    @PostMapping("/findAnalyseClassScoreListByCondition")
    @ApiOperation(value = "根据条件查找班级全部科目考试分析列表", notes = "返回响应对象,不包含总条数", response=AnalyseClassScore.class)
    public ResponseJson findAnalyseClassScoreListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody AnalyseClassScore analyseClassScore){
        List<AnalyseClassScore> data=analyseClassScoreService.findAnalyseClassScoreListByCondition(analyseClassScore);
        return new ResponseJson(data);
    }



}
