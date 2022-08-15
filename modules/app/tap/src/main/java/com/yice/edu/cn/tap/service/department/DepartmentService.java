package com.yice.edu.cn.tap.service.department;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.department.Department;
import com.yice.edu.cn.common.pojo.jw.department.DepartmentTeacher;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.util.object.ObjectKit;
import com.yice.edu.cn.tap.feignClient.department.DepartmentFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {
    @Autowired
    private DepartmentFeign departmentFeign;

    public Department findDepartmentById(String id) {
        return departmentFeign.findDepartmentById(id);
    }

    public Department saveDepartment(Department department) {
        return departmentFeign.saveDepartment(department);
    }

    public List<Department> findDepartmentListByCondition(Department department) {
        return departmentFeign.findDepartmentListByCondition(department);
    }

    public Department findOneDepartmentByCondition(Department department) {
        return departmentFeign.findOneDepartmentByCondition(department);
    }

    public long findDepartmentCountByCondition(Department department) {
        return departmentFeign.findDepartmentCountByCondition(department);
    }

    public void updateDepartment(Department department) {
        departmentFeign.updateDepartment(department);
    }

    public void deleteDepartment(String id) {
        departmentFeign.deleteDepartment(id);
    }

    public void deleteDepartmentByCondition(Department department) {
        departmentFeign.deleteDepartmentByCondition(department);
    }

    public List<Department> findDepartmentTreeBySchoolId(String mySchoolId,int personType) {
        return departmentFeign.findDepartmentTreeBySchoolId(mySchoolId,true,personType);
    }

    public List<Teacher> findSelectTeachers(String departmentId) {
        return departmentFeign.findSelectTeachers(departmentId);
    }

    public List<Teacher> findAllTeacherBySchoolId(String mySchoolId) {
        return departmentFeign.findAllTeacherBySchoolId(mySchoolId);
    }

    public void updateDepartmentMember(String departmentId, List<DepartmentTeacher> departmentTeachers) {
        departmentFeign.updateDepartmentMember(departmentId, departmentTeachers);
    }

    public List<Department> findDepartmentHaveOrNotTeacherForTree(String schoolId, boolean forDepartment,int personType) {
        if(forDepartment){
            Department department = new Department();
            department.setSchoolId(schoolId);
            Pager pager = new Pager().setPaging(false).setIncludes("id","name","parentId");
            department.setPager(pager);
            List<Department> departmentList = departmentFeign.findDepartmentListByCondition(department);
            return ObjectKit.buildTree(departmentList,"-1");

        }else{
            return departmentFeign.findDepartmentTreeBySchoolId(schoolId,true,personType);
        }
    }



    public List<Department> findDepartmentTreeToSchoolNotifyBySchoolId( String schoolId){
        return departmentFeign.findDepartmentTreeToSchoolNotifyBySchoolId(schoolId);
    }
}
