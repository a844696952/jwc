package com.yice.edu.cn.tap.service.dy.schoolManage;

import com.yice.edu.cn.common.pojo.mes.schoolManage.audit.MesInstitutionAudit;
import com.yice.edu.cn.tap.feignClient.dy.schoolManage.MesInstitutionAuditFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MesInstitutionAuditService {

    @Autowired
    private MesInstitutionAuditFeign mesInstitutionAuditFeign;

    public MesInstitutionAudit findMesInstitutionAuditById(String id) {
        return mesInstitutionAuditFeign.findMesInstitutionAuditById(id);
    }

    public MesInstitutionAudit saveMesInstitutionAudit(MesInstitutionAudit mesInstitutionAudit) {
        return mesInstitutionAuditFeign.saveMesInstitutionAudit(mesInstitutionAudit);
    }

    public List<MesInstitutionAudit> findMesInstitutionAuditListByCondition(MesInstitutionAudit mesInstitutionAudit) {
        return mesInstitutionAuditFeign.findMesInstitutionAuditListByCondition(mesInstitutionAudit);
    }

    public MesInstitutionAudit findOneMesInstitutionAuditByCondition(MesInstitutionAudit mesInstitutionAudit) {
        return mesInstitutionAuditFeign.findOneMesInstitutionAuditByCondition(mesInstitutionAudit);
    }

    public long findMesInstitutionAuditCountByCondition(MesInstitutionAudit mesInstitutionAudit) {
        return mesInstitutionAuditFeign.findMesInstitutionAuditCountByCondition(mesInstitutionAudit);
    }

    public void updateMesInstitutionAudit(MesInstitutionAudit mesInstitutionAudit) {
        mesInstitutionAuditFeign.updateMesInstitutionAudit(mesInstitutionAudit);
    }

    public void deleteMesInstitutionAudit(String id) {
        mesInstitutionAuditFeign.deleteMesInstitutionAudit(id);
    }

    public void deleteMesInstitutionAuditByCondition(MesInstitutionAudit mesInstitutionAudit) {
        mesInstitutionAuditFeign.deleteMesInstitutionAuditByCondition(mesInstitutionAudit);
    }

    public Map lookMesInstitutionAuditByIdAndRecordId(MesInstitutionAudit mesInstitutionAudit) {
        return mesInstitutionAuditFeign.lookMesInstitutionAuditByIdAndRecordId(mesInstitutionAudit);
    }




    public MesInstitutionAudit saveState(MesInstitutionAudit mesInstitutionAudit) {
        return mesInstitutionAuditFeign.saveState(mesInstitutionAudit);
    }
}
