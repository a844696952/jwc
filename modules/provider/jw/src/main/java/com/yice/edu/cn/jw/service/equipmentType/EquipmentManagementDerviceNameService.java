package com.yice.edu.cn.jw.service.equipmentType;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.jw.equipmentType.EquipmentManagementDerviceName;
import com.yice.edu.cn.jw.dao.equipmentType.EquipmentManagementDerviceNameDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EquipmentManagementDerviceNameService {
    @Autowired
    private EquipmentManagementDerviceNameDao equipmentManagementDerviceNameDao;
    @Autowired
    private SequenceId sequenceId;

    @Transactional(readOnly = true)
    public EquipmentManagementDerviceName findEquipmentManagementDerviceNameById(String id) {
        return equipmentManagementDerviceNameDao.findEquipmentManagementDerviceNameById(id);
    }
    @Transactional
    public void saveEquipmentManagementDerviceName(EquipmentManagementDerviceName equipmentManagementDerviceName) {
        equipmentManagementDerviceName.setId(sequenceId.nextId());
        equipmentManagementDerviceNameDao.saveEquipmentManagementDerviceName(equipmentManagementDerviceName);
    }
    @Transactional(readOnly = true)
    public List<EquipmentManagementDerviceName> findEquipmentManagementDerviceNameListByCondition(EquipmentManagementDerviceName equipmentManagementDerviceName) {
        return equipmentManagementDerviceNameDao.findEquipmentManagementDerviceNameListByCondition(equipmentManagementDerviceName);
    }
    @Transactional(readOnly = true)
    public EquipmentManagementDerviceName findOneEquipmentManagementDerviceNameByCondition(EquipmentManagementDerviceName equipmentManagementDerviceName) {
        return equipmentManagementDerviceNameDao.findOneEquipmentManagementDerviceNameByCondition(equipmentManagementDerviceName);
    }
    @Transactional(readOnly = true)
    public long findEquipmentManagementDerviceNameCountByCondition(EquipmentManagementDerviceName equipmentManagementDerviceName) {
        return equipmentManagementDerviceNameDao.findEquipmentManagementDerviceNameCountByCondition(equipmentManagementDerviceName);
    }
    @Transactional
    public void updateEquipmentManagementDerviceName(EquipmentManagementDerviceName equipmentManagementDerviceName) {
        equipmentManagementDerviceNameDao.updateEquipmentManagementDerviceName(equipmentManagementDerviceName);
    }
    @Transactional
    public void deleteEquipmentManagementDerviceName(String id) {
        equipmentManagementDerviceNameDao.deleteEquipmentManagementDerviceName(id);
    }
    @Transactional
    public void deleteEquipmentManagementDerviceNameByCondition(EquipmentManagementDerviceName equipmentManagementDerviceName) {
        equipmentManagementDerviceNameDao.deleteEquipmentManagementDerviceNameByCondition(equipmentManagementDerviceName);
    }
    @Transactional
    public void batchSaveEquipmentManagementDerviceName(List<EquipmentManagementDerviceName> equipmentManagementDerviceNames){
        equipmentManagementDerviceNames.forEach(equipmentManagementDerviceName -> equipmentManagementDerviceName.setId(sequenceId.nextId()));
        equipmentManagementDerviceNameDao.batchSaveEquipmentManagementDerviceName(equipmentManagementDerviceNames);
    }

}
