package com.yice.edu.cn.jw.dao.department;

import java.util.List;

import com.yice.edu.cn.common.pojo.jw.department.Department;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IDepartmentDao {
    List<Department> findDepartmentListByCondition(Department department);

    Department findOneDepartmentByCondition(Department department);

    long findDepartmentCountByCondition(Department department);

    Department findDepartmentById(@Param("id") String id);

    void saveDepartment(Department department);

    void updateDepartment(Department department);

    void deleteDepartment(@Param("id") String id);

    void deleteDepartmentByCondition(Department department);

    void batchSaveDepartment(List<Department> departments);

    List<Department> findDepartmentTreeBySchoolId(@Param("schoolId") String schoolId,@Param("personType") int personType);

    List<Teacher> findSelectTeachers(@Param("departmentId") String departmentId);

    List<Teacher> findAllTeacherBySchoolId(@Param("schoolId") String schoolId);

    List<Department> getMyDepartmentNames(@Param("id") String id);
    List<Department> findDepartmentTreeToSchoolNotifyBySchoolId(@Param("schoolId") String schoolId);

    List<Department> findDepartmentInIds(@Param("list") List<String> ids,@Param("schoolId") String schoolId);

    List<Department> findTeacherInIds(@Param("list") List<String> ids,@Param("schoolId") String schoolId);
}
