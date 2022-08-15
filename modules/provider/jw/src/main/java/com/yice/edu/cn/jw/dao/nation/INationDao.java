package com.yice.edu.cn.jw.dao.nation;

import java.util.List;

import com.yice.edu.cn.common.pojo.general.nation.Nation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface INationDao {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    List<Nation> findNationListByCondition(Nation nation);

    long findNationCountByCondition(Nation nation);

    Nation findOneNationByCondition(Nation nation);

    Nation findNationById(@Param("id") String id);

    void saveNation(Nation nation);

    void updateNation(Nation nation);

    void deleteNation(@Param("id") String id);

    void deleteNationByCondition(Nation nation);

    void batchSaveNation(List<Nation> nations);
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
