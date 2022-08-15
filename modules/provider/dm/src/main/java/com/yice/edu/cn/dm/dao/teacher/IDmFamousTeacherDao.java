package com.yice.edu.cn.dm.dao.teacher;

import java.util.List;

import com.yice.edu.cn.common.pojo.dm.teacher.DmFamousTeacher;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IDmFamousTeacherDao {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    List<DmFamousTeacher> findDmFamousTeacherListByCondition(DmFamousTeacher dmFamousTeacher);

    long findDmFamousTeacherCountByCondition(DmFamousTeacher dmFamousTeacher);

    DmFamousTeacher findOneDmFamousTeacherByCondition(DmFamousTeacher dmFamousTeacher);

    DmFamousTeacher findDmFamousTeacherById(@Param("id") String id);

    void saveDmFamousTeacher(DmFamousTeacher dmFamousTeacher);

    void updateDmFamousTeacher(DmFamousTeacher dmFamousTeacher);

    void updateDmFamousTeacherForAll(DmFamousTeacher dmFamousTeacher);

    void deleteDmFamousTeacher(@Param("id") String id);

    void deleteDmFamousTeacherByCondition(DmFamousTeacher dmFamousTeacher);

    void batchSaveDmFamousTeacher(List<DmFamousTeacher> dmFamousTeachers);
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
