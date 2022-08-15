package com.yice.edu.cn.osp.feignClient.dy.schoolManage;

import com.yice.edu.cn.common.pojo.mes.schoolManage.audit.MesOperateRecord;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="dy",contextId = "mesOperateRecordFeign",path = "/mesOperateRecord")
public interface MesOperateRecordFeign {
    @GetMapping("/findMesOperateRecordById/{id}")
    MesOperateRecord findMesOperateRecordById(@PathVariable("id") String id);
    @PostMapping("/saveMesOperateRecord")
    MesOperateRecord saveMesOperateRecord(MesOperateRecord mesOperateRecord);
    @PostMapping("/findMesOperateRecordListByCondition")
    List<MesOperateRecord> findMesOperateRecordListByCondition(MesOperateRecord mesOperateRecord);
    @PostMapping("/findOneMesOperateRecordByCondition")
    MesOperateRecord findOneMesOperateRecordByCondition(MesOperateRecord mesOperateRecord);
    @PostMapping("/findMesOperateRecordCountByCondition")
    long findMesOperateRecordCountByCondition(MesOperateRecord mesOperateRecord);
    @PostMapping("/updateMesOperateRecord")
    void updateMesOperateRecord(MesOperateRecord mesOperateRecord);
    @GetMapping("/deleteMesOperateRecord/{id}")
    void deleteMesOperateRecord(@PathVariable("id") String id);
    @PostMapping("/deleteMesOperateRecordByCondition")
    void deleteMesOperateRecordByCondition(MesOperateRecord mesOperateRecord);

    @PostMapping("/passOrRefuse")
    int passOrRefuse(MesOperateRecord mesOperateRecord);
}
