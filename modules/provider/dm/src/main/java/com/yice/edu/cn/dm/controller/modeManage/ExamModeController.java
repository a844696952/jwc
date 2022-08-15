package com.yice.edu.cn.dm.controller.modeManage;

import com.yice.edu.cn.common.pojo.dm.modeManage.ExamMode;
import com.yice.edu.cn.dm.service.modeManage.ExamModeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/examMode")
@Api(value = "/examMode",description = "考试模式表模块")
public class ExamModeController {


    @Autowired
    private ExamModeService examModeService;

    @GetMapping("/findExamModeById/{id}")
    @ApiOperation(value = "通过id查找考试模式表", notes = "返回考试模式表对象")
    public ExamMode findExamModeById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return examModeService.findExamModeById(id);
    }

    @PostMapping("/saveExamMode")
    @ApiOperation(value = "保存考试模式表", notes = "返回考试模式表对象")
    public ExamMode saveExamModeMode(
            @ApiParam(value = "考试模式表对象", required = true)
            @RequestBody ExamMode examMode){
        ExamMode mode = examModeService.saveExamMode(examMode);
        return mode;
    }

    @PostMapping("/findExamModeListByCondition")
    @ApiOperation(value = "根据条件查找考试模式表列表", notes = "返回考试模式表列表")
    public List<ExamMode> findExamModeListByCondition(
            @ApiParam(value = "考试模式对象")
            @RequestBody ExamMode examMode){
        return examModeService.findExamModeListByCondition(examMode);
    }
    @PostMapping("/findExamModeCountByCondition")
    @ApiOperation(value = "根据条件查找考试模式表列表个数", notes = "返回考试模式表总个数")
    public long findExamModeCountByCondition(
            @ApiParam(value = "考试模式表对象")
            @RequestBody ExamMode examMode){
        return examModeService.findExamModeCountByCondition(examMode);
    }



    @GetMapping("/deleteExamMode/{id}")
    @ApiOperation(value = "通过id删除考试模式表")
    public void deleteExamMode(
            @ApiParam(value = "考试模式表对象", required = true)
            @PathVariable String id){
        examModeService.deleteExamModeById(id);
    }



    @PostMapping("/close/{id}")
    @ApiOperation(value = "通过id关闭考试模式表")
    public void close(
            @ApiParam(value = "考试模式表对象", required = true)
            @PathVariable String id){
        examModeService.close(id);
    }




    @PostMapping("/updateExamMode")
    @ApiOperation(value = "修改考试模式表", notes = "考试模式表对象必传")
    public ExamMode updateExamMode(
            @ApiParam(value = "考试模式表对象,对象属性不为空则修改", required = true)
            @RequestBody ExamMode examMode){
       return examModeService.updateExamMode(examMode);
    }

    @PostMapping("/findByCExamModeByTime")
    @ApiOperation(value = "查询当前时间范围内的考试模式", notes = "考试模式表对象必传")
    public List<ExamMode> findByCExamModeByTime(
            @ApiParam(value = "考试模式表对象", required = true)
            @RequestBody ExamMode exam){
        return examModeService.findByCExamModeByTime(exam);
    }



    @PostMapping("/updateExamInfo")
    @ApiOperation(value = "修改考试模式表", notes = "考试模式表对象必传")
    public ExamMode updateExamInfo(
            @ApiParam(value = "考试模式表对象,对象属性不为空则修改", required = true)
            @RequestBody ExamMode examMode){
        return examModeService.updateExamInfo(examMode);
    }

    @PostMapping("/deleteExamInfo")
    @ApiOperation(value = "修改考试模式表", notes = "考试模式表对象必传")
    public ExamMode deleteExamInfo(
            @ApiParam(value = "考试模式表对象,对象属性不为空则修改", required = true)
            @RequestBody ExamMode examMode){
        return examModeService.updateExamInfo(examMode);
    }

}
