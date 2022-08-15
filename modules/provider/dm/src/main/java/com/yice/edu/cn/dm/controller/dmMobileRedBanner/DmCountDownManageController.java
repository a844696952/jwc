package com.yice.edu.cn.dm.controller.dmMobileRedBanner;

import com.yice.edu.cn.common.pojo.dm.dmMobileRedBanner.DmCountDownManage;
import com.yice.edu.cn.dm.service.dmMobileRedBanner.DmCountDownManageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dmCountDownManage")
@Api(value = "/dmCountDownManage",description = "模块")
public class DmCountDownManageController {
    @Autowired
    private DmCountDownManageService dmCountDownManageService;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findDmCountDownManageById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public DmCountDownManage findDmCountDownManageById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return dmCountDownManageService.findDmCountDownManageById(id);
    }

    @PostMapping("/saveDmCountDownManage")
    @ApiOperation(value = "保存", notes = "返回对象")
    public DmCountDownManage saveDmCountDownManage(
            @ApiParam(value = "对象", required = true)
            @RequestBody DmCountDownManage dmCountDownManage){
        dmCountDownManageService.saveDmCountDownManage(dmCountDownManage);
        return dmCountDownManage;
    }

    @PostMapping("/findDmCountDownManageListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<DmCountDownManage> findDmCountDownManageListByCondition(
            @ApiParam(value = "对象")
            @RequestBody DmCountDownManage dmCountDownManage){
        return dmCountDownManageService.findDmCountDownManageListByCondition(dmCountDownManage);
    }
    @PostMapping("/findDmCountDownManageCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findDmCountDownManageCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody DmCountDownManage dmCountDownManage){
        return dmCountDownManageService.findDmCountDownManageCountByCondition(dmCountDownManage);
    }

    @PostMapping("/updateDmCountDownManage")
    @ApiOperation(value = "修改有值的字段", notes = "对象必传")
    public void updateDmCountDownManage(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody DmCountDownManage dmCountDownManage){
        dmCountDownManageService.updateDmCountDownManage(dmCountDownManage);
    }

    @PostMapping("/updateDmCountDownManageStatus")
    @ApiOperation(value = "修改有值的字段", notes = "对象必传")
    public void updateDmCountDownManageStatus(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody DmCountDownManage dmCountDownManage){
        dmCountDownManageService.updateDmCountDownManageStatus(dmCountDownManage);
    }

    @PostMapping("/updateDmCountDownManageForAll")
    @ApiOperation(value = "修改所有字段", notes = "对象必传")
    public void updateDmCountDownManageForAll(
            @ApiParam(value = "对象", required = true)
            @RequestBody DmCountDownManage dmCountDownManage){
        dmCountDownManageService.updateDmCountDownManageForAll(dmCountDownManage);
    }

    @GetMapping("/deleteDmCountDownManage/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteDmCountDownManage(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        dmCountDownManageService.deleteDmCountDownManage(id);
    }
    @PostMapping("/deleteDmCountDownManageByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deleteDmCountDownManageByCondition(
            @ApiParam(value = "对象")
            @RequestBody DmCountDownManage dmCountDownManage){
        dmCountDownManageService.deleteDmCountDownManageByCondition(dmCountDownManage);
    }
    @PostMapping("/findOneDmCountDownManageByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public DmCountDownManage findOneDmCountDownManageByCondition(
            @ApiParam(value = "对象")
            @RequestBody DmCountDownManage dmCountDownManage){
        return dmCountDownManageService.findOneDmCountDownManageByCondition(dmCountDownManage);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
