package com.yice.edu.cn.dm.controller.dmHonourRollType;

import com.yice.edu.cn.common.pojo.dm.dmHonourRollType.DmHonourRollType;
import com.yice.edu.cn.dm.service.dmHonourRollType.DmHonourRollTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dmHonourRollType")
@Api(value = "/dmHonourRollType",description = "光荣榜类型模块")
public class DmHonourRollTypeController {
    @Autowired
    private DmHonourRollTypeService dmHonourRollTypeService;

    @GetMapping("/findDmHonourRollTypeById/{id}")
    @ApiOperation(value = "通过id查找光荣榜类型", notes = "返回光荣榜类型对象")
    public DmHonourRollType findDmHonourRollTypeById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return dmHonourRollTypeService.findDmHonourRollTypeById(id);
    }

    @PostMapping("/saveDmHonourRollType")
    @ApiOperation(value = "保存光荣榜类型", notes = "返回光荣榜类型对象")
    public DmHonourRollType saveDmHonourRollType(
            @ApiParam(value = "光荣榜类型对象", required = true)
            @RequestBody DmHonourRollType dmHonourRollType){
        dmHonourRollTypeService.saveDmHonourRollType(dmHonourRollType);
        return dmHonourRollType;
    }

    @PostMapping("/findDmHonourRollTypeListByCondition")
    @ApiOperation(value = "根据条件查找光荣榜类型列表", notes = "返回光荣榜类型列表")
    public List<DmHonourRollType> findDmHonourRollTypeListByCondition(
            @ApiParam(value = "光荣榜类型对象")
            @RequestBody DmHonourRollType dmHonourRollType){
        return dmHonourRollTypeService.findDmHonourRollTypeListByCondition(dmHonourRollType);
    }
    @PostMapping("/findDmHonourRollTypeCountByCondition")
    @ApiOperation(value = "根据条件查找光荣榜类型列表个数", notes = "返回光荣榜类型总个数")
    public long findDmHonourRollTypeCountByCondition(
            @ApiParam(value = "光荣榜类型对象")
            @RequestBody DmHonourRollType dmHonourRollType){
        return dmHonourRollTypeService.findDmHonourRollTypeCountByCondition(dmHonourRollType);
    }

    @PostMapping("/updateDmHonourRollType")
    @ApiOperation(value = "修改光荣榜类型", notes = "光荣榜类型对象必传")
    public void updateDmHonourRollType(
            @ApiParam(value = "光荣榜类型对象,对象属性不为空则修改", required = true)
            @RequestBody DmHonourRollType dmHonourRollType){
        dmHonourRollTypeService.updateDmHonourRollType(dmHonourRollType);
    }

    @GetMapping("/deleteDmHonourRollType/{id}")
    @ApiOperation(value = "通过id删除光荣榜类型")
    public void deleteDmHonourRollType(
            @ApiParam(value = "光荣榜类型对象", required = true)
            @PathVariable String id){
        dmHonourRollTypeService.deleteDmHonourRollType(id);
    }
    @PostMapping("/deleteDmHonourRollTypeByCondition")
    @ApiOperation(value = "根据条件删除光荣榜类型")
    public void deleteDmHonourRollTypeByCondition(
            @ApiParam(value = "光荣榜类型对象")
            @RequestBody DmHonourRollType dmHonourRollType){
        dmHonourRollTypeService.deleteDmHonourRollTypeByCondition(dmHonourRollType);
    }
    @PostMapping("/findOneDmHonourRollTypeByCondition")
    @ApiOperation(value = "根据条件查找单个光荣榜类型,结果必须为单条数据", notes = "返回单个光荣榜类型,没有时为空")
    public DmHonourRollType findOneDmHonourRollTypeByCondition(
            @ApiParam(value = "光荣榜类型对象")
            @RequestBody DmHonourRollType dmHonourRollType){
        return dmHonourRollTypeService.findOneDmHonourRollTypeByCondition(dmHonourRollType);
    }
}
