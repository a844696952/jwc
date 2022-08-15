package com.yice.edu.cn.dm.dao.student;

import java.util.List;

import com.yice.edu.cn.common.pojo.dm.student.AllStudent;
import com.yice.edu.cn.common.pojo.dm.student.DmMeritStudent;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IDmMeritStudentDao {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    List<DmMeritStudent> findDmMeritStudentListByCondition(DmMeritStudent dmMeritStudent);

    long findDmMeritStudentCountByCondition(DmMeritStudent dmMeritStudent);

    DmMeritStudent findOneDmMeritStudentByCondition(DmMeritStudent dmMeritStudent);

    DmMeritStudent findDmMeritStudentById(@Param("id") String id);

    void saveDmMeritStudent(DmMeritStudent dmMeritStudent);

    void updateDmMeritStudent(DmMeritStudent dmMeritStudent);

    void updateDmMeritStudentForAll(DmMeritStudent dmMeritStudent);

    void deleteDmMeritStudent(@Param("id") String id);

    void deleteDmMeritStudentByCondition(DmMeritStudent dmMeritStudent);

    void batchSaveDmMeritStudent(List<DmMeritStudent> dmMeritStudents);

    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

    List<AllStudent> findAllJwClassesAndStudents(String schoolId);

    List<Student> findStudentsByClassId(String id);

    List<DmMeritStudent> findAll(String schoolId);
}
