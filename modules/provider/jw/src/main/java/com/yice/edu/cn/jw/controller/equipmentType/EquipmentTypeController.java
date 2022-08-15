package com.yice.edu.cn.jw.controller.equipmentType;

import com.yice.edu.cn.common.pojo.jw.equipmentType.EquipmentType;
import com.yice.edu.cn.jw.service.equipmentType.EquipmentTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/equipmentType")
@Api(value = "/equipmentType",description = "模块")
public class EquipmentTypeController {
    @Autowired
    private EquipmentTypeService equipmentTypeService;

    @GetMapping("/findEquipmentTypeById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public EquipmentType findEquipmentTypeById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return equipmentTypeService.findEquipmentTypeById(id);
    }

    @PostMapping("/saveEquipmentType")
    @ApiOperation(value = "保存", notes = "返回对象")
    public EquipmentType saveEquipmentType(
            @ApiParam(value = "对象", required = true)
            @RequestBody EquipmentType equipmentType){
        equipmentTypeService.saveEquipmentType(equipmentType);
        return equipmentType;
    }

    @PostMapping("/findEquipmentTypeListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<EquipmentType> findEquipmentTypeListByCondition(
            @ApiParam(value = "对象")
            @RequestBody EquipmentType equipmentType){
        return equipmentTypeService.findEquipmentTypeListByCondition(equipmentType);
    }
    @PostMapping("/findEquipmentTypeCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findEquipmentTypeCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody EquipmentType equipmentType){
        return equipmentTypeService.findEquipmentTypeCountByCondition(equipmentType);
    }

    @PostMapping("/updateEquipmentType")
    @ApiOperation(value = "修改", notes = "对象必传")
    public void updateEquipmentType(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody EquipmentType equipmentType){
        equipmentTypeService.updateEquipmentType(equipmentType);
    }

    @GetMapping("/deleteEquipmentType/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteEquipmentType(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        equipmentTypeService.deleteEquipmentType(id);
    }
    @PostMapping("/deleteEquipmentTypeByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deleteEquipmentTypeByCondition(
            @ApiParam(value = "对象")
            @RequestBody EquipmentType equipmentType){
        equipmentTypeService.deleteEquipmentTypeByCondition(equipmentType);
    }
    @PostMapping("/findOneEquipmentTypeByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public EquipmentType findOneEquipmentTypeByCondition(
            @ApiParam(value = "对象")
            @RequestBody EquipmentType equipmentType){
        return equipmentTypeService.findOneEquipmentTypeByCondition(equipmentType);
    }

    @PostMapping("/saveEquipmentTypeGai")
    @ApiOperation(value = "保存", notes = "返回对象")
    public String saveEquipmentTypeGai(
            @ApiParam(value = "对象", required = true)
            @RequestBody EquipmentType equipmentType){
        return equipmentTypeService.saveEquipmentTypeGai(equipmentType);
    }

    @PostMapping("/updateEquipmentTypeGai")
    @ApiOperation(value = "修改", notes = "对象必传")
    public String updateEquipmentTypeGai(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody EquipmentType equipmentType){
        return equipmentTypeService.updateEquipmentTypeGai(equipmentType);
    }

    @GetMapping("/deleteEquipmentTypeGai/{id}")
    @ApiOperation(value = "通过id删除")
    public String deleteEquipmentTypeGai(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        return  equipmentTypeService.deleteEquipmentTypeGai(id);
    }

}
