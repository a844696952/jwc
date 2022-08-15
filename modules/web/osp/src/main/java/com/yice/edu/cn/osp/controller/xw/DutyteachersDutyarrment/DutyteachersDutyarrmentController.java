package com.yice.edu.cn.osp.controller.xw.DutyteachersDutyarrment;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.xw.dutyteachersDutyarrment.DutyteachersDutyarrment;
import com.yice.edu.cn.osp.service.xw.DutyteachersDutyarrment.DutyteachersDutyarrmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@RestController
@RequestMapping("/dutyteachersDutyarrment")
@Api(value = "/dutyteachersDutyarrment",description = "常规值班查询：值班教师和值班安排关联表模块")
public class DutyteachersDutyarrmentController {
    @Autowired
    private DutyteachersDutyarrmentService dutyteachersDutyarrmentService;

    @PostMapping("/saveDutyteachersDutyarrment")
    @ApiOperation(value = "保存常规值班查询：值班教师和值班安排关联表对象", notes = "返回保存好的常规值班查询：值班教师和值班安排关联表对象", response=DutyteachersDutyarrment.class)
    public ResponseJson saveDutyteachersDutyarrment(
            @ApiParam(value = "常规值班查询：值班教师和值班安排关联表对象", required = true)
            @RequestBody DutyteachersDutyarrment dutyteachersDutyarrment){
        DutyteachersDutyarrment s=dutyteachersDutyarrmentService.saveDutyteachersDutyarrment(dutyteachersDutyarrment);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findDutyteachersDutyarrmentById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找常规值班查询：值班教师和值班安排关联表", notes = "返回响应对象", response=DutyteachersDutyarrment.class)
    public ResponseJson findDutyteachersDutyarrmentById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        DutyteachersDutyarrment dutyteachersDutyarrment=dutyteachersDutyarrmentService.findDutyteachersDutyarrmentById(id);
        return new ResponseJson(dutyteachersDutyarrment);
    }

    @PostMapping("/update/updateDutyteachersDutyarrment")
    @ApiOperation(value = "修改常规值班查询：值班教师和值班安排关联表对象", notes = "返回响应对象")
    public ResponseJson updateDutyteachersDutyarrment(
            @ApiParam(value = "被修改的常规值班查询：值班教师和值班安排关联表对象,对象属性不为空则修改", required = true)
            @RequestBody DutyteachersDutyarrment dutyteachersDutyarrment){
        dutyteachersDutyarrmentService.updateDutyteachersDutyarrment(dutyteachersDutyarrment);
        return new ResponseJson();
    }

    @GetMapping("/look/lookDutyteachersDutyarrmentById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找常规值班查询：值班教师和值班安排关联表", notes = "返回响应对象", response=DutyteachersDutyarrment.class)
    public ResponseJson lookDutyteachersDutyarrmentById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        DutyteachersDutyarrment dutyteachersDutyarrment=dutyteachersDutyarrmentService.findDutyteachersDutyarrmentById(id);
        return new ResponseJson(dutyteachersDutyarrment);
    }

    @PostMapping("/findDutyteachersDutyarrmentsByCondition")
    @ApiOperation(value = "根据条件查找常规值班查询：值班教师和值班安排关联表", notes = "返回响应对象", response=DutyteachersDutyarrment.class)
    public ResponseJson findDutyteachersDutyarrmentsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody DutyteachersDutyarrment dutyteachersDutyarrment){
        List<DutyteachersDutyarrment> data=dutyteachersDutyarrmentService.findDutyteachersDutyarrmentListByCondition(dutyteachersDutyarrment);
        long count=dutyteachersDutyarrmentService.findDutyteachersDutyarrmentCountByCondition(dutyteachersDutyarrment);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneDutyteachersDutyarrmentByCondition")
    @ApiOperation(value = "根据条件查找单个常规值班查询：值班教师和值班安排关联表,结果必须为单条数据", notes = "没有时返回空", response=DutyteachersDutyarrment.class)
    public ResponseJson findOneDutyteachersDutyarrmentByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody DutyteachersDutyarrment dutyteachersDutyarrment){
        DutyteachersDutyarrment one=dutyteachersDutyarrmentService.findOneDutyteachersDutyarrmentByCondition(dutyteachersDutyarrment);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteDutyteachersDutyarrment/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteDutyteachersDutyarrment(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        dutyteachersDutyarrmentService.deleteDutyteachersDutyarrment(id);
        return new ResponseJson();
    }


    @PostMapping("/findDutyteachersDutyarrmentListByCondition")
    @ApiOperation(value = "根据条件查找常规值班查询：值班教师和值班安排关联表列表", notes = "返回响应对象,不包含总条数", response=DutyteachersDutyarrment.class)
    public ResponseJson findDutyteachersDutyarrmentListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody DutyteachersDutyarrment dutyteachersDutyarrment){
        List<DutyteachersDutyarrment> data=dutyteachersDutyarrmentService.findDutyteachersDutyarrmentListByCondition(dutyteachersDutyarrment);
        return new ResponseJson(data);
    }



}
