package com.yice.edu.cn.xw.service.dormManage.dorm;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.jw.building.Building;
import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.DormBuildAdmin;
import com.yice.edu.cn.xw.dao.dormManage.dorm.IDormBuildAdminDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DormBuildAdminService {
    @Autowired
    private IDormBuildAdminDao dormBuildAdminDao;
    @Autowired
    private SequenceId sequenceId;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @Transactional(readOnly = true)
    public DormBuildAdmin findDormBuildAdminById(String id) {
        return dormBuildAdminDao.findDormBuildAdminById(id);
    }
    @Transactional
    public void saveDormBuildAdmin(DormBuildAdmin dormBuildAdmin) {
        dormBuildAdmin.setId(sequenceId.nextId());
        dormBuildAdminDao.saveDormBuildAdmin(dormBuildAdmin);
    }
    @Transactional(readOnly = true)
    public List<DormBuildAdmin> findDormBuildAdminListByCondition(DormBuildAdmin dormBuildAdmin) {
        return dormBuildAdminDao.findDormBuildAdminListByCondition(dormBuildAdmin);
    }
    @Transactional(readOnly = true)
    public DormBuildAdmin findOneDormBuildAdminByCondition(DormBuildAdmin dormBuildAdmin) {
        return dormBuildAdminDao.findOneDormBuildAdminByCondition(dormBuildAdmin);
    }
    @Transactional(readOnly = true)
    public long findDormBuildAdminCountByCondition(DormBuildAdmin dormBuildAdmin) {
        return dormBuildAdminDao.findDormBuildAdminCountByCondition(dormBuildAdmin);
    }
    @Transactional
    public void updateDormBuildAdmin(DormBuildAdmin dormBuildAdmin) {
        dormBuildAdminDao.updateDormBuildAdmin(dormBuildAdmin);
    }
    @Transactional
    public void deleteDormBuildAdmin(String id) {
        dormBuildAdminDao.deleteDormBuildAdmin(id);
    }
    @Transactional
    public void deleteDormBuildAdminByCondition(DormBuildAdmin dormBuildAdmin) {
        dormBuildAdminDao.deleteDormBuildAdminByCondition(dormBuildAdmin);
    }
    @Transactional
    public void batchSaveDormBuildAdmin(List<DormBuildAdmin> dormBuildAdmins){
        dormBuildAdmins.forEach(dormBuildAdmin -> dormBuildAdmin.setId(sequenceId.nextId()));
        dormBuildAdminDao.batchSaveDormBuildAdmin(dormBuildAdmins);
    }

    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    @Transactional
    public List<Building> getBuildingList(Building building) {
        return dormBuildAdminDao.getBuildingList(building);
    }

    @Transactional
    public List<DormBuildAdmin> findDormBuildAdminListByConditionConnect(DormBuildAdmin dormBuildAdmin) {
        return dormBuildAdminDao.findDormBuildAdminListByConditionConnect(dormBuildAdmin);
    }


    @Transactional
    public long findDormBuildAdminListCountConnect(DormBuildAdmin dormBuildAdmin) {
        return dormBuildAdminDao.findDormBuildAdminListCountConnect(dormBuildAdmin);
    }


    @Transactional
    public List<DormBuildAdmin> findDormBuildTeacherByConditionConnect(DormBuildAdmin dormBuildAdmin) {
        return dormBuildAdminDao.findDormBuildTeacherByConditionConnect(dormBuildAdmin);
    }
    @Transactional
    public  List<Building> findCreateDormBuildingList(DormBuildAdmin dormBuildAdmin){
        return dormBuildAdminDao.findCreateDormBuildingList(dormBuildAdmin);
    }

    @Transactional
    public List<Building> findDormBuildListByCondition(DormBuildAdmin dormBuildAdmin){
        return dormBuildAdminDao.findDormBuildListByCondition(dormBuildAdmin);
    }



}
