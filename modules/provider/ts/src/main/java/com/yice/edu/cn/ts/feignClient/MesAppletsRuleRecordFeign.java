package com.yice.edu.cn.ts.feignClient;

import com.yice.edu.cn.common.pojo.jw.parent.Parent;
import com.yice.edu.cn.common.pojo.mes.classManage.ruleRecord.MesAppletsRuleRecord;
import com.yice.edu.cn.common.pojo.mes.classManage.ruleRecord.MesStudentRecordVo;
import com.yice.edu.cn.common.pojo.mes.wxPush.WxPushDetail;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    @PostMapping("/findHighFrequencyMesAppletsRuleRecord")
    List<MesAppletsRuleRecord> findHighFrequencyMesAppletsRuleRecord(MesAppletsRuleRecord mesAppletsRuleRecord);
    @PostMapping("/findMesAppletsRuleRecordByStudentIdAndSearchTime")
    MesAppletsRuleRecord findMesAppletsRuleRecordByStudentIdAndSearchTime(MesAppletsRuleRecord mesAppletsRuleRecord);
    @PostMapping("/findScoreChangeByDay")
    List<MesAppletsRuleRecord> findScoreChangeByDay(MesAppletsRuleRecord mesAppletsRuleRecord);
    @PostMapping("/findStudentRankChange")
    List<MesAppletsRuleRecord> findStudentRankChange(MesAppletsRuleRecord mesAppletsRuleRecord);
    @PostMapping("/findClassRuleTotalList")
    List<MesAppletsRuleRecord> findClassRuleTotalList(MesAppletsRuleRecord mesAppletsRuleRecord);
    @PostMapping("/findClassPikChar")
    MesAppletsRuleRecord findClassPikChar(MesAppletsRuleRecord mesAppletsRuleRecord);
    @PostMapping("/findClassRuleAdvanceList")
    List<MesAppletsRuleRecord> findClassRuleAdvanceList(MesAppletsRuleRecord mesAppletsRuleRecord);
    @PostMapping("/findClassRuleBackList")
    List<MesAppletsRuleRecord> findClassRuleBackList(MesAppletsRuleRecord mesAppletsRuleRecord);
    @GetMapping("/findTeachingClassByTid")
    List<MesAppletsRuleRecord> findTeachingClassByTid(@RequestParam("teacherId") String teacherId, @RequestParam("schoolId") String schoolId);
    @GetMapping("/findMesStudentRecordVoListByCid")
    List<MesStudentRecordVo> findMesStudentRecordVoListByCid(@RequestParam("classId") String classId, @RequestParam("schoolId") String schoolId);
    @GetMapping("/findMesStudentRecordVoListByClassId")
    List<Map<String, Object>> findMesStudentRecordVoListByClassId(@RequestParam("classId") String classId, @RequestParam("schoolId") String schoolId);
    @GetMapping("/findJwParentByStudentId/{studentId}")
    List<Parent> findJwParentByStudentId(@PathVariable("studentId") String studentId);
    @PostMapping("/findClassRankList")
    List<MesAppletsRuleRecord> findClassRankList(MesAppletsRuleRecord mesAppletsRuleRecord);
    @GetMapping("/findTodayRecordOidAndSid")
    List<MesStudentRecordVo> findTodayRecordOidAndSid();
    @GetMapping("/findClassIdByWeeks")
    List<String> findClassIdByWeeks();
    @GetMapping("/findMesStudentRecordVoBySid/{studentId}")
    List<MesStudentRecordVo> findMesStudentRecordVoBySid(@PathVariable("studentId") String studentId);

}
