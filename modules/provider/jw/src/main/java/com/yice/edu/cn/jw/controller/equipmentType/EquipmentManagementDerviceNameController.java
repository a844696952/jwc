package com.yice.edu.cn.jw.controller.equipmentType;

import com.yice.edu.cn.common.pojo.jw.equipmentType.EquipmentManagementDerviceName;
import com.yice.edu.cn.jw.service.equipmentType.EquipmentManagementDerviceNameService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/equipmentManagementDerviceName")
@Api(value = "/equipmentManagementDerviceName",description = "模块")
public class EquipmentManagementDerviceNameController {
    @Autowired
    private EquipmentManagementDerviceNameService equipmentManagementDerviceNameService;

    @GetMapping("/findEquipmentManagementDerviceNameById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public EquipmentManagementDerviceName findEquipmentManagementDerviceNameById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return equipmentManagementDerviceNameService.findEquipmentManagementDerviceNameById(id);
    }

    @PostMapping("/saveEquipmentManagementDerviceName")
    @ApiOperation(value = "保存", notes = "返回对象")
    public EquipmentManagementDerviceName saveEquipmentManagementDerviceName(
            @ApiParam(value = "对象", required = true)
            @RequestBody EquipmentManagementDerviceName equipmentManagementDerviceName){
        equipmentManagementDerviceNameService.saveEquipmentManagementDerviceName(equipmentManagementDerviceName);
        return equipmentManagementDerviceName;
    }

    @PostMapping("/findEquipmentManagementDerviceNameListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<EquipmentManagementDerviceName> findEquipmentManagementDerviceNameListByCondition(
            @ApiParam(value = "对象")
            @RequestBody EquipmentManagementDerviceName equipmentManagementDerviceName){
        return equipmentManagementDerviceNameService.findEquipmentManagementDerviceNameListByCondition(equipmentManagementDerviceName);
    }
    @PostMapping("/findEquipmentManagementDerviceNameCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findEquipmentManagementDerviceNameCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody EquipmentManagementDerviceName equipmentManagementDerviceName){
        return equipmentManagementDerviceNameService.findEquipmentManagementDerviceNameCountByCondition(equipmentManagementDerviceName);
    }

    @PostMapping("/updateEquipmentManagementDerviceName")
    @ApiOperation(value = "修改", notes = "对象必传")
    public void updateEquipmentManagementDerviceName(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody EquipmentManagementDerviceName equipmentManagementDerviceName){
        equipmentManagementDerviceNameService.updateEquipmentManagementDerviceName(equipmentManagementDerviceName);
    }

    @GetMapping("/deleteEquipmentManagementDerviceName/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteEquipmentManagementDerviceName(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        equipmentManagementDerviceNameService.deleteEquipmentManagementDerviceName(id);
    }
    @PostMapping("/deleteEquipmentManagementDerviceNameByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deleteEquipmentManagementDerviceNameByCondition(
            @ApiParam(value = "对象")
            @RequestBody EquipmentManagementDerviceName equipmentManagementDerviceName){
        equipmentManagementDerviceNameService.deleteEquipmentManagementDerviceNameByCondition(equipmentManagementDerviceName);
    }
    @PostMapping("/findOneEquipmentManagementDerviceNameByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public EquipmentManagementDerviceName findOneEquipmentManagementDerviceNameByCondition(
            @ApiParam(value = "对象")
            @RequestBody EquipmentManagementDerviceName equipmentManagementDerviceName){
        return equipmentManagementDerviceNameService.findOneEquipmentManagementDerviceNameByCondition(equipmentManagementDerviceName);
    }
}
