package com.yice.edu.cn.xw.controller.DutyteachersDutyarrment;

import com.yice.edu.cn.common.pojo.xw.dutyteachersDutyarrment.DutyteachersDutyarrment;
import com.yice.edu.cn.xw.service.DutyteachersDutyarrment.DutyteachersDutyarrmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dutyteachersDutyarrment")
@Api(value = "/dutyteachersDutyarrment",description = "常规值班查询：值班教师和值班安排关联表模块")
public class DutyteachersDutyarrmentController {
    @Autowired
    private DutyteachersDutyarrmentService dutyteachersDutyarrmentService;

    @GetMapping("/findDutyteachersDutyarrmentById/{id}")
    @ApiOperation(value = "通过id查找常规值班查询：值班教师和值班安排关联表", notes = "返回常规值班查询：值班教师和值班安排关联表对象")
    public DutyteachersDutyarrment findDutyteachersDutyarrmentById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return dutyteachersDutyarrmentService.findDutyteachersDutyarrmentById(id);
    }

    @PostMapping("/saveDutyteachersDutyarrment")
    @ApiOperation(value = "保存常规值班查询：值班教师和值班安排关联表", notes = "返回常规值班查询：值班教师和值班安排关联表对象")
    public DutyteachersDutyarrment saveDutyteachersDutyarrment(
            @ApiParam(value = "常规值班查询：值班教师和值班安排关联表对象", required = true)
            @RequestBody DutyteachersDutyarrment dutyteachersDutyarrment){
        dutyteachersDutyarrmentService.saveDutyteachersDutyarrment(dutyteachersDutyarrment);
        return dutyteachersDutyarrment;
    }

    @PostMapping("/findDutyteachersDutyarrmentListByCondition")
    @ApiOperation(value = "根据条件查找常规值班查询：值班教师和值班安排关联表列表", notes = "返回常规值班查询：值班教师和值班安排关联表列表")
    public List<DutyteachersDutyarrment> findDutyteachersDutyarrmentListByCondition(
            @ApiParam(value = "常规值班查询：值班教师和值班安排关联表对象")
            @RequestBody DutyteachersDutyarrment dutyteachersDutyarrment){
        return dutyteachersDutyarrmentService.findDutyteachersDutyarrmentListByCondition(dutyteachersDutyarrment);
    }
    @PostMapping("/findDutyteachersDutyarrmentCountByCondition")
    @ApiOperation(value = "根据条件查找常规值班查询：值班教师和值班安排关联表列表个数", notes = "返回常规值班查询：值班教师和值班安排关联表总个数")
    public long findDutyteachersDutyarrmentCountByCondition(
            @ApiParam(value = "常规值班查询：值班教师和值班安排关联表对象")
            @RequestBody DutyteachersDutyarrment dutyteachersDutyarrment){
        return dutyteachersDutyarrmentService.findDutyteachersDutyarrmentCountByCondition(dutyteachersDutyarrment);
    }

    @PostMapping("/updateDutyteachersDutyarrment")
    @ApiOperation(value = "修改常规值班查询：值班教师和值班安排关联表", notes = "常规值班查询：值班教师和值班安排关联表对象必传")
    public void updateDutyteachersDutyarrment(
            @ApiParam(value = "常规值班查询：值班教师和值班安排关联表对象,对象属性不为空则修改", required = true)
            @RequestBody DutyteachersDutyarrment dutyteachersDutyarrment){
        dutyteachersDutyarrmentService.updateDutyteachersDutyarrment(dutyteachersDutyarrment);
    }

    @GetMapping("/deleteDutyteachersDutyarrment/{id}")
    @ApiOperation(value = "通过id删除常规值班查询：值班教师和值班安排关联表")
    public void deleteDutyteachersDutyarrment(
            @ApiParam(value = "常规值班查询：值班教师和值班安排关联表对象", required = true)
            @PathVariable String id){
        dutyteachersDutyarrmentService.deleteDutyteachersDutyarrment(id);
    }
    @PostMapping("/deleteDutyteachersDutyarrmentByCondition")
    @ApiOperation(value = "根据条件删除常规值班查询：值班教师和值班安排关联表")
    public void deleteDutyteachersDutyarrmentByCondition(
            @ApiParam(value = "常规值班查询：值班教师和值班安排关联表对象")
            @RequestBody DutyteachersDutyarrment dutyteachersDutyarrment){
        dutyteachersDutyarrmentService.deleteDutyteachersDutyarrmentByCondition(dutyteachersDutyarrment);
    }
    @PostMapping("/findOneDutyteachersDutyarrmentByCondition")
    @ApiOperation(value = "根据条件查找单个常规值班查询：值班教师和值班安排关联表,结果必须为单条数据", notes = "返回单个常规值班查询：值班教师和值班安排关联表,没有时为空")
    public DutyteachersDutyarrment findOneDutyteachersDutyarrmentByCondition(
            @ApiParam(value = "常规值班查询：值班教师和值班安排关联表对象")
            @RequestBody DutyteachersDutyarrment dutyteachersDutyarrment){
        return dutyteachersDutyarrmentService.findOneDutyteachersDutyarrmentByCondition(dutyteachersDutyarrment);
    }
}
