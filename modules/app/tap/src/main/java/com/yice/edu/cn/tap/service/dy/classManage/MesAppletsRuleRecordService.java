package com.yice.edu.cn.tap.service.dy.classManage;

import com.yice.edu.cn.common.pojo.jw.parent.Parent;
import com.yice.edu.cn.common.pojo.mes.classManage.ruleRecord.MesAppletsRuleRecord;
import com.yice.edu.cn.common.pojo.mes.classManage.ruleRecord.MesStudentRecordVo;
import com.yice.edu.cn.tap.feignClient.dy.classManage.MesAppletsRuleRecordFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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

    public List<MesAppletsRuleRecord> findHighFrequencyMesAppletsRuleRecord(MesAppletsRuleRecord mesAppletsRuleRecord) {
        return mesAppletsRuleRecordFeign.findHighFrequencyMesAppletsRuleRecord(mesAppletsRuleRecord);
    }

    public MesAppletsRuleRecord findMesAppletsRuleRecordByStudentIdAndSearchTime(MesAppletsRuleRecord mesAppletsRuleRecord) {
        return mesAppletsRuleRecordFeign.findMesAppletsRuleRecordByStudentIdAndSearchTime(mesAppletsRuleRecord);
    }

    public List<MesAppletsRuleRecord> findScoreChangeByDay(MesAppletsRuleRecord mesAppletsRuleRecord) {
        return mesAppletsRuleRecordFeign.findScoreChangeByDay(mesAppletsRuleRecord);
    }

    public List<MesAppletsRuleRecord> findStudentRankChange(MesAppletsRuleRecord mesAppletsRuleRecord) {
        return mesAppletsRuleRecordFeign.findStudentRankChange(mesAppletsRuleRecord);
    }


   public List<MesAppletsRuleRecord> findClassRuleTotalList(MesAppletsRuleRecord mesAppletsRuleRecord){
        return mesAppletsRuleRecordFeign.findClassRuleTotalList(mesAppletsRuleRecord);
   }

   public MesAppletsRuleRecord findClassPikChar(MesAppletsRuleRecord mesAppletsRuleRecord){
       return mesAppletsRuleRecordFeign.findClassPikChar(mesAppletsRuleRecord);
   }

    public List<MesAppletsRuleRecord> findClassRuleAdvanceList(MesAppletsRuleRecord mesAppletsRuleRecord){
        return mesAppletsRuleRecordFeign.findClassRuleAdvanceList(mesAppletsRuleRecord);
    }

    public List<MesAppletsRuleRecord> findClassRuleBackList(MesAppletsRuleRecord mesAppletsRuleRecord){
        return mesAppletsRuleRecordFeign.findClassRuleBackList(mesAppletsRuleRecord);
    }

    public List<MesAppletsRuleRecord> findClassRankList(MesAppletsRuleRecord mesAppletsRuleRecord){
        return mesAppletsRuleRecordFeign.findClassRankList(mesAppletsRuleRecord);
    }


    public List<MesAppletsRuleRecord> findTeachingClassByTid(String teacherId,String schoolId){
        return mesAppletsRuleRecordFeign.findTeachingClassByTid(teacherId,schoolId);
    }

    public List<MesStudentRecordVo> findMesStudentRecordVoListByCid(String classId, String schoolId) {
        return mesAppletsRuleRecordFeign.findMesStudentRecordVoListByCid(classId,schoolId);
    }

    public List<Parent> findJwParentByStudentId(String studentId) {
        return mesAppletsRuleRecordFeign.findJwParentByStudentId(studentId);
    }

    public List<Map<String, Object>> findMesStudentRecordVoListByClassId(String classId, String schoolId) {
        return mesAppletsRuleRecordFeign.findMesStudentRecordVoListByClassId(classId,schoolId);
    }

    public Map findCurrentTermTimeBySchoolId(String schoolId) {
        return mesAppletsRuleRecordFeign.findCurrentTermTimeBySchoolId(schoolId);
    }


    public long findStudentCount(String studentId) {
        return mesAppletsRuleRecordFeign.findStudentCount(studentId);
    }
}
