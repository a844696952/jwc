package com.yice.edu.cn.xw.controller.wage;

import com.yice.edu.cn.common.pojo.xw.wage.WageAttribute;
import com.yice.edu.cn.xw.service.wage.WageAttributeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wageAttribute")
@Api(value = "/wageAttribute",description = "模块")
public class WageAttributeController {
    @Autowired
    private WageAttributeService wageAttributeService;

    @GetMapping("/findWageAttributeById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public WageAttribute findWageAttributeById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return wageAttributeService.findWageAttributeById(id);
    }

    @PostMapping("/saveWageAttribute")
    @ApiOperation(value = "保存", notes = "返回对象")
    public WageAttribute saveWageAttribute(
            @ApiParam(value = "对象", required = true)
            @RequestBody WageAttribute wageAttribute){
        wageAttributeService.saveWageAttribute(wageAttribute);
        return wageAttribute;
    }

    @PostMapping("/findWageAttributeListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<WageAttribute> findWageAttributeListByCondition(
            @ApiParam(value = "对象")
            @RequestBody WageAttribute wageAttribute){
        return wageAttributeService.findWageAttributeListByCondition(wageAttribute);
    }
    @PostMapping("/findWageAttributeCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findWageAttributeCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody WageAttribute wageAttribute){
        return wageAttributeService.findWageAttributeCountByCondition(wageAttribute);
    }

    @PostMapping("/updateWageAttribute")
    @ApiOperation(value = "修改", notes = "对象必传")
    public void updateWageAttribute(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody WageAttribute wageAttribute){
        wageAttributeService.updateWageAttribute(wageAttribute);
    }

    @GetMapping("/deleteWageAttribute/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteWageAttribute(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        wageAttributeService.deleteWageAttribute(id);
    }
    @PostMapping("/deleteWageAttributeByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deleteWageAttributeByCondition(
            @ApiParam(value = "对象")
            @RequestBody WageAttribute wageAttribute){
        wageAttributeService.deleteWageAttributeByCondition(wageAttribute);
    }
    @PostMapping("/findOneWageAttributeByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public WageAttribute findOneWageAttributeByCondition(
            @ApiParam(value = "对象")
            @RequestBody WageAttribute wageAttribute){
        return wageAttributeService.findOneWageAttributeByCondition(wageAttribute);
    }
}
