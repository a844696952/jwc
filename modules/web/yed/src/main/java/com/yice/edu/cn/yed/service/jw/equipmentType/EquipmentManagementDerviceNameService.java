package com.yice.edu.cn.yed.service.jw.equipmentType;

import com.yice.edu.cn.common.pojo.jw.equipmentType.EquipmentManagementDerviceName;
import com.yice.edu.cn.yed.feignClient.jw.equipmentType.EquipmentManagementDerviceNameFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipmentManagementDerviceNameService {
    @Autowired
    private EquipmentManagementDerviceNameFeign equipmentManagementDerviceNameFeign;

    public EquipmentManagementDerviceName findEquipmentManagementDerviceNameById(String id) {
        return equipmentManagementDerviceNameFeign.findEquipmentManagementDerviceNameById(id);
    }

    public EquipmentManagementDerviceName saveEquipmentManagementDerviceName(EquipmentManagementDerviceName equipmentManagementDerviceName) {
        return equipmentManagementDerviceNameFeign.saveEquipmentManagementDerviceName(equipmentManagementDerviceName);
    }

    public List<EquipmentManagementDerviceName> findEquipmentManagementDerviceNameListByCondition(EquipmentManagementDerviceName equipmentManagementDerviceName) {
        return equipmentManagementDerviceNameFeign.findEquipmentManagementDerviceNameListByCondition(equipmentManagementDerviceName);
    }

    public EquipmentManagementDerviceName findOneEquipmentManagementDerviceNameByCondition(EquipmentManagementDerviceName equipmentManagementDerviceName) {
        return equipmentManagementDerviceNameFeign.findOneEquipmentManagementDerviceNameByCondition(equipmentManagementDerviceName);
    }

    public long findEquipmentManagementDerviceNameCountByCondition(EquipmentManagementDerviceName equipmentManagementDerviceName) {
        return equipmentManagementDerviceNameFeign.findEquipmentManagementDerviceNameCountByCondition(equipmentManagementDerviceName);
    }

    public void updateEquipmentManagementDerviceName(EquipmentManagementDerviceName equipmentManagementDerviceName) {
        equipmentManagementDerviceNameFeign.updateEquipmentManagementDerviceName(equipmentManagementDerviceName);
    }

    public void deleteEquipmentManagementDerviceName(String id) {
        equipmentManagementDerviceNameFeign.deleteEquipmentManagementDerviceName(id);
    }

    public void deleteEquipmentManagementDerviceNameByCondition(EquipmentManagementDerviceName equipmentManagementDerviceName) {
        equipmentManagementDerviceNameFeign.deleteEquipmentManagementDerviceNameByCondition(equipmentManagementDerviceName);
    }
}
