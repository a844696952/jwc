package com.yice.edu.cn.xw.controller.pcdData;

import com.yice.edu.cn.common.pojo.xw.pcdData.PcdStudentData;
import com.yice.edu.cn.xw.service.pcdData.PcdStudentDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pcdStudentData")
@Api(value = "/pcdStudentData",description = "模块")
public class PcdStudentDataController {
    @Autowired
    private PcdStudentDataService pcdStudentDataService;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findPcdStudentDataById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public PcdStudentData findPcdStudentDataById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return pcdStudentDataService.findPcdStudentDataById(id);
    }

    @PostMapping("/savePcdStudentData")
    @ApiOperation(value = "保存", notes = "返回对象")
    public PcdStudentData savePcdStudentData(
            @ApiParam(value = "对象", required = true)
            @RequestBody PcdStudentData pcdStudentData){
        pcdStudentDataService.savePcdStudentData(pcdStudentData);
        return pcdStudentData;
    }

    @PostMapping("/batchSavePcdStudentData")
    @ApiOperation(value = "批量保存")
    public void batchSavePcdStudentData(
            @ApiParam(value = "对象集合", required = true)
            @RequestBody List<PcdStudentData> pcdStudentDatas){
        pcdStudentDataService.batchSavePcdStudentData(pcdStudentDatas);
    }

    @PostMapping("/findPcdStudentDataListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<PcdStudentData> findPcdStudentDataListByCondition(
            @ApiParam(value = "对象")
            @RequestBody PcdStudentData pcdStudentData){
        return pcdStudentDataService.findPcdStudentDataListByCondition(pcdStudentData);
    }
    @PostMapping("/findPcdStudentDataCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findPcdStudentDataCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody PcdStudentData pcdStudentData){
        return pcdStudentDataService.findPcdStudentDataCountByCondition(pcdStudentData);
    }

    @PostMapping("/updatePcdStudentData")
    @ApiOperation(value = "修改有值的字段", notes = "对象必传")
    public void updatePcdStudentData(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody PcdStudentData pcdStudentData){
        pcdStudentDataService.updatePcdStudentData(pcdStudentData);
    }
    @PostMapping("/updatePcdStudentDataForAll")
    @ApiOperation(value = "修改所有字段", notes = "对象必传")
    public void updatePcdStudentDataForAll(
            @ApiParam(value = "对象", required = true)
            @RequestBody PcdStudentData pcdStudentData){
        pcdStudentDataService.updatePcdStudentDataForAll(pcdStudentData);
    }

    @GetMapping("/deletePcdStudentData/{id}")
    @ApiOperation(value = "通过id删除")
    public void deletePcdStudentData(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        pcdStudentDataService.deletePcdStudentData(id);
    }
    @PostMapping("/deletePcdStudentDataByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deletePcdStudentDataByCondition(
            @ApiParam(value = "对象")
            @RequestBody PcdStudentData pcdStudentData){
        pcdStudentDataService.deletePcdStudentDataByCondition(pcdStudentData);
    }
    @PostMapping("/findOnePcdStudentDataByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public PcdStudentData findOnePcdStudentDataByCondition(
            @ApiParam(value = "对象")
            @RequestBody PcdStudentData pcdStudentData){
        return pcdStudentDataService.findOnePcdStudentDataByCondition(pcdStudentData);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    @PostMapping("/savePcdStudentDataKong")
    public void savePcdStudentDataKong(@RequestBody  PcdStudentData pcdStudentData){
        pcdStudentDataService.savePcdStudentDataKong(pcdStudentData);
    }
}
