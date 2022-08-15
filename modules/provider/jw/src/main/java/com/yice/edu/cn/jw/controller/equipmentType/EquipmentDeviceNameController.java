package com.yice.edu.cn.jw.controller.equipmentType;

import com.yice.edu.cn.common.pojo.jw.equipmentType.EquipmentDeviceName;
import com.yice.edu.cn.jw.service.equipmentType.EquipmentDeviceNameService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/equipmentDeviceName")
@Api(value = "/equipmentDeviceName",description = "模块")
public class EquipmentDeviceNameController {
    @Autowired
    private EquipmentDeviceNameService equipmentDeviceNameService;

    @GetMapping("/findEquipmentDeviceNameById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public EquipmentDeviceName findEquipmentDeviceNameById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return equipmentDeviceNameService.findEquipmentDeviceNameById(id);
    }

    @PostMapping("/saveEquipmentDeviceName")
    @ApiOperation(value = "保存", notes = "返回对象")
    public EquipmentDeviceName saveEquipmentDeviceName(
            @ApiParam(value = "对象", required = true)
            @RequestBody EquipmentDeviceName equipmentDeviceName){
        equipmentDeviceNameService.saveEquipmentDeviceName(equipmentDeviceName);
        return equipmentDeviceName;
    }

    @PostMapping("/findEquipmentDeviceNameListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<EquipmentDeviceName> findEquipmentDeviceNameListByCondition(
            @ApiParam(value = "对象")
            @RequestBody EquipmentDeviceName equipmentDeviceName){
        return equipmentDeviceNameService.findEquipmentDeviceNameListByCondition(equipmentDeviceName);
    }
    @PostMapping("/findEquipmentDeviceNameCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findEquipmentDeviceNameCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody EquipmentDeviceName equipmentDeviceName){
        return equipmentDeviceNameService.findEquipmentDeviceNameCountByCondition(equipmentDeviceName);
    }

    @PostMapping("/updateEquipmentDeviceName")
    @ApiOperation(value = "修改", notes = "对象必传")
    public void updateEquipmentDeviceName(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody EquipmentDeviceName equipmentDeviceName){
        equipmentDeviceNameService.updateEquipmentDeviceName(equipmentDeviceName);
    }

    @GetMapping("/deleteEquipmentDeviceName/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteEquipmentDeviceName(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        equipmentDeviceNameService.deleteEquipmentDeviceName(id);
    }
    @PostMapping("/deleteEquipmentDeviceNameByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deleteEquipmentDeviceNameByCondition(
            @ApiParam(value = "对象")
            @RequestBody EquipmentDeviceName equipmentDeviceName){
        equipmentDeviceNameService.deleteEquipmentDeviceNameByCondition(equipmentDeviceName);
    }
    @PostMapping("/findOneEquipmentDeviceNameByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public EquipmentDeviceName findOneEquipmentDeviceNameByCondition(
            @ApiParam(value = "对象")
            @RequestBody EquipmentDeviceName equipmentDeviceName){
        return equipmentDeviceNameService.findOneEquipmentDeviceNameByCondition(equipmentDeviceName);
    }



    @PostMapping("/saveEquipmentDeviceNameGai")
    @ApiOperation(value = "保存", notes = "返回对象")
    public String saveEquipmentDeviceNameGai(
            @ApiParam(value = "对象", required = true)
            @RequestBody EquipmentDeviceName equipmentDeviceName){
        return  equipmentDeviceNameService.saveEquipmentDeviceNameGai(equipmentDeviceName);
    }

    @PostMapping("/updateEquipmentDeviceNameGai")
    @ApiOperation(value = "修改", notes = "对象必传")
    public String updateEquipmentDeviceNameGai(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody EquipmentDeviceName equipmentDeviceName){
       return equipmentDeviceNameService.updateEquipmentDeviceNameGai(equipmentDeviceName);
    }

}
