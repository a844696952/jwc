package com.yice.edu.cn.yed.feignClient.jw.equipmentType;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.equipmentType.EquipmentType;
import com.yice.edu.cn.common.pojo.jw.equipmentType.EquipmentVendorManagement;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jw",contextId = "equipmentVendorManagementFeign",path = "/equipmentVendorManagement")
public interface EquipmentVendorManagementFeign {
    @GetMapping("/findEquipmentVendorManagementById/{id}")
    EquipmentVendorManagement findEquipmentVendorManagementById(@PathVariable("id") String id);
    @PostMapping("/saveEquipmentVendorManagement")
    EquipmentVendorManagement saveEquipmentVendorManagement(EquipmentVendorManagement equipmentVendorManagement);
    @PostMapping("/findEquipmentVendorManagementListByCondition")
    List<EquipmentVendorManagement> findEquipmentVendorManagementListByCondition(EquipmentVendorManagement equipmentVendorManagement);
    @PostMapping("/findOneEquipmentVendorManagementByCondition")
    EquipmentVendorManagement findOneEquipmentVendorManagementByCondition(EquipmentVendorManagement equipmentVendorManagement);
    @PostMapping("/findEquipmentVendorManagementCountByCondition")
    long findEquipmentVendorManagementCountByCondition(EquipmentVendorManagement equipmentVendorManagement);
    @PostMapping("/updateEquipmentVendorManagement")
    void updateEquipmentVendorManagement(EquipmentVendorManagement equipmentVendorManagement);
    @GetMapping("/deleteEquipmentVendorManagement/{id}")
    void deleteEquipmentVendorManagement(@PathVariable("id") String id);
    @PostMapping("/deleteEquipmentVendorManagementByCondition")
    void deleteEquipmentVendorManagementByCondition(EquipmentVendorManagement equipmentVendorManagement);

    @GetMapping("/equipmentVendorManagementsListShu")
    List<EquipmentType> equipmentVendorManagementsListShu();

    @PostMapping("/saveEquipmentVendorManagementGai")
    String saveEquipmentVendorManagementGai(EquipmentVendorManagement equipmentVendorManagement);


    @GetMapping("/findEquipmentVendorManagementByIdGai/{id}")
    ResponseJson findEquipmentVendorManagementByIdGai(@PathVariable("id") String id);

    @PostMapping("/updateEquipmentVendorManagementGai")
    String updateEquipmentVendorManagementGai(EquipmentVendorManagement equipmentVendorManagement);

    @GetMapping("/deleteEquipmentVendorManagementGai/{id}")
    String deleteEquipmentVendorManagementGai(@PathVariable("id") String id);
}
