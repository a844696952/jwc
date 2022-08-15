package com.yice.edu.cn.osp.service.zc.repairNew;

import com.yice.edu.cn.common.pojo.xw.zc.repairNew.RepairStaff;
import com.yice.edu.cn.osp.feignClient.zc.repairNew.RepairStaffFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RepairStaffService {
    @Autowired
    private RepairStaffFeign repairStaffFeign;

    public RepairStaff findRepairStaffById(String id) {
        return repairStaffFeign.findRepairStaffById(id);
    }

    public RepairStaff saveRepairStaff(RepairStaff repairStaff) {
        return repairStaffFeign.saveRepairStaff(repairStaff);
    }

    public List<RepairStaff> findRepairStaffListByCondition(RepairStaff repairStaff) {
        return repairStaffFeign.findRepairStaffListByCondition(repairStaff);
    }

    public RepairStaff findOneRepairStaffByCondition(RepairStaff repairStaff) {
        return repairStaffFeign.findOneRepairStaffByCondition(repairStaff);
    }

    public long findRepairStaffCountByCondition(RepairStaff repairStaff) {
        return repairStaffFeign.findRepairStaffCountByCondition(repairStaff);
    }

    public void updateRepairStaff(RepairStaff repairStaff) {
        repairStaffFeign.updateRepairStaff(repairStaff);
    }

    public void deleteRepairStaff(String id) {
        repairStaffFeign.deleteRepairStaff(id);
    }

    public void deleteRepairStaffByCondition(RepairStaff repairStaff) {
        repairStaffFeign.deleteRepairStaffByCondition(repairStaff);
    }


    public List<RepairStaff> findRepairStaffDormsByCondition(RepairStaff repairStaff) {
        return repairStaffFeign.findRepairStaffDormsByCondition(repairStaff);
    }

    public long findRepairStaffCountByCondition1(RepairStaff repairStaff) {
        return repairStaffFeign.findRepairStaffCountByCondition1(repairStaff);
    }

}
