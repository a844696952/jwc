package com.yice.edu.cn.yed.service.jw.equipmentType;

import com.yice.edu.cn.common.pojo.jw.equipmentType.EquipmentLibrary;
import com.yice.edu.cn.common.pojo.jw.school.School;
import com.yice.edu.cn.yed.feignClient.jw.equipmentType.EquipmentLibraryFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipmentLibraryService {
    @Autowired
    private EquipmentLibraryFeign equipmentLibraryFeign;

    public EquipmentLibrary findEquipmentLibraryById(String id) {
        return equipmentLibraryFeign.findEquipmentLibraryById(id);
    }

    public EquipmentLibrary saveEquipmentLibrary(EquipmentLibrary equipmentLibrary) {
        return equipmentLibraryFeign.saveEquipmentLibrary(equipmentLibrary);
    }

    public List<EquipmentLibrary> findEquipmentLibraryListByCondition(EquipmentLibrary equipmentLibrary) {
        return equipmentLibraryFeign.findEquipmentLibraryListByCondition(equipmentLibrary);
    }

    public EquipmentLibrary findOneEquipmentLibraryByCondition(EquipmentLibrary equipmentLibrary) {
        return equipmentLibraryFeign.findOneEquipmentLibraryByCondition(equipmentLibrary);
    }

    public long findEquipmentLibraryCountByCondition(EquipmentLibrary equipmentLibrary) {
        return equipmentLibraryFeign.findEquipmentLibraryCountByCondition(equipmentLibrary);
    }

    public void updateEquipmentLibrary(EquipmentLibrary equipmentLibrary) {
        equipmentLibraryFeign.updateEquipmentLibrary(equipmentLibrary);
    }

    public void deleteEquipmentLibrary(String id) {
        equipmentLibraryFeign.deleteEquipmentLibrary(id);
    }

    public void deleteEquipmentLibraryByCondition(EquipmentLibrary equipmentLibrary) {
        equipmentLibraryFeign.deleteEquipmentLibraryByCondition(equipmentLibrary);
    }

    public List<School> findSchoolByEquimentLibraryListGai(EquipmentLibrary equipmentLibrary){
        return equipmentLibraryFeign.findSchoolByEquimentLibraryListGai(equipmentLibrary);
    }
}
