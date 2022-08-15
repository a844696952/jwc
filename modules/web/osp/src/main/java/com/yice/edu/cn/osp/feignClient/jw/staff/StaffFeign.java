package com.yice.edu.cn.osp.feignClient.jw.staff;

import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jw",contextId = "staffFeign",path = "/staff")
public interface StaffFeign {
    @GetMapping("/findStaffById/{id}")
    Teacher findStaffById(@PathVariable("id") String id);
    @PostMapping("/saveStaff")
    Teacher saveStaff(Teacher staff);
    @PostMapping("/findStaffListByCondition")
    List<Teacher> findStaffListByCondition(Teacher staff);
    @PostMapping("/findOneStaffByCondition")
    Teacher findOneStaffByCondition(Teacher staff);
    @PostMapping("/findStaffCountByCondition")
    long findStaffCountByCondition(Teacher staff);
    @PostMapping("/updateStaff")
    Teacher updateStaff(Teacher staff);
    @GetMapping("/deleteStaff/{id}")
    void deleteStaff(@PathVariable("id") String id);
    @PostMapping("/deleteStaffByCondition")
    void deleteStaffByCondition(Teacher staff);
}
