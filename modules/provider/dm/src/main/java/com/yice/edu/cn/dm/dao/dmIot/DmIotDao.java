package com.yice.edu.cn.dm.dao.dmIot;

import java.util.List;

import com.yice.edu.cn.common.pojo.dm.dmIot.DmIot;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface DmIotDao {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    List<DmIot> findDmIotListByCondition(DmIot dmIot);

    long findDmIotCountByCondition(DmIot dmIot);

    DmIot findOneDmIotByCondition(DmIot dmIot);

    DmIot findDmIotById(@Param("id") String id);

    void saveDmIot(DmIot dmIot);

    void updateDmIot(DmIot dmIot);

    void updateDmIotForAll(DmIot dmIot);

    void deleteDmIot(@Param("id") String id);

    void deleteDmIotByCondition(DmIot dmIot);

    void batchSaveDmIot(List<DmIot> dmIots);
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
