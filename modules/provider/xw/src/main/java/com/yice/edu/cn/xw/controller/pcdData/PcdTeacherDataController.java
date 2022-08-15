package com.yice.edu.cn.xw.controller.pcdData;

import com.yice.edu.cn.common.pojo.xw.pcdData.PcdTeacherData;
import com.yice.edu.cn.xw.service.pcdData.PcdTeacherDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pcdTeacherData")
@Api(value = "/pcdTeacherData",description = "模块")
public class PcdTeacherDataController {
    @Autowired
    private PcdTeacherDataService pcdTeacherDataService;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findPcdTeacherDataById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public PcdTeacherData findPcdTeacherDataById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return pcdTeacherDataService.findPcdTeacherDataById(id);
    }

    @PostMapping("/savePcdTeacherData")
    @ApiOperation(value = "保存", notes = "返回对象")
    public PcdTeacherData savePcdTeacherData(
            @ApiParam(value = "对象", required = true)
            @RequestBody PcdTeacherData pcdTeacherData){
        pcdTeacherDataService.savePcdTeacherData(pcdTeacherData);
        return pcdTeacherData;
    }

    @PostMapping("/batchSavePcdTeacherData")
    @ApiOperation(value = "批量保存")
    public void batchSavePcdTeacherData(
            @ApiParam(value = "对象集合", required = true)
            @RequestBody List<PcdTeacherData> pcdTeacherDatas){
        pcdTeacherDataService.batchSavePcdTeacherData(pcdTeacherDatas);
    }

    @PostMapping("/findPcdTeacherDataListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<PcdTeacherData> findPcdTeacherDataListByCondition(
            @ApiParam(value = "对象")
            @RequestBody PcdTeacherData pcdTeacherData){
        return pcdTeacherDataService.findPcdTeacherDataListByCondition(pcdTeacherData);
    }
    @PostMapping("/findPcdTeacherDataCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findPcdTeacherDataCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody PcdTeacherData pcdTeacherData){
        return pcdTeacherDataService.findPcdTeacherDataCountByCondition(pcdTeacherData);
    }

    @PostMapping("/updatePcdTeacherData")
    @ApiOperation(value = "修改有值的字段", notes = "对象必传")
    public void updatePcdTeacherData(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody PcdTeacherData pcdTeacherData){
        pcdTeacherDataService.updatePcdTeacherData(pcdTeacherData);
    }
    @PostMapping("/updatePcdTeacherDataForAll")
    @ApiOperation(value = "修改所有字段", notes = "对象必传")
    public void updatePcdTeacherDataForAll(
            @ApiParam(value = "对象", required = true)
            @RequestBody PcdTeacherData pcdTeacherData){
        pcdTeacherDataService.updatePcdTeacherDataForAll(pcdTeacherData);
    }

    @GetMapping("/deletePcdTeacherData/{id}")
    @ApiOperation(value = "通过id删除")
    public void deletePcdTeacherData(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        pcdTeacherDataService.deletePcdTeacherData(id);
    }
    @PostMapping("/deletePcdTeacherDataByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deletePcdTeacherDataByCondition(
            @ApiParam(value = "对象")
            @RequestBody PcdTeacherData pcdTeacherData){
        pcdTeacherDataService.deletePcdTeacherDataByCondition(pcdTeacherData);
    }
    @PostMapping("/findOnePcdTeacherDataByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public PcdTeacherData findOnePcdTeacherDataByCondition(
            @ApiParam(value = "对象")
            @RequestBody PcdTeacherData pcdTeacherData){
        return pcdTeacherDataService.findOnePcdTeacherDataByCondition(pcdTeacherData);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    @PostMapping("/savePcdTeacherDataKong")
    public void savePcdTeacherDataKong(@RequestBody PcdTeacherData pcdTeacherData){
        pcdTeacherDataService.savePcdTeacherDataKong(pcdTeacherData);
    }
}
