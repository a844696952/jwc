package com.yice.edu.cn.xw.service.zc.repairNew;

import cn.hutool.core.date.DateUtil;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.xw.zc.repairNew.RepairStaff;
import com.yice.edu.cn.xw.dao.zc.repairNew.IRepairStaffDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RepairStaffService {
    @Autowired
    private IRepairStaffDao repairStaffDao;
    @Autowired
    private SequenceId sequenceId;

    @Transactional(readOnly = true)
    public RepairStaff findRepairStaffById(String id) {
        return repairStaffDao.findRepairStaffById(id);
    }
    @Transactional
    public void saveRepairStaff(RepairStaff repairStaff) {
        repairStaff.setId(sequenceId.nextId());
        repairStaff.setCreateTime(DateUtil.now());
        repairStaffDao.saveRepairStaff(repairStaff);
    }
    @Transactional(readOnly = true)
    public List<RepairStaff> findRepairStaffListByCondition(RepairStaff repairStaff) {
        return repairStaffDao.findRepairStaffListByCondition(repairStaff);
    }
    @Transactional(readOnly = true)
    public RepairStaff findOneRepairStaffByCondition(RepairStaff repairStaff) {
        return repairStaffDao.findOneRepairStaffByCondition(repairStaff);
    }
    @Transactional(readOnly = true)
    public long findRepairStaffCountByCondition(RepairStaff repairStaff) {
        return repairStaffDao.findRepairStaffCountByCondition(repairStaff);
    }
    @Transactional
    public void updateRepairStaff(RepairStaff repairStaff) {
        if(repairStaff.getRemark() == null){
            repairStaff.setRemark("");
        }
        repairStaffDao.updateRepairStaff(repairStaff);
    }
    @Transactional
    public void deleteRepairStaff(String id) {
        repairStaffDao.deleteRepairStaff(id);
    }
    @Transactional
    public void deleteRepairStaffByCondition(RepairStaff repairStaff) {
        repairStaffDao.deleteRepairStaffByCondition(repairStaff);
    }
    @Transactional
    public void batchSaveRepairStaff(List<RepairStaff> repairStaffs){
        repairStaffs.forEach(repairStaff -> repairStaff.setId(sequenceId.nextId()));
        repairStaffDao.batchSaveRepairStaff(repairStaffs);
    }


    @Transactional(readOnly = true)
    public List<RepairStaff> findRepairStaffDormsByCondition(RepairStaff repairStaff) {
        repairStaff.setStaffType("2");
        return repairStaffDao.findRepairStaffDormsByCondition(repairStaff);
    }
    @Transactional(readOnly = true)
    public long findRepairStaffCountByCondition1(RepairStaff repairStaff) {
        repairStaff.setStaffType("2");
        return repairStaffDao.findRepairStaffCountByCondition1(repairStaff);
    }

}
