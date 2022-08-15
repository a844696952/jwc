package com.yice.edu.cn.jw.dao.student;

import com.yice.edu.cn.common.pojo.jw.student.StudentFamily;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IStudentFamilyDao {
    List<StudentFamily> findStudentFamilyListByCondition(StudentFamily studentFamily);

    long findStudentFamilyCountByCondition(StudentFamily studentFamily);

    List<StudentFamily> findStudentFamilyById(String id);

    void saveStudentFamily(StudentFamily studentFamily);

    void updateStudentFamily(StudentFamily studentFamily);

    void deleteStudentFamily(String id);

    void deleteStudentFamilyByCondition(StudentFamily studentFamily);

    void batchSaveStudentFamily(List<StudentFamily> studentFamilys);
}
