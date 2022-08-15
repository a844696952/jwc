package com.yice.edu.cn.xw.controller.clCustomMaterial;

import com.yice.edu.cn.common.pojo.xw.clCustomMaterial.ClCustomMaterialData;
import com.yice.edu.cn.xw.service.clCustomMaterial.ClCustomMaterialDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clCustomMaterialData")
@Api(value = "/clCustomMaterialData",description = "模块")
public class ClCustomMaterialDataController {
    @Autowired
    private ClCustomMaterialDataService clCustomMaterialDataService;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findClCustomMaterialDataById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public ClCustomMaterialData findClCustomMaterialDataById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return clCustomMaterialDataService.findClCustomMaterialDataById(id);
    }

    @PostMapping("/saveClCustomMaterialData")
    @ApiOperation(value = "保存", notes = "返回对象")
    public ClCustomMaterialData saveClCustomMaterialData(
            @ApiParam(value = "对象", required = true)
            @RequestBody ClCustomMaterialData clCustomMaterialData){
        clCustomMaterialDataService.saveClCustomMaterialData(clCustomMaterialData);
        return clCustomMaterialData;
    }

    @PostMapping("/batchSaveClCustomMaterialData")
    @ApiOperation(value = "批量保存")
    public void batchSaveClCustomMaterialData(
            @ApiParam(value = "对象集合", required = true)
            @RequestBody List<ClCustomMaterialData> clCustomMaterialDatas){
        clCustomMaterialDataService.batchSaveClCustomMaterialData(clCustomMaterialDatas);
    }

    @PostMapping("/findClCustomMaterialDataListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<ClCustomMaterialData> findClCustomMaterialDataListByCondition(
            @ApiParam(value = "对象")
            @RequestBody ClCustomMaterialData clCustomMaterialData){
        return clCustomMaterialDataService.findClCustomMaterialDataListByCondition(clCustomMaterialData);
    }
    @PostMapping("/findClCustomMaterialDataCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findClCustomMaterialDataCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody ClCustomMaterialData clCustomMaterialData){
        return clCustomMaterialDataService.findClCustomMaterialDataCountByCondition(clCustomMaterialData);
    }

    @PostMapping("/updateClCustomMaterialData")
    @ApiOperation(value = "修改有值的字段", notes = "对象必传")
    public void updateClCustomMaterialData(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody ClCustomMaterialData clCustomMaterialData){
        clCustomMaterialDataService.updateClCustomMaterialData(clCustomMaterialData);
    }
    @PostMapping("/updateClCustomMaterialDataForAll")
    @ApiOperation(value = "修改所有字段", notes = "对象必传")
    public void updateClCustomMaterialDataForAll(
            @ApiParam(value = "对象", required = true)
            @RequestBody ClCustomMaterialData clCustomMaterialData){
        clCustomMaterialDataService.updateClCustomMaterialDataForAll(clCustomMaterialData);
    }

    @GetMapping("/deleteClCustomMaterialData/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteClCustomMaterialData(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        clCustomMaterialDataService.deleteClCustomMaterialData(id);
    }
    @PostMapping("/deleteClCustomMaterialDataByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deleteClCustomMaterialDataByCondition(
            @ApiParam(value = "对象")
            @RequestBody ClCustomMaterialData clCustomMaterialData){
        clCustomMaterialDataService.deleteClCustomMaterialDataByCondition(clCustomMaterialData);
    }
    @PostMapping("/findOneClCustomMaterialDataByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public ClCustomMaterialData findOneClCustomMaterialDataByCondition(
            @ApiParam(value = "对象")
            @RequestBody ClCustomMaterialData clCustomMaterialData){
        return clCustomMaterialDataService.findOneClCustomMaterialDataByCondition(clCustomMaterialData);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    @PostMapping("/findClCustomMaterialDataMaxWeight")
    public Long findClCustomMaterialDataMaxWeight(@RequestBody ClCustomMaterialData clCustomMaterialData){
        return clCustomMaterialDataService.findClCustomMaterialDataMaxWeight(clCustomMaterialData);
    }

    @PostMapping("/saveClCustomMaterialDataAndClWeight")
    public void saveClCustomMaterialDataAndClWeight(@RequestBody ClCustomMaterialData clCustomMaterialData){
        clCustomMaterialDataService.saveClCustomMaterialDataAndClWeight(clCustomMaterialData);
    }

    @PostMapping("/findClCustomMaterialDataListByConditionKong")
    public List<ClCustomMaterialData> findClCustomMaterialDataListByConditionKong(@RequestBody ClCustomMaterialData clCustomMaterialData){
       return clCustomMaterialDataService.findClCustomMaterialDataListByConditionKong(clCustomMaterialData);
    }

    @PostMapping("/findClCustomMaterialDataCountByConditionKong")
    public long findClCustomMaterialDataCountByConditionKong(@RequestBody ClCustomMaterialData clCustomMaterialData){
        return clCustomMaterialDataService.findClCustomMaterialDataCountByConditionKong(clCustomMaterialData);
    }
}
