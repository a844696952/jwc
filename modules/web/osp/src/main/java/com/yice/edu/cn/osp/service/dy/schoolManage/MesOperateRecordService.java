package com.yice.edu.cn.osp.service.dy.schoolManage;

import com.yice.edu.cn.common.pojo.mes.schoolManage.audit.MesOperateRecord;
import com.yice.edu.cn.osp.feignClient.dy.schoolManage.MesOperateRecordFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MesOperateRecordService {
    @Autowired
    private MesOperateRecordFeign mesOperateRecordFeign;

    public MesOperateRecord findMesOperateRecordById(String id) {
        return mesOperateRecordFeign.findMesOperateRecordById(id);
    }

    public MesOperateRecord saveMesOperateRecord(MesOperateRecord mesOperateRecord) {
        return mesOperateRecordFeign.saveMesOperateRecord(mesOperateRecord);
    }

    public List<MesOperateRecord> findMesOperateRecordListByCondition(MesOperateRecord mesOperateRecord) {
        return mesOperateRecordFeign.findMesOperateRecordListByCondition(mesOperateRecord);
    }

    public MesOperateRecord findOneMesOperateRecordByCondition(MesOperateRecord mesOperateRecord) {
        return mesOperateRecordFeign.findOneMesOperateRecordByCondition(mesOperateRecord);
    }

    public long findMesOperateRecordCountByCondition(MesOperateRecord mesOperateRecord) {
        return mesOperateRecordFeign.findMesOperateRecordCountByCondition(mesOperateRecord);
    }

    public void updateMesOperateRecord(MesOperateRecord mesOperateRecord) {
        mesOperateRecordFeign.updateMesOperateRecord(mesOperateRecord);
    }

    public void deleteMesOperateRecord(String id) {
        mesOperateRecordFeign.deleteMesOperateRecord(id);
    }

    public void deleteMesOperateRecordByCondition(MesOperateRecord mesOperateRecord) {
        mesOperateRecordFeign.deleteMesOperateRecordByCondition(mesOperateRecord);
    }

    public int passOrRefuse(MesOperateRecord mesOperateRecord) {
        return mesOperateRecordFeign.passOrRefuse(mesOperateRecord);
    }
}
