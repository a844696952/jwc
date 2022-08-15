package com.yice.edu.cn.xw.controller.dutyArrment;

import com.yice.edu.cn.common.pojo.xw.dutyArrment.DutyArrment;
import com.yice.edu.cn.xw.service.dutyArrment.DutyArrmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dutyArrment")
@Api(value = "/dutyArrment",description = "常规值班安排表模块")
public class DutyArrmentController {
    @Autowired
    private DutyArrmentService dutyArrmentService;

    @GetMapping("/findDutyArrmentById/{id}")
    @ApiOperation(value = "通过id查找常规值班安排表", notes = "返回常规值班安排表对象")
    public DutyArrment findDutyArrmentById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return dutyArrmentService.findDutyArrmentById(id);
    }

    @PostMapping("/saveDutyArrment")
    @ApiOperation(value = "保存常规值班安排表", notes = "返回常规值班安排表对象")
    public DutyArrment saveDutyArrment(
            @ApiParam(value = "常规值班安排表对象", required = true)
            @RequestBody DutyArrment dutyArrment){
        dutyArrmentService.saveDutyArrment(dutyArrment);
        return dutyArrment;
    }

    @PostMapping("/findDutyArrmentListByConditionForLike")
    @ApiOperation(value = "根据条件查找常规值班安排表列表", notes = "返回常规值班安排表列表")
    public List<DutyArrment> findDutyArrmentListByConditionForLike(
            @ApiParam(value = "常规值班安排表对象")
            @RequestBody DutyArrment dutyArrment){
        return dutyArrmentService.findDutyArrmentListByConditionForLike(dutyArrment);
    }

    @PostMapping("/findDutyArrmentListByCondition")
    @ApiOperation(value = "根据条件查找常规值班安排表列表", notes = "返回常规值班安排表列表")
    public List<DutyArrment> findDutyArrmentListByCondition(
            @ApiParam(value = "常规值班安排表对象")
            @RequestBody DutyArrment dutyArrment){
        return dutyArrmentService.findDutyArrmentListByCondition(dutyArrment);
    }
    @PostMapping("/findDutyArrmentCountByCondition")
    @ApiOperation(value = "根据条件查找常规值班安排表列表个数", notes = "返回常规值班安排表总个数")
    public long findDutyArrmentCountByCondition(
            @ApiParam(value = "常规值班安排表对象")
            @RequestBody DutyArrment dutyArrment){
        return dutyArrmentService.findDutyArrmentCountByCondition(dutyArrment);
    }

    @PostMapping("/findDutyArrmentCountByConditionForLike")
    @ApiOperation(value = "根据条件查找常规值班安排表列表个数", notes = "返回常规值班安排表总个数")
    public long  findDutyArrmentCountByConditionForLike(
            @ApiParam(value = "常规值班安排表对象")
            @RequestBody DutyArrment dutyArrment){
        return dutyArrmentService. findDutyArrmentCountByConditionForLike(dutyArrment);
    }

    @PostMapping("/updateDutyArrment")
    @ApiOperation(value = "修改常规值班安排表", notes = "常规值班安排表对象必传")
    public void updateDutyArrment(
            @ApiParam(value = "常规值班安排表对象,对象属性不为空则修改", required = true)
            @RequestBody DutyArrment dutyArrment){
        dutyArrmentService.updateDutyArrment(dutyArrment);
    }

    @GetMapping("/deleteDutyArrment/{id}")
    @ApiOperation(value = "通过id删除常规值班安排表")
    public void deleteDutyArrment(
            @ApiParam(value = "常规值班安排表对象", required = true)
            @PathVariable String id){
        dutyArrmentService.deleteDutyArrment(id);
    }
    @PostMapping("/deleteDutyArrmentByCondition")
    @ApiOperation(value = "根据条件删除常规值班安排表")
    public void deleteDutyArrmentByCondition(
            @ApiParam(value = "常规值班安排表对象")
            @RequestBody DutyArrment dutyArrment){
        dutyArrmentService.deleteDutyArrmentByCondition(dutyArrment);
    }
    @PostMapping("/findOneDutyArrmentByCondition")
    @ApiOperation(value = "根据条件查找单个常规值班安排表,结果必须为单条数据", notes = "返回单个常规值班安排表,没有时为空")
    public DutyArrment findOneDutyArrmentByCondition(
            @ApiParam(value = "常规值班安排表对象")
            @RequestBody DutyArrment dutyArrment){
        return dutyArrmentService.findOneDutyArrmentByCondition(dutyArrment);
    }
}
