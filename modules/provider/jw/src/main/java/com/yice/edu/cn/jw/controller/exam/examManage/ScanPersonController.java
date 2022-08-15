package com.yice.edu.cn.jw.controller.exam.examManage;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.exam.examManage.ScanPerson;
import com.yice.edu.cn.jw.service.exam.examManage.ScanPersonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/scanPerson")
@Api(value = "/scanPerson",description = "扫描员管理表模块")
public class ScanPersonController {
    @Autowired
    private ScanPersonService scanPersonService;

    @GetMapping("/findScanPersonById/{id}")
    @ApiOperation(value = "通过id查找扫描员管理表", notes = "返回扫描员管理表对象")
    public ScanPerson findScanPersonById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return scanPersonService.findScanPersonById(id);
    }

    @PostMapping("/saveScanPerson")
    @ApiOperation(value = "保存扫描员管理表", notes = "返回扫描员管理表对象")
    public ScanPerson saveScanPerson(
            @ApiParam(value = "扫描员管理表对象", required = true)
            @RequestBody ScanPerson scanPerson){
        scanPersonService.saveScanPerson(scanPerson);
        return scanPerson;
    }

    @PostMapping("/findScanPersonListByCondition")
    @ApiOperation(value = "根据条件查找扫描员管理表列表", notes = "返回扫描员管理表列表")
    public List<ScanPerson> findScanPersonListByCondition(
            @ApiParam(value = "扫描员管理表对象")
            @RequestBody ScanPerson scanPerson){
        return scanPersonService.findScanPersonListByCondition(scanPerson);
    }
    @PostMapping("/findScanPersonCountByCondition")
    @ApiOperation(value = "根据条件查找扫描员管理表列表个数", notes = "返回扫描员管理表总个数")
    public long findScanPersonCountByCondition(
            @ApiParam(value = "扫描员管理表对象")
            @RequestBody ScanPerson scanPerson){
        return scanPersonService.findScanPersonCountByCondition(scanPerson);
    }

    @PostMapping("/updateScanPerson")
    @ApiOperation(value = "修改扫描员管理表", notes = "扫描员管理表对象必传")
    public void updateScanPerson(
            @ApiParam(value = "扫描员管理表对象,对象属性不为空则修改", required = true)
            @RequestBody ScanPerson scanPerson){
        scanPersonService.updateScanPerson(scanPerson);
    }

    @GetMapping("/deleteScanPerson/{id}")
    @ApiOperation(value = "通过id删除扫描员管理表")
    public void deleteScanPerson(
            @ApiParam(value = "扫描员管理表对象", required = true)
            @PathVariable String id){
        scanPersonService.deleteScanPerson(id);
    }
    @PostMapping("/deleteScanPersonByCondition")
    @ApiOperation(value = "根据条件删除扫描员管理表")
    public void deleteScanPersonByCondition(
            @ApiParam(value = "扫描员管理表对象")
            @RequestBody ScanPerson scanPerson){
        scanPersonService.deleteScanPersonByCondition(scanPerson);
    }
    @PostMapping("/findOneScanPersonByCondition")
    @ApiOperation(value = "根据条件查找单个扫描员管理表,结果必须为单条数据", notes = "返回单个扫描员管理表,没有时为空")
    public ScanPerson findOneScanPersonByCondition(
            @ApiParam(value = "扫描员管理表对象")
            @RequestBody ScanPerson scanPerson){
        return scanPersonService.findOneScanPersonByCondition(scanPerson);
    }




    @PostMapping("/find/findAllScanPerson")
    @ApiOperation(value = "根据条件查找扫描员管理表", notes = "返回响应对象", response=ScanPerson.class)
    public List<ScanPerson> findAllScanPerson(@ApiParam(value = "扫描员管理表对象")
                                                  @RequestBody ScanPerson scanPerson){
        return scanPersonService.findAllScanPerson(scanPerson);
    }



    @PostMapping("/ignore/addScanPersonList")
    @ApiOperation(value = "保存扫描员管理表对象", notes = "返回保存好的扫描员管理表对象", response=ScanPerson.class)
    public List<ScanPerson> addScanPersonList(
            @ApiParam(value = "扫描员管理表对象", required = true)
            @RequestBody List<ScanPerson> scanPerson){

         scanPersonService.batchSaveScanPerson(scanPerson);
         return scanPerson;

    }


    @PostMapping("/ignore/findTeacherByIds")
    @ApiOperation(value = "保存扫描员管理表对象", notes = "返回保存好的扫描员管理表对象", response=ScanPerson.class)
    public List<ScanPerson> findTeacherByIds(
            @ApiParam(value = "扫描员管理表对象", required = true)
            @RequestBody ScanPerson scanPerson){
        return scanPersonService.findTeacherByIds(scanPerson);


    }
}
