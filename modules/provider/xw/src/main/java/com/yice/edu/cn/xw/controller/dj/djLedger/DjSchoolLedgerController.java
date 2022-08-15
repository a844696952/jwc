package com.yice.edu.cn.xw.controller.dj.djLedger;

import com.yice.edu.cn.common.pojo.xw.dj.djLedgerTemplate.DjSchoolLedger;
import com.yice.edu.cn.xw.service.dj.djLedger.DjSchoolLedgerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/djSchoolLedger")
@Api(value = "/djSchoolLedger",description = "模块")
public class DjSchoolLedgerController {
    @Autowired
    private DjSchoolLedgerService djSchoolLedgerService;

    @GetMapping("/findDjSchoolLedgerById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public DjSchoolLedger findDjSchoolLedgerById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return djSchoolLedgerService.findDjSchoolLedgerById(id);
    }

    @PostMapping("/saveDjSchoolLedger")
    @ApiOperation(value = "保存", notes = "返回对象")
    public DjSchoolLedger saveDjSchoolLedger(
            @ApiParam(value = "对象", required = true)
            @RequestBody DjSchoolLedger djSchoolLedger){
        djSchoolLedgerService.saveDjSchoolLedger(djSchoolLedger);
        return djSchoolLedger;
    }

    @PostMapping("/findDjSchoolLedgerListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<DjSchoolLedger> findDjSchoolLedgerListByCondition(
            @ApiParam(value = "对象")
            @RequestBody DjSchoolLedger djSchoolLedger){
        return djSchoolLedgerService.findDjSchoolLedgerListByCondition(djSchoolLedger);
    }
    @PostMapping("/findDjSchoolLedgerCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findDjSchoolLedgerCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody DjSchoolLedger djSchoolLedger){
        return djSchoolLedgerService.findDjSchoolLedgerCountByCondition(djSchoolLedger);
    }

    @PostMapping("/updateDjSchoolLedger")
    @ApiOperation(value = "修改", notes = "对象必传")
    public void updateDjSchoolLedger(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody DjSchoolLedger djSchoolLedger){
        djSchoolLedgerService.updateDjSchoolLedger(djSchoolLedger);
    }

    @GetMapping("/deleteDjSchoolLedger/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteDjSchoolLedger(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        djSchoolLedgerService.deleteDjSchoolLedger(id);
    }
    @PostMapping("/deleteDjSchoolLedgerByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deleteDjSchoolLedgerByCondition(
            @ApiParam(value = "对象")
            @RequestBody DjSchoolLedger djSchoolLedger){
        djSchoolLedgerService.deleteDjSchoolLedgerByCondition(djSchoolLedger);
    }
    @PostMapping("/findOneDjSchoolLedgerByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public DjSchoolLedger findOneDjSchoolLedgerByCondition(
            @ApiParam(value = "对象")
            @RequestBody DjSchoolLedger djSchoolLedger){
        return djSchoolLedgerService.findOneDjSchoolLedgerByCondition(djSchoolLedger);
    }
}
