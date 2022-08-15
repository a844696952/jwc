package com.yice.edu.cn.dm.dao.honourRoll;

import java.util.List;

import com.yice.edu.cn.common.pojo.dm.classCard.DmDeleteData;
import com.yice.edu.cn.common.pojo.dm.honourRoll.DmHonourRoll;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface DmHonourRollDao {
    List<DmHonourRoll> findDmHonourRollListByCondition(DmHonourRoll dmHonourRoll);

    long findDmHonourRollCountByCondition(DmHonourRoll dmHonourRoll);

    DmHonourRoll findOneDmHonourRollByCondition(DmHonourRoll dmHonourRoll);

    DmHonourRoll findDmHonourRollById(@Param("id") String id);

    void saveDmHonourRoll(DmHonourRoll dmHonourRoll);

    void updateDmHonourRoll(DmHonourRoll dmHonourRoll);

    void deleteDmHonourRoll(@Param("id") String id);

    void deleteDmHonourRollByCondition(DmHonourRoll dmHonourRoll);

    void batchSaveDmHonourRoll(List<DmHonourRoll> dmHonourRolls);

    String getStudentName(String[] studentList);

    void stopHonourRoll(DmHonourRoll dmHonourRoll);

    long findDmHonourRoll(DmHonourRoll dmHonourRoll);

    DmHonourRoll findDmHonourRollByStudentId(@Param("id") String id);

    void deleteDmHonourRollByClassId(DmDeleteData dmDeleteData);
}
