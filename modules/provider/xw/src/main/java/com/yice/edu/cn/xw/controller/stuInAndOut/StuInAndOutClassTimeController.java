package com.yice.edu.cn.xw.controller.stuInAndOut;

import com.yice.edu.cn.common.pojo.xw.stuInAndOut.StuInAndOutClassTime;
import com.yice.edu.cn.xw.service.stuInAndOut.StuInAndOutClassTimeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stuInAndOutClassTime")
@Api(value = "/stuInAndOutClassTime",description = "模块")
public class StuInAndOutClassTimeController {
    @Autowired
    private StuInAndOutClassTimeService stuInAndOutClassTimeService;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findStuInAndOutClassTimeById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public StuInAndOutClassTime findStuInAndOutClassTimeById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return stuInAndOutClassTimeService.findStuInAndOutClassTimeById(id);
    }

    @PostMapping("/saveStuInAndOutClassTime")
    @ApiOperation(value = "保存", notes = "返回对象")
    public StuInAndOutClassTime saveStuInAndOutClassTime(
            @ApiParam(value = "对象", required = true)
            @RequestBody StuInAndOutClassTime stuInAndOutClassTime){
        stuInAndOutClassTimeService.saveStuInAndOutClassTime(stuInAndOutClassTime);
        return stuInAndOutClassTime;
    }

    @PostMapping("/batchSaveStuInAndOutClassTime")
    @ApiOperation(value = "批量保存")
    public void batchSaveStuInAndOutClassTime(
            @ApiParam(value = "对象集合", required = true)
            @RequestBody List<StuInAndOutClassTime> stuInAndOutClassTimes){
        stuInAndOutClassTimeService.batchSaveStuInAndOutClassTime(stuInAndOutClassTimes);
    }

    @PostMapping("/findStuInAndOutClassTimeListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<StuInAndOutClassTime> findStuInAndOutClassTimeListByCondition(
            @ApiParam(value = "对象")
            @RequestBody StuInAndOutClassTime stuInAndOutClassTime){
        return stuInAndOutClassTimeService.findStuInAndOutClassTimeListByCondition(stuInAndOutClassTime);
    }
    @PostMapping("/findStuInAndOutClassTimeCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findStuInAndOutClassTimeCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody StuInAndOutClassTime stuInAndOutClassTime){
        return stuInAndOutClassTimeService.findStuInAndOutClassTimeCountByCondition(stuInAndOutClassTime);
    }

    @PostMapping("/updateStuInAndOutClassTime")
    @ApiOperation(value = "修改有值的字段", notes = "对象必传")
    public void updateStuInAndOutClassTime(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody StuInAndOutClassTime stuInAndOutClassTime){
        stuInAndOutClassTimeService.updateStuInAndOutClassTime(stuInAndOutClassTime);
    }
    @PostMapping("/updateStuInAndOutClassTimeForAll")
    @ApiOperation(value = "修改所有字段", notes = "对象必传")
    public void updateStuInAndOutClassTimeForAll(
            @ApiParam(value = "对象", required = true)
            @RequestBody StuInAndOutClassTime stuInAndOutClassTime){
        stuInAndOutClassTimeService.updateStuInAndOutClassTimeForAll(stuInAndOutClassTime);
    }

    @GetMapping("/deleteStuInAndOutClassTime/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteStuInAndOutClassTime(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        stuInAndOutClassTimeService.deleteStuInAndOutClassTime(id);
    }
    @PostMapping("/deleteStuInAndOutClassTimeByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deleteStuInAndOutClassTimeByCondition(
            @ApiParam(value = "对象")
            @RequestBody StuInAndOutClassTime stuInAndOutClassTime){
        stuInAndOutClassTimeService.deleteStuInAndOutClassTimeByCondition(stuInAndOutClassTime);
    }
    @PostMapping("/findOneStuInAndOutClassTimeByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public StuInAndOutClassTime findOneStuInAndOutClassTimeByCondition(
            @ApiParam(value = "对象")
            @RequestBody StuInAndOutClassTime stuInAndOutClassTime){
        return stuInAndOutClassTimeService.findOneStuInAndOutClassTimeByCondition(stuInAndOutClassTime);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

}
