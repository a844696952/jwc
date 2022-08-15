package com.yice.edu.cn.tap.service.department;

import com.yice.edu.cn.common.pojo.jw.department.DepartmentTeacher;
import com.yice.edu.cn.tap.feignClient.department.DepartmentTeacherFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentTeacherService {
    @Autowired
    private DepartmentTeacherFeign departmentTeacherFeign;

    public DepartmentTeacher findDepartmentTeacherById(String id) {
        return departmentTeacherFeign.findDepartmentTeacherById(id);
    }

    public DepartmentTeacher saveDepartmentTeacher(DepartmentTeacher departmentTeacher) {
        return departmentTeacherFeign.saveDepartmentTeacher(departmentTeacher);
    }

    public List<DepartmentTeacher> findDepartmentTeacherListByCondition(DepartmentTeacher departmentTeacher) {
        return departmentTeacherFeign.findDepartmentTeacherListByCondition(departmentTeacher);
    }

    public DepartmentTeacher findOneDepartmentTeacherByCondition(DepartmentTeacher departmentTeacher) {
        return departmentTeacherFeign.findOneDepartmentTeacherByCondition(departmentTeacher);
    }

    public long findDepartmentTeacherCountByCondition(DepartmentTeacher departmentTeacher) {
        return departmentTeacherFeign.findDepartmentTeacherCountByCondition(departmentTeacher);
    }

    public void updateDepartmentTeacher(DepartmentTeacher departmentTeacher) {
        departmentTeacherFeign.updateDepartmentTeacher(departmentTeacher);
    }

    public void deleteDepartmentTeacher(String id) {
        departmentTeacherFeign.deleteDepartmentTeacher(id);
    }

    public void deleteDepartmentTeacherByCondition(DepartmentTeacher departmentTeacher) {
        departmentTeacherFeign.deleteDepartmentTeacherByCondition(departmentTeacher);
    }

    public void updateDepartmentMember(String teacherId,List<DepartmentTeacher> departmentTeachers){
        departmentTeacherFeign.updateDepartmentMember(teacherId,departmentTeachers);
    }

    public List<DepartmentTeacher> findDepartmentByTeacherId(String teacherId){
        return departmentTeacherFeign.findDepartmentByTeacherId(teacherId);
    }
}
