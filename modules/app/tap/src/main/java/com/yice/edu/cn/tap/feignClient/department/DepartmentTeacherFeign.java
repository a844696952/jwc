package com.yice.edu.cn.tap.feignClient.department;

import com.yice.edu.cn.common.pojo.jw.department.DepartmentTeacher;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jw",path = "/departmentTeacher")
public interface DepartmentTeacherFeign {
    @GetMapping("/findDepartmentTeacherById/{id}")
    DepartmentTeacher findDepartmentTeacherById(@PathVariable("id") String id);
    @PostMapping("/saveDepartmentTeacher")
    DepartmentTeacher saveDepartmentTeacher(DepartmentTeacher departmentTeacher);
    @PostMapping("/findDepartmentTeacherListByCondition")
    List<DepartmentTeacher> findDepartmentTeacherListByCondition(DepartmentTeacher departmentTeacher);
    @PostMapping("/findOneDepartmentTeacherByCondition")
    DepartmentTeacher findOneDepartmentTeacherByCondition(DepartmentTeacher departmentTeacher);
    @PostMapping("/findDepartmentTeacherCountByCondition")
    long findDepartmentTeacherCountByCondition(DepartmentTeacher departmentTeacher);
    @PostMapping("/updateDepartmentTeacher")
    void updateDepartmentTeacher(DepartmentTeacher departmentTeacher);
    @GetMapping("/deleteDepartmentTeacher/{id}")
    void deleteDepartmentTeacher(@PathVariable("id") String id);
    @PostMapping("/deleteDepartmentTeacherByCondition")
    void deleteDepartmentTeacherByCondition(DepartmentTeacher departmentTeacher);
    @PostMapping("/updateTeacherDepartments/{teacherId}")
    void updateDepartmentMember(@PathVariable("teacherId") String teacherId, List<DepartmentTeacher> departmentTeachers);
    @GetMapping("/findDepartmentByTeacherId/{teacherId}")
    List<DepartmentTeacher> findDepartmentByTeacherId(@PathVariable("teacherId") String teacherId);
}
