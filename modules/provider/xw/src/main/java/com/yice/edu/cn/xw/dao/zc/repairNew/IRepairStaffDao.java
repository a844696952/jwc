package com.yice.edu.cn.xw.dao.zc.repairNew;

import java.util.List;

import com.yice.edu.cn.common.pojo.xw.zc.repairNew.RepairStaff;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IRepairStaffDao {
    List<RepairStaff> findRepairStaffListByCondition(RepairStaff repairStaff);

    RepairStaff findOneRepairStaffByCondition(RepairStaff repairStaff);

    long findRepairStaffCountByCondition(RepairStaff repairStaff);

    RepairStaff findRepairStaffById(@Param("id") String id);

    void saveRepairStaff(RepairStaff repairStaff);

    void updateRepairStaff(RepairStaff repairStaff);

    void deleteRepairStaff(@Param("id") String id);

    void deleteRepairStaffByCondition(RepairStaff repairStaff);

    void batchSaveRepairStaff(List<RepairStaff> repairStaffs);



    List<RepairStaff> findRepairStaffDormsByCondition(RepairStaff repairStaff);

    long findRepairStaffCountByCondition1(RepairStaff repairStaff);

}
