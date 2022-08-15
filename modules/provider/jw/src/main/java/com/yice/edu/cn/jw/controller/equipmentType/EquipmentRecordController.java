package com.yice.edu.cn.jw.controller.equipmentType;

import com.yice.edu.cn.common.pojo.jw.equipmentType.EquipmentRecord;
import com.yice.edu.cn.jw.service.equipmentType.EquipmentRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/equipmentRecord")
@Api(value = "/equipmentRecord",description = "模块")
public class EquipmentRecordController {
    @Autowired
    private EquipmentRecordService equipmentRecordService;

    @GetMapping("/findEquipmentRecordById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public EquipmentRecord findEquipmentRecordById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return equipmentRecordService.findEquipmentRecordById(id);
    }

    @PostMapping("/saveEquipmentRecord")
    @ApiOperation(value = "保存", notes = "返回对象")
    public EquipmentRecord saveEquipmentRecord(
            @ApiParam(value = "对象", required = true)
            @RequestBody EquipmentRecord equipmentRecord){
        equipmentRecordService.saveEquipmentRecord(equipmentRecord);
        return equipmentRecord;
    }

    @PostMapping("/findEquipmentRecordListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<EquipmentRecord> findEquipmentRecordListByCondition(
            @ApiParam(value = "对象")
            @RequestBody EquipmentRecord equipmentRecord){

        return equipmentRecordService.findEquipmentRecordListByCondition(equipmentRecord);
    }
    @PostMapping("/findEquipmentRecordCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findEquipmentRecordCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody EquipmentRecord equipmentRecord){
        return equipmentRecordService.findEquipmentRecordCountByCondition(equipmentRecord);
    }

    @PostMapping("/updateEquipmentRecord")
    @ApiOperation(value = "修改", notes = "对象必传")
    public void updateEquipmentRecord(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody EquipmentRecord equipmentRecord){
        equipmentRecordService.updateEquipmentRecord(equipmentRecord);
    }

    @GetMapping("/deleteEquipmentRecord/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteEquipmentRecord(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        equipmentRecordService.deleteEquipmentRecord(id);
    }
    @PostMapping("/deleteEquipmentRecordByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deleteEquipmentRecordByCondition(
            @ApiParam(value = "对象")
            @RequestBody EquipmentRecord equipmentRecord){
        equipmentRecordService.deleteEquipmentRecordByCondition(equipmentRecord);
    }
    @PostMapping("/findOneEquipmentRecordByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public EquipmentRecord findOneEquipmentRecordByCondition(
            @ApiParam(value = "对象")
            @RequestBody EquipmentRecord equipmentRecord){
        return equipmentRecordService.findOneEquipmentRecordByCondition(equipmentRecord);
    }
}
