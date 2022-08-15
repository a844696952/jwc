package com.yice.edu.cn.jw.service.equipmentType;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.jw.equipmentType.EquipmentDeviceName;
import com.yice.edu.cn.common.pojo.jw.equipmentType.EquipmentType;
import com.yice.edu.cn.jw.dao.equipmentType.EquipmentTypeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EquipmentTypeService {
    @Autowired
    private EquipmentTypeDao equipmentTypeDao;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private EquipmentDeviceNameService equipmentDeviceNameService;

    @Transactional(readOnly = true)
    public EquipmentType findEquipmentTypeById(String id) {
        return equipmentTypeDao.findEquipmentTypeById(id);
    }
    @Transactional
    public void saveEquipmentType(EquipmentType equipmentType) {
        equipmentType.setId(sequenceId.nextId());
        equipmentTypeDao.saveEquipmentType(equipmentType);
    }
    @Transactional(readOnly = true)
    public List<EquipmentType> findEquipmentTypeListByCondition(EquipmentType equipmentType) {
        return equipmentTypeDao.findEquipmentTypeListByCondition(equipmentType);
    }
    @Transactional(readOnly = true)
    public EquipmentType findOneEquipmentTypeByCondition(EquipmentType equipmentType) {
        return equipmentTypeDao.findOneEquipmentTypeByCondition(equipmentType);
    }
    @Transactional(readOnly = true)
    public long findEquipmentTypeCountByCondition(EquipmentType equipmentType) {
        return equipmentTypeDao.findEquipmentTypeCountByCondition(equipmentType);
    }
    @Transactional
    public void updateEquipmentType(EquipmentType equipmentType) {
        equipmentTypeDao.updateEquipmentType(equipmentType);
    }
    @Transactional
    public void deleteEquipmentType(String id) {
        equipmentTypeDao.deleteEquipmentType(id);
    }
    @Transactional
    public void deleteEquipmentTypeByCondition(EquipmentType equipmentType) {
        equipmentTypeDao.deleteEquipmentTypeByCondition(equipmentType);
    }
    @Transactional
    public void batchSaveEquipmentType(List<EquipmentType> equipmentTypes){
        equipmentTypes.forEach(equipmentType -> equipmentType.setId(sequenceId.nextId()));
        equipmentTypeDao.batchSaveEquipmentType(equipmentTypes);
    }

    //添加新的类型时做的校验
    @Transactional
    public String saveEquipmentTypeGai(EquipmentType equipmentType){
        EquipmentType equipmentType1 = new EquipmentType();
        EquipmentType equipmentType2 = new EquipmentType();

        equipmentType1.setClassName(equipmentType.getClassName());

        long number = findEquipmentTypeCountByCondition(equipmentType1);

        if(number!=0){
            return "error";
        }
        if(equipmentType.getEquipmentNumber()!=null){
            equipmentType2.setEquipmentNumber(equipmentType.getEquipmentNumber());
            number = findEquipmentTypeCountByCondition(equipmentType2);
        }
        if(number!=0){
            return "false";
        }else{
            saveEquipmentType(equipmentType);
            return  "success";
        }
    }

    //修改时做的校验
    @Transactional
    public String updateEquipmentTypeGai(EquipmentType equipmentType) {
        EquipmentType equipmentType1 = new EquipmentType();
        EquipmentType equipmentType2 = new EquipmentType();
        equipmentType1.setClassName(equipmentType.getClassName());
        //查询所有与传进来参数的类型名称相等的list
        List<EquipmentType> equipmentTypeList = findEquipmentTypeListByCondition(equipmentType1);

        if(equipmentTypeList.size()!=0){
            for (int i = 0; i < equipmentTypeList.size(); i++) {
                //如果不是当前这个对象，则返回异常
                if (!equipmentType.getId().equals(equipmentTypeList.get(i).getId())) {
                    return "error";
                }
            }
        }

        if (equipmentType.getEquipmentNumber() != null) {
            equipmentType2.setEquipmentNumber(equipmentType.getEquipmentNumber());
            //查询所有与传进来参数的设备编号相等的list
            List<EquipmentType> equipmentTypeList1 = findEquipmentTypeListByCondition(equipmentType2);

            if(equipmentTypeList1.size()!=0){
                for (int i = 0; i < equipmentTypeList1.size(); i++) {
                    //如果不是当前这个对象，则返回false
                    System.out.println(equipmentTypeList1.get(i).getEquipmentNumber());
                    if (!equipmentType.getId().equals(equipmentTypeList1.get(i).getId())) {
                        return "false";
                    }
                }
            }

        }else{
            equipmentType.setEquipmentNumber("");
        }
            updateEquipmentType(equipmentType);
            return "success";
    }


    //删除时做的校正
    @Transactional
    public String deleteEquipmentTypeGai(String id) {
        EquipmentDeviceName equipmentDeviceName = new EquipmentDeviceName();
        equipmentDeviceName.setEquipmentId(id);
        List<EquipmentDeviceName> list = equipmentDeviceNameService.findEquipmentDeviceNameListByCondition(equipmentDeviceName);
        if(list.size()!=0){
            return "error";
        }
        equipmentTypeDao.deleteEquipmentType(id);
        return "success";
    }

}
