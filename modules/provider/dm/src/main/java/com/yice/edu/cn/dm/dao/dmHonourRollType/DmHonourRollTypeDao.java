package com.yice.edu.cn.dm.dao.dmHonourRollType;

import java.util.List;

import com.yice.edu.cn.common.pojo.dm.dmHonourRollType.DmHonourRollType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface DmHonourRollTypeDao {
    List<DmHonourRollType> findDmHonourRollTypeListByCondition(DmHonourRollType dmHonourRollType);

    long findDmHonourRollTypeCountByCondition(DmHonourRollType dmHonourRollType);

    DmHonourRollType findOneDmHonourRollTypeByCondition(DmHonourRollType dmHonourRollType);

    DmHonourRollType findDmHonourRollTypeById(@Param("id") String id);

    void saveDmHonourRollType(DmHonourRollType dmHonourRollType);

    void updateDmHonourRollType(DmHonourRollType dmHonourRollType);

    void deleteDmHonourRollType(@Param("id") String id);

    void deleteDmHonourRollTypeByCondition(DmHonourRollType dmHonourRollType);

    void batchSaveDmHonourRollType(List<DmHonourRollType> dmHonourRollTypes);
}
