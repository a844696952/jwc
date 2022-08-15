package com.yice.edu.cn.jw.dao.equipmentType;

import java.util.List;

import com.yice.edu.cn.common.pojo.jw.equipmentType.EquipmentDeviceName;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface EquipmentDeviceNameDao {
    List<EquipmentDeviceName> findEquipmentDeviceNameListByCondition(EquipmentDeviceName equipmentDeviceName);

    EquipmentDeviceName findOneEquipmentDeviceNameByCondition(EquipmentDeviceName equipmentDeviceName);

    long findEquipmentDeviceNameCountByCondition(EquipmentDeviceName equipmentDeviceName);

    EquipmentDeviceName findEquipmentDeviceNameById(@Param("id") String id);

    void saveEquipmentDeviceName(EquipmentDeviceName equipmentDeviceName);

    void updateEquipmentDeviceName(EquipmentDeviceName equipmentDeviceName);

    void deleteEquipmentDeviceName(@Param("id") String id);

    void deleteEquipmentDeviceNameByCondition(EquipmentDeviceName equipmentDeviceName);

    void batchSaveEquipmentDeviceName(List<EquipmentDeviceName> equipmentDeviceNames);
}
