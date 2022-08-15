package com.yice.edu.cn.jw.service.equipmentType;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.jw.equipmentType.EquipmentRecord;
import com.yice.edu.cn.jw.dao.equipmentType.EquipmentRecordDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EquipmentRecordService {
    @Autowired
    private EquipmentRecordDao equipmentRecordDao;

    @Autowired
    private EquipmentVendorManagementService equipmentVendorManagementService;
    @Autowired
    private SequenceId sequenceId;

    @Transactional(readOnly = true)
    public EquipmentRecord findEquipmentRecordById(String id) {
        return equipmentRecordDao.findEquipmentRecordById(id);
    }
    @Transactional
    public void saveEquipmentRecord(EquipmentRecord equipmentRecord) {
        equipmentRecord.setId(sequenceId.nextId());
        equipmentRecordDao.saveEquipmentRecord(equipmentRecord);
    }
    //查询已被修改
    @Transactional(readOnly = true)
    public List<EquipmentRecord> findEquipmentRecordListByCondition(EquipmentRecord equipmentRecord) {
        String[] zero = equipmentRecord.getSearchTimeZone();

        if(equipmentRecord.getSearchTimeZone()!=null&&equipmentRecord.getSearchTimeZone().length>0){
            equipmentRecord.setStartTime(zero[0]);
            equipmentRecord.setEndTime(zero[1]);
        }
        return equipmentRecordDao.findEquipmentRecordListByCondition(equipmentRecord);
    }

    /*@Transactional(readOnly = true)原生查询
    public List<EquipmentRecord> findEquipmentRecordListByCondition(EquipmentRecord equipmentRecord) {
        return equipmentRecordDao.findEquipmentRecordListByCondition(equipmentRecord);
    }*/
    @Transactional(readOnly = true)
    public EquipmentRecord findOneEquipmentRecordByCondition(EquipmentRecord equipmentRecord) {
        return equipmentRecordDao.findOneEquipmentRecordByCondition(equipmentRecord);
    }
    @Transactional(readOnly = true)
    public long findEquipmentRecordCountByCondition(EquipmentRecord equipmentRecord) {
        String[] zero = equipmentRecord.getSearchTimeZone();
        if(zero.length!=0&&zero!=null){
            equipmentRecord.setStartTime(zero[0]);
            equipmentRecord.setEndTime(zero[1]);
        }
        return equipmentRecordDao.findEquipmentRecordCountByCondition(equipmentRecord);
    }
    @Transactional
    public void updateEquipmentRecord(EquipmentRecord equipmentRecord) {
        equipmentRecordDao.updateEquipmentRecord(equipmentRecord);
    }
    @Transactional
    public void deleteEquipmentRecord(String id) {
        equipmentRecordDao.deleteEquipmentRecord(id);
    }
    @Transactional
    public void deleteEquipmentRecordByCondition(EquipmentRecord equipmentRecord) {
        equipmentRecordDao.deleteEquipmentRecordByCondition(equipmentRecord);
    }
    @Transactional
    public void batchSaveEquipmentRecord(List<EquipmentRecord> equipmentRecords){
        equipmentRecords.forEach(equipmentRecord -> equipmentRecord.setId(sequenceId.nextId()));
        equipmentRecordDao.batchSaveEquipmentRecord(equipmentRecords);
    }

}
