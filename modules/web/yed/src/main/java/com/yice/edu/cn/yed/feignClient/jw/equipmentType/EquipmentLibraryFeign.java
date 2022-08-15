package com.yice.edu.cn.yed.feignClient.jw.equipmentType;

import com.yice.edu.cn.common.pojo.jw.equipmentType.EquipmentLibrary;
import com.yice.edu.cn.common.pojo.jw.school.School;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jw",contextId = "equipmentLibraryFeign",path = "/equipmentLibrary")
public interface EquipmentLibraryFeign {
    @GetMapping("/findEquipmentLibraryById/{id}")
    EquipmentLibrary findEquipmentLibraryById(@PathVariable("id") String id);
    @PostMapping("/saveEquipmentLibrary")
    EquipmentLibrary saveEquipmentLibrary(EquipmentLibrary equipmentLibrary);
    @PostMapping("/findEquipmentLibraryListByCondition")
    List<EquipmentLibrary> findEquipmentLibraryListByCondition(EquipmentLibrary equipmentLibrary);
    @PostMapping("/findOneEquipmentLibraryByCondition")
    EquipmentLibrary findOneEquipmentLibraryByCondition(EquipmentLibrary equipmentLibrary);
    @PostMapping("/findEquipmentLibraryCountByCondition")
    long findEquipmentLibraryCountByCondition(EquipmentLibrary equipmentLibrary);
    @PostMapping("/updateEquipmentLibrary")
    void updateEquipmentLibrary(EquipmentLibrary equipmentLibrary);
    @GetMapping("/deleteEquipmentLibrary/{id}")
    void deleteEquipmentLibrary(@PathVariable("id") String id);
    @PostMapping("/deleteEquipmentLibraryByCondition")
    void deleteEquipmentLibraryByCondition(EquipmentLibrary equipmentLibrary);

    @PostMapping("/findSchoolByEquimentLibraryListGai")
    List<School> findSchoolByEquimentLibraryListGai(EquipmentLibrary equipmentLibrary);
}
