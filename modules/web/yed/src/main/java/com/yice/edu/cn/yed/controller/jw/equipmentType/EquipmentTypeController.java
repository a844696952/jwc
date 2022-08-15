package com.yice.edu.cn.yed.controller.jw.equipmentType;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.equipmentType.EquipmentManagementDerviceName;
import com.yice.edu.cn.common.pojo.jw.equipmentType.EquipmentType;
import com.yice.edu.cn.yed.service.jw.equipmentType.EquipmentManagementDerviceNameService;
import com.yice.edu.cn.yed.service.jw.equipmentType.EquipmentTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@RestController
@RequestMapping("/equipmentType")
@Api(value = "/equipmentType",description = "模块")
public class EquipmentTypeController {
    @Autowired
    private EquipmentTypeService equipmentTypeService;

    @Autowired
    private EquipmentManagementDerviceNameService equipmentManagementDerviceNameService;

    @PostMapping("/saveEquipmentType")
    @ApiOperation(value = "保存对象", notes = "返回响应对象")
    public ResponseJson saveEquipmentType(
            @ApiParam(value = "对象", required = true)
            @RequestBody EquipmentType equipmentType){
        EquipmentType s=equipmentTypeService.saveEquipmentType(equipmentType);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findEquipmentTypeById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象")
    public ResponseJson findEquipmentTypeById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        EquipmentType equipmentType=equipmentTypeService.findEquipmentTypeById(id);
        return new ResponseJson(equipmentType);
    }

    @PostMapping("/update/updateEquipmentType")
    @ApiOperation(value = "修改对象", notes = "返回响应对象")
    public ResponseJson updateEquipmentType(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody EquipmentType equipmentType){
        equipmentTypeService.updateEquipmentType(equipmentType);
        return new ResponseJson();
    }

    @GetMapping("/look/lookEquipmentTypeById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象")
    public ResponseJson lookEquipmentTypeById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        EquipmentType equipmentType=equipmentTypeService.findEquipmentTypeById(id);
        return new ResponseJson(equipmentType);
    }

    @PostMapping("/findEquipmentTypesByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象")
    public ResponseJson findEquipmentTypesByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody EquipmentType equipmentType){
        List<EquipmentType> data=equipmentTypeService.findEquipmentTypeListByCondition(equipmentType);
        long count=equipmentTypeService.findEquipmentTypeCountByCondition(equipmentType);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneEquipmentTypeByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空")
    public ResponseJson findOneEquipmentTypeByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody EquipmentType equipmentType){
        EquipmentType one=equipmentTypeService.findOneEquipmentTypeByCondition(equipmentType);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteEquipmentType/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteEquipmentType(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        equipmentTypeService.deleteEquipmentType(id);
        return new ResponseJson();
    }


    @PostMapping("/findEquipmentTypeListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数")
    public ResponseJson findEquipmentTypeListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody EquipmentType equipmentType){
        List<EquipmentType> data=equipmentTypeService.findEquipmentTypeListByCondition(equipmentType);
        return new ResponseJson(data);
    }


    @PostMapping("/saveEquipmentTypeGai")
    @ApiOperation(value = "保存对象", notes = "返回响应对象")
    public ResponseJson saveEquipmentTypeGai(
            @ApiParam(value = "对象", required = true)
            @RequestBody EquipmentType equipmentType){
        String s=equipmentTypeService.saveEquipmentTypeGai(equipmentType);
        return new ResponseJson(s);
    }

    @PostMapping("/update/updateEquipmentTypeGai")
    @ApiOperation(value = "修改对象", notes = "返回响应对象")
    public ResponseJson updateEquipmentTypeGai(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody EquipmentType equipmentType){
        String s = equipmentTypeService.updateEquipmentTypeGai(equipmentType);
        return new ResponseJson(s);
    }

    @GetMapping("/deleteEquipmentTypeGai/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteEquipmentTypeGai(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        String s = equipmentTypeService.deleteEquipmentTypeGai(id);
        return new ResponseJson(s);
    }



}
