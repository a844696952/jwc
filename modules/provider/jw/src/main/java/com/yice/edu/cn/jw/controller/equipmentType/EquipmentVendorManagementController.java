package com.yice.edu.cn.jw.controller.equipmentType;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.equipmentType.EquipmentType;
import com.yice.edu.cn.common.pojo.jw.equipmentType.EquipmentVendorManagement;
import com.yice.edu.cn.jw.service.equipmentType.EquipmentVendorManagementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/equipmentVendorManagement")
@Api(value = "/equipmentVendorManagement",description = "模块")
public class EquipmentVendorManagementController {
    @Autowired
    private EquipmentVendorManagementService equipmentVendorManagementService;

    @GetMapping("/findEquipmentVendorManagementById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public EquipmentVendorManagement findEquipmentVendorManagementById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return equipmentVendorManagementService.findEquipmentVendorManagementById(id);
    }

    @PostMapping("/saveEquipmentVendorManagement")
    @ApiOperation(value = "保存", notes = "返回对象")
    public EquipmentVendorManagement saveEquipmentVendorManagement(
            @ApiParam(value = "对象", required = true)
            @RequestBody EquipmentVendorManagement equipmentVendorManagement){
        equipmentVendorManagementService.saveEquipmentVendorManagement(equipmentVendorManagement);
        return equipmentVendorManagement;
    }

    @PostMapping("/findEquipmentVendorManagementListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<EquipmentVendorManagement> findEquipmentVendorManagementListByCondition(
            @ApiParam(value = "对象")
            @RequestBody EquipmentVendorManagement equipmentVendorManagement){
        return equipmentVendorManagementService.findEquipmentVendorManagementListByCondition(equipmentVendorManagement);
    }
    @PostMapping("/findEquipmentVendorManagementCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findEquipmentVendorManagementCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody EquipmentVendorManagement equipmentVendorManagement){
        return equipmentVendorManagementService.findEquipmentVendorManagementCountByCondition(equipmentVendorManagement);
    }

    @PostMapping("/updateEquipmentVendorManagement")
    @ApiOperation(value = "修改", notes = "对象必传")
    public void updateEquipmentVendorManagement(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody EquipmentVendorManagement equipmentVendorManagement){
        equipmentVendorManagementService.updateEquipmentVendorManagement(equipmentVendorManagement);
    }

    @GetMapping("/deleteEquipmentVendorManagement/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteEquipmentVendorManagement(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        equipmentVendorManagementService.deleteEquipmentVendorManagement(id);
    }
    @PostMapping("/deleteEquipmentVendorManagementByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deleteEquipmentVendorManagementByCondition(
            @ApiParam(value = "对象")
            @RequestBody EquipmentVendorManagement equipmentVendorManagement){
        equipmentVendorManagementService.deleteEquipmentVendorManagementByCondition(equipmentVendorManagement);
    }
    @PostMapping("/findOneEquipmentVendorManagementByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public EquipmentVendorManagement findOneEquipmentVendorManagementByCondition(
            @ApiParam(value = "对象")
            @RequestBody EquipmentVendorManagement equipmentVendorManagement){
        return equipmentVendorManagementService.findOneEquipmentVendorManagementByCondition(equipmentVendorManagement);
    }

    @GetMapping("/equipmentVendorManagementsListShu")
    @ApiOperation(value = "不用传值",notes = "返回多个")
    public  List<EquipmentType> equipmentVendorManagementsListShu(){
        return  equipmentVendorManagementService.equipmentVendorManagementsListShu();
    }

    @PostMapping("/saveEquipmentVendorManagementGai")
    @ApiOperation(value = "保存", notes = "返回对象")
    public String saveEquipmentVendorManagementGai(
            @ApiParam(value = "对象", required = true)
            @RequestBody EquipmentVendorManagement equipmentVendorManagement){
        return equipmentVendorManagementService.saveEquipmentVendorManagementGai(equipmentVendorManagement);
    }


    @GetMapping("/findEquipmentVendorManagementByIdGai/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public ResponseJson findEquipmentVendorManagementByIdGai(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return equipmentVendorManagementService.findEquipmentVendorManagementByIdGai(id);
    }

    @PostMapping("/updateEquipmentVendorManagementGai")
    @ApiOperation(value = "修改", notes = "对象必传")
    public String updateEquipmentVendorManagementGai(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody EquipmentVendorManagement equipmentVendorManagement){
        return equipmentVendorManagementService.updateEquipmentVendorManagementGai(equipmentVendorManagement);
    }

    @GetMapping("/deleteEquipmentVendorManagementGai/{id}")
    @ApiOperation(value = "通过id删除")
    public String deleteEquipmentVendorManagementGai(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        return equipmentVendorManagementService.deleteEquipmentVendorManagementGai(id);
    }

}
