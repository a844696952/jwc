package com.yice.edu.cn.osp.feignClient.dy.schoolManage;

import com.yice.edu.cn.common.pojo.mes.schoolManage.inspectRecord.MesInspectRecord;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(value="dy",contextId = "mesInspectRecordFeign",path = "/mesInspectRecord")
public interface MesInspectRecordFeign {
    @GetMapping("/findMesInspectRecordById/{id}")
    MesInspectRecord findMesInspectRecordById(@PathVariable("id") String id);
    @PostMapping("/saveMesInspectRecord")
    MesInspectRecord saveMesInspectRecord(MesInspectRecord mesInspectRecord);
    @PostMapping("/findMesInspectRecordListByCondition")
    List<MesInspectRecord> findMesInspectRecordListByCondition(MesInspectRecord mesInspectRecord);
    @PostMapping("/findOneMesInspectRecordByCondition")
    MesInspectRecord findOneMesInspectRecordByCondition(MesInspectRecord mesInspectRecord);
    @PostMapping("/findMesInspectRecordCountByCondition")
    long findMesInspectRecordCountByCondition(MesInspectRecord mesInspectRecord);
    @PostMapping("/updateMesInspectRecord")
    void updateMesInspectRecord(MesInspectRecord mesInspectRecord);
    @GetMapping("/deleteMesInspectRecord/{id}")
    void deleteMesInspectRecord(@PathVariable("id") String id);
    @PostMapping("/deleteMesInspectRecordByCondition")
    void deleteMesInspectRecordByCondition(MesInspectRecord mesInspectRecord);
    @GetMapping("/findTlInstitutionStatistics")
    List<MesInspectRecord> findTlInstitutionStatistics(@RequestParam("type") Integer type, @RequestParam("schoolId") String schoolId);
    @GetMapping("/findRadarStatistics")
    List<MesInspectRecord> findRadarStatistics(@RequestParam("type") Integer type, @RequestParam("schoolId") String schoolId);

    @PostMapping("/findMesInspectRecordByClassId")
    Map<String,Object> findMesInspectRecordByClassId(MesInspectRecord mesInspectRecord);

    @PostMapping("/findMesInspectRecordCountByClassId")
    long findMesInspectRecordCountByClassId(MesInspectRecord mesInspectRecord);

    @GetMapping("/findMesInspectRecordByRecordId/{id}")
    Map findMesInspectRecordByRecordId(@PathVariable("id") String id);

    @GetMapping("/findMesInspectRecordListByClassId/{classId}")
    Map<String, List<MesInspectRecord>> findMesInspectRecordListByClassId(@PathVariable("classId") String classId);
}
