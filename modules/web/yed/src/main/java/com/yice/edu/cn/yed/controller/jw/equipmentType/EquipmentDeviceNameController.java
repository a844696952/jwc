package com.yice.edu.cn.yed.controller.jw.equipmentType;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.equipmentType.EquipmentDeviceName;
import com.yice.edu.cn.common.pojo.jw.equipmentType.EquipmentManagementDerviceName;
import com.yice.edu.cn.yed.service.jw.equipmentType.EquipmentDeviceNameService;
import com.yice.edu.cn.yed.service.jw.equipmentType.EquipmentManagementDerviceNameService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@RestController
@RequestMapping("/equipmentDeviceName")
@Api(value = "/equipmentDeviceName",description = "模块")
public class EquipmentDeviceNameController {
    @Autowired
    private EquipmentDeviceNameService equipmentDeviceNameService;

    @Autowired
    private EquipmentManagementDerviceNameService equipmentManagementDerviceNameService;

    @PostMapping("/saveEquipmentDeviceName")
    @ApiOperation(value = "保存对象", notes = "返回响应对象")
    public ResponseJson saveEquipmentDeviceName(
            @ApiParam(value = "对象", required = true)
            @RequestBody EquipmentDeviceName equipmentDeviceName){
        EquipmentDeviceName s=equipmentDeviceNameService.saveEquipmentDeviceName(equipmentDeviceName);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findEquipmentDeviceNameById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象")
    public ResponseJson findEquipmentDeviceNameById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        EquipmentDeviceName equipmentDeviceName=equipmentDeviceNameService.findEquipmentDeviceNameById(id);
        return new ResponseJson(equipmentDeviceName);
    }

    @PostMapping("/update/updateEquipmentDeviceName")
    @ApiOperation(value = "修改对象", notes = "返回响应对象")
    public ResponseJson updateEquipmentDeviceName(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody EquipmentDeviceName equipmentDeviceName){
        equipmentDeviceNameService.updateEquipmentDeviceName(equipmentDeviceName);
        return new ResponseJson();
    }

    @GetMapping("/look/lookEquipmentDeviceNameById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象")
    public ResponseJson lookEquipmentDeviceNameById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        EquipmentDeviceName equipmentDeviceName=equipmentDeviceNameService.findEquipmentDeviceNameById(id);
        return new ResponseJson(equipmentDeviceName);
    }

    @PostMapping("/findEquipmentDeviceNamesByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象")
    public ResponseJson findEquipmentDeviceNamesByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody EquipmentDeviceName equipmentDeviceName){
        List<EquipmentDeviceName> data=equipmentDeviceNameService.findEquipmentDeviceNameListByCondition(equipmentDeviceName);
        long count=equipmentDeviceNameService.findEquipmentDeviceNameCountByCondition(equipmentDeviceName);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneEquipmentDeviceNameByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空")
    public ResponseJson findOneEquipmentDeviceNameByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody EquipmentDeviceName equipmentDeviceName){
        EquipmentDeviceName one=equipmentDeviceNameService.findOneEquipmentDeviceNameByCondition(equipmentDeviceName);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteEquipmentDeviceName/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteEquipmentDeviceName(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        equipmentDeviceNameService.deleteEquipmentDeviceName(id);
        return new ResponseJson();
    }


    @PostMapping("/findEquipmentDeviceNameListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数")
    public ResponseJson findEquipmentDeviceNameListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody EquipmentDeviceName equipmentDeviceName){
        List<EquipmentDeviceName> data=equipmentDeviceNameService.findEquipmentDeviceNameListByCondition(equipmentDeviceName);
        return new ResponseJson(data);
    }


    @PostMapping("/saveEquipmentDeviceNameGai")
    @ApiOperation(value = "保存对象", notes = "返回响应对象")
    public ResponseJson saveEquipmentDeviceNameGai(
            @ApiParam(value = "对象", required = true)
            @RequestBody EquipmentDeviceName equipmentDeviceName){
        String s=equipmentDeviceNameService.saveEquipmentDeviceNameGai(equipmentDeviceName);
        return new ResponseJson(s);
    }

    @PostMapping("/update/updateEquipmentDeviceNameGai")
    @ApiOperation(value = "修改对象", notes = "返回响应对象")
    public ResponseJson updateEquipmentDeviceNameGai(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody EquipmentDeviceName equipmentDeviceName){
       String s =  equipmentDeviceNameService.updateEquipmentDeviceNameGai(equipmentDeviceName);
        return new ResponseJson(s);
    }

    @GetMapping("/look/lookEquimentManagementDerviceName/{id}")
    @ApiOperation(value = "查询此设备是否在使用",notes = "返回查询结果")
    public  ResponseJson lookEquimentManagementDerviceName(
            @ApiParam(value = "要查询的id",required =  true)
            @PathVariable String id
    ){
        System.out.println(id);
        EquipmentManagementDerviceName equipmentManagementDerviceName = new EquipmentManagementDerviceName();
        equipmentManagementDerviceName.setDeviceId(id);
        long count =  equipmentManagementDerviceNameService.findEquipmentManagementDerviceNameCountByCondition(equipmentManagementDerviceName);

        return new ResponseJson(count);
    }

}
