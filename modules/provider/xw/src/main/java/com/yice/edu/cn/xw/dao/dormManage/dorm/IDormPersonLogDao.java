package com.yice.edu.cn.xw.dao.dormManage.dorm;

import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.DormPersonLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IDormPersonLogDao {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    List<DormPersonLog> findDormPersonLogListByCondition(DormPersonLog dormPersonLog);

    long findDormPersonLogCountByCondition(DormPersonLog dormPersonLog);

    DormPersonLog findOneDormPersonLogByCondition(DormPersonLog dormPersonLog);

    DormPersonLog findDormPersonLogById(@Param("id") String id);

    void saveDormPersonLog(DormPersonLog dormPersonLog);

    void updateDormPersonLog(DormPersonLog dormPersonLog);

    void deleteDormPersonLog(@Param("id") String id);

    void deleteDormPersonLogByCondition(DormPersonLog dormPersonLog);

    void batchSaveDormPersonLog(List<DormPersonLog> dormPersonLogs);
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
