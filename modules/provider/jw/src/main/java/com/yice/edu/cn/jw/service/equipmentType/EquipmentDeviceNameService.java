package com.yice.edu.cn.jw.service.equipmentType;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.jw.equipmentType.EquipmentDeviceName;
import com.yice.edu.cn.common.pojo.jw.equipmentType.EquipmentManagementDerviceName;
import com.yice.edu.cn.jw.dao.equipmentType.EquipmentDeviceNameDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EquipmentDeviceNameService {
    @Autowired
    private EquipmentDeviceNameDao equipmentDeviceNameDao;
    @Autowired
    private SequenceId sequenceId;

    @Autowired
    private EquipmentManagementDerviceNameService equipmentManagementDerviceNameService;

    @Transactional(readOnly = true)
    public EquipmentDeviceName findEquipmentDeviceNameById(String id) {
        return equipmentDeviceNameDao.findEquipmentDeviceNameById(id);
    }
    @Transactional
    public void saveEquipmentDeviceName(EquipmentDeviceName equipmentDeviceName) {
        equipmentDeviceName.setId(sequenceId.nextId());
        equipmentDeviceNameDao.saveEquipmentDeviceName(equipmentDeviceName);
    }
    @Transactional(readOnly = true)
    public List<EquipmentDeviceName> findEquipmentDeviceNameListByCondition(EquipmentDeviceName equipmentDeviceName) {
        return equipmentDeviceNameDao.findEquipmentDeviceNameListByCondition(equipmentDeviceName);
    }
    @Transactional(readOnly = true)
    public EquipmentDeviceName findOneEquipmentDeviceNameByCondition(EquipmentDeviceName equipmentDeviceName) {
        return equipmentDeviceNameDao.findOneEquipmentDeviceNameByCondition(equipmentDeviceName);
    }
    @Transactional(readOnly = true)
    public long findEquipmentDeviceNameCountByCondition(EquipmentDeviceName equipmentDeviceName) {
        return equipmentDeviceNameDao.findEquipmentDeviceNameCountByCondition(equipmentDeviceName);
    }
    @Transactional
    public void updateEquipmentDeviceName(EquipmentDeviceName equipmentDeviceName) {
        equipmentDeviceNameDao.updateEquipmentDeviceName(equipmentDeviceName);
    }
    //删除已被修改，目前暂未使用
    @Transactional
    public void deleteEquipmentDeviceName(String id) {
        EquipmentManagementDerviceName equipmentManagementDerviceName = new EquipmentManagementDerviceName();
        equipmentManagementDerviceName.setDeviceId(id);
        List<EquipmentManagementDerviceName> list = equipmentManagementDerviceNameService.findEquipmentManagementDerviceNameListByCondition(equipmentManagementDerviceName);
        if(list.size()>0){
            list.forEach(l->{
                equipmentManagementDerviceNameService.deleteEquipmentManagementDerviceNameByCondition(l);
            });
        }
        equipmentDeviceNameDao.deleteEquipmentDeviceName(id);
    }

    /*@Transactional原生代码
    public void deleteEquipmentDeviceName(String id) {

        equipmentDeviceNameDao.deleteEquipmentDeviceName(id);
    }*/
    @Transactional
    public void deleteEquipmentDeviceNameByCondition(EquipmentDeviceName equipmentDeviceName) {
        equipmentDeviceNameDao.deleteEquipmentDeviceNameByCondition(equipmentDeviceName);
    }
    @Transactional
    public void batchSaveEquipmentDeviceName(List<EquipmentDeviceName> equipmentDeviceNames){
        equipmentDeviceNames.forEach(equipmentDeviceName -> equipmentDeviceName.setId(sequenceId.nextId()));
        equipmentDeviceNameDao.batchSaveEquipmentDeviceName(equipmentDeviceNames);
    }


    //添加校正是否已存在相同
    @Transactional
    public String saveEquipmentDeviceNameGai(EquipmentDeviceName equipmentDeviceName) {
        EquipmentDeviceName equipmentDeviceName1 = new EquipmentDeviceName();
        equipmentDeviceName1.setDeviceName(equipmentDeviceName.getDeviceName());
        equipmentDeviceName1.setEquipmentId(equipmentDeviceName.getEquipmentId());
        long count = findEquipmentDeviceNameCountByCondition(equipmentDeviceName1);
        if(count!=0){
            return "error";
        }
        equipmentDeviceName.setId(sequenceId.nextId());
        equipmentDeviceNameDao.saveEquipmentDeviceName(equipmentDeviceName);
        return "success";
    }


    //修改时做的校正
    @Transactional
    public String updateEquipmentDeviceNameGai(EquipmentDeviceName equipmentDeviceName) {
        EquipmentDeviceName equipmentDeviceName1 = new EquipmentDeviceName();
        equipmentDeviceName1.setDeviceName(equipmentDeviceName.getDeviceName());
        equipmentDeviceName1.setEquipmentId(equipmentDeviceName.getEquipmentId());
        List<EquipmentDeviceName> listByCondition = findEquipmentDeviceNameListByCondition(equipmentDeviceName1);
        if(listByCondition.size()!=0){
            for(int i=0;i<listByCondition.size();i++){
                if(!equipmentDeviceName.getId().equals(listByCondition.get(i).getId())){
                    return "error";
                }
            }
        }
        equipmentDeviceNameDao.updateEquipmentDeviceName(equipmentDeviceName);
        return "success";
    }



}
