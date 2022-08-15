package com.yice.edu.cn.jy.controller.analysisStudentScore;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yice.edu.cn.common.pojo.xq.analysisStudentScore.AnalysisStudentScore;
import com.yice.edu.cn.jy.service.analysisClassScore.AnalyseClassScoreService;
import com.yice.edu.cn.jy.service.analysisClassScore.AnalyseExamTopicService;
import com.yice.edu.cn.jy.service.analysisStudentScore.AnalysisStudentScoreService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/analysisStudentScore")
@Api(value = "/analysisStudentScore",description = "学生考试成绩-单科的模块")
public class AnalysisStudentScoreController {
    @Autowired
    private AnalysisStudentScoreService analysisStudentScoreService;
    @Autowired
    private AnalyseClassScoreService analyseClassScoreService;
    @Autowired
    private AnalyseExamTopicService analyseExamTopicService;
    
    @GetMapping("/findAnalysisStudentScoreById/{id}")
    @ApiOperation(value = "通过id查找学生考试成绩-单科的", notes = "返回学生考试成绩-单科的对象")
    public AnalysisStudentScore findAnalysisStudentScoreById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return analysisStudentScoreService.findAnalysisStudentScoreById(id);
    }

    @PostMapping("/saveAnalysisStudentScore")
    @ApiOperation(value = "保存学生考试成绩-单科的", notes = "返回学生考试成绩-单科的对象")
    public AnalysisStudentScore saveAnalysisStudentScore(
            @ApiParam(value = "学生考试成绩-单科的对象", required = true)
            @RequestBody AnalysisStudentScore analysisStudentScore){
        analysisStudentScoreService.saveAnalysisStudentScore(analysisStudentScore);
        return analysisStudentScore;
    }

    @PostMapping("/findAnalysisStudentScoreListByCondition")
    @ApiOperation(value = "根据条件查找学生考试成绩-单科的列表", notes = "返回学生考试成绩-单科的列表")
    public List<AnalysisStudentScore> findAnalysisStudentScoreListByCondition(
            @ApiParam(value = "学生考试成绩-单科的对象")
            @RequestBody AnalysisStudentScore analysisStudentScore){
        return analysisStudentScoreService.findAnalysisStudentScoreListByCondition(analysisStudentScore);
    }
    @PostMapping("/findAnalysisStudentScoreCountByCondition")
    @ApiOperation(value = "根据条件查找学生考试成绩-单科的列表个数", notes = "返回学生考试成绩-单科的总个数")
    public long findAnalysisStudentScoreCountByCondition(
            @ApiParam(value = "学生考试成绩-单科的对象")
            @RequestBody AnalysisStudentScore analysisStudentScore){
        return analysisStudentScoreService.findAnalysisStudentScoreCountByCondition(analysisStudentScore);
    }

    @PostMapping("/updateAnalysisStudentScore")
    @ApiOperation(value = "修改学生考试成绩-单科的", notes = "学生考试成绩-单科的对象必传")
    public void updateAnalysisStudentScore(
            @ApiParam(value = "学生考试成绩-单科的对象,对象属性不为空则修改", required = true)
            @RequestBody AnalysisStudentScore analysisStudentScore){
        analysisStudentScoreService.updateAnalysisStudentScore(analysisStudentScore);
    }

    @GetMapping("/deleteAnalysisStudentScore/{id}")
    @ApiOperation(value = "通过id删除学生考试成绩-单科的")
    public void deleteAnalysisStudentScore(
            @ApiParam(value = "学生考试成绩-单科的对象", required = true)
            @PathVariable String id){
        analysisStudentScoreService.deleteAnalysisStudentScore(id);
    }
    @PostMapping("/deleteAnalysisStudentScoreByCondition")
    @ApiOperation(value = "根据条件删除学生考试成绩-单科的")
    public void deleteAnalysisStudentScoreByCondition(
            @ApiParam(value = "学生考试成绩-单科的对象")
            @RequestBody AnalysisStudentScore analysisStudentScore){
        analysisStudentScoreService.deleteAnalysisStudentScoreByCondition(analysisStudentScore);
    }
    @PostMapping("/findOneAnalysisStudentScoreByCondition")
    @ApiOperation(value = "根据条件查找单个学生考试成绩-单科的,结果必须为单条数据", notes = "返回单个学生考试成绩-单科的,没有时为空")
    public AnalysisStudentScore findOneAnalysisStudentScoreByCondition(
            @ApiParam(value = "学生考试成绩-单科的对象")
            @RequestBody AnalysisStudentScore analysisStudentScore){
        return analysisStudentScoreService.findOneAnalysisStudentScoreByCondition(analysisStudentScore);
    }
    @GetMapping("/analysisStudentScore/{examinationId}")
    public void analysisStudentScore(@PathVariable String examinationId){
        analysisStudentScoreService.analysisStudentScore(examinationId);
    }

    @PostMapping("/findStudentExamScoreListByCondition")
    @ApiOperation(value = "根据条件查找学生考试成绩列表", notes = "返回学生考试列表")
    public List<AnalysisStudentScore> findStudentExamScoreListByCondition(
            @ApiParam(value = "学生考试成绩-单科的对象")
            @RequestBody AnalysisStudentScore analysisStudentScore){
        return analysisStudentScoreService.findStudentExamScoreListByCondition(analysisStudentScore);
    }

    @PostMapping("/findStudentScoresCountByCondition")
    @ApiOperation(value = "根据条件查找学生考试成绩列表", notes = "返回学生考试列表")
    public Long findStudentScoresCountByCondition(
            @ApiParam(value = "学生考试成绩-单科的对象")
            @RequestBody AnalysisStudentScore analysisStudentScore){
        return analysisStudentScoreService.findStudentScoresCountByCondition(analysisStudentScore);
    }



}
