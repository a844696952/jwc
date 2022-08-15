package com.yice.edu.cn.bmp.feignClient.dy;

import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.pojo.mes.classManage.ruleRecord.MesAppletsRuleRecord;
import com.yice.edu.cn.common.pojo.mes.wxPush.WxPushDetail;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

@FeignClient(value="dy",contextId = "mesAppletsRuleRecordFeign",path = "/mesAppletsRuleRecord")
public interface MesAppletsRuleRecordFeign {
    @GetMapping("/findMesAppletsRuleRecordById/{id}")
    MesAppletsRuleRecord findMesAppletsRuleRecordById(@PathVariable("id") String id);
    @PostMapping("/saveMesAppletsRuleRecord")
    MesAppletsRuleRecord saveMesAppletsRuleRecord(MesAppletsRuleRecord mesAppletsRuleRecord);
    @PostMapping("/findMesAppletsRuleRecordListByCondition")
    List<MesAppletsRuleRecord> findMesAppletsRuleRecordListByCondition(MesAppletsRuleRecord mesAppletsRuleRecord);
    @PostMapping("/findOneMesAppletsRuleRecordByCondition")
    MesAppletsRuleRecord findOneMesAppletsRuleRecordByCondition(MesAppletsRuleRecord mesAppletsRuleRecord);
    @PostMapping("/findMesAppletsRuleRecordCountByCondition")
    long findMesAppletsRuleRecordCountByCondition(MesAppletsRuleRecord mesAppletsRuleRecord);
    @PostMapping("/updateMesAppletsRuleRecord")
    void updateMesAppletsRuleRecord(MesAppletsRuleRecord mesAppletsRuleRecord);
    @GetMapping("/deleteMesAppletsRuleRecord/{id}")
    void deleteMesAppletsRuleRecord(@PathVariable("id") String id);
    @PostMapping("/deleteMesAppletsRuleRecordByCondition")
    void deleteMesAppletsRuleRecordByCondition(MesAppletsRuleRecord mesAppletsRuleRecord);
    @GetMapping("/findStudentByParentId/{parentId}")
    List<Student> findStudentByParentId(@PathVariable("parentId") String parentId);
    @GetMapping("/findClassTeacherTelByClassesId/{classesId}")
    List<Teacher> findClassTeacherTelByClassesId(@PathVariable("classesId") String classesId);
    @PostMapping("/findStudentRankChange")
    List<MesAppletsRuleRecord> findStudentRankChange(MesAppletsRuleRecord mesAppletsRuleRecord);
    @PostMapping("/findScoreChangeByDay")
    List<MesAppletsRuleRecord> findScoreChangeByDay(MesAppletsRuleRecord mesAppletsRuleRecord);
    @PostMapping("/findHighFrequencyMesAppletsRuleRecord")
    List<MesAppletsRuleRecord> findHighFrequencyMesAppletsRuleRecord(MesAppletsRuleRecord mesAppletsRuleRecord);
    @PostMapping("/findMesAppletsRuleRecordByStudentIdAndSearchTime")
    MesAppletsRuleRecord findMesAppletsRuleRecordByStudentIdAndSearchTime(MesAppletsRuleRecord mesAppletsRuleRecord);
    @GetMapping("/findCurrentTermTime/{studentId}")
    Map findCurrentTermTime(@PathVariable("studentId") String studentId);
    @GetMapping("/findWxPushDetailListByOpenId/{openId}")
    List<WxPushDetail> findWxPushDetailListByOpenId(@PathVariable("openId") String openId);
    @GetMapping("/updateWxPushDetailRead/{id}")
    void updateWxPushDetailRead(@PathVariable("id") String id);
    @GetMapping("/findStudentCount/{studentId}")
    long findStudentCount(@PathVariable("studentId") String studentId);
}
