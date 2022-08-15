package com.yice.edu.cn.osp.controller.jw.exam.examManage;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.StuScore;
import com.yice.edu.cn.osp.service.jw.exam.examManage.StuScoreService;
import com.yice.edu.cn.osp.service.xq.analysisStudentScore.AnalysisStudentScoreService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@RestController
@RequestMapping("/stuScore")
@Api(value = "/stuScore",description = "学生成绩模块")
public class StuScoreController {
    @Autowired
    private StuScoreService stuScoreService;

    @PostMapping("/saveStuScore")
    @ApiOperation(value = "保存学生成绩对象", notes = "返回保存好的学生成绩对象", response= StuScore.class)
    public ResponseJson saveStuScore(
            @ApiParam(value = "学生成绩对象", required = true)
            @RequestBody StuScore stuScore){
        StuScore s=stuScoreService.saveStuScore(stuScore);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findStuScoreById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找学生成绩", notes = "返回响应对象", response=StuScore.class)
    public ResponseJson findStuScoreById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        StuScore stuScore=stuScoreService.findStuScoreById(id);
        return new ResponseJson(stuScore);
    }

    @PostMapping("/update/updateStuScore")
    @ApiOperation(value = "修改学生成绩对象", notes = "返回响应对象")
    public ResponseJson updateStuScore(
            @ApiParam(value = "被修改的学生成绩对象,对象属性不为空则修改", required = true)
            @RequestBody StuScore stuScore){
        stuScoreService.updateStuScore(stuScore);
        return new ResponseJson();
    }

    @GetMapping("/look/lookStuScoreById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找学生成绩", notes = "返回响应对象", response=StuScore.class)
    public ResponseJson lookStuScoreById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        StuScore stuScore=stuScoreService.findStuScoreById(id);
        return new ResponseJson(stuScore);
    }

    @PostMapping("/findStuScoresByCondition")
    @ApiOperation(value = "根据条件查找学生成绩", notes = "返回响应对象", response=StuScore.class)
    public ResponseJson findStuScoresByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody StuScore stuScore){
        List<StuScore> data=stuScoreService.findStuScoreListByCondition(stuScore);
        long count=stuScoreService.findStuScoreCountByCondition(stuScore);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneStuScoreByCondition")
    @ApiOperation(value = "根据条件查找单个学生成绩,结果必须为单条数据", notes = "没有时返回空", response=StuScore.class)
    public ResponseJson findOneStuScoreByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody StuScore stuScore){
        StuScore one=stuScoreService.findOneStuScoreByCondition(stuScore);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteStuScore/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteStuScore(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        stuScoreService.deleteStuScore(id);
        return new ResponseJson();
    }


    @PostMapping("/findStuScoreListByCondition")
    @ApiOperation(value = "根据条件查找学生成绩列表", notes = "返回响应对象,不包含总条数", response=StuScore.class)
    public ResponseJson findStuScoreListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody StuScore stuScore){
        List<StuScore> data=stuScoreService.findStuScoreListByCondition(stuScore);
        return new ResponseJson(data);
    }
}
