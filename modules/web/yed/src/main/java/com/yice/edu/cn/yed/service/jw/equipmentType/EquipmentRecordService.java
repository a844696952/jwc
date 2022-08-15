package com.yice.edu.cn.yed.service.jw.equipmentType;

import com.yice.edu.cn.common.pojo.jw.equipmentType.EquipmentRecord;
import com.yice.edu.cn.yed.feignClient.jw.equipmentType.EquipmentRecordFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipmentRecordService {
    @Autowired
    private EquipmentRecordFeign equipmentRecordFeign;

    public EquipmentRecord findEquipmentRecordById(String id) {
        return equipmentRecordFeign.findEquipmentRecordById(id);
    }

    public EquipmentRecord saveEquipmentRecord(EquipmentRecord equipmentRecord) {
        return equipmentRecordFeign.saveEquipmentRecord(equipmentRecord);
    }

    public List<EquipmentRecord> findEquipmentRecordListByCondition(EquipmentRecord equipmentRecord) {
        return equipmentRecordFeign.findEquipmentRecordListByCondition(equipmentRecord);
    }

    public EquipmentRecord findOneEquipmentRecordByCondition(EquipmentRecord equipmentRecord) {
        return equipmentRecordFeign.findOneEquipmentRecordByCondition(equipmentRecord);
    }

    public long findEquipmentRecordCountByCondition(EquipmentRecord equipmentRecord) {
        return equipmentRecordFeign.findEquipmentRecordCountByCondition(equipmentRecord);
    }

    public void updateEquipmentRecord(EquipmentRecord equipmentRecord) {
        equipmentRecordFeign.updateEquipmentRecord(equipmentRecord);
    }

    public void deleteEquipmentRecord(String id) {
        equipmentRecordFeign.deleteEquipmentRecord(id);
    }

    public void deleteEquipmentRecordByCondition(EquipmentRecord equipmentRecord) {
        equipmentRecordFeign.deleteEquipmentRecordByCondition(equipmentRecord);
    }
}
