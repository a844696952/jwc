package com.yice.edu.cn.yed.service.jw.equipmentType;

import com.yice.edu.cn.common.pojo.jw.equipmentType.EquipmentDeviceName;
import com.yice.edu.cn.yed.feignClient.jw.equipmentType.EquipmentDeviceNameFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipmentDeviceNameService {
    @Autowired
    private EquipmentDeviceNameFeign equipmentDeviceNameFeign;

    public EquipmentDeviceName findEquipmentDeviceNameById(String id) {
        return equipmentDeviceNameFeign.findEquipmentDeviceNameById(id);
    }

    public EquipmentDeviceName saveEquipmentDeviceName(EquipmentDeviceName equipmentDeviceName) {
        return equipmentDeviceNameFeign.saveEquipmentDeviceName(equipmentDeviceName);
    }

    public List<EquipmentDeviceName> findEquipmentDeviceNameListByCondition(EquipmentDeviceName equipmentDeviceName) {
        return equipmentDeviceNameFeign.findEquipmentDeviceNameListByCondition(equipmentDeviceName);
    }

    public EquipmentDeviceName findOneEquipmentDeviceNameByCondition(EquipmentDeviceName equipmentDeviceName) {
        return equipmentDeviceNameFeign.findOneEquipmentDeviceNameByCondition(equipmentDeviceName);
    }

    public long findEquipmentDeviceNameCountByCondition(EquipmentDeviceName equipmentDeviceName) {
        return equipmentDeviceNameFeign.findEquipmentDeviceNameCountByCondition(equipmentDeviceName);
    }

    public void updateEquipmentDeviceName(EquipmentDeviceName equipmentDeviceName) {
        equipmentDeviceNameFeign.updateEquipmentDeviceName(equipmentDeviceName);
    }

    public void deleteEquipmentDeviceName(String id) {
        equipmentDeviceNameFeign.deleteEquipmentDeviceName(id);
    }

    public void deleteEquipmentDeviceNameByCondition(EquipmentDeviceName equipmentDeviceName) {
        equipmentDeviceNameFeign.deleteEquipmentDeviceNameByCondition(equipmentDeviceName);
    }

    public String saveEquipmentDeviceNameGai(EquipmentDeviceName equipmentDeviceName) {
        return equipmentDeviceNameFeign.saveEquipmentDeviceNameGai(equipmentDeviceName);
    }

    public String updateEquipmentDeviceNameGai(EquipmentDeviceName equipmentDeviceName) {
        return equipmentDeviceNameFeign.updateEquipmentDeviceNameGai(equipmentDeviceName);
    }

}
