package com.yice.edu.cn.osp.feignClient.dy.schoolManage;

import com.yice.edu.cn.common.pojo.mes.schoolManage.audit.MesInstitutionAudit;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

@FeignClient(value="dy",contextId = "mesInstitutionAuditFeign",path = "/mesInstitutionAudit")
public interface MesInstitutionAuditFeign {
    @GetMapping("/findMesInstitutionAuditById/{id}")
    MesInstitutionAudit findMesInstitutionAuditById(@PathVariable("id") String id);
    @PostMapping("/saveMesInstitutionAudit")
    MesInstitutionAudit saveMesInstitutionAudit(MesInstitutionAudit mesInstitutionAudit);
    @PostMapping("/findMesInstitutionAuditListByCondition")
    List<MesInstitutionAudit> findMesInstitutionAuditListByCondition(MesInstitutionAudit mesInstitutionAudit);
    @PostMapping("/findOneMesInstitutionAuditByCondition")
    MesInstitutionAudit findOneMesInstitutionAuditByCondition(MesInstitutionAudit mesInstitutionAudit);
    @PostMapping("/findMesInstitutionAuditCountByCondition")
    long findMesInstitutionAuditCountByCondition(MesInstitutionAudit mesInstitutionAudit);
    @PostMapping("/updateMesInstitutionAudit")
    void updateMesInstitutionAudit(MesInstitutionAudit mesInstitutionAudit);
    @GetMapping("/deleteMesInstitutionAudit/{id}")
    void deleteMesInstitutionAudit(@PathVariable("id") String id);
    @PostMapping("/deleteMesInstitutionAuditByCondition")
    void deleteMesInstitutionAuditByCondition(MesInstitutionAudit mesInstitutionAudit);

    @PostMapping("/lookMesInstitutionAuditById/{auditId}")
    Map lookMesInstitutionAuditById(@PathVariable("auditId") String auditId);

    @PostMapping("/findMesInstitutionAuditListCountByCondition")
    long findMesInstitutionAuditListCountByCondition(MesInstitutionAudit mesInstitutionAudit);

    @PostMapping("/findCurrentSchoolYear/{schoolId}")
    Map findCurrentSchoolYear(@PathVariable("schoolId") String schoolId);
}
