package com.yice.edu.cn.jw.service.equipmentType;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.equipmentType.*;
import com.yice.edu.cn.jw.dao.equipmentType.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class EquipmentVendorManagementService {
    @Autowired
    private EquipmentVendorManagementDao equipmentVendorManagementDao;

    @Autowired
    private EquipmentTypeDao equipmentTypeDao;

    @Autowired
    private EquipmentDeviceNameDao equipmentDeviceNameDao;

    @Autowired
    private EquipmentManagementDerviceNameDao  equipmentManagementDerviceNameDao;
    @Autowired
    private EquipmentLibraryDao equipmentLibraryDao;
    @Autowired
    private SequenceId sequenceId;

    @Autowired
    private EquipmentManagementDerviceNameService equipmentManagementDerviceNameService;

    @Transactional(readOnly = true)
    public EquipmentVendorManagement findEquipmentVendorManagementById(String id) {
        return equipmentVendorManagementDao.findEquipmentVendorManagementById(id);
    }
    @Transactional
    public void saveEquipmentVendorManagement(EquipmentVendorManagement equipmentVendorManagement) {
        equipmentVendorManagement.setId(sequenceId.nextId());
        equipmentVendorManagementDao.saveEquipmentVendorManagement(equipmentVendorManagement);
    }

    //已修改调用方法
    @Transactional(readOnly = true)
    public List<EquipmentVendorManagement> findEquipmentVendorManagementListByCondition(EquipmentVendorManagement equipmentVendorManagement) {
        return equipmentVendorManagementDao.findEquipmentVendorManagementListByConditionGai(equipmentVendorManagement);
    }

    /*@Transactional(readOnly = true)  原生查询
    public List<EquipmentVendorManagement> findEquipmentVendorManagementListByCondition(EquipmentVendorManagement equipmentVendorManagement) {
        return equipmentVendorManagementDao.findEquipmentVendorManagementListByConditionGai(equipmentVendorManagement);
    }*/
    @Transactional(readOnly = true)
    public EquipmentVendorManagement findOneEquipmentVendorManagementByCondition(EquipmentVendorManagement equipmentVendorManagement) {
        return equipmentVendorManagementDao.findOneEquipmentVendorManagementByCondition(equipmentVendorManagement);
    }
    @Transactional(readOnly = true)
    public long findEquipmentVendorManagementCountByCondition(EquipmentVendorManagement equipmentVendorManagement) {
        return equipmentVendorManagementDao.findEquipmentVendorManagementCountByCondition(equipmentVendorManagement);
    }
    @Transactional
    public void updateEquipmentVendorManagement(EquipmentVendorManagement equipmentVendorManagement) {
        equipmentVendorManagementDao.updateEquipmentVendorManagement(equipmentVendorManagement);
    }
    @Transactional
    public void deleteEquipmentVendorManagement(String id) {
        equipmentVendorManagementDao.deleteEquipmentVendorManagement(id);
    }
    @Transactional
    public void deleteEquipmentVendorManagementByCondition(EquipmentVendorManagement equipmentVendorManagement) {
        equipmentVendorManagementDao.deleteEquipmentVendorManagementByCondition(equipmentVendorManagement);
    }
    @Transactional
    public void batchSaveEquipmentVendorManagement(List<EquipmentVendorManagement> equipmentVendorManagements){
        equipmentVendorManagements.forEach(equipmentVendorManagement -> equipmentVendorManagement.setId(sequenceId.nextId()));
        equipmentVendorManagementDao.batchSaveEquipmentVendorManagement(equipmentVendorManagements);
    }


    //查询出设备类型及其所拥有的设备
    @Transactional
    public List<EquipmentType> equipmentVendorManagementsListShu(){
        EquipmentType equipmentType1 = new EquipmentType();
        List<EquipmentType> equipmentTypeList = chashu(equipmentType1);

        return equipmentTypeList;
    }

    @Transactional
    public List<EquipmentType> chashu(EquipmentType equipmentType1){
        //查询出所有的设备类型
        List<EquipmentType> equipmentTypeList = equipmentTypeDao.findEquipmentTypeListByCondition(equipmentType1);

        equipmentTypeList.forEach(f->{
            EquipmentDeviceName equipmentDeviceName = new EquipmentDeviceName();
            equipmentDeviceName.setEquipmentId(f.getId());
            //通过设备类型找出所有的设备
            List<EquipmentDeviceName> equipmentDeviceName1 = equipmentDeviceNameDao.findEquipmentDeviceNameListByCondition(equipmentDeviceName);

            List<EquipmentType> equipmentTypeList1 = new ArrayList<>();
            equipmentDeviceName1.forEach(s->{
                EquipmentType equipmentType = new EquipmentType();
                equipmentType.setId(s.getId());
                equipmentType.setClassName(s.getDeviceName());
                equipmentTypeList1.add(equipmentType);
            });
            f.setChildren(equipmentTypeList1);
        });
        equipmentTypeList.forEach(f->{
            if(f.getChildren().size()>0){
                f.setFlags(false);
            }else{
                f.setFlags(true);
            }
        });
        return  equipmentTypeList;
    }

    //提交
    @Transactional
    public String saveEquipmentVendorManagementGai(EquipmentVendorManagement equipmentVendorManagement) {
        EquipmentVendorManagement equipmentVendorManagement1 = new EquipmentVendorManagement();
        equipmentVendorManagement1.setVendorName(equipmentVendorManagement.getVendorName());
       long  a =  equipmentVendorManagementDao.findEquipmentVendorManagementCountByCondition(equipmentVendorManagement1);
        if(a!=0){
            return  "error";
        }
        EquipmentManagementDerviceName equipmentManagementDerviceName = new EquipmentManagementDerviceName();
        equipmentVendorManagement.setId(sequenceId.nextId());
        equipmentManagementDerviceName.setVendorManagementId(equipmentVendorManagement.getId());
        if(equipmentVendorManagement.getDeviceId()!=null){
            String[] s = equipmentVendorManagement.getDeviceId().split(",");
            for(int i =0;i<s.length;i++){
                equipmentManagementDerviceName.setDeviceId(s[i]);
                equipmentManagementDerviceNameService.saveEquipmentManagementDerviceName(equipmentManagementDerviceName);
            }
            equipmentVendorManagementDao.saveEquipmentVendorManagement(equipmentVendorManagement);
        }else{
            return  "false";
        }

        return "success";
    }

    //点击修改时查找到当前数据
    public ResponseJson findEquipmentVendorManagementByIdGai(String id) {
        List<EquipmentType> equipmentTypeList = equipmentVendorManagementsListShu();
        EquipmentVendorManagement equipmentVendorManagement =equipmentVendorManagementDao.findEquipmentVendorManagementByIdGai(id);
        EquipmentManagementDerviceName equipmentManagementDerviceName = new EquipmentManagementDerviceName();
        equipmentManagementDerviceName.setVendorManagementId(id);
        List<EquipmentManagementDerviceName> list =  equipmentManagementDerviceNameDao.findEquipmentManagementDerviceNameListByCondition(equipmentManagementDerviceName);



        return  new ResponseJson(equipmentVendorManagement,equipmentTypeList,list);
    }


    //修改校正(中间记录删除后重新插入)
    @Transactional
    public String updateEquipmentVendorManagementGai(EquipmentVendorManagement equipmentVendorManagement) {
        //判断厂商名称是否已存在
        EquipmentVendorManagement e = new EquipmentVendorManagement();
        e.setVendorName(equipmentVendorManagement.getVendorName());
        List<EquipmentVendorManagement> lists = findEquipmentVendorManagementListByCondition(e);
        System.out.println(lists);
        if(lists.size()!=0){
            for (int i=0;i<lists.size();i++){
                if(!equipmentVendorManagement.getId().equals(lists.get(i).getId())){
                    System.out.println(equipmentVendorManagement.getId());
                    System.out.println(lists.get(i).getId());
                    return "error";
                }
            }
        }


        EquipmentManagementDerviceName equipmentManagementDerviceName = new EquipmentManagementDerviceName();
        //将传进来的设备id进行分割
        String[] id = equipmentVendorManagement.getDeviceId().split(",");
        //获取需要修改的厂商id
        equipmentManagementDerviceName.setVendorManagementId(equipmentVendorManagement.getId());
        //找到中间表存在的权限
        List<EquipmentManagementDerviceName> list =  equipmentManagementDerviceNameDao.findEquipmentManagementDerviceNameListByCondition(equipmentManagementDerviceName);
        //将其全部删除
        list.forEach(f->{
           equipmentManagementDerviceNameDao.deleteEquipmentManagementDerviceNameByCondition(f);
        });

        //重新插入需要添加的权限
        for (int i =0;i<id.length;i++){
           equipmentManagementDerviceName.setDeviceId(id[i]);
           equipmentManagementDerviceNameService.saveEquipmentManagementDerviceName(equipmentManagementDerviceName);
        }
        return "success";
    }

    //删除
    @Transactional
    public String deleteEquipmentVendorManagementGai(String id) {
        EquipmentLibrary equipmentLibrary = new EquipmentLibrary();
        equipmentLibrary.setVendorId(id);
        long count = equipmentLibraryDao.findEquipmentLibraryCountByCondition(equipmentLibrary);
        if(count!=0){
            return "error";
        }
        //删除厂商里的记录
        equipmentVendorManagementDao.deleteEquipmentVendorManagement(id);

        EquipmentManagementDerviceName equipmentManagementDerviceName = new EquipmentManagementDerviceName();
        equipmentManagementDerviceName.setVendorManagementId(id);
        //查找出中间表的记录
        List<EquipmentManagementDerviceName> equipmentManagementDerviceNames = equipmentManagementDerviceNameDao.findEquipmentManagementDerviceNameListByCondition(equipmentManagementDerviceName);
        //批量删除
        equipmentManagementDerviceNames.forEach(f->{
            equipmentManagementDerviceNameDao.deleteEquipmentManagementDerviceNameByCondition(f);
        });
        return "success";
    }
}
