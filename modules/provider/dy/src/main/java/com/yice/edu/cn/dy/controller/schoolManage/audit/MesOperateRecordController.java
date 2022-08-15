package com.yice.edu.cn.dy.controller.schoolManage.audit;

import com.yice.edu.cn.common.pojo.mes.schoolManage.audit.MesOperateRecord;
import com.yice.edu.cn.dy.service.schoolManage.audit.MesOperateRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mesOperateRecord")
@Api(value = "/mesOperateRecord",description = "操作记录表模块")
public class MesOperateRecordController {
    @Autowired
    private MesOperateRecordService mesOperateRecordService;

    @GetMapping("/findMesOperateRecordById/{id}")
    @ApiOperation(value = "通过id查找操作记录表", notes = "返回操作记录表对象")
    public MesOperateRecord findMesOperateRecordById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return mesOperateRecordService.findMesOperateRecordById(id);
    }

    @PostMapping("/saveMesOperateRecord")
    @ApiOperation(value = "保存操作记录表", notes = "返回操作记录表对象")
    public MesOperateRecord saveMesOperateRecord(
            @ApiParam(value = "操作记录表对象", required = true)
            @RequestBody MesOperateRecord mesOperateRecord){
        mesOperateRecordService.saveMesOperateRecord(mesOperateRecord);
        return mesOperateRecord;
    }

    @PostMapping("/findMesOperateRecordListByCondition")
    @ApiOperation(value = "根据条件查找操作记录表列表", notes = "返回操作记录表列表")
    public List<MesOperateRecord> findMesOperateRecordListByCondition(
            @ApiParam(value = "操作记录表对象")
            @RequestBody MesOperateRecord mesOperateRecord){
        return mesOperateRecordService.findMesOperateRecordListByCondition(mesOperateRecord);
    }
    @PostMapping("/findMesOperateRecordCountByCondition")
    @ApiOperation(value = "根据条件查找操作记录表列表个数", notes = "返回操作记录表总个数")
    public long findMesOperateRecordCountByCondition(
            @ApiParam(value = "操作记录表对象")
            @RequestBody MesOperateRecord mesOperateRecord){
        return mesOperateRecordService.findMesOperateRecordCountByCondition(mesOperateRecord);
    }

    @PostMapping("/updateMesOperateRecord")
    @ApiOperation(value = "修改操作记录表", notes = "操作记录表对象必传")
    public void updateMesOperateRecord(
            @ApiParam(value = "操作记录表对象,对象属性不为空则修改", required = true)
            @RequestBody MesOperateRecord mesOperateRecord){
        mesOperateRecordService.updateMesOperateRecord(mesOperateRecord);
    }

    @GetMapping("/deleteMesOperateRecord/{id}")
    @ApiOperation(value = "通过id删除操作记录表")
    public void deleteMesOperateRecord(
            @ApiParam(value = "操作记录表对象", required = true)
            @PathVariable String id){
        mesOperateRecordService.deleteMesOperateRecord(id);
    }
    @PostMapping("/deleteMesOperateRecordByCondition")
    @ApiOperation(value = "根据条件删除操作记录表")
    public void deleteMesOperateRecordByCondition(
            @ApiParam(value = "操作记录表对象")
            @RequestBody MesOperateRecord mesOperateRecord){
        mesOperateRecordService.deleteMesOperateRecordByCondition(mesOperateRecord);
    }
    @PostMapping("/findOneMesOperateRecordByCondition")
    @ApiOperation(value = "根据条件查找单个操作记录表,结果必须为单条数据", notes = "返回单个操作记录表,没有时为空")
    public MesOperateRecord findOneMesOperateRecordByCondition(
            @ApiParam(value = "操作记录表对象")
            @RequestBody MesOperateRecord mesOperateRecord){
        return mesOperateRecordService.findOneMesOperateRecordByCondition(mesOperateRecord);
    }

    @PostMapping("/passOrRefuse")
    @ApiOperation(value = "审核通过", notes = "返回响应对象")
    public int passOrRefuse(
            @ApiParam(value = "被修改的事件审核表对象,对象属性不为空则修改", required = true)
            @RequestBody MesOperateRecord mesOperateRecord){
        return mesOperateRecordService.passOrRefuse(mesOperateRecord);
    }


    @PostMapping("/batchSaveMesOperateRecord")
    @ApiOperation(value = "保存操作记录表", notes = "返回操作记录表对象")
    public void batchSaveMesOperateRecord(
            @ApiParam(value = "操作记录表对象", required = true)
            @RequestBody List<MesOperateRecord> mesOperateRecords){
        mesOperateRecordService.batchSaveMesOperateRecord(mesOperateRecords);
    }









}
