package com.yice.edu.cn.jy.controller.analysisClassScore;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yice.edu.cn.common.pojo.xq.AnalyseClassScore.AnalyseAllSubjectGradeScore;
import com.yice.edu.cn.jy.service.analysisClassScore.AnalyseAllSubjectGradeScoreService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/analyseAllSubjectGradeScore")
@Api(value = "/analyseAllSubjectGradeScore",description = "全部科目全年级考试分析模块")
public class AnalyseAllSubjectGradeScoreController {
    @Autowired
    private AnalyseAllSubjectGradeScoreService analyseAllSubjectGradeScoreService;

    @GetMapping("/findAnalyseAllSubjectGradeScoreById/{id}")
    @ApiOperation(value = "通过id查找全部科目全年级考试分析", notes = "返回全部科目全年级考试分析对象")
    public AnalyseAllSubjectGradeScore findAnalyseAllSubjectGradeScoreById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return analyseAllSubjectGradeScoreService.findAnalyseAllSubjectGradeScoreById(id);
    }

    @PostMapping("/saveAnalyseAllSubjectGradeScore")
    @ApiOperation(value = "保存全部科目全年级考试分析", notes = "返回全部科目全年级考试分析对象")
    public AnalyseAllSubjectGradeScore saveAnalyseAllSubjectGradeScore(
            @ApiParam(value = "全部科目全年级考试分析对象", required = true)
            @RequestBody AnalyseAllSubjectGradeScore analyseAllSubjectGradeScore){
        analyseAllSubjectGradeScoreService.saveAnalyseAllSubjectGradeScore(analyseAllSubjectGradeScore);
        return analyseAllSubjectGradeScore;
    }

    @PostMapping("/findAnalyseAllSubjectGradeScoreListByCondition")
    @ApiOperation(value = "根据条件查找全部科目全年级考试分析列表", notes = "返回全部科目全年级考试分析列表")
    public List<AnalyseAllSubjectGradeScore> findAnalyseAllSubjectGradeScoreListByCondition(
            @ApiParam(value = "全部科目全年级考试分析对象")
            @RequestBody AnalyseAllSubjectGradeScore analyseAllSubjectGradeScore){
        return analyseAllSubjectGradeScoreService.findAnalyseAllSubjectGradeScoreListByCondition(analyseAllSubjectGradeScore);
    }
    @PostMapping("/findAnalyseAllSubjectGradeScoreCountByCondition")
    @ApiOperation(value = "根据条件查找全部科目全年级考试分析列表个数", notes = "返回全部科目全年级考试分析总个数")
    public long findAnalyseAllSubjectGradeScoreCountByCondition(
            @ApiParam(value = "全部科目全年级考试分析对象")
            @RequestBody AnalyseAllSubjectGradeScore analyseAllSubjectGradeScore){
        return analyseAllSubjectGradeScoreService.findAnalyseAllSubjectGradeScoreCountByCondition(analyseAllSubjectGradeScore);
    }

    @PostMapping("/updateAnalyseAllSubjectGradeScore")
    @ApiOperation(value = "修改全部科目全年级考试分析", notes = "全部科目全年级考试分析对象必传")
    public void updateAnalyseAllSubjectGradeScore(
            @ApiParam(value = "全部科目全年级考试分析对象,对象属性不为空则修改", required = true)
            @RequestBody AnalyseAllSubjectGradeScore analyseAllSubjectGradeScore){
        analyseAllSubjectGradeScoreService.updateAnalyseAllSubjectGradeScore(analyseAllSubjectGradeScore);
    }

    @GetMapping("/deleteAnalyseAllSubjectGradeScore/{id}")
    @ApiOperation(value = "通过id删除全部科目全年级考试分析")
    public void deleteAnalyseAllSubjectGradeScore(
            @ApiParam(value = "全部科目全年级考试分析对象", required = true)
            @PathVariable String id){
        analyseAllSubjectGradeScoreService.deleteAnalyseAllSubjectGradeScore(id);
    }
    @PostMapping("/deleteAnalyseAllSubjectGradeScoreByCondition")
    @ApiOperation(value = "根据条件删除全部科目全年级考试分析")
    public void deleteAnalyseAllSubjectGradeScoreByCondition(
            @ApiParam(value = "全部科目全年级考试分析对象")
            @RequestBody AnalyseAllSubjectGradeScore analyseAllSubjectGradeScore){
        analyseAllSubjectGradeScoreService.deleteAnalyseAllSubjectGradeScoreByCondition(analyseAllSubjectGradeScore);
    }
    @PostMapping("/findOneAnalyseAllSubjectGradeScoreByCondition")
    @ApiOperation(value = "根据条件查找单个全部科目全年级考试分析,结果必须为单条数据", notes = "返回单个全部科目全年级考试分析,没有时为空")
    public AnalyseAllSubjectGradeScore findOneAnalyseAllSubjectGradeScoreByCondition(
            @ApiParam(value = "全部科目全年级考试分析对象")
            @RequestBody AnalyseAllSubjectGradeScore analyseAllSubjectGradeScore){
        return analyseAllSubjectGradeScoreService.findOneAnalyseAllSubjectGradeScoreByCondition(analyseAllSubjectGradeScore);
    }
}
