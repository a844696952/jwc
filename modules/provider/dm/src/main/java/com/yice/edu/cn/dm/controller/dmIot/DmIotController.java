package com.yice.edu.cn.dm.controller.dmIot;

import com.yice.edu.cn.common.pojo.dm.dmIot.DmIot;
import com.yice.edu.cn.dm.service.dmIot.DmIotService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dmIot")
@Api(value = "/dmIot",description = "物联网表模块")
public class DmIotController {
    @Autowired
    private DmIotService dmIotService;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findDmIotById/{id}")
    @ApiOperation(value = "通过id查找物联网表", notes = "返回物联网表对象")
    public DmIot findDmIotById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return dmIotService.findDmIotById(id);
    }

    @PostMapping("/saveDmIot")
    @ApiOperation(value = "保存物联网表", notes = "返回物联网表对象")
    public DmIot saveDmIot(
            @ApiParam(value = "物联网表对象", required = true)
            @RequestBody DmIot dmIot){
        dmIotService.saveDmIot(dmIot);
        return dmIot;
    }

    @PostMapping("/batchSaveDmIot")
    @ApiOperation(value = "批量保存物联网表")
    public void batchSaveDmIot(
            @ApiParam(value = "物联网表对象集合", required = true)
            @RequestBody List<DmIot> dmIots){
        dmIotService.batchSaveDmIot(dmIots);
    }

    @PostMapping("/findDmIotListByCondition")
    @ApiOperation(value = "根据条件查找物联网表列表", notes = "返回物联网表列表")
    public List<DmIot> findDmIotListByCondition(
            @ApiParam(value = "物联网表对象")
            @RequestBody DmIot dmIot){
        return dmIotService.findDmIotListByCondition(dmIot);
    }
    @PostMapping("/findDmIotCountByCondition")
    @ApiOperation(value = "根据条件查找物联网表列表个数", notes = "返回物联网表总个数")
    public long findDmIotCountByCondition(
            @ApiParam(value = "物联网表对象")
            @RequestBody DmIot dmIot){
        return dmIotService.findDmIotCountByCondition(dmIot);
    }

    @PostMapping("/updateDmIot")
    @ApiOperation(value = "修改物联网表有值的字段", notes = "物联网表对象必传")
    public void updateDmIot(
            @ApiParam(value = "物联网表对象,对象属性不为空则修改", required = true)
            @RequestBody DmIot dmIot){
        dmIotService.updateDmIot(dmIot);
    }
    @PostMapping("/updateDmIotForAll")
    @ApiOperation(value = "修改物联网表所有字段", notes = "物联网表对象必传")
    public void updateDmIotForAll(
            @ApiParam(value = "物联网表对象", required = true)
            @RequestBody DmIot dmIot){
        dmIotService.updateDmIotForAll(dmIot);
    }

    @GetMapping("/deleteDmIot/{id}")
    @ApiOperation(value = "通过id删除物联网表")
    public void deleteDmIot(
            @ApiParam(value = "物联网表对象", required = true)
            @PathVariable String id){
        dmIotService.deleteDmIot(id);
    }
    @PostMapping("/deleteDmIotByCondition")
    @ApiOperation(value = "根据条件删除物联网表")
    public void deleteDmIotByCondition(
            @ApiParam(value = "物联网表对象")
            @RequestBody DmIot dmIot){
        dmIotService.deleteDmIotByCondition(dmIot);
    }
    @PostMapping("/findOneDmIotByCondition")
    @ApiOperation(value = "根据条件查找单个物联网表,结果必须为单条数据", notes = "返回单个物联网表,没有时为空")
    public DmIot findOneDmIotByCondition(
            @ApiParam(value = "物联网表对象")
            @RequestBody DmIot dmIot){
        return dmIotService.findOneDmIotByCondition(dmIot);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
