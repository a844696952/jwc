package com.yice.edu.cn.jy.dao.prepareLessonsNew;

import java.util.List;

import com.yice.edu.cn.common.pojo.jy.prepareLessonsNew.JyMaterialExtend;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IJyMaterialExtendDao {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    List<JyMaterialExtend> findJyMaterialExtendListByCondition(JyMaterialExtend jyMaterialExtend);

    long findJyMaterialExtendCountByCondition(JyMaterialExtend jyMaterialExtend);

    JyMaterialExtend findOneJyMaterialExtendByCondition(JyMaterialExtend jyMaterialExtend);

    JyMaterialExtend findJyMaterialExtendById(@Param("id") String id);

    void saveJyMaterialExtend(JyMaterialExtend jyMaterialExtend);

    void updateJyMaterialExtend(JyMaterialExtend jyMaterialExtend);

    void updateJyMaterialExtendForAll(JyMaterialExtend jyMaterialExtend);

    void deleteJyMaterialExtend(@Param("id") String id);

    void deleteJyMaterialExtendByCondition(JyMaterialExtend jyMaterialExtend);

    void batchSaveJyMaterialExtend(List<JyMaterialExtend> jyMaterialExtends);
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
