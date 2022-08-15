package com.yice.edu.cn.xw.controller.wage;

import com.yice.edu.cn.common.pojo.xw.wage.WageValue;
import com.yice.edu.cn.xw.service.wage.WageValueService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wageValue")
@Api(value = "/wageValue",description = "工资记录表模块")
public class WageValueController {
    @Autowired
    private WageValueService wageValueService;

    @GetMapping("/findWageValueById/{id}")
    @ApiOperation(value = "通过id查找工资记录表", notes = "返回工资记录表对象")
    public WageValue findWageValueById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return wageValueService.findWageValueById(id);
    }

    @PostMapping("/saveWageValue")
    @ApiOperation(value = "保存工资记录表", notes = "返回工资记录表对象")
    public WageValue saveWageValue(
            @ApiParam(value = "工资记录表对象", required = true)
            @RequestBody WageValue wageValue){
        wageValueService.saveWageValue(wageValue);
        return wageValue;
    }

    @PostMapping("/findWageValueListByCondition")
    @ApiOperation(value = "根据条件查找工资记录表列表", notes = "返回工资记录表列表")
    public List<WageValue> findWageValueListByCondition(
            @ApiParam(value = "工资记录表对象")
            @RequestBody WageValue wageValue){
        return wageValueService.findWageValueListByCondition(wageValue);
    }
    @PostMapping("/findWageValueCountByCondition")
    @ApiOperation(value = "根据条件查找工资记录表列表个数", notes = "返回工资记录表总个数")
    public long findWageValueCountByCondition(
            @ApiParam(value = "工资记录表对象")
            @RequestBody WageValue wageValue){
        return wageValueService.findWageValueCountByCondition(wageValue);
    }

    @PostMapping("/updateWageValue")
    @ApiOperation(value = "修改工资记录表", notes = "工资记录表对象必传")
    public void updateWageValue(
            @ApiParam(value = "工资记录表对象,对象属性不为空则修改", required = true)
            @RequestBody WageValue wageValue){
        wageValueService.updateWageValue(wageValue);
    }

    @GetMapping("/deleteWageValue/{id}")
    @ApiOperation(value = "通过id删除工资记录表")
    public void deleteWageValue(
            @ApiParam(value = "工资记录表对象", required = true)
            @PathVariable String id){
        wageValueService.deleteWageValue(id);
    }
    @PostMapping("/deleteWageValueByCondition")
    @ApiOperation(value = "根据条件删除工资记录表")
    public void deleteWageValueByCondition(
            @ApiParam(value = "工资记录表对象")
            @RequestBody WageValue wageValue){
        wageValueService.deleteWageValueByCondition(wageValue);
    }
    @PostMapping("/findOneWageValueByCondition")
    @ApiOperation(value = "根据条件查找单个工资记录表,结果必须为单条数据", notes = "返回单个工资记录表,没有时为空")
    public WageValue findOneWageValueByCondition(
            @ApiParam(value = "工资记录表对象")
            @RequestBody WageValue wageValue){
        return wageValueService.findOneWageValueByCondition(wageValue);
    }
}
