package com.yice.edu.cn.tap.feignClient.doc;

import com.yice.edu.cn.common.pojo.jw.department.Department;
import com.yice.edu.cn.common.pojo.jw.department.DepartmentTeacher;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jw",path = "/department")
public interface DepartmentFeign {
    @GetMapping("/findDepartmentById/{id}")
    Department findDepartmentById(@PathVariable("id") String id);
    @PostMapping("/saveDepartment")
    Department saveDepartment(Department department);
    @PostMapping("/findDepartmentListByCondition")
    List<Department> findDepartmentListByCondition(Department department);
    @PostMapping("/findOneDepartmentByCondition")
    Department findOneDepartmentByCondition(Department department);
    @PostMapping("/findDepartmentCountByCondition")
    long findDepartmentCountByCondition(Department department);
    @PostMapping("/updateDepartment")
    void updateDepartment(Department department);
    @GetMapping("/deleteDepartment/{id}")
    void deleteDepartment(@PathVariable("id") String id);
    @PostMapping("/deleteDepartmentByCondition")
    void deleteDepartmentByCondition(Department department);
    @GetMapping("/findDepartmentTreeBySchoolId/{schoolId}")
    List<Department> findDepartmentTreeBySchoolId(@PathVariable("schoolId") String schoolId);
    @GetMapping("/findSelectTeachers/{departmentId}")
    List<Teacher> findSelectTeachers(@PathVariable("departmentId") String departmentId);
    @GetMapping("/findAllTeacherBySchoolId/{mySchoolId}")
    List<Teacher> findAllTeacherBySchoolId(@PathVariable("mySchoolId") String mySchoolId);
    @PostMapping("/updateDepartmentMember/{departmentId}")
    void updateDepartmentMember(@PathVariable("departmentId") String departmentId, List<DepartmentTeacher> departmentTeachers);

    @GetMapping("/findDepartmentTreeToSchoolNotifyBySchoolId/{schoolId}")
    List<Department> findDepartmentTreeToSchoolNotifyBySchoolId(@PathVariable("schoolId") String schoolId);


}
