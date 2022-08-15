package com.yice.edu.cn.jw.controller.equipmentType;

import com.yice.edu.cn.common.pojo.jw.equipmentType.EquipmentLibrary;
import com.yice.edu.cn.common.pojo.jw.school.School;
import com.yice.edu.cn.jw.service.equipmentType.EquipmentLibraryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/equipmentLibrary")
@Api(value = "/equipmentLibrary",description = "模块")
public class EquipmentLibraryController {
    @Autowired
    private EquipmentLibraryService equipmentLibraryService;

    @GetMapping("/findEquipmentLibraryById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public EquipmentLibrary findEquipmentLibraryById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return equipmentLibraryService.findEquipmentLibraryById(id);
    }

    @PostMapping("/saveEquipmentLibrary")
    @ApiOperation(value = "保存", notes = "返回对象")
    public EquipmentLibrary saveEquipmentLibrary(
            @ApiParam(value = "对象", required = true)
            @RequestBody EquipmentLibrary equipmentLibrary){
        equipmentLibraryService.saveEquipmentLibrary(equipmentLibrary);
        return equipmentLibrary;
    }

    @PostMapping("/findEquipmentLibraryListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<EquipmentLibrary> findEquipmentLibraryListByCondition(
            @ApiParam(value = "对象")
            @RequestBody EquipmentLibrary equipmentLibrary){
        return equipmentLibraryService.findEquipmentLibraryListByCondition(equipmentLibrary);
    }
    @PostMapping("/findEquipmentLibraryCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findEquipmentLibraryCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody EquipmentLibrary equipmentLibrary){
        return equipmentLibraryService.findEquipmentLibraryCountByCondition(equipmentLibrary);
    }

    @PostMapping("/updateEquipmentLibrary")
    @ApiOperation(value = "修改", notes = "对象必传")
    public void updateEquipmentLibrary(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody EquipmentLibrary equipmentLibrary){
        equipmentLibraryService.updateEquipmentLibrary(equipmentLibrary);
    }

    @GetMapping("/deleteEquipmentLibrary/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteEquipmentLibrary(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        equipmentLibraryService.deleteEquipmentLibrary(id);
    }
    @PostMapping("/deleteEquipmentLibraryByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deleteEquipmentLibraryByCondition(
            @ApiParam(value = "对象")
            @RequestBody EquipmentLibrary equipmentLibrary){
        equipmentLibraryService.deleteEquipmentLibraryByCondition(equipmentLibrary);
    }
    @PostMapping("/findOneEquipmentLibraryByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public EquipmentLibrary findOneEquipmentLibraryByCondition(
            @ApiParam(value = "对象")
            @RequestBody EquipmentLibrary equipmentLibrary){
        return equipmentLibraryService.findOneEquipmentLibraryByCondition(equipmentLibrary);
    }

    @PostMapping("/findSchoolByEquimentLibraryListGai")
    @ApiOperation(value ="根据条件查询学校",notes = "返回数据列表")
    public List<School> findSchoolByEquimentLibraryListGai(
            @ApiParam(value = "对象")
            @RequestBody EquipmentLibrary equipmentLibrary
    ){
        return equipmentLibraryService.findSchoolByEquimentLibraryListGai(equipmentLibrary);
    }
}
