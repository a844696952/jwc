package com.yice.edu.cn.dm.dao.honourRoll;

import java.util.List;

import com.yice.edu.cn.common.pojo.dm.classCard.DmDeleteData;
import com.yice.edu.cn.common.pojo.dm.honourRoll.DmHonourRollStudent;
import com.yice.edu.cn.common.pojo.dm.honourRoll.EccHonourRoll;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface DmHonourRollStudentDao {
    List<DmHonourRollStudent> findDmHonourRollStudentListByCondition(DmHonourRollStudent dmHonourRollStudent);

    long findDmHonourRollStudentCountByCondition(DmHonourRollStudent dmHonourRollStudent);

    DmHonourRollStudent findOneDmHonourRollStudentByCondition(DmHonourRollStudent dmHonourRollStudent);

    DmHonourRollStudent findDmHonourRollStudentById(@Param("id") String id);

    void saveDmHonourRollStudent(DmHonourRollStudent dmHonourRollStudent);

    void updateDmHonourRollStudent(DmHonourRollStudent dmHonourRollStudent);

    void deleteDmHonourRollStudent(@Param("id") String id);

    void deleteDmHonourRollStudentByCondition(DmHonourRollStudent dmHonourRollStudent);

    void batchSaveDmHonourRollStudent(List<DmHonourRollStudent> dmHonourRollStudents);

    List<DmHonourRollStudent> findDmHonourRollStudentListByConditions(DmHonourRollStudent dmHonourRollStudent);

    long findDmHonourRollStudentCountByConditions(DmHonourRollStudent dmHonourRollStudent);

    List<EccHonourRoll> getHonourRollList(DmHonourRollStudent dmHonourRollStudent);

    void deleteDmHonourRollStudentByClassId(DmDeleteData dmDeleteData);
}
