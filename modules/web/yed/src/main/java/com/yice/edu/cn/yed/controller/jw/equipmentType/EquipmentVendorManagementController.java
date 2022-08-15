package com.yice.edu.cn.yed.controller.jw.equipmentType;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.equipmentType.EquipmentType;
import com.yice.edu.cn.common.pojo.jw.equipmentType.EquipmentVendorManagement;
import com.yice.edu.cn.yed.service.jw.equipmentType.EquipmentVendorManagementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@RestController
@RequestMapping("/equipmentVendorManagement")
@Api(value = "/equipmentVendorManagement",description = "模块")
public class EquipmentVendorManagementController {
    @Autowired
    private EquipmentVendorManagementService equipmentVendorManagementService;

    //提交方法已修改
    @PostMapping("/saveEquipmentVendorManagement")
    @ApiOperation(value = "保存对象", notes = "返回响应对象")
    public ResponseJson saveEquipmentVendorManagement(
            @ApiParam(value = "对象", required = true)
            @RequestBody EquipmentVendorManagement equipmentVendorManagement){
        String s=equipmentVendorManagementService.saveEquipmentVendorManagementGai(equipmentVendorManagement);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findEquipmentVendorManagementById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象")
    public ResponseJson findEquipmentVendorManagementById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        EquipmentVendorManagement equipmentVendorManagement=equipmentVendorManagementService.findEquipmentVendorManagementById(id);
        return new ResponseJson(equipmentVendorManagement);
    }

    @PostMapping("/update/updateEquipmentVendorManagement")
    @ApiOperation(value = "修改对象", notes = "返回响应对象")
    public ResponseJson updateEquipmentVendorManagement(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody EquipmentVendorManagement equipmentVendorManagement){
        equipmentVendorManagementService.updateEquipmentVendorManagement(equipmentVendorManagement);
        return new ResponseJson();
    }

    @GetMapping("/look/lookEquipmentVendorManagementById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象")
    public ResponseJson lookEquipmentVendorManagementById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        EquipmentVendorManagement equipmentVendorManagement=equipmentVendorManagementService.findEquipmentVendorManagementById(id);
        return new ResponseJson(equipmentVendorManagement);
    }

    @PostMapping("/findEquipmentVendorManagementsByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象")
    public ResponseJson findEquipmentVendorManagementsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody EquipmentVendorManagement equipmentVendorManagement){
        List<EquipmentVendorManagement> data=equipmentVendorManagementService.findEquipmentVendorManagementListByCondition(equipmentVendorManagement);
        long count=equipmentVendorManagementService.findEquipmentVendorManagementCountByCondition(equipmentVendorManagement);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneEquipmentVendorManagementByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空")
    public ResponseJson findOneEquipmentVendorManagementByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody EquipmentVendorManagement equipmentVendorManagement){
        EquipmentVendorManagement one=equipmentVendorManagementService.findOneEquipmentVendorManagementByCondition(equipmentVendorManagement);
        return new ResponseJson(one);
    }

    //删除方法已修改
    @GetMapping("/deleteEquipmentVendorManagement/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteEquipmentVendorManagement(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        String count = equipmentVendorManagementService.deleteEquipmentVendorManagementGai(id);
        return new ResponseJson(count);
    }


    @PostMapping("/findEquipmentVendorManagementListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数")
    public ResponseJson findEquipmentVendorManagementListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody EquipmentVendorManagement equipmentVendorManagement){
        List<EquipmentVendorManagement> data=equipmentVendorManagementService.findEquipmentVendorManagementListByCondition(equipmentVendorManagement);
        return new ResponseJson(data);
    }


    @GetMapping("/update/equipmentVendorManagementsListShu")
    @ApiOperation(value = "不用传值",notes = "返回多条记录")
    public  ResponseJson equipmentVendorManagementsListShu(){
        List<EquipmentType> equipmentTypeList = equipmentVendorManagementService.equipmentVendorManagementsListShu();
        return  new ResponseJson(equipmentTypeList);
    }


    @GetMapping("/update/findEquipmentVendorManagementByIdGai/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象")
    public ResponseJson findEquipmentVendorManagementByIdGai(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        return equipmentVendorManagementService.findEquipmentVendorManagementByIdGai(id);
    }

    @PostMapping("/update/updateEquipmentVendorManagementGai")
    @ApiOperation(value = "修改对象", notes = "返回响应对象")
    public ResponseJson updateEquipmentVendorManagementGai(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody EquipmentVendorManagement equipmentVendorManagement){
        String s = equipmentVendorManagementService.updateEquipmentVendorManagementGai(equipmentVendorManagement);
        return new ResponseJson(s);
    }

}