package com.yice.edu.cn.yed.service.jw.equipmentType;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.equipmentType.EquipmentType;
import com.yice.edu.cn.common.pojo.jw.equipmentType.EquipmentVendorManagement;
import com.yice.edu.cn.yed.feignClient.jw.equipmentType.EquipmentVendorManagementFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipmentVendorManagementService {
    @Autowired
    private EquipmentVendorManagementFeign equipmentVendorManagementFeign;

    public EquipmentVendorManagement findEquipmentVendorManagementById(String id) {
        return equipmentVendorManagementFeign.findEquipmentVendorManagementById(id);
    }

    public EquipmentVendorManagement saveEquipmentVendorManagement(EquipmentVendorManagement equipmentVendorManagement) {
        return equipmentVendorManagementFeign.saveEquipmentVendorManagement(equipmentVendorManagement);
    }

    public List<EquipmentVendorManagement> findEquipmentVendorManagementListByCondition(EquipmentVendorManagement equipmentVendorManagement) {
        return equipmentVendorManagementFeign.findEquipmentVendorManagementListByCondition(equipmentVendorManagement);
    }

    public EquipmentVendorManagement findOneEquipmentVendorManagementByCondition(EquipmentVendorManagement equipmentVendorManagement) {
        return equipmentVendorManagementFeign.findOneEquipmentVendorManagementByCondition(equipmentVendorManagement);
    }

    public long findEquipmentVendorManagementCountByCondition(EquipmentVendorManagement equipmentVendorManagement) {
        return equipmentVendorManagementFeign.findEquipmentVendorManagementCountByCondition(equipmentVendorManagement);
    }

    public void updateEquipmentVendorManagement(EquipmentVendorManagement equipmentVendorManagement) {
        equipmentVendorManagementFeign.updateEquipmentVendorManagement(equipmentVendorManagement);
    }

    public void deleteEquipmentVendorManagement(String id) {
        equipmentVendorManagementFeign.deleteEquipmentVendorManagement(id);
    }

    public void deleteEquipmentVendorManagementByCondition(EquipmentVendorManagement equipmentVendorManagement) {
        equipmentVendorManagementFeign.deleteEquipmentVendorManagementByCondition(equipmentVendorManagement);
    }

    public List<EquipmentType> equipmentVendorManagementsListShu(){
        return  equipmentVendorManagementFeign.equipmentVendorManagementsListShu();
    }

    public String saveEquipmentVendorManagementGai(EquipmentVendorManagement equipmentVendorManagement) {
        return equipmentVendorManagementFeign.saveEquipmentVendorManagementGai(equipmentVendorManagement);
    }

    public ResponseJson findEquipmentVendorManagementByIdGai(String id) {
        return equipmentVendorManagementFeign.findEquipmentVendorManagementByIdGai(id);
    }

    public String updateEquipmentVendorManagementGai(EquipmentVendorManagement equipmentVendorManagement){
        return equipmentVendorManagementFeign.updateEquipmentVendorManagementGai(equipmentVendorManagement);
    }

    public  String deleteEquipmentVendorManagementGai(String id){
       return equipmentVendorManagementFeign.deleteEquipmentVendorManagementGai(id);
    }
}
