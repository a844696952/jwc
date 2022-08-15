package com.yice.edu.cn.yed.controller.jw.equipmentType;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.equipmentType.EquipmentLibrary;
import com.yice.edu.cn.common.pojo.jw.equipmentType.EquipmentVendorManagement;
import com.yice.edu.cn.common.pojo.jw.school.School;
import com.yice.edu.cn.yed.service.jw.equipmentType.EquipmentLibraryService;
import com.yice.edu.cn.yed.service.jw.equipmentType.EquipmentVendorManagementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/equipmentLibrary")
@Api(value = "/equipmentLibrary",description = "模块")
public class EquipmentLibraryController {
    @Autowired
    private EquipmentLibraryService equipmentLibraryService;

    @Autowired
    private EquipmentVendorManagementService equipmentVendorManagementService;

    @PostMapping("/saveEquipmentLibrary")
    @ApiOperation(value = "保存对象", notes = "返回响应对象")
    public ResponseJson saveEquipmentLibrary(
            @ApiParam(value = "对象", required = true)
            @RequestBody EquipmentLibrary equipmentLibrary){
        EquipmentLibrary s=equipmentLibraryService.saveEquipmentLibrary(equipmentLibrary);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findEquipmentLibraryById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象")
    public ResponseJson findEquipmentLibraryById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        EquipmentLibrary equipmentLibrary=equipmentLibraryService.findEquipmentLibraryById(id);
        return new ResponseJson(equipmentLibrary);
    }

    @PostMapping("/update/updateEquipmentLibrary")
    @ApiOperation(value = "修改对象", notes = "返回响应对象")
    public ResponseJson updateEquipmentLibrary(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody EquipmentLibrary equipmentLibrary){
        equipmentLibraryService.updateEquipmentLibrary(equipmentLibrary);
        return new ResponseJson();
    }

    @GetMapping("/look/lookEquipmentLibraryById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象")
    public ResponseJson lookEquipmentLibraryById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        EquipmentLibrary equipmentLibrary=equipmentLibraryService.findEquipmentLibraryById(id);
        return new ResponseJson(equipmentLibrary);
    }

    @PostMapping("/findEquipmentLibrarysByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象")
    public ResponseJson findEquipmentLibrarysByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody EquipmentLibrary equipmentLibrary){
        List<EquipmentLibrary> data=equipmentLibraryService.findEquipmentLibraryListByCondition(equipmentLibrary);
        long count=equipmentLibraryService.findEquipmentLibraryCountByCondition(equipmentLibrary);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneEquipmentLibraryByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空")
    public ResponseJson findOneEquipmentLibraryByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody EquipmentLibrary equipmentLibrary){
        EquipmentLibrary one=equipmentLibraryService.findOneEquipmentLibraryByCondition(equipmentLibrary);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteEquipmentLibrary/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteEquipmentLibrary(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        equipmentLibraryService.deleteEquipmentLibrary(id);
        return new ResponseJson();
    }


    @PostMapping("/findEquipmentLibraryListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数")
    public ResponseJson findEquipmentLibraryListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody EquipmentLibrary equipmentLibrary){
        List<EquipmentLibrary> data=equipmentLibraryService.findEquipmentLibraryListByCondition(equipmentLibrary);
        return new ResponseJson(data);
    }

    @GetMapping("look/findVendorAndDeviceList")
    @ApiOperation(value = "查询出所有的厂商名称",notes = "返回所有")
    public  ResponseJson findVendorAndDeviceList(){
        EquipmentVendorManagement e = new EquipmentVendorManagement();
        List<EquipmentVendorManagement> list = equipmentVendorManagementService.findEquipmentVendorManagementListByCondition(e);
        list.forEach(f->{
            List<EquipmentVendorManagement> list1 = new ArrayList<>();
            if(f.getDeviceId()!=null&&f.getDeviceName()!=null){
                String[] s = f.getDeviceId().split(",");
                String[] fx = f.getDeviceName().split(",");
                for(int i=0;i<s.length;i++){
                    EquipmentVendorManagement equipmentVendorManagement = new EquipmentVendorManagement();
                    equipmentVendorManagement.setDeviceName(fx[i]);
                    equipmentVendorManagement.setDeviceId(s[i]);
                    list1.add(equipmentVendorManagement);
                }
                f.setChilren(list1);
            }
        });

        return  new ResponseJson(list);
    }

    @PostMapping("look/findSchoolByEquimentLibraryListGai")
    @ApiOperation(value = "根据条件查找列表",notes = "返回符合条件的列表")
    public ResponseJson findSchoolByEquimentLibraryListGai(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody EquipmentLibrary equipmentLibrary
    ){
        List<School> schools = equipmentLibraryService.findSchoolByEquimentLibraryListGai(equipmentLibrary);
        return  new ResponseJson(schools);
    }


}
