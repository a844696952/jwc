package com.yice.edu.cn.xw.controller.pcdData;

import com.yice.edu.cn.common.pojo.xw.pcdData.PcdTeachingData;
import com.yice.edu.cn.xw.service.pcdData.PcdTeachingDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pcdTeachingData")
@Api(value = "/pcdTeachingData",description = "模块")
public class PcdTeachingDataController {
    @Autowired
    private PcdTeachingDataService pcdTeachingDataService;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findPcdTeachingDataById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public PcdTeachingData findPcdTeachingDataById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return pcdTeachingDataService.findPcdTeachingDataById(id);
    }

    @PostMapping("/savePcdTeachingData")
    @ApiOperation(value = "保存", notes = "返回对象")
    public PcdTeachingData savePcdTeachingData(
            @ApiParam(value = "对象", required = true)
            @RequestBody PcdTeachingData pcdTeachingData){
        pcdTeachingDataService.savePcdTeachingData(pcdTeachingData);
        return pcdTeachingData;
    }

    @PostMapping("/batchSavePcdTeachingData")
    @ApiOperation(value = "批量保存")
    public void batchSavePcdTeachingData(
            @ApiParam(value = "对象集合", required = true)
            @RequestBody List<PcdTeachingData> pcdTeachingDatas){
        pcdTeachingDataService.batchSavePcdTeachingData(pcdTeachingDatas);
    }

    @PostMapping("/findPcdTeachingDataListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<PcdTeachingData> findPcdTeachingDataListByCondition(
            @ApiParam(value = "对象")
            @RequestBody PcdTeachingData pcdTeachingData){
        return pcdTeachingDataService.findPcdTeachingDataListByCondition(pcdTeachingData);
    }
    @PostMapping("/findPcdTeachingDataCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findPcdTeachingDataCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody PcdTeachingData pcdTeachingData){
        return pcdTeachingDataService.findPcdTeachingDataCountByCondition(pcdTeachingData);
    }

    @PostMapping("/updatePcdTeachingData")
    @ApiOperation(value = "修改有值的字段", notes = "对象必传")
    public void updatePcdTeachingData(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody PcdTeachingData pcdTeachingData){
        pcdTeachingDataService.updatePcdTeachingData(pcdTeachingData);
    }
    @PostMapping("/updatePcdTeachingDataForAll")
    @ApiOperation(value = "修改所有字段", notes = "对象必传")
    public void updatePcdTeachingDataForAll(
            @ApiParam(value = "对象", required = true)
            @RequestBody PcdTeachingData pcdTeachingData){
        pcdTeachingDataService.updatePcdTeachingDataForAll(pcdTeachingData);
    }

    @GetMapping("/deletePcdTeachingData/{id}")
    @ApiOperation(value = "通过id删除")
    public void deletePcdTeachingData(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        pcdTeachingDataService.deletePcdTeachingData(id);
    }
    @PostMapping("/deletePcdTeachingDataByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deletePcdTeachingDataByCondition(
            @ApiParam(value = "对象")
            @RequestBody PcdTeachingData pcdTeachingData){
        pcdTeachingDataService.deletePcdTeachingDataByCondition(pcdTeachingData);
    }
    @PostMapping("/findOnePcdTeachingDataByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public PcdTeachingData findOnePcdTeachingDataByCondition(
            @ApiParam(value = "对象")
            @RequestBody PcdTeachingData pcdTeachingData){
        return pcdTeachingDataService.findOnePcdTeachingDataByCondition(pcdTeachingData);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    @PostMapping("/savePcdTeachingDataKong")
    public void savePcdTeachingDataKong(@RequestBody PcdTeachingData pcdTeachingData){
        pcdTeachingDataService.savePcdTeachingDataKong(pcdTeachingData);
    }

    @GetMapping("/findPcdTeachingDataByIdKong/{id}")
    public PcdTeachingData findPcdTeachingDataByIdKong(@PathVariable("id")String id){
        return pcdTeachingDataService.findPcdTeachingDataByIdKong(id);
    }
}
