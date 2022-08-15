package com.yice.edu.cn.xw.dao.department;

import com.yice.edu.cn.common.pojo.jw.department.Department;
import com.yice.edu.cn.common.pojo.jw.department.DepartmentTeacher;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface IDepartmentTeacherDao {
    List<DepartmentTeacher> findDepartmentTeacherListByCondition(DepartmentTeacher departmentTeacher);

    DepartmentTeacher findOneDepartmentTeacherByCondition(DepartmentTeacher departmentTeacher);

    long findDepartmentTeacherCountByCondition(DepartmentTeacher departmentTeacher);

    DepartmentTeacher findDepartmentTeacherById(@Param("id") String id);

    void saveDepartmentTeacher(DepartmentTeacher departmentTeacher);

    void updateDepartmentTeacher(DepartmentTeacher departmentTeacher);

    void deleteDepartmentTeacher(@Param("id") String id);

    void deleteDepartmentTeacherByCondition(DepartmentTeacher departmentTeacher);

    void batchSaveDepartmentTeacher(List<DepartmentTeacher> departmentTeachers);
    @Select("SELECT DISTINCT d.`name` AS departmentName,dt.* FROM jw_department d \n" +
            "INNER JOIN jw_department_teacher dt ON dt.`department_id` = d.`id`\n" +
            "WHERE dt.`teacher_id` = #{teacherId}")
    @ResultType(DepartmentTeacher.class)
    List<DepartmentTeacher> findDepartmentByTeacherId(@Param("teacherId") String teacherId);
    @Select("SELECT DISTINCT d.`id`,d.`name` FROM jw_department d \n" +
            "INNER JOIN jw_department_teacher dt ON dt.`department_id`=d.`id`\n" +
            "INNER JOIN jw_teacher t ON t.`id` = dt.`teacher_id`\n" +
            "WHERE t.`person_type`=2 AND t.`school_id` = #{schoolId}")
    @ResultType(Department.class)
    List<Department> findDepartmentBySchoolId4Staff(@Param("schoolId") String schoolId);
}
