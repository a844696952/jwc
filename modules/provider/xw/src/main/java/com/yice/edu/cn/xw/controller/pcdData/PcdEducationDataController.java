package com.yice.edu.cn.xw.controller.pcdData;

import com.yice.edu.cn.common.pojo.xw.pcdData.PcdEducationData;
import com.yice.edu.cn.xw.service.pcdData.PcdEducationDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pcdEducationData")
@Api(value = "/pcdEducationData",description = "模块")
public class PcdEducationDataController {
    @Autowired
    private PcdEducationDataService pcdEducationDataService;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findPcdEducationDataById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public PcdEducationData findPcdEducationDataById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return pcdEducationDataService.findPcdEducationDataById(id);
    }

    @PostMapping("/savePcdEducationData")
    @ApiOperation(value = "保存", notes = "返回对象")
    public PcdEducationData savePcdEducationData(
            @ApiParam(value = "对象", required = true)
            @RequestBody PcdEducationData pcdEducationData){
        pcdEducationDataService.savePcdEducationData(pcdEducationData);
        return pcdEducationData;
    }

    @PostMapping("/batchSavePcdEducationData")
    @ApiOperation(value = "批量保存")
    public void batchSavePcdEducationData(
            @ApiParam(value = "对象集合", required = true)
            @RequestBody List<PcdEducationData> pcdEducationDatas){
        pcdEducationDataService.batchSavePcdEducationData(pcdEducationDatas);
    }

    @PostMapping("/findPcdEducationDataListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<PcdEducationData> findPcdEducationDataListByCondition(
            @ApiParam(value = "对象")
            @RequestBody PcdEducationData pcdEducationData){
        return pcdEducationDataService.findPcdEducationDataListByCondition(pcdEducationData);
    }
    @PostMapping("/findPcdEducationDataCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findPcdEducationDataCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody PcdEducationData pcdEducationData){
        return pcdEducationDataService.findPcdEducationDataCountByCondition(pcdEducationData);
    }

    @PostMapping("/updatePcdEducationData")
    @ApiOperation(value = "修改有值的字段", notes = "对象必传")
    public void updatePcdEducationData(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody PcdEducationData pcdEducationData){
        pcdEducationDataService.updatePcdEducationData(pcdEducationData);
    }
    @PostMapping("/updatePcdEducationDataForAll")
    @ApiOperation(value = "修改所有字段", notes = "对象必传")
    public void updatePcdEducationDataForAll(
            @ApiParam(value = "对象", required = true)
            @RequestBody PcdEducationData pcdEducationData){
        pcdEducationDataService.updatePcdEducationDataForAll(pcdEducationData);
    }

    @GetMapping("/deletePcdEducationData/{id}")
    @ApiOperation(value = "通过id删除")
    public void deletePcdEducationData(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        pcdEducationDataService.deletePcdEducationData(id);
    }
    @PostMapping("/deletePcdEducationDataByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deletePcdEducationDataByCondition(
            @ApiParam(value = "对象")
            @RequestBody PcdEducationData pcdEducationData){
        pcdEducationDataService.deletePcdEducationDataByCondition(pcdEducationData);
    }
    @PostMapping("/findOnePcdEducationDataByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public PcdEducationData findOnePcdEducationDataByCondition(
            @ApiParam(value = "对象")
            @RequestBody PcdEducationData pcdEducationData){
        return pcdEducationDataService.findOnePcdEducationDataByCondition(pcdEducationData);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    @PostMapping("/savePcdEducationDataKong")
    public void  savePcdEducationDataKong(@RequestBody PcdEducationData pcdEducationData){
        pcdEducationDataService.savePcdEducationDataKong(pcdEducationData);

    }

    @GetMapping("/findIndexDataByEehId/{eehId}")
    public Map<String,Object> findIndexDataByEehId(@PathVariable("eehId")String eehId){
        return pcdEducationDataService.findIndexDataByEehId(eehId);
    }
}
