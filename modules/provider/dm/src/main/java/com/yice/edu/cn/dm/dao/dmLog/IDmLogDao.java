package com.yice.edu.cn.dm.dao.dmLog;

import java.util.List;

import com.yice.edu.cn.common.pojo.dm.classCard.DmLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

@Mapper
public interface IDmLogDao {
    List<DmLog> findDmLogListByCondition(DmLog dmLog);

    long findDmLogCountByCondition(DmLog dmLog);

    DmLog findOneDmLogByCondition(DmLog dmLog);

    DmLog findDmLogById(@Param("id") String id);

    void saveDmLog(DmLog dmLog);

    void updateDmLog(DmLog dmLog);

    void deleteDmLog(@Param("id") String id);

    void deleteDmLogByCondition(DmLog dmLog);

    void batchSaveDmLog(List<DmLog> dmLogs);

    List<String> findTeacherPostBySid(@Param(value = "studentId")String studentId);

    List<String> findParentsByStudentId(@Param(value = "studentId")String studentId);

}
