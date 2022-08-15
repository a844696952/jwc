package com.yice.edu.cn.jw.dao.practice;

import java.util.List;

import com.yice.edu.cn.common.pojo.jw.practice.PracticeTeacher;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IPracticeTeacherDao {
    List<PracticeTeacher> findPracticeTeacherListByCondition(PracticeTeacher practiceTeacher);

    PracticeTeacher findOnePracticeTeacherByCondition(PracticeTeacher practiceTeacher);

    long findPracticeTeacherCountByCondition(PracticeTeacher practiceTeacher);

    PracticeTeacher findPracticeTeacherById(@Param("id") String id);

    void savePracticeTeacher(PracticeTeacher practiceTeacher);

    void updatePracticeTeacher(PracticeTeacher practiceTeacher);

    void deletePracticeTeacher(@Param("id") String id);

    void deletePracticeTeacherByCondition(PracticeTeacher practiceTeacher);

    void batchSavePracticeTeacher(List<PracticeTeacher> practiceTeachers);

    List<PracticeTeacher> findPracticeTeacherListById(@Param("id") String id);

    List<PracticeTeacher> findPracticeTeacherNameById(@Param("id") String id);
}
