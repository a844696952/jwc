package com.yice.edu.cn.dm.dao.dmStudentAttendant;

import java.util.List;

import com.yice.edu.cn.common.pojo.dm.dmStudentAttendant.DmStudentAttendantList;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IDmStudentAttendantListDao {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    List<DmStudentAttendantList> findDmStudentAttendantListListByCondition(DmStudentAttendantList dmStudentAttendantList);

    long findDmStudentAttendantListCountByCondition(DmStudentAttendantList dmStudentAttendantList);

    DmStudentAttendantList findOneDmStudentAttendantListByCondition(DmStudentAttendantList dmStudentAttendantList);

    DmStudentAttendantList findDmStudentAttendantListById(@Param("id") String id);

    void saveDmStudentAttendantList(DmStudentAttendantList dmStudentAttendantList);

    void updateDmStudentAttendantList(DmStudentAttendantList dmStudentAttendantList);

    void updateDmStudentAttendantListForAll(DmStudentAttendantList dmStudentAttendantList);

    void deleteDmStudentAttendantList(@Param("id") String id);

    void deleteDmStudentAttendantListByCondition(DmStudentAttendantList dmStudentAttendantList);

    void batchSaveDmStudentAttendantList(List<DmStudentAttendantList> dmStudentAttendantLists);

    void deleteDmStudentAttendantByClassIds(@Param("clazzIds") List<String> clazzIds);
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
