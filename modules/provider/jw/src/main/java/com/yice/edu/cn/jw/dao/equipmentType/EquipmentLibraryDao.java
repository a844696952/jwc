package com.yice.edu.cn.jw.dao.equipmentType;

import java.util.List;

import com.yice.edu.cn.common.pojo.jw.equipmentType.EquipmentLibrary;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface EquipmentLibraryDao {
    List<EquipmentLibrary> findEquipmentLibraryListByCondition(EquipmentLibrary equipmentLibrary);

    EquipmentLibrary findOneEquipmentLibraryByCondition(EquipmentLibrary equipmentLibrary);

    long findEquipmentLibraryCountByCondition(EquipmentLibrary equipmentLibrary);

    EquipmentLibrary findEquipmentLibraryById(@Param("id") String id);

    void saveEquipmentLibrary(EquipmentLibrary equipmentLibrary);

    void updateEquipmentLibrary(EquipmentLibrary equipmentLibrary);

    void deleteEquipmentLibrary(@Param("id") String id);

    void deleteEquipmentLibraryByCondition(EquipmentLibrary equipmentLibrary);

    void batchSaveEquipmentLibrary(List<EquipmentLibrary> equipmentLibrarys);

    List<EquipmentLibrary> findEquipmentLibraryListByConditionGai(EquipmentLibrary equipmentLibrary);

    EquipmentLibrary findEquipmentLibraryByIdGai(@Param("id")String id);
}
