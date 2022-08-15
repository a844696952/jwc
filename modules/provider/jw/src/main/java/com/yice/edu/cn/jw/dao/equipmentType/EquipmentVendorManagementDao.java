package com.yice.edu.cn.jw.dao.equipmentType;

import java.util.List;

import com.yice.edu.cn.common.pojo.jw.equipmentType.EquipmentVendorManagement;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface EquipmentVendorManagementDao {
    List<EquipmentVendorManagement> findEquipmentVendorManagementListByCondition(EquipmentVendorManagement equipmentVendorManagement);

    EquipmentVendorManagement findOneEquipmentVendorManagementByCondition(EquipmentVendorManagement equipmentVendorManagement);

    long findEquipmentVendorManagementCountByCondition(EquipmentVendorManagement equipmentVendorManagement);

    EquipmentVendorManagement findEquipmentVendorManagementById(@Param("id") String id);

    void saveEquipmentVendorManagement(EquipmentVendorManagement equipmentVendorManagement);

    void updateEquipmentVendorManagement(EquipmentVendorManagement equipmentVendorManagement);

    void deleteEquipmentVendorManagement(@Param("id") String id);

    void deleteEquipmentVendorManagementByCondition(EquipmentVendorManagement equipmentVendorManagement);

    void batchSaveEquipmentVendorManagement(List<EquipmentVendorManagement> equipmentVendorManagements);

    List<EquipmentVendorManagement> findEquipmentVendorManagementListByConditionGai(EquipmentVendorManagement equipmentVendorManagement);


    EquipmentVendorManagement findEquipmentVendorManagementByIdGai(@Param("id") String id);



}
