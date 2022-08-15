package com.yice.edu.cn.jw.dao.equipmentType;

import java.util.List;

import com.yice.edu.cn.common.pojo.jw.equipmentType.EquipmentManagementDerviceName;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface EquipmentManagementDerviceNameDao {
    List<EquipmentManagementDerviceName> findEquipmentManagementDerviceNameListByCondition(EquipmentManagementDerviceName equipmentManagementDerviceName);

    EquipmentManagementDerviceName findOneEquipmentManagementDerviceNameByCondition(EquipmentManagementDerviceName equipmentManagementDerviceName);

    long findEquipmentManagementDerviceNameCountByCondition(EquipmentManagementDerviceName equipmentManagementDerviceName);

    EquipmentManagementDerviceName findEquipmentManagementDerviceNameById(@Param("id") String id);

    void saveEquipmentManagementDerviceName(EquipmentManagementDerviceName equipmentManagementDerviceName);

    void updateEquipmentManagementDerviceName(EquipmentManagementDerviceName equipmentManagementDerviceName);

    void deleteEquipmentManagementDerviceName(@Param("id") String id);

    void deleteEquipmentManagementDerviceNameByCondition(EquipmentManagementDerviceName equipmentManagementDerviceName);

    void batchSaveEquipmentManagementDerviceName(List<EquipmentManagementDerviceName> equipmentManagementDerviceNames);
}
