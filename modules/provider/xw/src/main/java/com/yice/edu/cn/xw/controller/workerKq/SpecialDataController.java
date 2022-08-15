package com.yice.edu.cn.xw.controller.workerKq;

import com.yice.edu.cn.common.pojo.xw.workerKq.SpecialData;
import com.yice.edu.cn.xw.service.workerKq.SpecialDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/specialData")
@Api(value = "/specialData",description = "考勤管理基础规则表模块")
public class SpecialDataController {
    @Autowired
    private SpecialDataService specialDataService;

    @GetMapping("/findSpecialDataById/{id}")
    @ApiOperation(value = "通过id查找考勤管理基础规则表", notes = "返回考勤管理基础规则表对象")
    public SpecialData findSpecialDataById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return specialDataService.findSpecialDataById(id);
    }

    @PostMapping("/saveSpecialData")
    @ApiOperation(value = "保存考勤管理基础规则表", notes = "返回考勤管理基础规则表对象")
    public SpecialData saveSpecialData(
            @ApiParam(value = "考勤管理基础规则表对象", required = true)
            @RequestBody SpecialData specialData){
        specialDataService.saveSpecialData(specialData);
        return specialData;
    }

    @PostMapping("/findSpecialDataListByCondition")
    @ApiOperation(value = "根据条件查找考勤管理基础规则表列表", notes = "返回考勤管理基础规则表列表")
    public List<SpecialData> findSpecialDataListByCondition(
            @ApiParam(value = "考勤管理基础规则表对象")
            @RequestBody SpecialData specialData){
        return specialDataService.findSpecialDataListByCondition(specialData);
    }
    @PostMapping("/findSpecialDataCountByCondition")
    @ApiOperation(value = "根据条件查找考勤管理基础规则表列表个数", notes = "返回考勤管理基础规则表总个数")
    public long findSpecialDataCountByCondition(
            @ApiParam(value = "考勤管理基础规则表对象")
            @RequestBody SpecialData specialData){
        return specialDataService.findSpecialDataCountByCondition(specialData);
    }

    @PostMapping("/updateSpecialData")
    @ApiOperation(value = "修改考勤管理基础规则表", notes = "考勤管理基础规则表对象必传")
    public void updateSpecialData(
            @ApiParam(value = "考勤管理基础规则表对象,对象属性不为空则修改", required = true)
            @RequestBody SpecialData specialData){
        specialDataService.updateSpecialData(specialData);
    }

    @GetMapping("/deleteSpecialData/{id}")
    @ApiOperation(value = "通过id删除考勤管理基础规则表")
    public void deleteSpecialData(
            @ApiParam(value = "考勤管理基础规则表对象", required = true)
            @PathVariable String id){
        specialDataService.deleteSpecialData(id);
    }
    @PostMapping("/deleteSpecialDataByCondition")
    @ApiOperation(value = "根据条件删除考勤管理基础规则表")
    public void deleteSpecialDataByCondition(
            @ApiParam(value = "考勤管理基础规则表对象")
            @RequestBody SpecialData specialData){
        specialDataService.deleteSpecialDataByCondition(specialData);
    }
    @PostMapping("/findOneSpecialDataByCondition")
    @ApiOperation(value = "根据条件查找单个考勤管理基础规则表,结果必须为单条数据", notes = "返回单个考勤管理基础规则表,没有时为空")
    public SpecialData findOneSpecialDataByCondition(
            @ApiParam(value = "考勤管理基础规则表对象")
            @RequestBody SpecialData specialData){
        return specialDataService.findOneSpecialDataByCondition(specialData);
    }
}
