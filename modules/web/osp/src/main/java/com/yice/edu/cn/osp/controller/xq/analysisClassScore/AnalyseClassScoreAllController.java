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
import com.yice.edu.cn.common.pojo.xq.AnalyseClassScore.AnalyseClassScoreAll;
import com.yice.edu.cn.osp.service.xq.analysisClassScore.AnalyseClassScoreAllService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/analyseClassScoreAll")
@Api(value = "/analyseClassScoreAll",description = "班级全部科目考试分析模块")
public class AnalyseClassScoreAllController {
    @Autowired
    private AnalyseClassScoreAllService analyseClassScoreAllService;

    @PostMapping("/saveAnalyseClassScoreAll")
    @ApiOperation(value = "保存班级全部科目考试分析对象", notes = "返回保存好的班级全部科目考试分析对象", response=AnalyseClassScoreAll.class)
    public ResponseJson saveAnalyseClassScoreAll(
            @ApiParam(value = "班级全部科目考试分析对象", required = true)
            @RequestBody AnalyseClassScoreAll analyseClassScoreAll){
        AnalyseClassScoreAll s=analyseClassScoreAllService.saveAnalyseClassScoreAll(analyseClassScoreAll);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findAnalyseClassScoreAllById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找班级全部科目考试分析", notes = "返回响应对象", response=AnalyseClassScoreAll.class)
    public ResponseJson findAnalyseClassScoreAllById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        AnalyseClassScoreAll analyseClassScoreAll=analyseClassScoreAllService.findAnalyseClassScoreAllById(id);
        return new ResponseJson(analyseClassScoreAll);
    }

    @PostMapping("/update/updateAnalyseClassScoreAll")
    @ApiOperation(value = "修改班级全部科目考试分析对象", notes = "返回响应对象")
    public ResponseJson updateAnalyseClassScoreAll(
            @ApiParam(value = "被修改的班级全部科目考试分析对象,对象属性不为空则修改", required = true)
            @RequestBody AnalyseClassScoreAll analyseClassScoreAll){
        analyseClassScoreAllService.updateAnalyseClassScoreAll(analyseClassScoreAll);
        return new ResponseJson();
    }

    @GetMapping("/look/lookAnalyseClassScoreAllById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找班级全部科目考试分析", notes = "返回响应对象", response=AnalyseClassScoreAll.class)
    public ResponseJson lookAnalyseClassScoreAllById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        AnalyseClassScoreAll analyseClassScoreAll=analyseClassScoreAllService.findAnalyseClassScoreAllById(id);
        return new ResponseJson(analyseClassScoreAll);
    }

    @PostMapping("/findAnalyseClassScoreAllsByCondition")
    @ApiOperation(value = "根据条件查找班级全部科目考试分析", notes = "返回响应对象", response=AnalyseClassScoreAll.class)
    public ResponseJson findAnalyseClassScoreAllsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody AnalyseClassScoreAll analyseClassScoreAll){
        List<AnalyseClassScoreAll> data=analyseClassScoreAllService.findAnalyseClassScoreAllListByCondition(analyseClassScoreAll);
        long count=analyseClassScoreAllService.findAnalyseClassScoreAllCountByCondition(analyseClassScoreAll);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneAnalyseClassScoreAllByCondition")
    @ApiOperation(value = "根据条件查找单个班级全部科目考试分析,结果必须为单条数据", notes = "没有时返回空", response=AnalyseClassScoreAll.class)
    public ResponseJson findOneAnalyseClassScoreAllByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody AnalyseClassScoreAll analyseClassScoreAll){
        AnalyseClassScoreAll one=analyseClassScoreAllService.findOneAnalyseClassScoreAllByCondition(analyseClassScoreAll);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteAnalyseClassScoreAll/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteAnalyseClassScoreAll(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        analyseClassScoreAllService.deleteAnalyseClassScoreAll(id);
        return new ResponseJson();
    }


    @PostMapping("/findAnalyseClassScoreAllListByCondition")
    @ApiOperation(value = "根据条件查找班级全部科目考试分析列表", notes = "返回响应对象,不包含总条数", response=AnalyseClassScoreAll.class)
    public ResponseJson findAnalyseClassScoreAllListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody AnalyseClassScoreAll analyseClassScoreAll){
        List<AnalyseClassScoreAll> data=analyseClassScoreAllService.findAnalyseClassScoreAllListByCondition(analyseClassScoreAll);
        return new ResponseJson(data);
    }

    @GetMapping("/analyseClassScore/{examId}")
    @ApiOperation(value = "examId 考试对象id", notes = "开始班级的学情分析")
    public void analyseClassScore(@PathVariable("examId") String examId) {
    	analyseClassScoreAllService.analyseClassScore(examId);
    }
}
