package com.yice.edu.cn.jw.dao.equipmentType;

import java.util.List;

import com.yice.edu.cn.common.pojo.jw.equipmentType.EquipmentRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface EquipmentRecordDao {
    List<EquipmentRecord> findEquipmentRecordListByCondition(EquipmentRecord equipmentRecord);

    EquipmentRecord findOneEquipmentRecordByCondition(EquipmentRecord equipmentRecord);

    long findEquipmentRecordCountByCondition(EquipmentRecord equipmentRecord);

    EquipmentRecord findEquipmentRecordById(@Param("id") String id);

    void saveEquipmentRecord(EquipmentRecord equipmentRecord);

    void updateEquipmentRecord(EquipmentRecord equipmentRecord);

    void deleteEquipmentRecord(@Param("id") String id);

    void deleteEquipmentRecordByCondition(EquipmentRecord equipmentRecord);

    void batchSaveEquipmentRecord(List<EquipmentRecord> equipmentRecords);
}
