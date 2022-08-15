package com.yice.edu.cn.osp.feignClient.dy.classManage.ruleRecord;

import com.yice.edu.cn.common.pojo.jw.parent.Parent;
import com.yice.edu.cn.common.pojo.mes.classManage.ruleRecord.MesAppletsRuleRecord;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

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
    @PostMapping("/findMesAppletsRuleRecordByStudentIdAndSearchTime")
    MesAppletsRuleRecord findMesAppletsRuleRecordByStudentIdAndSearchTime(MesAppletsRuleRecord mesAppletsRuleRecord);
    @PostMapping("/findScoreChangeByDay")
    List<MesAppletsRuleRecord> findScoreChangeByDay(MesAppletsRuleRecord mesAppletsRuleRecord);
    @PostMapping("/findHighFrequencyMesAppletsRuleRecord")
    List<MesAppletsRuleRecord> findHighFrequencyMesAppletsRuleRecord(MesAppletsRuleRecord mesAppletsRuleRecord);
    @PostMapping("/findStudentRankChange")
    List<MesAppletsRuleRecord> findStudentRankChange(MesAppletsRuleRecord mesAppletsRuleRecord);
    @GetMapping("/findJwParentByStudentId/{studentId}")
    List<Parent> findJwParentByStudentId(@PathVariable("studentId") String studentId);
}
