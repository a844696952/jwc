package com.yice.edu.cn.jw.controller.dynamicTask;

import com.yice.edu.cn.common.pojo.ts.dynamicCron.DynamicCron;
import com.yice.edu.cn.jw.service.dynamicTask.DynamicCronService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dynamicCron")
@Api(value = "/dynamicCron",description = "动态任务规则列表模块")
public class DynamicCronController {
    @Autowired
    private DynamicCronService dynamicCronService;

    @GetMapping("/findDynamicCronById/{id}")
    @ApiOperation(value = "通过id查找动态任务规则列表", notes = "返回动态任务规则列表对象")
    public DynamicCron findDynamicCronById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return dynamicCronService.findDynamicCronById(id);
    }

    @PostMapping("/saveDynamicCron")
    @ApiOperation(value = "保存动态任务规则列表", notes = "返回动态任务规则列表对象")
    public DynamicCron saveDynamicCron(
            @ApiParam(value = "动态任务规则列表对象", required = true)
            @RequestBody DynamicCron dynamicCron){
        dynamicCronService.saveDynamicCron(dynamicCron);
        return dynamicCron;
    }

    @PostMapping("/findDynamicCronListByCondition")
    @ApiOperation(value = "根据条件查找动态任务规则列表列表", notes = "返回动态任务规则列表列表")
    public List<DynamicCron> findDynamicCronListByCondition(
            @ApiParam(value = "动态任务规则列表对象")
            @RequestBody DynamicCron dynamicCron){
        return dynamicCronService.findDynamicCronListByCondition(dynamicCron);
    }
    @PostMapping("/findDynamicCronCountByCondition")
    @ApiOperation(value = "根据条件查找动态任务规则列表列表个数", notes = "返回动态任务规则列表总个数")
    public long findDynamicCronCountByCondition(
            @ApiParam(value = "动态任务规则列表对象")
            @RequestBody DynamicCron dynamicCron){
        return dynamicCronService.findDynamicCronCountByCondition(dynamicCron);
    }

    @PostMapping("/updateDynamicCron")
    @ApiOperation(value = "修改动态任务规则列表", notes = "动态任务规则列表对象必传")
    public void updateDynamicCron(
            @ApiParam(value = "动态任务规则列表对象,对象属性不为空则修改", required = true)
            @RequestBody DynamicCron dynamicCron){
        dynamicCronService.updateDynamicCron(dynamicCron);
    }

    @GetMapping("/deleteDynamicCron/{id}")
    @ApiOperation(value = "通过id删除动态任务规则列表")
    public void deleteDynamicCron(
            @ApiParam(value = "动态任务规则列表对象", required = true)
            @PathVariable String id){
        dynamicCronService.deleteDynamicCron(id);
    }
    @PostMapping("/deleteDynamicCronByCondition")
    @ApiOperation(value = "根据条件删除动态任务规则列表")
    public void deleteDynamicCronByCondition(
            @ApiParam(value = "动态任务规则列表对象")
            @RequestBody DynamicCron dynamicCron){
        dynamicCronService.deleteDynamicCronByCondition(dynamicCron);
    }
    @PostMapping("/findOneDynamicCronByCondition")
    @ApiOperation(value = "根据条件查找单个动态任务规则列表,结果必须为单条数据", notes = "返回单个动态任务规则列表,没有时为空")
    public DynamicCron findOneDynamicCronByCondition(
            @ApiParam(value = "动态任务规则列表对象")
            @RequestBody DynamicCron dynamicCron){
        return dynamicCronService.findOneDynamicCronByCondition(dynamicCron);
    }
}
