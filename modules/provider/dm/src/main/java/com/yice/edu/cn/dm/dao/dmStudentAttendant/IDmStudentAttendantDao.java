package com.yice.edu.cn.dm.dao.dmStudentAttendant;

import java.util.List;

import com.yice.edu.cn.common.pojo.dm.dmStudentAttendant.DmStudentAttendant;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IDmStudentAttendantDao {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    List<DmStudentAttendant> findDmStudentAttendantListByCondition(DmStudentAttendant dmStudentAttendant);

    long findDmStudentAttendantCountByCondition(DmStudentAttendant dmStudentAttendant);

    DmStudentAttendant findOneDmStudentAttendantByCondition(DmStudentAttendant dmStudentAttendant);

    DmStudentAttendant findDmStudentAttendantById(@Param("id") String id);

    void saveDmStudentAttendant(DmStudentAttendant dmStudentAttendant);

    void updateDmStudentAttendant(DmStudentAttendant dmStudentAttendant);

    void updateDmStudentAttendantForNotNull(DmStudentAttendant dmStudentAttendant);

    void deleteDmStudentAttendant(@Param("id") String id);

    void deleteDmStudentAttendantByCondition(DmStudentAttendant dmStudentAttendant);

    void batchSaveDmStudentAttendant(List<DmStudentAttendant> dmStudentAttendants);
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    void deleteDmStudentAttendantByClassIds(@Param("clazzIds") List<String> clazzIds);

}
