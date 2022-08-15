package com.yice.edu.cn.mes.feign;

import com.yice.edu.cn.common.pojo.mes.schoolManage.inspectRecord.MesInspectRecord;
import com.yice.edu.cn.common.pojo.mes.schoolManage.institution.MesInstitution;
import com.yice.edu.cn.common.pojo.mes.schoolManage.institution.MesUserAuthInstitution;
import com.yice.edu.cn.common.pojo.mes.schoolManage.query.MirQuery;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value = "dy", contextId = "mesInspectRecordFeign", path = "/mesInspectRecord")
public interface MesInspectRecordFeign {
    @GetMapping("/findMesInspectRecordById/{id}")
    MesInspectRecord findMesInspectRecordById(@PathVariable("id") String id);
    @PostMapping("/saveMesInspectRecord")
    Boolean saveMesInspectRecord(MesInspectRecord mesInspectRecord);
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

    /**
     * 根据老师的带班（班主任） 班级Id去查相关的检查记录
     */
    @PostMapping("/findMirAndClassId")
    List<MesInspectRecord> findMirAndClassId(MirQuery query);

    @PostMapping("/findMirAndClassIdCount")
    long findMirAndClassIdCount(MirQuery query);

    @GetMapping("/findMesInstitutionOlList/{userId}")
    List<MesInstitution> findMesInstitutionOlList(@PathVariable("userId") String userId);

    @PostMapping("/findMesInstitutionTlListByParentId")
    List<MesInstitution> findMesInstitutionTlListByParentId(MesUserAuthInstitution mesUserAuthInstitution);

    @GetMapping("/findReference/{id}")
    MesInspectRecord findReference(@PathVariable("id") String id);

    @PostMapping("findHistoryMesInspectRecordListByCondition")
    List<MesInspectRecord> findHistoryMesInspectRecordListByCondition(MesInspectRecord mesInspectRecord);

    @GetMapping("findMesInspectRecordOneById/{id}")
    MesInspectRecord findMesInspectRecordOneById(@PathVariable("id") String id);

    @PostMapping("/ifHaveRecord")
    Boolean ifHaveRecord(MesInspectRecord mesInspectRecord);

}
