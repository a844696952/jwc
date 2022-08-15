package com.yice.edu.cn.osp.service.dy.schoolManage;

import com.yice.edu.cn.common.pojo.mes.schoolManage.inspectRecord.MesInspectRecord;
import com.yice.edu.cn.osp.feignClient.dy.schoolManage.MesInspectRecordFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MesInspectRecordService {
    @Autowired
    private MesInspectRecordFeign mesInspectRecordFeign;

    public MesInspectRecord findMesInspectRecordById(String id) {
        return mesInspectRecordFeign.findMesInspectRecordById(id);
    }

    public MesInspectRecord saveMesInspectRecord(MesInspectRecord mesInspectRecord) {
        return mesInspectRecordFeign.saveMesInspectRecord(mesInspectRecord);
    }

    public List<MesInspectRecord> findMesInspectRecordListByCondition(MesInspectRecord mesInspectRecord) {
        return mesInspectRecordFeign.findMesInspectRecordListByCondition(mesInspectRecord);
    }

    public MesInspectRecord findOneMesInspectRecordByCondition(MesInspectRecord mesInspectRecord) {
        return mesInspectRecordFeign.findOneMesInspectRecordByCondition(mesInspectRecord);
    }

    public long findMesInspectRecordCountByCondition(MesInspectRecord mesInspectRecord) {
        return mesInspectRecordFeign.findMesInspectRecordCountByCondition(mesInspectRecord);
    }

    public void updateMesInspectRecord(MesInspectRecord mesInspectRecord) {
        mesInspectRecordFeign.updateMesInspectRecord(mesInspectRecord);
    }

    public void deleteMesInspectRecord(String id) {
        mesInspectRecordFeign.deleteMesInspectRecord(id);
    }

    public void deleteMesInspectRecordByCondition(MesInspectRecord mesInspectRecord) {
        mesInspectRecordFeign.deleteMesInspectRecordByCondition(mesInspectRecord);
    }

    public List<MesInspectRecord> findTlInstitutionStatistics(Integer type, String schoolId) {
        return mesInspectRecordFeign.findTlInstitutionStatistics(type,schoolId);
    }

    public List<MesInspectRecord> findRadarStatistics(Integer type, String schoolId) {
        return mesInspectRecordFeign.findRadarStatistics(type,schoolId);
    }

    public Map<String,Object> findMesInspectRecordByClassId(MesInspectRecord mesInspectRecord) {
        return mesInspectRecordFeign.findMesInspectRecordByClassId(mesInspectRecord);
    }

    public long findMesInspectRecordCountByClassId(MesInspectRecord mesInspectRecord) {
        return mesInspectRecordFeign.findMesInspectRecordCountByClassId(mesInspectRecord);
    }

    public Map findMesInspectRecordByRecordId(String id) {
        return mesInspectRecordFeign.findMesInspectRecordByRecordId(id);
    }

    public Map<String, List<MesInspectRecord>> findMesInspectRecordListByClassId(String classId) {
        return mesInspectRecordFeign.findMesInspectRecordListByClassId(classId);
    }
}
