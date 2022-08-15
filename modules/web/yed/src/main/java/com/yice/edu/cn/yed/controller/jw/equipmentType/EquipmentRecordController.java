package com.yice.edu.cn.yed.controller.jw.equipmentType;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.equipmentType.EquipmentRecord;
import com.yice.edu.cn.yed.service.jw.equipmentType.EquipmentRecordService;
import com.yice.edu.cn.yed.service.jw.equipmentType.EquipmentVendorManagementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@RestController
@RequestMapping("/equipmentRecord")
@Api(value = "/equipmentRecord",description = "模块")
public class EquipmentRecordController {
    @Autowired
    private EquipmentRecordService equipmentRecordService;

    @Autowired
    private EquipmentVendorManagementService equipmentVendorManagementService;

    @PostMapping("/saveEquipmentRecord")
    @ApiOperation(value = "保存对象", notes = "返回响应对象")
    public ResponseJson saveEquipmentRecord(
            @ApiParam(value = "对象", required = true)
            @RequestBody EquipmentRecord equipmentRecord){
        EquipmentRecord s=equipmentRecordService.saveEquipmentRecord(equipmentRecord);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findEquipmentRecordById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象")
    public ResponseJson findEquipmentRecordById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        EquipmentRecord equipmentRecord=equipmentRecordService.findEquipmentRecordById(id);
        return new ResponseJson(equipmentRecord);
    }

    @PostMapping("/update/updateEquipmentRecord")
    @ApiOperation(value = "修改对象", notes = "返回响应对象")
    public ResponseJson updateEquipmentRecord(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody EquipmentRecord equipmentRecord){
        equipmentRecordService.updateEquipmentRecord(equipmentRecord);
        return new ResponseJson();
    }

    @GetMapping("/look/lookEquipmentRecordById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象")
    public ResponseJson lookEquipmentRecordById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        EquipmentRecord equipmentRecord=equipmentRecordService.findEquipmentRecordById(id);
        return new ResponseJson(equipmentRecord);
    }

    @PostMapping("/findEquipmentRecordsByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象")
    public ResponseJson findEquipmentRecordsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody EquipmentRecord equipmentRecord){
        List<EquipmentRecord> data=equipmentRecordService.findEquipmentRecordListByCondition(equipmentRecord);
        long count=equipmentRecordService.findEquipmentRecordCountByCondition(equipmentRecord);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneEquipmentRecordByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空")
    public ResponseJson findOneEquipmentRecordByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody EquipmentRecord equipmentRecord){
        EquipmentRecord one=equipmentRecordService.findOneEquipmentRecordByCondition(equipmentRecord);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteEquipmentRecord/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteEquipmentRecord(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        equipmentRecordService.deleteEquipmentRecord(id);
        return new ResponseJson();
    }


    @PostMapping("/findEquipmentRecordListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数")
    public ResponseJson findEquipmentRecordListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody EquipmentRecord equipmentRecord){
        List<EquipmentRecord> data=equipmentRecordService.findEquipmentRecordListByCondition(equipmentRecord);
        return new ResponseJson(data);
    }



}
