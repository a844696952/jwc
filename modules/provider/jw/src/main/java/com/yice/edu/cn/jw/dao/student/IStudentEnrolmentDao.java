package com.yice.edu.cn.jw.dao.student;

import java.util.List;

import com.yice.edu.cn.common.pojo.jw.student.StudentEnrolment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IStudentEnrolmentDao {
    List<StudentEnrolment> findStudentEnrolmentListByCondition(StudentEnrolment studentEnrolment);

    StudentEnrolment findOneStudentEnrolmentByCondition(StudentEnrolment studentEnrolment);

    long findStudentEnrolmentCountByCondition(StudentEnrolment studentEnrolment);

    StudentEnrolment findStudentEnrolmentById(@Param("id") String id);

    void saveStudentEnrolment(StudentEnrolment studentEnrolment);

    void updateStudentEnrolment(StudentEnrolment studentEnrolment);

    void deleteStudentEnrolment(@Param("id") String id);

    void deleteStudentEnrolmentByCondition(StudentEnrolment studentEnrolment);

    void batchSaveStudentEnrolment(List<StudentEnrolment> studentEnrolments);
}
