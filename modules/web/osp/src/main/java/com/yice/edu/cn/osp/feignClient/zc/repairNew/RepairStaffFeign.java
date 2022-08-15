package com.yice.edu.cn.osp.feignClient.zc.repairNew;

import com.yice.edu.cn.common.pojo.xw.zc.repairNew.RepairStaff;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="xw",contextId = "repairStaffFeign",path = "/repairStaff")
public interface RepairStaffFeign {
    @GetMapping("/findRepairStaffById/{id}")
    RepairStaff findRepairStaffById(@PathVariable("id") String id);
    @PostMapping("/saveRepairStaff")
    RepairStaff saveRepairStaff(RepairStaff repairStaff);
    @PostMapping("/findRepairStaffListByCondition")
    List<RepairStaff> findRepairStaffListByCondition(RepairStaff repairStaff);
    @PostMapping("/findOneRepairStaffByCondition")
    RepairStaff findOneRepairStaffByCondition(RepairStaff repairStaff);
    @PostMapping("/findRepairStaffCountByCondition")
    long findRepairStaffCountByCondition(RepairStaff repairStaff);
    @PostMapping("/updateRepairStaff")
    void updateRepairStaff(RepairStaff repairStaff);
    @GetMapping("/deleteRepairStaff/{id}")
    void deleteRepairStaff(@PathVariable("id") String id);
    @PostMapping("/deleteRepairStaffByCondition")
    void deleteRepairStaffByCondition(RepairStaff repairStaff);


    @PostMapping("/findRepairStaffDormsByCondition")
    List<RepairStaff> findRepairStaffDormsByCondition(RepairStaff repairStaff);
    @PostMapping("/findRepairStaffCountByCondition1")
    long findRepairStaffCountByCondition1(RepairStaff repairStaff);

}
