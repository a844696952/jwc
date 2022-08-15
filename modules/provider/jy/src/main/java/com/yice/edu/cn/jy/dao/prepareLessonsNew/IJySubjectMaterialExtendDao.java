package com.yice.edu.cn.jy.dao.prepareLessonsNew;

import java.util.List;

import com.yice.edu.cn.common.pojo.jy.prepareLessonsNew.JySubjectMaterialExtend;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IJySubjectMaterialExtendDao {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    List<JySubjectMaterialExtend> findJySubjectMaterialExtendListByCondition(JySubjectMaterialExtend jySubjectMaterialExtend);

    long findJySubjectMaterialExtendCountByCondition(JySubjectMaterialExtend jySubjectMaterialExtend);

    JySubjectMaterialExtend findOneJySubjectMaterialExtendByCondition(JySubjectMaterialExtend jySubjectMaterialExtend);

    JySubjectMaterialExtend findJySubjectMaterialExtendById(@Param("id") String id);

    void saveJySubjectMaterialExtend(JySubjectMaterialExtend jySubjectMaterialExtend);

    void updateJySubjectMaterialExtend(JySubjectMaterialExtend jySubjectMaterialExtend);

    void updateJySubjectMaterialExtendForAll(JySubjectMaterialExtend jySubjectMaterialExtend);

    void deleteJySubjectMaterialExtend(@Param("id") String id);

    void deleteJySubjectMaterialExtendByCondition(JySubjectMaterialExtend jySubjectMaterialExtend);

    void batchSaveJySubjectMaterialExtend(List<JySubjectMaterialExtend> jySubjectMaterialExtends);
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
