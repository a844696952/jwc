package com.yice.edu.cn.jw.service.equipmentType;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.jw.equipmentType.EquipmentDeviceName;
import com.yice.edu.cn.common.pojo.jw.equipmentType.EquipmentLibrary;
import com.yice.edu.cn.common.pojo.jw.equipmentType.EquipmentRecord;
import com.yice.edu.cn.common.pojo.jw.equipmentType.EquipmentVendorManagement;
import com.yice.edu.cn.common.pojo.jw.school.School;
import com.yice.edu.cn.jw.dao.equipmentType.EquipmentLibraryDao;
import com.yice.edu.cn.jw.dao.region.IRegionDao;
import com.yice.edu.cn.jw.service.school.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EquipmentLibraryService {
    @Autowired
    private EquipmentLibraryDao equipmentLibraryDao;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private EquipmentRecordService equipmentRecordService;
    @Autowired
    private EquipmentVendorManagementService equipmentVendorManagementService;
    @Autowired
    private EquipmentDeviceNameService equipmentDeviceNameService;

    @Autowired
    private SchoolService schoolService;

    @Autowired
    private IRegionDao regionDao;


    //通过单个id查询已被修改
    public EquipmentLibrary findEquipmentLibraryById(String id) {
        return equipmentLibraryDao.findEquipmentLibraryByIdGai(id);
    }

   /* @Transactional(readOnly = true)原生查询
    public EquipmentLibrary findEquipmentLibraryById(String id) {
        return equipmentLibraryDao.findEquipmentLibraryByIdGai(id);
    }*/

    //添加已被修改
    @Transactional
    public void saveEquipmentLibrary(EquipmentLibrary equipmentLibrary) {
        saveEquipmentLibraryGai(equipmentLibrary);
    }

    //查询已被修改
    @Transactional(readOnly = true)
    public List<EquipmentLibrary> findEquipmentLibraryListByCondition(EquipmentLibrary equipmentLibrary) {
        System.out.println(equipmentLibrary);
        return equipmentLibraryDao.findEquipmentLibraryListByConditionGai(equipmentLibrary);
    }

    /*@Transactional(readOnly = true) 原生
    public List<EquipmentLibrary> findEquipmentLibraryListByCondition(EquipmentLibrary equipmentLibrary) {
        return equipmentLibraryDao.findEquipmentLibraryListByCondition(equipmentLibrary);
    }*/
    @Transactional(readOnly = true)
    public EquipmentLibrary findOneEquipmentLibraryByCondition(EquipmentLibrary equipmentLibrary) {
        return equipmentLibraryDao.findOneEquipmentLibraryByCondition(equipmentLibrary);
    }
    @Transactional(readOnly = true)
    public long findEquipmentLibraryCountByCondition(EquipmentLibrary equipmentLibrary) {
        return equipmentLibraryDao.findEquipmentLibraryCountByCondition(equipmentLibrary);
    }
    @Transactional
    public void updateEquipmentLibrary(EquipmentLibrary equipmentLibrary) {
       EquipmentLibrary equipmentLibrary1 =  findEquipmentLibraryById(equipmentLibrary.getId());
       int jishu = equipmentLibrary.getEquipmentNumber();
       if(equipmentLibrary.getEquipmentNumber()==equipmentLibrary1.getEquipmentNumber()){
           deleteEquipmentLibrary(equipmentLibrary.getId());
       }else{
           int num  =equipmentLibrary1.getEquipmentNumber()-equipmentLibrary.getEquipmentNumber();
           equipmentLibrary.setEquipmentNumber(num);
           equipmentLibraryDao.updateEquipmentLibrary(equipmentLibrary);
       }

       EquipmentRecord equipmentRecord = new EquipmentRecord();
       equipmentRecord.setRemarks(equipmentLibrary.getRemarks());
       equipmentRecord.setType(2);
       equipmentRecord.setEquipmentNumber(jishu);
       equipmentRecord.setDeviceName(equipmentLibrary.getDeviceName());
       equipmentRecord.setVendorName(equipmentLibrary.getVendorName());
       equipmentRecord.setCityId(equipmentLibrary.getCityId());
       equipmentRecord.setCityName(equipmentLibrary.getCityName());
       equipmentRecord.setDistrictName(equipmentLibrary.getDistrictName());
       equipmentRecord.setDistrictId(equipmentLibrary.getDistrictId());
       equipmentRecord.setProvinceId(equipmentLibrary.getProvinceId());
       equipmentRecord.setProvinceName(equipmentLibrary.getProvinceName());
       equipmentRecord.setRemarks(equipmentLibrary.getRemarks());
       equipmentRecord.setSchoolName(equipmentLibrary.getSchoolName());

       equipmentRecordService.saveEquipmentRecord(equipmentRecord);
    }

   /* @Transactional原生修改
    public void updateEquipmentLibrary(EquipmentLibrary equipmentLibrary) {
        equipmentLibraryDao.updateEquipmentLibrary(equipmentLibrary);
    }*/
    //删除已被修改
    @Transactional
    public void deleteEquipmentLibrary(String id) {
        EquipmentLibrary equipmentLibrary = equipmentLibraryDao.findEquipmentLibraryByIdGai(id);

        EquipmentRecord equipmentRecord = new EquipmentRecord();
        equipmentRecord.setVendorName(equipmentLibrary.getVendorName());
        equipmentRecord.setDeviceName(equipmentLibrary.getDeviceName());
        equipmentRecord.setType(3);
        equipmentRecord.setEquipmentNumber(equipmentLibrary.getEquipmentNumber());
        equipmentRecord.setRemarks(equipmentLibrary.getRemarks());

        //添加删除记录
        equipmentRecordService.saveEquipmentRecord(equipmentRecord);

        equipmentLibraryDao.deleteEquipmentLibrary(id);
    }

    /*@Transactional原生删除
    public void deleteEquipmentLibrary(String id) {

        equipmentLibraryDao.deleteEquipmentLibrary(id);
    }*/
    @Transactional
    public void deleteEquipmentLibraryByCondition(EquipmentLibrary equipmentLibrary) {
        equipmentLibraryDao.deleteEquipmentLibraryByCondition(equipmentLibrary);
    }
    @Transactional
    public void batchSaveEquipmentLibrary(List<EquipmentLibrary> equipmentLibrarys){
        equipmentLibrarys.forEach(equipmentLibrary -> equipmentLibrary.setId(sequenceId.nextId()));
        equipmentLibraryDao.batchSaveEquipmentLibrary(equipmentLibrarys);
    }

    @Transactional
    public void saveEquipmentLibraryGai(EquipmentLibrary equipmentLibrary) {
        EquipmentLibrary equipmentLibrary1 = new EquipmentLibrary();
        equipmentLibrary1.setDeviceId(equipmentLibrary.getDeviceId());
        equipmentLibrary1.setVendorId(equipmentLibrary.getVendorId());
        //查出当前是否已存在相同类别
        List<EquipmentLibrary> equipmentLibraries = equipmentLibraryDao.findEquipmentLibraryListByConditionGai(equipmentLibrary1);
        if(equipmentLibraries.size()!=0){
            //有的话 则改为修改库存
            equipmentLibraries.forEach(f->{
                f.setEquipmentNumber(f.getEquipmentNumber()+equipmentLibrary.getEquipmentNumber());
                equipmentLibraryDao.updateEquipmentLibrary(f);
            });
        }else{
            //没有的话则新添加
            equipmentLibrary.setId(sequenceId.nextId());
            equipmentLibraryDao.saveEquipmentLibrary(equipmentLibrary);
        }

        //添加入库记录
        EquipmentRecord equipmentRecord = new EquipmentRecord();


        EquipmentDeviceName s  = equipmentDeviceNameService.findEquipmentDeviceNameById(equipmentLibrary.getDeviceId());
        EquipmentVendorManagement equipmentVendorManagement  =  equipmentVendorManagementService.findEquipmentVendorManagementById(equipmentLibrary.getVendorId());

        equipmentRecord.setVendorName(equipmentVendorManagement.getVendorName());
        equipmentRecord.setDeviceName(s.getDeviceName());
        equipmentRecord.setRemarks(equipmentLibrary.getRemarks());
        equipmentRecord.setEquipmentNumber(equipmentLibrary.getEquipmentNumber());
        equipmentRecord.setType(1);
        equipmentRecordService.saveEquipmentRecord(equipmentRecord);
    }


    @Transactional
    public List<School> findSchoolByEquimentLibraryListGai(EquipmentLibrary equipmentLibrary){
        School school = new School();
        school.setCityId(equipmentLibrary.getCityId());
        school.setDistrictId(equipmentLibrary.getDistrictId());
        school.setProvinceId(equipmentLibrary.getProvinceId());

        List<School> list = schoolService.findSchoolListByCondition(school);
        return list;
    }
}
