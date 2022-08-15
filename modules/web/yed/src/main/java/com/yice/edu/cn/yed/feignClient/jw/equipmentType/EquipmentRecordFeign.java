package com.yice.edu.cn.yed.feignClient.jw.equipmentType;

import com.yice.edu.cn.common.pojo.jw.equipmentType.EquipmentRecord;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jw",contextId = "equipmentRecordFeign",path = "/equipmentRecord")
public interface EquipmentRecordFeign {
    @GetMapping("/findEquipmentRecordById/{id}")
    EquipmentRecord findEquipmentRecordById(@PathVariable("id") String id);
    @PostMapping("/saveEquipmentRecord")
    EquipmentRecord saveEquipmentRecord(EquipmentRecord equipmentRecord);
    @PostMapping("/findEquipmentRecordListByCondition")
    List<EquipmentRecord> findEquipmentRecordListByCondition(EquipmentRecord equipmentRecord);
    @PostMapping("/findOneEquipmentRecordByCondition")
    EquipmentRecord findOneEquipmentRecordByCondition(EquipmentRecord equipmentRecord);
    @PostMapping("/findEquipmentRecordCountByCondition")
    long findEquipmentRecordCountByCondition(EquipmentRecord equipmentRecord);
    @PostMapping("/updateEquipmentRecord")
    void updateEquipmentRecord(EquipmentRecord equipmentRecord);
    @GetMapping("/deleteEquipmentRecord/{id}")
    void deleteEquipmentRecord(@PathVariable("id") String id);
    @PostMapping("/deleteEquipmentRecordByCondition")
    void deleteEquipmentRecordByCondition(EquipmentRecord equipmentRecord);
}
