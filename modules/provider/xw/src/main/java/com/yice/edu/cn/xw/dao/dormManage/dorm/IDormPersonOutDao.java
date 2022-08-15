package com.yice.edu.cn.xw.dao.dormManage.dorm;

import java.util.List;

import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.DormPersonLog;
import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.DormPersonOut;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IDormPersonOutDao {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    List<DormPersonOut> findDormPersonOutListByCondition(DormPersonOut dormPersonOut);

    long findDormPersonOutCountByCondition(DormPersonOut dormPersonOut);

    DormPersonOut findOneDormPersonOutByCondition(DormPersonOut dormPersonOut);

    DormPersonOut findDormPersonOutById(@Param("id") String id);

    void saveDormPersonOut(DormPersonOut dormPersonOut);

    void updateDormPersonOut(DormPersonOut dormPersonOut);

    void deleteDormPersonOut(@Param("id") String id);

    void deleteDormPersonOutByCondition(DormPersonOut dormPersonOut);

    void batchSaveDormPersonOut(List<DormPersonOut> dormPersonOuts);
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    List<DormPersonOut> findDormPersonOutListByConditionAndPersonType(DormPersonOut dormPersonOut);

    long findDormPersonOutCountByConditionAndPersonType(DormPersonOut dormPersonOut);

    void deleteDormPersonOutForStudentByTime();

    void deleteDormPersonLogForStudentByTime();
}
