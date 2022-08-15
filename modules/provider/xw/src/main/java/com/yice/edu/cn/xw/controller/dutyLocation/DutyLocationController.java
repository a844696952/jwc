package com.yice.edu.cn.xw.controller.dutyLocation;

import com.yice.edu.cn.common.pojo.xw.dutyLocation.DutyLocation;
import com.yice.edu.cn.xw.service.dutyLocation.DutyLocationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dutyLocation")
@Api(value = "/dutyLocation",description = "值班地点管理模块")
public class DutyLocationController {
    @Autowired
    private DutyLocationService dutyLocationService;

    @GetMapping("/findDutyLocationById/{id}")
    @ApiOperation(value = "通过id查找值班地点管理", notes = "返回值班地点管理对象")
    public DutyLocation findDutyLocationById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return dutyLocationService.findDutyLocationById(id);
    }

    @PostMapping("/saveDutyLocation")
    @ApiOperation(value = "保存值班地点管理", notes = "返回值班地点管理对象")
    public DutyLocation saveDutyLocation(
            @ApiParam(value = "值班地点管理对象", required = true)
            @RequestBody DutyLocation dutyLocation){
        dutyLocationService.saveDutyLocation(dutyLocation);
        return dutyLocation;
    }

    @PostMapping("/findDutyLocationListByCondition")
    @ApiOperation(value = "根据条件查找值班地点管理列表", notes = "返回值班地点管理列表")
    public List<DutyLocation> findDutyLocationListByCondition(
            @ApiParam(value = "值班地点管理对象")
            @RequestBody DutyLocation dutyLocation){
        return dutyLocationService.findDutyLocationListByCondition(dutyLocation);
    }
    @PostMapping("/findDutyLocationCountByCondition")
    @ApiOperation(value = "根据条件查找值班地点管理列表个数", notes = "返回值班地点管理总个数")
    public long findDutyLocationCountByCondition(
            @ApiParam(value = "值班地点管理对象")
            @RequestBody DutyLocation dutyLocation){
        return dutyLocationService.findDutyLocationCountByCondition(dutyLocation);
    }

    @PostMapping("/updateDutyLocation")
    @ApiOperation(value = "修改值班地点管理", notes = "值班地点管理对象必传")
    public void updateDutyLocation(
            @ApiParam(value = "值班地点管理对象,对象属性不为空则修改", required = true)
            @RequestBody DutyLocation dutyLocation){
        dutyLocationService.updateDutyLocation(dutyLocation);
    }

    @GetMapping("/deleteDutyLocation/{id}")
    @ApiOperation(value = "通过id删除值班地点管理")
    public void deleteDutyLocation(
            @ApiParam(value = "值班地点管理对象", required = true)
            @PathVariable String id){
        dutyLocationService.deleteDutyLocation(id);
    }
    @PostMapping("/deleteDutyLocationByCondition")
    @ApiOperation(value = "根据条件删除值班地点管理")
    public DutyLocation deleteDutyLocationByCondition(
            @ApiParam(value = "值班地点管理对象")
            @RequestBody DutyLocation dutyLocation){
        dutyLocationService.deleteDutyLocationByCondition(dutyLocation);
        return dutyLocation;
    }
    @PostMapping("/findOneDutyLocationByCondition")
    @ApiOperation(value = "根据条件查找单个值班地点管理,结果必须为单条数据", notes = "返回单个值班地点管理,没有时为空")
    public DutyLocation findOneDutyLocationByCondition(
            @ApiParam(value = "值班地点管理对象")
            @RequestBody DutyLocation dutyLocation){
        return dutyLocationService.findOneDutyLocationByCondition(dutyLocation);
    }
}
