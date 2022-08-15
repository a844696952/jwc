package com.yice.edu.cn.yed.feignClient.jw.equipmentType;

import com.yice.edu.cn.common.pojo.jw.equipmentType.EquipmentType;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jw",contextId = "equipmentTypeFeign",path = "/equipmentType")
public interface EquipmentTypeFeign {
    @GetMapping("/findEquipmentTypeById/{id}")
    EquipmentType findEquipmentTypeById(@PathVariable("id") String id);
    @PostMapping("/saveEquipmentType")
    EquipmentType saveEquipmentType(EquipmentType equipmentType);
    @PostMapping("/findEquipmentTypeListByCondition")
    List<EquipmentType> findEquipmentTypeListByCondition(EquipmentType equipmentType);
    @PostMapping("/findOneEquipmentTypeByCondition")
    EquipmentType findOneEquipmentTypeByCondition(EquipmentType equipmentType);
    @PostMapping("/findEquipmentTypeCountByCondition")
    long findEquipmentTypeCountByCondition(EquipmentType equipmentType);
    @PostMapping("/updateEquipmentType")
    void updateEquipmentType(EquipmentType equipmentType);
    @GetMapping("/deleteEquipmentType/{id}")
    void deleteEquipmentType(@PathVariable("id") String id);
    @PostMapping("/deleteEquipmentTypeByCondition")
    void deleteEquipmentTypeByCondition(EquipmentType equipmentType);

    @PostMapping("/saveEquipmentTypeGai")
    String saveEquipmentTypeGai(EquipmentType equipmentType);

    @PostMapping("/updateEquipmentTypeGai")
    String updateEquipmentTypeGai(EquipmentType equipmentType);


    @GetMapping("/deleteEquipmentTypeGai/{id}")
    String deleteEquipmentTypeGai(@PathVariable("id") String id);
}
