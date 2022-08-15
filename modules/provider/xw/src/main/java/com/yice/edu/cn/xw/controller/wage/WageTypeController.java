package com.yice.edu.cn.xw.controller.wage;

import com.yice.edu.cn.common.pojo.xw.wage.WageType;
import com.yice.edu.cn.xw.service.wage.WageTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wageType")
@Api(value = "/wageType",description = "模块")
public class WageTypeController {
    @Autowired
    private WageTypeService wageTypeService;

    @GetMapping("/findWageTypeById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public WageType findWageTypeById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return wageTypeService.findWageTypeById(id);
    }

    @PostMapping("/saveWageType")
    @ApiOperation(value = "保存", notes = "返回对象")
    public WageType saveWageType(
            @ApiParam(value = "对象", required = true)
            @RequestBody WageType wageType){
        wageTypeService.saveWageType(wageType);
        return wageType;
    }

    @PostMapping("/findWageTypeListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<WageType> findWageTypeListByCondition(
            @ApiParam(value = "对象")
            @RequestBody WageType wageType){
        return wageTypeService.findWageTypeListByCondition(wageType);
    }
    @PostMapping("/findWageTypeListByConditionNotState")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<WageType> findWageTypeListByConditionNotState(
            @ApiParam(value = "对象")
            @RequestBody WageType wageType){
        return wageTypeService.findWageTypeListByConditionNotState(wageType);
    }
    @PostMapping("/findWageTypeCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findWageTypeCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody WageType wageType){
        return wageTypeService.findWageTypeCountByCondition(wageType);
    }

    @PostMapping("/updateWageType")
    @ApiOperation(value = "修改", notes = "对象必传")
    public void updateWageType(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody WageType wageType){
        wageTypeService.updateWageType(wageType);
    }

    @GetMapping("/deleteWageType/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteWageType(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        wageTypeService.deleteWageType(id);
    }
    @PostMapping("/deleteWageTypeByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deleteWageTypeByCondition(
            @ApiParam(value = "对象")
            @RequestBody WageType wageType){
        wageTypeService.deleteWageTypeByCondition(wageType);
    }
    @PostMapping("/findOneWageTypeByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public WageType findOneWageTypeByCondition(
            @ApiParam(value = "对象")
            @RequestBody WageType wageType){
        return wageTypeService.findOneWageTypeByCondition(wageType);
    }

    @PostMapping("/findWageTypeListByCondition1")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<WageType> findWageTypeListByCondition1(
            @ApiParam(value = "对象")
            @RequestBody WageType wageType){
        return wageTypeService.findWageTypeListByCondition1(wageType);
    }

    @GetMapping("/updateWageTypeState/{id}")
    @ApiOperation(value = "通过id修改")
    public void updateWageTypeState(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        wageTypeService.updateWageTypeState(id);
    }

    @PostMapping("/findWageTypeListByState")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<WageType> findWageTypeListByState(
            @ApiParam(value = "对象")
            @RequestBody WageType wageType){
        return wageTypeService.findWageTypeListByState(wageType);
    }

    @PostMapping("/findWageAttributeListByTypeId")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<WageType> findWageAttributeListByTypeId(
            @ApiParam(value = "对象")
            @RequestBody WageType wageType){
        return wageTypeService.findWageAttributeListByTypeId(wageType);
    }
}
