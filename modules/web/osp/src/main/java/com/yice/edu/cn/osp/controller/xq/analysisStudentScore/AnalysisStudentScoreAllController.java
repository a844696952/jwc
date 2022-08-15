package com.yice.edu.cn.osp.controller.xq.analysisStudentScore;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.xq.analysisStudentScore.AnalysisStudentScoreAll;
import com.yice.edu.cn.osp.service.xq.analysisStudentScore.AnalysisStudentScoreAllService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/analysisStudentScoreAll")
@Api(value = "/analysisStudentScoreAll",description = "学生考试成绩-总成绩模块")
public class AnalysisStudentScoreAllController {
    @Autowired
    private AnalysisStudentScoreAllService analysisStudentScoreAllService;

    @PostMapping("/saveAnalysisStudentScoreAll")
    @ApiOperation(value = "保存学生考试成绩-总成绩对象", notes = "返回保存好的学生考试成绩-总成绩对象", response= AnalysisStudentScoreAll.class)
    public ResponseJson saveAnalysisStudentScoreAll(
            @ApiParam(value = "学生考试成绩-总成绩对象", required = true)
            @RequestBody AnalysisStudentScoreAll analysisStudentScoreAll){
       analysisStudentScoreAll.setSchoolId(mySchoolId());
        AnalysisStudentScoreAll s=analysisStudentScoreAllService.saveAnalysisStudentScoreAll(analysisStudentScoreAll);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findAnalysisStudentScoreAllById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找学生考试成绩-总成绩", notes = "返回响应对象", response=AnalysisStudentScoreAll.class)
    public ResponseJson findAnalysisStudentScoreAllById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        AnalysisStudentScoreAll analysisStudentScoreAll=analysisStudentScoreAllService.findAnalysisStudentScoreAllById(id);
        return new ResponseJson(analysisStudentScoreAll);
    }

    @PostMapping("/update/updateAnalysisStudentScoreAll")
    @ApiOperation(value = "修改学生考试成绩-总成绩对象", notes = "返回响应对象")
    public ResponseJson updateAnalysisStudentScoreAll(
            @ApiParam(value = "被修改的学生考试成绩-总成绩对象,对象属性不为空则修改", required = true)
            @RequestBody AnalysisStudentScoreAll analysisStudentScoreAll){
        analysisStudentScoreAllService.updateAnalysisStudentScoreAll(analysisStudentScoreAll);
        return new ResponseJson();
    }

    @GetMapping("/look/lookAnalysisStudentScoreAllById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找学生考试成绩-总成绩", notes = "返回响应对象", response=AnalysisStudentScoreAll.class)
    public ResponseJson lookAnalysisStudentScoreAllById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        AnalysisStudentScoreAll analysisStudentScoreAll=analysisStudentScoreAllService.findAnalysisStudentScoreAllById(id);
        return new ResponseJson(analysisStudentScoreAll);
    }

    @PostMapping("/findAnalysisStudentScoreAllsByCondition")
    @ApiOperation(value = "根据条件查找学生考试成绩-总成绩", notes = "返回响应对象", response=AnalysisStudentScoreAll.class)
    public ResponseJson findAnalysisStudentScoreAllsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody AnalysisStudentScoreAll analysisStudentScoreAll){
       analysisStudentScoreAll.setSchoolId(mySchoolId());
        List<AnalysisStudentScoreAll> data=analysisStudentScoreAllService.findAnalysisStudentScoreAllListByCondition(analysisStudentScoreAll);
        long count=analysisStudentScoreAllService.findAnalysisStudentScoreAllCountByCondition(analysisStudentScoreAll);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneAnalysisStudentScoreAllByCondition")
    @ApiOperation(value = "根据条件查找单个学生考试成绩-总成绩,结果必须为单条数据", notes = "没有时返回空", response=AnalysisStudentScoreAll.class)
    public ResponseJson findOneAnalysisStudentScoreAllByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody AnalysisStudentScoreAll analysisStudentScoreAll){
        AnalysisStudentScoreAll one=analysisStudentScoreAllService.findOneAnalysisStudentScoreAllByCondition(analysisStudentScoreAll);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteAnalysisStudentScoreAll/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteAnalysisStudentScoreAll(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        analysisStudentScoreAllService.deleteAnalysisStudentScoreAll(id);
        return new ResponseJson();
    }


    @PostMapping("/findAnalysisStudentScoreAllListByCondition")
    @ApiOperation(value = "根据条件查找学生考试成绩-总成绩列表", notes = "返回响应对象,不包含总条数", response=AnalysisStudentScoreAll.class)
    public ResponseJson findAnalysisStudentScoreAllListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody AnalysisStudentScoreAll analysisStudentScoreAll){
       analysisStudentScoreAll.setSchoolId(mySchoolId());
        List<AnalysisStudentScoreAll> data=analysisStudentScoreAllService.findAnalysisStudentScoreAllListByCondition(analysisStudentScoreAll);
        return new ResponseJson(data);
    }



}
