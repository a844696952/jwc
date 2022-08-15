package com.yice.edu.cn.yed.controller.dm.dmClassCardArea;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.dm.dmClassCardArea.DmClassCardArea;
import com.yice.edu.cn.common.pojo.jw.building.Building;
import com.yice.edu.cn.yed.service.dm.dmClassCardArea.DmClassCardAreaService;
import com.yice.edu.cn.yed.service.jw.building.BuildingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@RestController
@RequestMapping("/dmClassCardArea")
@Api(value = "/dmClassCardArea",description = "电子班牌场地设备管理模块")
public class DmClassCardAreaController {
    @Autowired
    private DmClassCardAreaService dmClassCardAreaService;
    @Autowired
    private BuildingService buildingService;
    @PostMapping("/saveDmClassCardArea")
    @ApiOperation(value = "保存电子班牌场地设备管理对象", notes = "返回保存好的电子班牌场地设备管理对象", response=DmClassCardArea.class)
    public ResponseJson saveDmClassCardArea(
            @ApiParam(value = "电子班牌场地设备管理对象", required = true)
            @RequestBody DmClassCardArea dmClassCardArea){
        DmClassCardArea s=dmClassCardAreaService.saveDmClassCardArea(dmClassCardArea);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findDmClassCardAreaById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找电子班牌场地设备管理", notes = "返回响应对象", response=DmClassCardArea.class)
    public ResponseJson findDmClassCardAreaById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        DmClassCardArea dmClassCardArea=dmClassCardAreaService.findDmClassCardAreaById(id);
        return new ResponseJson(dmClassCardArea);
    }

    @PostMapping("/update/updateDmClassCardArea")
    @ApiOperation(value = "修改电子班牌场地设备管理对象", notes = "返回响应对象")
    public ResponseJson updateDmClassCardArea(
            @ApiParam(value = "被修改的电子班牌场地设备管理对象,对象属性不为空则修改", required = true)
            @RequestBody DmClassCardArea dmClassCardArea){
        dmClassCardAreaService.updateDmClassCardArea(dmClassCardArea);
        return new ResponseJson();
    }

    @GetMapping("/look/lookDmClassCardAreaById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找电子班牌场地设备管理", notes = "返回响应对象", response=DmClassCardArea.class)
    public ResponseJson lookDmClassCardAreaById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        DmClassCardArea dmClassCardArea=dmClassCardAreaService.findDmClassCardAreaById(id);
        return new ResponseJson(dmClassCardArea);
    }

    @PostMapping("/findDmClassCardAreasByCondition")
    @ApiOperation(value = "根据条件查找电子班牌场地设备管理", notes = "返回响应对象", response=DmClassCardArea.class)
    public ResponseJson findDmClassCardAreasByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody DmClassCardArea dmClassCardArea){
        List<DmClassCardArea> data=dmClassCardAreaService.findDmClassCardAreaListByCondition(dmClassCardArea);
        long count=dmClassCardAreaService.findDmClassCardAreaCountByCondition(dmClassCardArea);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneDmClassCardAreaByCondition")
    @ApiOperation(value = "根据条件查找单个电子班牌场地设备管理,结果必须为单条数据", notes = "没有时返回空", response=DmClassCardArea.class)
    public ResponseJson findOneDmClassCardAreaByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody DmClassCardArea dmClassCardArea){
        DmClassCardArea one=dmClassCardAreaService.findOneDmClassCardAreaByCondition(dmClassCardArea);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteDmClassCardArea/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteDmClassCardArea(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        dmClassCardAreaService.deleteDmClassCardArea(id);
        return new ResponseJson();
    }


    @PostMapping("/findDmClassCardAreaListByCondition")
    @ApiOperation(value = "根据条件查找电子班牌场地设备管理列表", notes = "返回响应对象,不包含总条数", response=DmClassCardArea.class)
    public ResponseJson findDmClassCardAreaListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody DmClassCardArea dmClassCardArea){
        List<DmClassCardArea> data=dmClassCardAreaService.findDmClassCardAreaListByCondition(dmClassCardArea);
        return new ResponseJson(data);
    }
    @GetMapping("/find/getBuildingSpaceTree/{id}")
    @ApiOperation(value = "获取楼栋场地树", notes = "返回楼栋信息列表")
    public ResponseJson getBuildingSpaceTree(@PathVariable("id")String id){
        List<Building> data= buildingService.getBuildingSpaceTree(id);
        return  new ResponseJson(data);
    }




}
