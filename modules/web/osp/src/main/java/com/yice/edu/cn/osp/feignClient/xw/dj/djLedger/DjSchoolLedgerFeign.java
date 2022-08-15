package com.yice.edu.cn.osp.feignClient.xw.dj.djLedger;

import com.yice.edu.cn.common.pojo.xw.dj.djLedgerTemplate.DjSchoolLedger;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="xw",contextId = "DjSchoolLedgerFeign",path = "/djSchoolLedger")
public interface DjSchoolLedgerFeign {
    @GetMapping("/findDjSchoolLedgerById/{id}")
    DjSchoolLedger findDjSchoolLedgerById(@PathVariable("id") String id);
    @PostMapping("/saveDjSchoolLedger")
    DjSchoolLedger saveDjSchoolLedger(DjSchoolLedger djSchoolLedger);
    @PostMapping("/findDjSchoolLedgerListByCondition")
    List<DjSchoolLedger> findDjSchoolLedgerListByCondition(DjSchoolLedger djSchoolLedger);
    @PostMapping("/findOneDjSchoolLedgerByCondition")
    DjSchoolLedger findOneDjSchoolLedgerByCondition(DjSchoolLedger djSchoolLedger);
    @PostMapping("/findDjSchoolLedgerCountByCondition")
    long findDjSchoolLedgerCountByCondition(DjSchoolLedger djSchoolLedger);
    @PostMapping("/updateDjSchoolLedger")
    void updateDjSchoolLedger(DjSchoolLedger djSchoolLedger);
    @GetMapping("/deleteDjSchoolLedger/{id}")
    void deleteDjSchoolLedger(@PathVariable("id") String id);
    @PostMapping("/deleteDjSchoolLedgerByCondition")
    void deleteDjSchoolLedgerByCondition(DjSchoolLedger djSchoolLedger);
}
