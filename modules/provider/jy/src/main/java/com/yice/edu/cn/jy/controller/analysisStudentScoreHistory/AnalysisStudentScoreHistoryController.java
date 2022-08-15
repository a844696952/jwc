package com.yice.edu.cn.jy.controller.analysisStudentScoreHistory;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.xq.analyseDataHistory.AnalysisStudentScoreHistory;
import com.yice.edu.cn.common.pojo.xq.analysisStudentScore.AnalysisStudentScore;
import com.yice.edu.cn.jy.service.analysisStudentScoreHistory.AnalysisStudentScoreHistoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@RestController
@RequestMapping("/analysisStudentScoreHistory")
@Api(value = "/analysisStudentScoreHistory",description = "模块")
public class AnalysisStudentScoreHistoryController {
    @Autowired
    private AnalysisStudentScoreHistoryService analysisStudentScoreHistoryService;

    @PostMapping("/saveAnalysisStudentScoreHistory")
    @ApiOperation(value = "保存对象", notes = "返回保存好的对象", response= AnalysisStudentScoreHistory.class)
    public AnalysisStudentScoreHistory saveAnalysisStudentScoreHistory(
            @ApiParam(value = "对象", required = true)
            @RequestBody AnalysisStudentScoreHistory analysisStudentScoreHistory){
        return analysisStudentScoreHistoryService.saveAnalysisStudentScoreHistory(analysisStudentScoreHistory);
    }


    @PostMapping("/updateAnalysisStudentScoreHistory")
    @ApiOperation(value = "修改对象", notes = "返回响应对象")
    public void updateAnalysisStudentScoreHistory(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody AnalysisStudentScoreHistory analysisStudentScoreHistory){
        analysisStudentScoreHistoryService.updateAnalysisStudentScoreHistory(analysisStudentScoreHistory);
    }

    @GetMapping("/findAnalysisStudentScoreHistoryById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象", response=AnalysisStudentScoreHistory.class)
    public AnalysisStudentScoreHistory findAnalysisStudentScoreHistoryById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        return analysisStudentScoreHistoryService.findAnalysisStudentScoreHistoryById(id);
    }

    @GetMapping("/deleteAnalysisStudentScoreHistory/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public void deleteAnalysisStudentScoreHistory(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        analysisStudentScoreHistoryService.deleteAnalysisStudentScoreHistory(id);
    }


    @PostMapping("/findAnalysisStudentScoreHistoryListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数", response=AnalysisStudentScoreHistory.class)
    public List<AnalysisStudentScoreHistory> findAnalysisStudentScoreHistoryListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody AnalysisStudentScoreHistory analysisStudentScoreHistory){
        return analysisStudentScoreHistoryService.findAnalysisStudentScoreHistoryListByCondition(analysisStudentScoreHistory);
    }

    @PostMapping("/findStudentScoresCountByCondition")
    @ApiOperation(value = "根据条件查找学生考试成绩列表", notes = "返回学生考试列表")
    public Long findStudentScoresCountByCondition(
            @ApiParam(value = "学生考试成绩-单科的对象")
            @RequestBody AnalysisStudentScoreHistory analysisStudentScoreHistory){
        return analysisStudentScoreHistoryService.findStudentScoresCountByCondition(analysisStudentScoreHistory);
    }



}
