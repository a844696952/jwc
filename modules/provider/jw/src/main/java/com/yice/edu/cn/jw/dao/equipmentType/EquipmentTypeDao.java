package com.yice.edu.cn.jw.dao.equipmentType;

import java.util.List;

import com.yice.edu.cn.common.pojo.jw.equipmentType.EquipmentType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface EquipmentTypeDao {
    List<EquipmentType> findEquipmentTypeListByCondition(EquipmentType equipmentType);

    EquipmentType findOneEquipmentTypeByCondition(EquipmentType equipmentType);

    long findEquipmentTypeCountByCondition(EquipmentType equipmentType);

    EquipmentType findEquipmentTypeById(@Param("id") String id);

    void saveEquipmentType(EquipmentType equipmentType);

    void updateEquipmentType(EquipmentType equipmentType);

    void deleteEquipmentType(@Param("id") String id);

    void deleteEquipmentTypeByCondition(EquipmentType equipmentType);

    void batchSaveEquipmentType(List<EquipmentType> equipmentTypes);
}
