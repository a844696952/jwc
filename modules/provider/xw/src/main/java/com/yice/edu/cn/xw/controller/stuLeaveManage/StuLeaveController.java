package com.yice.edu.cn.xw.controller.stuLeaveManage;

import cn.hutool.core.date.DateUtil;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.xw.stuLeaveManage.StuLeave;
import com.yice.edu.cn.xw.service.stuLeaveManage.StuLeaveService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stuLeave")
@Api(value = "/stuLeave", description = "模块")
public class StuLeaveController {
    @Autowired
    private StuLeaveService stuLeaveService;

    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findStuLeaveById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public StuLeave findStuLeaveById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id) {
        return stuLeaveService.findStuLeaveById(id);
    }

    @PostMapping("/saveStuLeave")
    @ApiOperation(value = "保存", notes = "返回对象")
    public StuLeave saveStuLeave(
            @ApiParam(value = "对象", required = true)
            @RequestBody StuLeave stuLeave) {
        stuLeaveService.saveStuLeave(stuLeave);
        return stuLeave;
    }

    @PostMapping("/batchSaveStuLeave")
    @ApiOperation(value = "批量保存")
    public void batchSaveStuLeave(
            @ApiParam(value = "对象集合", required = true)
            @RequestBody List<StuLeave> stuLeaves) {
        stuLeaveService.batchSaveStuLeave(stuLeaves);
    }

    @PostMapping("/findStuLeaveListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<StuLeave> findStuLeaveListByCondition(
            @ApiParam(value = "对象")
            @RequestBody StuLeave stuLeave) {
        return stuLeaveService.findStuLeaveListByCondition(stuLeave);
    }

    @PostMapping("/findStuLeaveCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findStuLeaveCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody StuLeave stuLeave) {
        return stuLeaveService.findStuLeaveCountByCondition(stuLeave);
    }

    @PostMapping("/updateStuLeave")
    @ApiOperation(value = "修改有值的字段", notes = "对象必传")
    public void updateStuLeave(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody StuLeave stuLeave) {
        stuLeaveService.updateStuLeave(stuLeave);
    }

    @PostMapping("/updateStuLeaveForAll")
    @ApiOperation(value = "修改所有字段", notes = "对象必传")
    public void updateStuLeaveForAll(
            @ApiParam(value = "对象", required = true)
            @RequestBody StuLeave stuLeave) {
        stuLeaveService.updateStuLeaveForAll(stuLeave);
    }

    @GetMapping("/deleteStuLeave/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteStuLeave(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id) {
        stuLeaveService.deleteStuLeave(id);
    }

    @PostMapping("/deleteStuLeaveByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deleteStuLeaveByCondition(
            @ApiParam(value = "对象")
            @RequestBody StuLeave stuLeave) {
        stuLeaveService.deleteStuLeaveByCondition(stuLeave);
    }

    @PostMapping("/findOneStuLeaveByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public StuLeave findOneStuLeaveByCondition(
            @ApiParam(value = "对象")
            @RequestBody StuLeave stuLeave) {
        return stuLeaveService.findOneStuLeaveByCondition(stuLeave);
    }
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

    @PostMapping("/findStuLeaveListByConditionForWH")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<StuLeave> findStuLeaveListByConditionForWH(
            @ApiParam(value = "对象")
            @RequestBody StuLeave stuLeave) {
        return stuLeaveService.findStuLeaveListByConditionForWH(stuLeave);
    }

    @PostMapping("/findStuLeaveCountByConditionForWH")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findStuLeaveCountByConditionForWH(
            @ApiParam(value = "对象")
            @RequestBody StuLeave stuLeave) {
        return stuLeaveService.findStuLeaveCountByConditionForWH(stuLeave);
    }
}
