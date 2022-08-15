package com.yice.edu.cn.jy.controller.analysisClassScore;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yice.edu.cn.common.pojo.xq.AnalyseClassScore.AnalyseSubjectGradeScore;
import com.yice.edu.cn.jy.service.analysisClassScore.AnalyseSubjectGradeScoreService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/analyseSubjectGradeScore")
@Api(value = "/analyseSubjectGradeScore",description = "单科全年级分析模块")
public class AnalyseSubjectGradeScoreController {
    @Autowired
    private AnalyseSubjectGradeScoreService analyseSubjectGradeScoreService;

    @GetMapping("/findAnalyseSubjectGradeScoreById/{id}")
    @ApiOperation(value = "通过id查找单科全年级分析", notes = "返回单科全年级分析对象")
    public AnalyseSubjectGradeScore findAnalyseSubjectGradeScoreById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return analyseSubjectGradeScoreService.findAnalyseSubjectGradeScoreById(id);
    }

    @PostMapping("/saveAnalyseSubjectGradeScore")
    @ApiOperation(value = "保存单科全年级分析", notes = "返回单科全年级分析对象")
    public AnalyseSubjectGradeScore saveAnalyseSubjectGradeScore(
            @ApiParam(value = "单科全年级分析对象", required = true)
            @RequestBody AnalyseSubjectGradeScore analyseSubjectGradeScore){
        analyseSubjectGradeScoreService.saveAnalyseSubjectGradeScore(analyseSubjectGradeScore);
        return analyseSubjectGradeScore;
    }

    @PostMapping("/findAnalyseSubjectGradeScoreListByCondition")
    @ApiOperation(value = "根据条件查找单科全年级分析列表", notes = "返回单科全年级分析列表")
    public List<AnalyseSubjectGradeScore> findAnalyseSubjectGradeScoreListByCondition(
            @ApiParam(value = "单科全年级分析对象")
            @RequestBody AnalyseSubjectGradeScore analyseSubjectGradeScore){
        return analyseSubjectGradeScoreService.findAnalyseSubjectGradeScoreListByCondition(analyseSubjectGradeScore);
    }
    @PostMapping("/findAnalyseSubjectGradeScoreCountByCondition")
    @ApiOperation(value = "根据条件查找单科全年级分析列表个数", notes = "返回单科全年级分析总个数")
    public long findAnalyseSubjectGradeScoreCountByCondition(
            @ApiParam(value = "单科全年级分析对象")
            @RequestBody AnalyseSubjectGradeScore analyseSubjectGradeScore){
        return analyseSubjectGradeScoreService.findAnalyseSubjectGradeScoreCountByCondition(analyseSubjectGradeScore);
    }

    @PostMapping("/updateAnalyseSubjectGradeScore")
    @ApiOperation(value = "修改单科全年级分析", notes = "单科全年级分析对象必传")
    public void updateAnalyseSubjectGradeScore(
            @ApiParam(value = "单科全年级分析对象,对象属性不为空则修改", required = true)
            @RequestBody AnalyseSubjectGradeScore analyseSubjectGradeScore){
        analyseSubjectGradeScoreService.updateAnalyseSubjectGradeScore(analyseSubjectGradeScore);
    }

    @GetMapping("/deleteAnalyseSubjectGradeScore/{id}")
    @ApiOperation(value = "通过id删除单科全年级分析")
    public void deleteAnalyseSubjectGradeScore(
            @ApiParam(value = "单科全年级分析对象", required = true)
            @PathVariable String id){
        analyseSubjectGradeScoreService.deleteAnalyseSubjectGradeScore(id);
    }
    @PostMapping("/deleteAnalyseSubjectGradeScoreByCondition")
    @ApiOperation(value = "根据条件删除单科全年级分析")
    public void deleteAnalyseSubjectGradeScoreByCondition(
            @ApiParam(value = "单科全年级分析对象")
            @RequestBody AnalyseSubjectGradeScore analyseSubjectGradeScore){
        analyseSubjectGradeScoreService.deleteAnalyseSubjectGradeScoreByCondition(analyseSubjectGradeScore);
    }
    @PostMapping("/findOneAnalyseSubjectGradeScoreByCondition")
    @ApiOperation(value = "根据条件查找单个单科全年级分析,结果必须为单条数据", notes = "返回单个单科全年级分析,没有时为空")
    public AnalyseSubjectGradeScore findOneAnalyseSubjectGradeScoreByCondition(
            @ApiParam(value = "单科全年级分析对象")
            @RequestBody AnalyseSubjectGradeScore analyseSubjectGradeScore){
        return analyseSubjectGradeScoreService.findOneAnalyseSubjectGradeScoreByCondition(analyseSubjectGradeScore);
    }
}
