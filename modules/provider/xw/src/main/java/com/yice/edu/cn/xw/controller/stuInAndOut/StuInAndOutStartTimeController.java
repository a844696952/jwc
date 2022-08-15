package com.yice.edu.cn.xw.controller.stuInAndOut;

import com.yice.edu.cn.common.pojo.xw.stuInAndOut.StuInAndOutStartTime;
import com.yice.edu.cn.xw.service.stuInAndOut.StuInAndOutStartTimeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stuInAndOutStartTime")
@Api(value = "/stuInAndOutStartTime",description = "模块")
public class StuInAndOutStartTimeController {
    @Autowired
    private StuInAndOutStartTimeService stuInAndOutStartTimeService;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findStuInAndOutStartTimeById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public StuInAndOutStartTime findStuInAndOutStartTimeById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return stuInAndOutStartTimeService.findStuInAndOutStartTimeById(id);
    }

    @PostMapping("/saveStuInAndOutStartTime")
    @ApiOperation(value = "保存", notes = "返回对象")
    public StuInAndOutStartTime saveStuInAndOutStartTime(
            @ApiParam(value = "对象", required = true)
            @RequestBody StuInAndOutStartTime stuInAndOutStartTime){
        stuInAndOutStartTimeService.saveStuInAndOutStartTime(stuInAndOutStartTime);
        return stuInAndOutStartTime;
    }

    @PostMapping("/batchSaveStuInAndOutStartTime")
    @ApiOperation(value = "批量保存")
    public void batchSaveStuInAndOutStartTime(
            @ApiParam(value = "对象集合", required = true)
            @RequestBody List<StuInAndOutStartTime> stuInAndOutStartTimes){
        stuInAndOutStartTimeService.batchSaveStuInAndOutStartTime(stuInAndOutStartTimes);
    }

    @PostMapping("/findStuInAndOutStartTimeListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<StuInAndOutStartTime> findStuInAndOutStartTimeListByCondition(
            @ApiParam(value = "对象")
            @RequestBody StuInAndOutStartTime stuInAndOutStartTime){
        return stuInAndOutStartTimeService.findStuInAndOutStartTimeListByCondition(stuInAndOutStartTime);
    }
    @PostMapping("/findStuInAndOutStartTimeCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findStuInAndOutStartTimeCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody StuInAndOutStartTime stuInAndOutStartTime){
        return stuInAndOutStartTimeService.findStuInAndOutStartTimeCountByCondition(stuInAndOutStartTime);
    }

    @PostMapping("/updateStuInAndOutStartTime")
    @ApiOperation(value = "修改有值的字段", notes = "对象必传")
    public void updateStuInAndOutStartTime(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody StuInAndOutStartTime stuInAndOutStartTime){
        stuInAndOutStartTimeService.updateStuInAndOutStartTime(stuInAndOutStartTime);
    }
    @PostMapping("/updateStuInAndOutStartTimeForAll")
    @ApiOperation(value = "修改所有字段", notes = "对象必传")
    public void updateStuInAndOutStartTimeForAll(
            @ApiParam(value = "对象", required = true)
            @RequestBody StuInAndOutStartTime stuInAndOutStartTime){
        stuInAndOutStartTimeService.updateStuInAndOutStartTimeForAll(stuInAndOutStartTime);
    }

    @GetMapping("/deleteStuInAndOutStartTime/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteStuInAndOutStartTime(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        stuInAndOutStartTimeService.deleteStuInAndOutStartTime(id);
    }
    @PostMapping("/deleteStuInAndOutStartTimeByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deleteStuInAndOutStartTimeByCondition(
            @ApiParam(value = "对象")
            @RequestBody StuInAndOutStartTime stuInAndOutStartTime){
        stuInAndOutStartTimeService.deleteStuInAndOutStartTimeByCondition(stuInAndOutStartTime);
    }
    @PostMapping("/findOneStuInAndOutStartTimeByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public StuInAndOutStartTime findOneStuInAndOutStartTimeByCondition(
            @ApiParam(value = "对象")
            @RequestBody StuInAndOutStartTime stuInAndOutStartTime){
        return stuInAndOutStartTimeService.findOneStuInAndOutStartTimeByCondition(stuInAndOutStartTime);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    @PostMapping("/sendToSchoolTeacherStuNowStatus")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public void sendToSchoolTeacherStuNowStatus(@RequestBody String taskId){
        stuInAndOutStartTimeService.sendToSchoolTeacherStuNowStatus(taskId);
    }

    @GetMapping("/stuNotArriveSchool")
    @ApiOperation(value = "学校每日统计学生未到校记录", notes = "返回单个,没有时为空")
    public void stuNotArriveSchool(){
        stuInAndOutStartTimeService.stuNotArriveSchool(null);
    }
}
