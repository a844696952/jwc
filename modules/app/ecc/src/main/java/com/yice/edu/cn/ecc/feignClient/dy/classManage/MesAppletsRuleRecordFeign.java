package com.yice.edu.cn.ecc.feignClient.dy.classManage;

import com.yice.edu.cn.common.pojo.mes.classManage.ruleRecord.MesAppletsRuleRecord;
import com.yice.edu.cn.common.pojo.mes.classManage.ruleRecord.MesStudentRecordVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    @PostMapping("/findTodayMesAppletsRuleRecordList")
    List<MesAppletsRuleRecord> findTodayMesAppletsRuleRecordList(MesAppletsRuleRecord mesAppletsRuleRecord);
    @GetMapping("/findMesStudentRecordVoListByCid")
    List<MesStudentRecordVo> findMesStudentRecordVoListByCid(@RequestParam("classId") String classId, @RequestParam("schoolId") String schoolId);
    @GetMapping("/findJwClaCadresBySid/{studentId}")
    Boolean findJwClaCadresBySid(@PathVariable("studentId") String studentId);
    @PostMapping("/findClassRuleTotalList")
    List<MesAppletsRuleRecord> findClassRuleTotalList(MesAppletsRuleRecord mesAppletsRuleRecord);
}
