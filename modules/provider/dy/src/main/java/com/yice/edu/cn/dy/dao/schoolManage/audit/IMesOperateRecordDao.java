package com.yice.edu.cn.dy.dao.schoolManage.audit;

import com.yice.edu.cn.common.pojo.mes.schoolManage.audit.MesOperateRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IMesOperateRecordDao {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    List<MesOperateRecord> findMesOperateRecordListByCondition(MesOperateRecord mesOperateRecord);

    long findMesOperateRecordCountByCondition(MesOperateRecord mesOperateRecord);

    MesOperateRecord findOneMesOperateRecordByCondition(MesOperateRecord mesOperateRecord);

    MesOperateRecord findMesOperateRecordById(@Param("id") String id);

    void saveMesOperateRecord(MesOperateRecord mesOperateRecord);

    void updateMesOperateRecord(MesOperateRecord mesOperateRecord);

    void deleteMesOperateRecord(@Param("id") String id);

    void deleteMesOperateRecordByCondition(MesOperateRecord mesOperateRecord);

    void batchSaveMesOperateRecord(List<MesOperateRecord> mesOperateRecords);

    MesOperateRecord selectMesOperateRecordForAppeal(String auditId);

    List<MesOperateRecord> findMesOperateRecordsByAuditId(@Param("auditId")String auditId);

    List<MesOperateRecord> selectMesOperateRecordExceptLatestAppeal(@Param("auditId") String auditId, @Param("id") String id);

    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    List<String> selectTeacherIdsByAuditId(String auditId);
}
