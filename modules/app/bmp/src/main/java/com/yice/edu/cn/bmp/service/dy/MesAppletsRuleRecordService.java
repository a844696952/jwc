package com.yice.edu.cn.bmp.service.dy;

import com.yice.edu.cn.bmp.feignClient.dy.MesAppletsRuleRecordFeign;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.pojo.mes.classManage.ruleRecord.MesAppletsRuleRecord;
import com.yice.edu.cn.common.pojo.mes.wxPush.WxPushDetail;
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

    public List<Student> findStudentByParentId(String parentId) {
        return mesAppletsRuleRecordFeign.findStudentByParentId(parentId);
    }

    public List<Teacher> findClassTeacherTelByClassesId(String classesId) {
        return mesAppletsRuleRecordFeign.findClassTeacherTelByClassesId(classesId);
    }

    public List<MesAppletsRuleRecord> findStudentRankChange(MesAppletsRuleRecord mesAppletsRuleRecord) {
        return mesAppletsRuleRecordFeign.findStudentRankChange(mesAppletsRuleRecord);
    }

    public List<MesAppletsRuleRecord> findScoreChangeByDay(MesAppletsRuleRecord mesAppletsRuleRecord) {
        return mesAppletsRuleRecordFeign.findScoreChangeByDay(mesAppletsRuleRecord);
    }

    public List<MesAppletsRuleRecord> findHighFrequencyMesAppletsRuleRecord(MesAppletsRuleRecord mesAppletsRuleRecord) {
        return mesAppletsRuleRecordFeign.findHighFrequencyMesAppletsRuleRecord(mesAppletsRuleRecord);
    }

    public MesAppletsRuleRecord findMesAppletsRuleRecordByStudentIdAndSearchTime(MesAppletsRuleRecord mesAppletsRuleRecord) {
        return mesAppletsRuleRecordFeign.findMesAppletsRuleRecordByStudentIdAndSearchTime(mesAppletsRuleRecord);
    }

    public Map findCurrentTermTime(String studentId) {
        return mesAppletsRuleRecordFeign.findCurrentTermTime(studentId);
    }

    public List<WxPushDetail> findWxPushDetailListByOpenId(String openId) {
        return mesAppletsRuleRecordFeign.findWxPushDetailListByOpenId(openId);
    }

    public void updateWxPushDetailRead(String id) {
        mesAppletsRuleRecordFeign.updateWxPushDetailRead(id);
    }

    public long findStudentCount(String studentId) {
        return mesAppletsRuleRecordFeign.findStudentCount(studentId);
    }
}
