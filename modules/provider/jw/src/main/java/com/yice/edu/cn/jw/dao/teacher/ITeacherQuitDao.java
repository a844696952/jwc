package com.yice.edu.cn.jw.dao.teacher;

import java.util.List;

import com.yice.edu.cn.common.pojo.jw.teacher.TeacherQuit;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ITeacherQuitDao {

    List<TeacherQuit> findQuitTeachers4Yed(TeacherQuit teacherQuit);

    long findQuitTeachersCount4Yed(TeacherQuit teacherQuit);

    List<TeacherQuit> findTeacherQuitListByCondition(TeacherQuit teacherQuit);

    long findTeacherQuitCountByCondition(TeacherQuit teacherQuit);

    TeacherQuit findOneTeacherQuitByCondition(TeacherQuit teacherQuit);

    TeacherQuit findTeacherQuitById(@Param("id") String id);

    void saveTeacherQuit(TeacherQuit teacherQuit);

    void updateTeacherQuit(TeacherQuit teacherQuit);

    void deleteTeacherQuit(@Param("id") String id);

    void deleteTeacherQuitByCondition(TeacherQuit teacherQuit);

    void batchSaveTeacherQuit(List<TeacherQuit> teacherQuits);
}
