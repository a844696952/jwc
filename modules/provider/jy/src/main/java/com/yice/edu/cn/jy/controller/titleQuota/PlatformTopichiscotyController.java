package com.yice.edu.cn.jy.controller.titleQuota;

import com.yice.edu.cn.common.pojo.jy.titleQuota.PlatformTopichiscoty;
import com.yice.edu.cn.jy.service.titleQuota.PlatformTopichiscotyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/platformTopichiscoty")
@Api(value = "/platformTopichiscoty",description = "模块")
public class PlatformTopichiscotyController {
    @Autowired
    private PlatformTopichiscotyService platformTopichiscotyService;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findPlatformTopichiscotyById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public PlatformTopichiscoty findPlatformTopichiscotyById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return platformTopichiscotyService.findPlatformTopichiscotyById(id);
    }

    @PostMapping("/savePlatformTopichiscoty")
    @ApiOperation(value = "保存", notes = "返回对象")
    public PlatformTopichiscoty savePlatformTopichiscoty(
            @ApiParam(value = "对象", required = true)
            @RequestBody PlatformTopichiscoty platformTopichiscoty){
        platformTopichiscotyService.savePlatformTopichiscoty(platformTopichiscoty);
        return platformTopichiscoty;
    }

    @PostMapping("/batchSavePlatformTopichiscoty")
    @ApiOperation(value = "批量保存")
    public void batchSavePlatformTopichiscoty(
            @ApiParam(value = "对象集合", required = true)
            @RequestBody List<PlatformTopichiscoty> platformTopichiscotys){
        platformTopichiscotyService.batchSavePlatformTopichiscoty(platformTopichiscotys);
    }

    @PostMapping("/findPlatformTopichiscotyListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<PlatformTopichiscoty> findPlatformTopichiscotyListByCondition(
            @ApiParam(value = "对象")
            @RequestBody PlatformTopichiscoty platformTopichiscoty){
        return platformTopichiscotyService.findPlatformTopichiscotyListByCondition(platformTopichiscoty);
    }
    @PostMapping("/findPlatformTopichiscotyCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findPlatformTopichiscotyCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody PlatformTopichiscoty platformTopichiscoty){
        return platformTopichiscotyService.findPlatformTopichiscotyCountByCondition(platformTopichiscoty);
    }

    @PostMapping("/updatePlatformTopichiscoty")
    @ApiOperation(value = "修改有值的字段", notes = "对象必传")
    public void updatePlatformTopichiscoty(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody PlatformTopichiscoty platformTopichiscoty){
        platformTopichiscotyService.updatePlatformTopichiscoty(platformTopichiscoty);
    }
    @PostMapping("/updatePlatformTopichiscotyForAll")
    @ApiOperation(value = "修改所有字段", notes = "对象必传")
    public void updatePlatformTopichiscotyForAll(
            @ApiParam(value = "对象", required = true)
            @RequestBody PlatformTopichiscoty platformTopichiscoty){
        platformTopichiscotyService.updatePlatformTopichiscotyForAll(platformTopichiscoty);
    }

    @GetMapping("/deletePlatformTopichiscoty/{id}")
    @ApiOperation(value = "通过id删除")
    public void deletePlatformTopichiscoty(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        platformTopichiscotyService.deletePlatformTopichiscoty(id);
    }
    @PostMapping("/deletePlatformTopichiscotyByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deletePlatformTopichiscotyByCondition(
            @ApiParam(value = "对象")
            @RequestBody PlatformTopichiscoty platformTopichiscoty){
        platformTopichiscotyService.deletePlatformTopichiscotyByCondition(platformTopichiscoty);
    }
    @PostMapping("/findOnePlatformTopichiscotyByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public PlatformTopichiscoty findOnePlatformTopichiscotyByCondition(
            @ApiParam(value = "对象")
            @RequestBody PlatformTopichiscoty platformTopichiscoty){
        return platformTopichiscotyService.findOnePlatformTopichiscotyByCondition(platformTopichiscoty);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
