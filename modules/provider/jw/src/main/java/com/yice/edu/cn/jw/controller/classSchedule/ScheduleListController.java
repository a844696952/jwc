package com.yice.edu.cn.jw.controller.classSchedule;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.classSchedule.ScheduleList;
import com.yice.edu.cn.jw.service.classSchedule.ScheduleListService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/scheduleList")
@Api(value = "/scheduleList",description = "课表管理列表模块")
public class ScheduleListController {
    @Autowired
    private ScheduleListService scheduleListService;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findScheduleListById/{id}")
    @ApiOperation(value = "通过id查找课表管理列表", notes = "返回课表管理列表对象")
    public ScheduleList findScheduleListById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return scheduleListService.findScheduleListById(id);
    }

    @PostMapping("/saveScheduleList")
    @ApiOperation(value = "保存课表管理列表", notes = "返回课表管理列表对象")
    public ScheduleList saveScheduleList(
            @ApiParam(value = "课表管理列表对象", required = true)
            @RequestBody ScheduleList scheduleList){
        scheduleListService.saveScheduleList(scheduleList);
        return scheduleList;
    }

    @PostMapping("/batchSaveScheduleList")
    @ApiOperation(value = "批量保存课表管理列表")
    public void batchSaveScheduleList(
            @ApiParam(value = "课表管理列表对象集合", required = true)
            @RequestBody List<ScheduleList> scheduleLists){
        scheduleListService.batchSaveScheduleList(scheduleLists);
    }

    @PostMapping("/findScheduleListListByCondition")
    @ApiOperation(value = "根据条件查找课表管理列表列表", notes = "返回课表管理列表列表")
    public List<ScheduleList> findScheduleListListByCondition(
            @ApiParam(value = "课表管理列表对象")
            @RequestBody ScheduleList scheduleList){
        return scheduleListService.findScheduleListListByCondition(scheduleList);
    }
    @PostMapping("/findScheduleListCountByCondition")
    @ApiOperation(value = "根据条件查找课表管理列表列表个数", notes = "返回课表管理列表总个数")
    public long findScheduleListCountByCondition(
            @ApiParam(value = "课表管理列表对象")
            @RequestBody ScheduleList scheduleList){
        return scheduleListService.findScheduleListCountByCondition(scheduleList);
    }

    @PostMapping("/updateScheduleList")
    @ApiOperation(value = "修改课表管理列表有值的字段", notes = "课表管理列表对象必传")
    public void updateScheduleList(
            @ApiParam(value = "课表管理列表对象,对象属性不为空则修改", required = true)
            @RequestBody ScheduleList scheduleList){
        scheduleListService.updateScheduleList(scheduleList);
    }
    @PostMapping("/updateScheduleListForAll")
    @ApiOperation(value = "修改课表管理列表所有字段", notes = "课表管理列表对象必传")
    public void updateScheduleListForAll(
            @ApiParam(value = "课表管理列表对象", required = true)
            @RequestBody ScheduleList scheduleList){
        scheduleListService.updateScheduleListForAll(scheduleList);
    }

    @GetMapping("/deleteScheduleList/{id}")
    @ApiOperation(value = "通过id删除课表管理列表")
    public void deleteScheduleList(
            @ApiParam(value = "课表管理列表对象", required = true)
            @PathVariable String id){
        scheduleListService.deleteScheduleList(id);
    }
    @PostMapping("/deleteScheduleListByCondition")
    @ApiOperation(value = "根据条件删除课表管理列表")
    public void deleteScheduleListByCondition(
            @ApiParam(value = "课表管理列表对象")
            @RequestBody ScheduleList scheduleList){
        scheduleListService.deleteScheduleListByCondition(scheduleList);
    }
    @PostMapping("/findOneScheduleListByCondition")
    @ApiOperation(value = "根据条件查找单个课表管理列表,结果必须为单条数据", notes = "返回单个课表管理列表,没有时为空")
    public ScheduleList findOneScheduleListByCondition(
            @ApiParam(value = "课表管理列表对象")
            @RequestBody ScheduleList scheduleList){
        return scheduleListService.findOneScheduleListByCondition(scheduleList);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

    @PostMapping("/updateScheduleAndSavePastSchedule")
    public void updateScheduleAndSavePastSchedule(
            @RequestBody ScheduleList scheduleList
    ){
        scheduleListService.updateScheduleAndSavePastSchedule(scheduleList);
    }

    @PostMapping("/saveScheduleListKong")
    public ResponseJson saveScheduleListKong(@RequestBody ScheduleList scheduleList){
        return scheduleListService.saveScheduleListKong(scheduleList);
    }

    @GetMapping("/deleteScheduleAndClassSchedule/{id}")
    public void deleteScheduleAndClassSchedule(@PathVariable("id")String id){
        scheduleListService.deleteScheduleAndClassSchedule(id);
    }

    @PostMapping("/issueScheduleListKong")
    public ResponseJson issueScheduleListKong(@RequestBody ScheduleList scheduleList){
        return scheduleListService.issueScheduleListKong(scheduleList);
    }
}
