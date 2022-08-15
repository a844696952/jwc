package com.yice.edu.cn.xw.controller.clCustomMaterial;

import com.yice.edu.cn.common.pojo.xw.clCustomMaterial.ClWeight;
import com.yice.edu.cn.xw.service.clCustomMaterial.ClWeightService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clWeight")
@Api(value = "/clWeight",description = "自定义材料权重表模块")
public class ClWeightController {
    @Autowired
    private ClWeightService clWeightService;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findClWeightById/{id}")
    @ApiOperation(value = "通过id查找自定义材料权重表", notes = "返回自定义材料权重表对象")
    public ClWeight findClWeightById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return clWeightService.findClWeightById(id);
    }

    @PostMapping("/saveClWeight")
    @ApiOperation(value = "保存自定义材料权重表", notes = "返回自定义材料权重表对象")
    public ClWeight saveClWeight(
            @ApiParam(value = "自定义材料权重表对象", required = true)
            @RequestBody ClWeight clWeight){
        clWeightService.saveClWeight(clWeight);
        return clWeight;
    }

    @PostMapping("/batchSaveClWeight")
    @ApiOperation(value = "批量保存自定义材料权重表")
    public void batchSaveClWeight(
            @ApiParam(value = "自定义材料权重表对象集合", required = true)
            @RequestBody List<ClWeight> clWeights){
        clWeightService.batchSaveClWeight(clWeights);
    }

    @PostMapping("/findClWeightListByCondition")
    @ApiOperation(value = "根据条件查找自定义材料权重表列表", notes = "返回自定义材料权重表列表")
    public List<ClWeight> findClWeightListByCondition(
            @ApiParam(value = "自定义材料权重表对象")
            @RequestBody ClWeight clWeight){
        return clWeightService.findClWeightListByCondition(clWeight);
    }
    @PostMapping("/findClWeightCountByCondition")
    @ApiOperation(value = "根据条件查找自定义材料权重表列表个数", notes = "返回自定义材料权重表总个数")
    public long findClWeightCountByCondition(
            @ApiParam(value = "自定义材料权重表对象")
            @RequestBody ClWeight clWeight){
        return clWeightService.findClWeightCountByCondition(clWeight);
    }

    @PostMapping("/updateClWeight")
    @ApiOperation(value = "修改自定义材料权重表有值的字段", notes = "自定义材料权重表对象必传")
    public void updateClWeight(
            @ApiParam(value = "自定义材料权重表对象,对象属性不为空则修改", required = true)
            @RequestBody ClWeight clWeight){
        clWeightService.updateClWeight(clWeight);
    }
    @PostMapping("/updateClWeightForAll")
    @ApiOperation(value = "修改自定义材料权重表所有字段", notes = "自定义材料权重表对象必传")
    public void updateClWeightForAll(
            @ApiParam(value = "自定义材料权重表对象", required = true)
            @RequestBody ClWeight clWeight){
        clWeightService.updateClWeightForAll(clWeight);
    }

    @GetMapping("/deleteClWeight/{id}")
    @ApiOperation(value = "通过id删除自定义材料权重表")
    public void deleteClWeight(
            @ApiParam(value = "自定义材料权重表对象", required = true)
            @PathVariable String id){
        clWeightService.deleteClWeight(id);
    }
    @PostMapping("/deleteClWeightByCondition")
    @ApiOperation(value = "根据条件删除自定义材料权重表")
    public void deleteClWeightByCondition(
            @ApiParam(value = "自定义材料权重表对象")
            @RequestBody ClWeight clWeight){
        clWeightService.deleteClWeightByCondition(clWeight);
    }
    @PostMapping("/findOneClWeightByCondition")
    @ApiOperation(value = "根据条件查找单个自定义材料权重表,结果必须为单条数据", notes = "返回单个自定义材料权重表,没有时为空")
    public ClWeight findOneClWeightByCondition(
            @ApiParam(value = "自定义材料权重表对象")
            @RequestBody ClWeight clWeight){
        return clWeightService.findOneClWeightByCondition(clWeight);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    @PostMapping("/addMaxWeight")
    public Long addMaxWeight(@RequestBody ClWeight clWeight){
        return clWeightService.addMaxWeight(clWeight);
    }
}
