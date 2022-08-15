package com.yice.edu.cn.jw.controller.classSchedule;

import com.yice.edu.cn.common.pojo.jw.classSchedule.PastClassSchedule;
import com.yice.edu.cn.jw.service.classSchedule.PastClassScheduleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pastClassSchedule")
@Api(value = "/pastClassSchedule",description = "往期课表记录表模块")
public class PastClassScheduleController {
    @Autowired
    private PastClassScheduleService pastClassScheduleService;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findPastClassScheduleById/{id}")
    @ApiOperation(value = "通过id查找往期课表记录表", notes = "返回往期课表记录表对象")
    public PastClassSchedule findPastClassScheduleById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return pastClassScheduleService.findPastClassScheduleById(id);
    }

    @PostMapping("/savePastClassSchedule")
    @ApiOperation(value = "保存往期课表记录表", notes = "返回往期课表记录表对象")
    public PastClassSchedule savePastClassSchedule(
            @ApiParam(value = "往期课表记录表对象", required = true)
            @RequestBody PastClassSchedule pastClassSchedule){
        pastClassScheduleService.savePastClassSchedule(pastClassSchedule);
        return pastClassSchedule;
    }

    @PostMapping("/batchSavePastClassSchedule")
    @ApiOperation(value = "批量保存往期课表记录表")
    public void batchSavePastClassSchedule(
            @ApiParam(value = "往期课表记录表对象集合", required = true)
            @RequestBody List<PastClassSchedule> pastClassSchedules){
        pastClassScheduleService.batchSavePastClassSchedule(pastClassSchedules);
    }

    @PostMapping("/findPastClassScheduleListByCondition")
    @ApiOperation(value = "根据条件查找往期课表记录表列表", notes = "返回往期课表记录表列表")
    public List<PastClassSchedule> findPastClassScheduleListByCondition(
            @ApiParam(value = "往期课表记录表对象")
            @RequestBody PastClassSchedule pastClassSchedule){
        return pastClassScheduleService.findPastClassScheduleListByCondition(pastClassSchedule);
    }
    @PostMapping("/findPastClassScheduleCountByCondition")
    @ApiOperation(value = "根据条件查找往期课表记录表列表个数", notes = "返回往期课表记录表总个数")
    public long findPastClassScheduleCountByCondition(
            @ApiParam(value = "往期课表记录表对象")
            @RequestBody PastClassSchedule pastClassSchedule){
        return pastClassScheduleService.findPastClassScheduleCountByCondition(pastClassSchedule);
    }

    @PostMapping("/updatePastClassSchedule")
    @ApiOperation(value = "修改往期课表记录表有值的字段", notes = "往期课表记录表对象必传")
    public void updatePastClassSchedule(
            @ApiParam(value = "往期课表记录表对象,对象属性不为空则修改", required = true)
            @RequestBody PastClassSchedule pastClassSchedule){
        pastClassScheduleService.updatePastClassSchedule(pastClassSchedule);
    }
    @PostMapping("/updatePastClassScheduleForAll")
    @ApiOperation(value = "修改往期课表记录表所有字段", notes = "往期课表记录表对象必传")
    public void updatePastClassScheduleForAll(
            @ApiParam(value = "往期课表记录表对象", required = true)
            @RequestBody PastClassSchedule pastClassSchedule){
        pastClassScheduleService.updatePastClassScheduleForAll(pastClassSchedule);
    }

    @GetMapping("/deletePastClassSchedule/{id}")
    @ApiOperation(value = "通过id删除往期课表记录表")
    public void deletePastClassSchedule(
            @ApiParam(value = "往期课表记录表对象", required = true)
            @PathVariable String id){
        pastClassScheduleService.deletePastClassSchedule(id);
    }
    @PostMapping("/deletePastClassScheduleByCondition")
    @ApiOperation(value = "根据条件删除往期课表记录表")
    public void deletePastClassScheduleByCondition(
            @ApiParam(value = "往期课表记录表对象")
            @RequestBody PastClassSchedule pastClassSchedule){
        pastClassScheduleService.deletePastClassScheduleByCondition(pastClassSchedule);
    }
    @PostMapping("/findOnePastClassScheduleByCondition")
    @ApiOperation(value = "根据条件查找单个往期课表记录表,结果必须为单条数据", notes = "返回单个往期课表记录表,没有时为空")
    public PastClassSchedule findOnePastClassScheduleByCondition(
            @ApiParam(value = "往期课表记录表对象")
            @RequestBody PastClassSchedule pastClassSchedule){
        return pastClassScheduleService.findOnePastClassScheduleByCondition(pastClassSchedule);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

    @PostMapping("/findListPastClassScheduleByCountKong")
    public List<PastClassSchedule> findListPastClassScheduleByCountKong(@RequestBody PastClassSchedule pastClassSchedule){
        return pastClassScheduleService.findListPastClassScheduleByCountKong(pastClassSchedule);
    }
}
