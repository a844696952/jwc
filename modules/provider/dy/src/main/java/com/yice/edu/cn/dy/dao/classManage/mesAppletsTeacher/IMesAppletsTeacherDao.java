package com.yice.edu.cn.dy.dao.classManage.mesAppletsTeacher;

import com.yice.edu.cn.common.pojo.mes.classManage.mesAppletsTeacher.MesAppletsTeacher;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IMesAppletsTeacherDao {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    List<MesAppletsTeacher> findMesAppletsTeacherListByCondition(MesAppletsTeacher mesAppletsTeacher);

    long findMesAppletsTeacherCountByCondition(MesAppletsTeacher mesAppletsTeacher);

    MesAppletsTeacher findOneMesAppletsTeacherByCondition(MesAppletsTeacher mesAppletsTeacher);

    MesAppletsTeacher findMesAppletsTeacherById(@Param("id") String id);

    void saveMesAppletsTeacher(MesAppletsTeacher mesAppletsTeacher);

    void updateMesAppletsTeacher(MesAppletsTeacher mesAppletsTeacher);

    void deleteMesAppletsTeacher(@Param("id") String id);

    void deleteMesAppletsTeacherByCondition(MesAppletsTeacher mesAppletsTeacher);

    void batchSaveMesAppletsTeacher(List<MesAppletsTeacher> mesAppletsTeachers);
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
