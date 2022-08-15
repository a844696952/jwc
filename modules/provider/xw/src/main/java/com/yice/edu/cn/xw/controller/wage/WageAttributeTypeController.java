package com.yice.edu.cn.xw.controller.wage;

import com.yice.edu.cn.common.pojo.xw.wage.WageAttributeType;
import com.yice.edu.cn.xw.service.wage.WageAttributeTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wageAttributeType")
@Api(value = "/wageAttributeType",description = "模块")
public class WageAttributeTypeController {
    @Autowired
    private WageAttributeTypeService wageAttributeTypeService;

    @GetMapping("/findWageAttributeTypeById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public WageAttributeType findWageAttributeTypeById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return wageAttributeTypeService.findWageAttributeTypeById(id);
    }

    @PostMapping("/saveWageAttributeType")
    @ApiOperation(value = "保存", notes = "返回对象")
    public WageAttributeType saveWageAttributeType(
            @ApiParam(value = "对象", required = true)
            @RequestBody WageAttributeType wageAttributeType){
        wageAttributeTypeService.saveWageAttributeType(wageAttributeType);
        return wageAttributeType;
    }

    @PostMapping("/findWageAttributeTypeListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<WageAttributeType> findWageAttributeTypeListByCondition(
            @ApiParam(value = "对象")
            @RequestBody WageAttributeType wageAttributeType){
        return wageAttributeTypeService.findWageAttributeTypeListByCondition(wageAttributeType);
    }
    @PostMapping("/findWageAttributeTypeCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findWageAttributeTypeCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody WageAttributeType wageAttributeType){
        return wageAttributeTypeService.findWageAttributeTypeCountByCondition(wageAttributeType);
    }

    @PostMapping("/updateWageAttributeType")
    @ApiOperation(value = "修改", notes = "对象必传")
    public void updateWageAttributeType(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody WageAttributeType wageAttributeType){
        wageAttributeTypeService.updateWageAttributeType(wageAttributeType);
    }

    @GetMapping("/deleteWageAttributeType/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteWageAttributeType(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        wageAttributeTypeService.deleteWageAttributeType(id);
    }
    @PostMapping("/deleteWageAttributeTypeByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deleteWageAttributeTypeByCondition(
            @ApiParam(value = "对象")
            @RequestBody WageAttributeType wageAttributeType){
        wageAttributeTypeService.deleteWageAttributeTypeByCondition(wageAttributeType);
    }
    @PostMapping("/findOneWageAttributeTypeByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public WageAttributeType findOneWageAttributeTypeByCondition(
            @ApiParam(value = "对象")
            @RequestBody WageAttributeType wageAttributeType){
        return wageAttributeTypeService.findOneWageAttributeTypeByCondition(wageAttributeType);
    }

    @GetMapping("/findWageAttributeTypeByTypeId/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public List<WageAttributeType> findWageAttributeTypeByTypeId(@PathVariable("id") String id){
        return wageAttributeTypeService.findWageAttributeTypeByTypeId(id);
    }
}
