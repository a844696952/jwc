package com.yice.edu.cn.xw.controller.stuInAndOut;

import com.yice.edu.cn.common.pojo.xw.stuInAndOut.StuStatusChange;
import com.yice.edu.cn.xw.service.stuInAndOut.StuStatusChangeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stuStatusChange")
@Api(value = "/stuStatusChange",description = "模块")
public class StuStatusChangeController {
    @Autowired
    private StuStatusChangeService stuStatusChangeService;

    @GetMapping("/findStuStatusChangeById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public StuStatusChange findStuStatusChangeById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return stuStatusChangeService.findStuStatusChangeById(id);
    }

    @PostMapping("/saveStuStatusChange")
    @ApiOperation(value = "保存", notes = "返回对象")
    public StuStatusChange saveStuStatusChange(
            @ApiParam(value = "对象", required = true)
            @RequestBody StuStatusChange stuStatusChange){
        stuStatusChangeService.saveStuStatusChange(stuStatusChange);
        return stuStatusChange;
    }

    @PostMapping("/findStuStatusChangeListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<StuStatusChange> findStuStatusChangeListByCondition(
            @ApiParam(value = "对象")
            @RequestBody StuStatusChange stuStatusChange){
        return stuStatusChangeService.findStuStatusChangeListByCondition(stuStatusChange);
    }
    @PostMapping("/findStuStatusChangeCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findStuStatusChangeCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody StuStatusChange stuStatusChange){
        return stuStatusChangeService.findStuStatusChangeCountByCondition(stuStatusChange);
    }

    @PostMapping("/updateStuStatusChange")
    @ApiOperation(value = "修改", notes = "对象必传")
    public void updateStuStatusChange(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody StuStatusChange stuStatusChange){
        stuStatusChangeService.updateStuStatusChange(stuStatusChange);
    }

    @GetMapping("/deleteStuStatusChange/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteStuStatusChange(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        stuStatusChangeService.deleteStuStatusChange(id);
    }
    @PostMapping("/deleteStuStatusChangeByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deleteStuStatusChangeByCondition(
            @ApiParam(value = "对象")
            @RequestBody StuStatusChange stuStatusChange){
        stuStatusChangeService.deleteStuStatusChangeByCondition(stuStatusChange);
    }
    @PostMapping("/findOneStuStatusChangeByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public StuStatusChange findOneStuStatusChangeByCondition(
            @ApiParam(value = "对象")
            @RequestBody StuStatusChange stuStatusChange){
        return stuStatusChangeService.findOneStuStatusChangeByCondition(stuStatusChange);
    }
}
