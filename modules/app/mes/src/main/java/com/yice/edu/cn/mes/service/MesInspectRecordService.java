package com.yice.edu.cn.mes.service;

import com.yice.edu.cn.common.pojo.general.dd.Dd;
import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import com.yice.edu.cn.common.pojo.mes.schoolManage.inspectRecord.MesInspectRecord;
import com.yice.edu.cn.common.pojo.mes.schoolManage.institution.MesInstitution;
import com.yice.edu.cn.common.pojo.mes.schoolManage.institution.MesUserAuthInstitution;
import com.yice.edu.cn.mes.feign.DdFeign;
import com.yice.edu.cn.mes.feign.JwClassesFeign;
import com.yice.edu.cn.mes.feign.MesInspectRecordFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MesInspectRecordService {
    @Autowired
    private MesInspectRecordFeign mesInspectRecordFeign;
    @Autowired
    private DdFeign ddFeign;

    @Autowired
    private JwClassesFeign jwClassesFeign;

    public MesInspectRecord findMesInspectRecordById(String id) {
        return mesInspectRecordFeign.findMesInspectRecordById(id);
    }

    public Boolean saveMesInspectRecord(MesInspectRecord mesInspectRecord) {
        return mesInspectRecordFeign.saveMesInspectRecord(mesInspectRecord);
    }

    public List<MesInspectRecord> findMesInspectRecordListByCondition(MesInspectRecord mesInspectRecord) {
        return mesInspectRecordFeign.findMesInspectRecordListByCondition(mesInspectRecord);
    }

    public MesInspectRecord findOneMesInspectRecordByCondition(MesInspectRecord mesInspectRecord) {
        return mesInspectRecordFeign.findOneMesInspectRecordByCondition(mesInspectRecord);
    }

    public long findMesInspectRecordCountByCondition(MesInspectRecord mesInspectRecord) {
        return mesInspectRecordFeign.findMesInspectRecordCountByCondition(mesInspectRecord);
    }

    public void updateMesInspectRecord(MesInspectRecord mesInspectRecord) {
        mesInspectRecordFeign.updateMesInspectRecord(mesInspectRecord);
    }

    public void deleteMesInspectRecord(String id) {
        mesInspectRecordFeign.deleteMesInspectRecord(id);
    }

    public void deleteMesInspectRecordByCondition(MesInspectRecord mesInspectRecord) {
        mesInspectRecordFeign.deleteMesInspectRecordByCondition(mesInspectRecord);
    }

    public List<MesInstitution> findMesInstitutionOlList(String userId) {
        return mesInspectRecordFeign.findMesInstitutionOlList(userId);
    }

    public List<MesInstitution> findMesInstitutionTlListByParentId(MesUserAuthInstitution mesUserAuthInstitution) {
        return mesInspectRecordFeign.findMesInstitutionTlListByParentId(mesUserAuthInstitution);
    }

    public MesInspectRecord findReference(String id) {
        return mesInspectRecordFeign.findReference(id);
    }


    public List<Dd> findDdListBySchoolId(String schoolId) {
        return ddFeign.findDdListBySchoolId(schoolId);
    }

    public List<MesInspectRecord> findHistoryMesInspectRecordListByCondition(MesInspectRecord mesInspectRecord) {
        return mesInspectRecordFeign.findHistoryMesInspectRecordListByCondition(mesInspectRecord);
    }

    public List<JwClasses> findClassesByGradeId(JwClasses jwClasses) {
        return jwClassesFeign.findClassesByGradeId(jwClasses);
    }

    public MesInspectRecord findMesInspectRecordOneById(String id) {
        return mesInspectRecordFeign.findMesInspectRecordOneById(id);
    }

    public Boolean ifHaveRecord(MesInspectRecord mesInspectRecord) {
        return mesInspectRecordFeign.ifHaveRecord(mesInspectRecord);
    }

}
