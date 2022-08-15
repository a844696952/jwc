package com.yice.edu.cn.dy.dao.schoolManage.audit;

import com.yice.edu.cn.common.pojo.mes.schoolManage.audit.MesInstitutionAudit;
import com.yice.edu.cn.common.pojo.mes.schoolManage.audit.MesOperateRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IMesInstitutionAuditDao {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    List<MesInstitutionAudit> findMesInstitutionAuditListByCondition(MesInstitutionAudit mesInstitutionAudit);

    long findMesInstitutionAuditCountByCondition(MesInstitutionAudit mesInstitutionAudit);

    MesInstitutionAudit findOneMesInstitutionAuditByCondition(MesInstitutionAudit mesInstitutionAudit);

    MesInstitutionAudit findMesInstitutionAuditById(@Param("id") String id);

    void saveMesInstitutionAudit(MesInstitutionAudit mesInstitutionAudit);

    void updateMesInstitutionAudit(MesInstitutionAudit mesInstitutionAudit);

    void deleteMesInstitutionAudit(@Param("id") String id);

    void deleteMesInstitutionAuditByCondition(MesInstitutionAudit mesInstitutionAudit);

    void batchSaveMesInstitutionAudit(List<MesInstitutionAudit> mesInstitutionAudits);

    List<MesInstitutionAudit> selectMesInstitutionAuditListByCondition(MesInstitutionAudit mesInstitutionAudit);


    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

    int updateStatus(MesOperateRecord mesOperateRecord);


    List<MesInstitutionAudit> selectAuditStatusZero(@Param("dateTime")String dateTime);

    void updateStatusByIds(List<String> ids);

    String selectRecordIdById(String auditId);

    List<MesInstitutionAudit> selectMesInstitutionAuditListBySchoolId(String schoolId);

    void updateStatusBySchoolId(String schoolId);

    long findMesInstitutionAuditListCountByCondition(MesInstitutionAudit mesInstitutionAudit);
}
