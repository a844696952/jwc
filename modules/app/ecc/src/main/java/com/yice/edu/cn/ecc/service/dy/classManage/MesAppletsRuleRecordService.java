package com.yice.edu.cn.ecc.service.dy.classManage;

import com.yice.edu.cn.common.pojo.mes.classManage.ruleRecord.MesAppletsRuleRecord;
import com.yice.edu.cn.common.pojo.mes.classManage.ruleRecord.MesStudentRecordVo;
import com.yice.edu.cn.ecc.feignClient.dy.classManage.MesAppletsRuleRecordFeign;
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

    public List<MesAppletsRuleRecord> findTodayMesAppletsRuleRecordList(MesAppletsRuleRecord mesAppletsRuleRecord) {
        return mesAppletsRuleRecordFeign.findTodayMesAppletsRuleRecordList(mesAppletsRuleRecord);
    }

    public List<MesStudentRecordVo> findMesStudentRecordVoListByCid(String classId, String schoolId) {
        return mesAppletsRuleRecordFeign.findMesStudentRecordVoListByCid(classId,schoolId);
    }

    public Boolean findJwClaCadresBySid(String studentId) {
        return mesAppletsRuleRecordFeign.findJwClaCadresBySid(studentId);
    }

    public List<MesAppletsRuleRecord> findClassRuleTotalList(MesAppletsRuleRecord mesAppletsRuleRecord){
        List<MesAppletsRuleRecord> list = mesAppletsRuleRecordFeign.findClassRuleTotalList(mesAppletsRuleRecord);
        if (list.size()<3){
            return list;
        }
        return list.subList(0,2);
    }
}
