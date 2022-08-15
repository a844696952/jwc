package com.yice.edu.cn.yed.controller.jw.equipmentType;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.equipmentType.EquipmentManagementDerviceName;
import com.yice.edu.cn.yed.service.jw.equipmentType.EquipmentManagementDerviceNameService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@RestController
@RequestMapping("/equipmentManagementDerviceName")
@Api(value = "/equipmentManagementDerviceName",description = "模块")
public class EquipmentManagementDerviceNameController {
    @Autowired
    private EquipmentManagementDerviceNameService equipmentManagementDerviceNameService;

    @PostMapping("/saveEquipmentManagementDerviceName")
    @ApiOperation(value = "保存对象", notes = "返回响应对象")
    public ResponseJson saveEquipmentManagementDerviceName(
            @ApiParam(value = "对象", required = true)
            @RequestBody EquipmentManagementDerviceName equipmentManagementDerviceName){
        EquipmentManagementDerviceName s=equipmentManagementDerviceNameService.saveEquipmentManagementDerviceName(equipmentManagementDerviceName);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findEquipmentManagementDerviceNameById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象")
    public ResponseJson findEquipmentManagementDerviceNameById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        EquipmentManagementDerviceName equipmentManagementDerviceName=equipmentManagementDerviceNameService.findEquipmentManagementDerviceNameById(id);
        return new ResponseJson(equipmentManagementDerviceName);
    }

    @PostMapping("/update/updateEquipmentManagementDerviceName")
    @ApiOperation(value = "修改对象", notes = "返回响应对象")
    public ResponseJson updateEquipmentManagementDerviceName(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody EquipmentManagementDerviceName equipmentManagementDerviceName){
        equipmentManagementDerviceNameService.updateEquipmentManagementDerviceName(equipmentManagementDerviceName);
        return new ResponseJson();
    }

    @GetMapping("/look/lookEquipmentManagementDerviceNameById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象")
    public ResponseJson lookEquipmentManagementDerviceNameById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        EquipmentManagementDerviceName equipmentManagementDerviceName=equipmentManagementDerviceNameService.findEquipmentManagementDerviceNameById(id);
        return new ResponseJson(equipmentManagementDerviceName);
    }

    @PostMapping("/findEquipmentManagementDerviceNamesByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象")
    public ResponseJson findEquipmentManagementDerviceNamesByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody EquipmentManagementDerviceName equipmentManagementDerviceName){
        List<EquipmentManagementDerviceName> data=equipmentManagementDerviceNameService.findEquipmentManagementDerviceNameListByCondition(equipmentManagementDerviceName);
        long count=equipmentManagementDerviceNameService.findEquipmentManagementDerviceNameCountByCondition(equipmentManagementDerviceName);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneEquipmentManagementDerviceNameByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空")
    public ResponseJson findOneEquipmentManagementDerviceNameByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody EquipmentManagementDerviceName equipmentManagementDerviceName){
        EquipmentManagementDerviceName one=equipmentManagementDerviceNameService.findOneEquipmentManagementDerviceNameByCondition(equipmentManagementDerviceName);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteEquipmentManagementDerviceName/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteEquipmentManagementDerviceName(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        equipmentManagementDerviceNameService.deleteEquipmentManagementDerviceName(id);
        return new ResponseJson();
    }


    @PostMapping("/findEquipmentManagementDerviceNameListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数")
    public ResponseJson findEquipmentManagementDerviceNameListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody EquipmentManagementDerviceName equipmentManagementDerviceName){
        List<EquipmentManagementDerviceName> data=equipmentManagementDerviceNameService.findEquipmentManagementDerviceNameListByCondition(equipmentManagementDerviceName);
        return new ResponseJson(data);
    }



}
