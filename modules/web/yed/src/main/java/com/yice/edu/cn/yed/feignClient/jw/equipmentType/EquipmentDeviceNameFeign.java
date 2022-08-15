package com.yice.edu.cn.yed.feignClient.jw.equipmentType;

import com.yice.edu.cn.common.pojo.jw.equipmentType.EquipmentDeviceName;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jw",contextId = "equipmentDeviceNameFeign",path = "/equipmentDeviceName")
public interface EquipmentDeviceNameFeign {
    @GetMapping("/findEquipmentDeviceNameById/{id}")
    EquipmentDeviceName findEquipmentDeviceNameById(@PathVariable("id") String id);
    @PostMapping("/saveEquipmentDeviceName")
    EquipmentDeviceName saveEquipmentDeviceName(EquipmentDeviceName equipmentDeviceName);
    @PostMapping("/findEquipmentDeviceNameListByCondition")
    List<EquipmentDeviceName> findEquipmentDeviceNameListByCondition(EquipmentDeviceName equipmentDeviceName);
    @PostMapping("/findOneEquipmentDeviceNameByCondition")
    EquipmentDeviceName findOneEquipmentDeviceNameByCondition(EquipmentDeviceName equipmentDeviceName);
    @PostMapping("/findEquipmentDeviceNameCountByCondition")
    long findEquipmentDeviceNameCountByCondition(EquipmentDeviceName equipmentDeviceName);
    @PostMapping("/updateEquipmentDeviceName")
    void updateEquipmentDeviceName(EquipmentDeviceName equipmentDeviceName);
    @GetMapping("/deleteEquipmentDeviceName/{id}")
    void deleteEquipmentDeviceName(@PathVariable("id") String id);
    @PostMapping("/deleteEquipmentDeviceNameByCondition")
    void deleteEquipmentDeviceNameByCondition(EquipmentDeviceName equipmentDeviceName);

    @PostMapping("/saveEquipmentDeviceNameGai")
    String saveEquipmentDeviceNameGai(EquipmentDeviceName equipmentDeviceName);

    @PostMapping("/updateEquipmentDeviceNameGai")
    String updateEquipmentDeviceNameGai(EquipmentDeviceName equipmentDeviceName);

}
