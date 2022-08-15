package com.yice.edu.cn.yed.feignClient.jw.equipmentType;

import com.yice.edu.cn.common.pojo.jw.equipmentType.EquipmentManagementDerviceName;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jw",contextId = "equipmentManagementDerviceNameFeign",path = "/equipmentManagementDerviceName")
public interface EquipmentManagementDerviceNameFeign {
    @GetMapping("/findEquipmentManagementDerviceNameById/{id}")
    EquipmentManagementDerviceName findEquipmentManagementDerviceNameById(@PathVariable("id") String id);
    @PostMapping("/saveEquipmentManagementDerviceName")
    EquipmentManagementDerviceName saveEquipmentManagementDerviceName(EquipmentManagementDerviceName equipmentManagementDerviceName);
    @PostMapping("/findEquipmentManagementDerviceNameListByCondition")
    List<EquipmentManagementDerviceName> findEquipmentManagementDerviceNameListByCondition(EquipmentManagementDerviceName equipmentManagementDerviceName);
    @PostMapping("/findOneEquipmentManagementDerviceNameByCondition")
    EquipmentManagementDerviceName findOneEquipmentManagementDerviceNameByCondition(EquipmentManagementDerviceName equipmentManagementDerviceName);
    @PostMapping("/findEquipmentManagementDerviceNameCountByCondition")
    long findEquipmentManagementDerviceNameCountByCondition(EquipmentManagementDerviceName equipmentManagementDerviceName);
    @PostMapping("/updateEquipmentManagementDerviceName")
    void updateEquipmentManagementDerviceName(EquipmentManagementDerviceName equipmentManagementDerviceName);
    @GetMapping("/deleteEquipmentManagementDerviceName/{id}")
    void deleteEquipmentManagementDerviceName(@PathVariable("id") String id);
    @PostMapping("/deleteEquipmentManagementDerviceNameByCondition")
    void deleteEquipmentManagementDerviceNameByCondition(EquipmentManagementDerviceName equipmentManagementDerviceName);
}
