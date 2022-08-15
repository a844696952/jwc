package com.yice.edu.cn.osp.service.dy.classManage.ruleRecord;

import com.yice.edu.cn.common.pojo.jw.parent.Parent;
import com.yice.edu.cn.common.pojo.mes.classManage.ruleRecord.MesAppletsRuleRecord;
import com.yice.edu.cn.osp.feignClient.dy.classManage.ruleRecord.MesAppletsRuleRecordFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MesAppletsRuleRecordService {
    @Autowired
    private MesAppletsRuleRecordFeign mesAppletsRuleRecordFeign;

    public MesAppletsRuleRecord findMesAppletsRuleRecordById(String id) {
        return mesAppletsRuleRecordFeign.findMesAppletsRuleRecordById(id);
    }

    public MesAppletsRuleRecord saveMesAppletsRuleRecord(MesAppletsRuleRecord mesAppletsRuleRecord) {
        return mesAppletsRuleRecordFeign.saveMesAppletsRuleRecord(mesAppletsRuleRecord);
    }

    public List<MesAppletsRuleRecord> findMesAppletsRuleRecordListByCondition(MesAppletsRuleRecord mesAppletsRuleRecord) {
        return mesAppletsRuleRecordFeign.findMesAppletsRuleRecordListByCondition(mesAppletsRuleRecord);
    }

    public MesAppletsRuleRecord findOneMesAppletsRuleRecordByCondition(MesAppletsRuleRecord mesAppletsRuleRecord) {
        return mesAppletsRuleRecordFeign.findOneMesAppletsRuleRecordByCondition(mesAppletsRuleRecord);
    }

    public long findMesAppletsRuleRecordCountByCondition(MesAppletsRuleRecord mesAppletsRuleRecord) {
        return mesAppletsRuleRecordFeign.findMesAppletsRuleRecordCountByCondition(mesAppletsRuleRecord);
    }

    public void updateMesAppletsRuleRecord(MesAppletsRuleRecord mesAppletsRuleRecord) {
        mesAppletsRuleRecordFeign.updateMesAppletsRuleRecord(mesAppletsRuleRecord);
    }

    public void deleteMesAppletsRuleRecord(String id) {
        mesAppletsRuleRecordFeign.deleteMesAppletsRuleRecord(id);
    }

    public void deleteMesAppletsRuleRecordByCondition(MesAppletsRuleRecord mesAppletsRuleRecord) {
        mesAppletsRuleRecordFeign.deleteMesAppletsRuleRecordByCondition(mesAppletsRuleRecord);
    }

    public MesAppletsRuleRecord findMesAppletsRuleRecordByStudentIdAndSearchTime(MesAppletsRuleRecord mesAppletsRuleRecord) {
        return mesAppletsRuleRecordFeign.findMesAppletsRuleRecordByStudentIdAndSearchTime(mesAppletsRuleRecord);
    }

    public List<MesAppletsRuleRecord> findScoreChangeByDay(MesAppletsRuleRecord mesAppletsRuleRecord) {
        return mesAppletsRuleRecordFeign.findScoreChangeByDay(mesAppletsRuleRecord);
    }

    public List<MesAppletsRuleRecord> findHighFrequencyMesAppletsRuleRecord(MesAppletsRuleRecord mesAppletsRuleRecord) {
        return mesAppletsRuleRecordFeign.findHighFrequencyMesAppletsRuleRecord(mesAppletsRuleRecord);
    }

    public List<MesAppletsRuleRecord> findStudentRankChange(MesAppletsRuleRecord mesAppletsRuleRecord) {
        return mesAppletsRuleRecordFeign.findStudentRankChange(mesAppletsRuleRecord);
    }

    public List<Parent> findJwParentByStudentId(String studentId) {
        return mesAppletsRuleRecordFeign.findJwParentByStudentId(studentId);
    }
}
