package com.yice.edu.cn.yed.service.jw.equipmentType;

import com.yice.edu.cn.common.pojo.jw.equipmentType.EquipmentType;
import com.yice.edu.cn.yed.feignClient.jw.equipmentType.EquipmentTypeFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipmentTypeService {
    @Autowired
    private EquipmentTypeFeign equipmentTypeFeign;

    public EquipmentType findEquipmentTypeById(String id) {
        return equipmentTypeFeign.findEquipmentTypeById(id);
    }

    public EquipmentType saveEquipmentType(EquipmentType equipmentType) {
        return equipmentTypeFeign.saveEquipmentType(equipmentType);
    }

    public List<EquipmentType> findEquipmentTypeListByCondition(EquipmentType equipmentType) {
        return equipmentTypeFeign.findEquipmentTypeListByCondition(equipmentType);
    }

    public EquipmentType findOneEquipmentTypeByCondition(EquipmentType equipmentType) {
        return equipmentTypeFeign.findOneEquipmentTypeByCondition(equipmentType);
    }

    public long findEquipmentTypeCountByCondition(EquipmentType equipmentType) {
        return equipmentTypeFeign.findEquipmentTypeCountByCondition(equipmentType);
    }

    public void updateEquipmentType(EquipmentType equipmentType) {
        equipmentTypeFeign.updateEquipmentType(equipmentType);
    }

    public void deleteEquipmentType(String id) {
        equipmentTypeFeign.deleteEquipmentType(id);
    }

    public void deleteEquipmentTypeByCondition(EquipmentType equipmentType) {
        equipmentTypeFeign.deleteEquipmentTypeByCondition(equipmentType);
    }


    public String saveEquipmentTypeGai(EquipmentType equipmentType) {
        return equipmentTypeFeign.saveEquipmentTypeGai(equipmentType);
    }

    public String updateEquipmentTypeGai(EquipmentType equipmentType) {
        return equipmentTypeFeign.updateEquipmentTypeGai(equipmentType);
    }

    public String deleteEquipmentTypeGai(String id){
        return equipmentTypeFeign.deleteEquipmentTypeGai(id);
    }
}
