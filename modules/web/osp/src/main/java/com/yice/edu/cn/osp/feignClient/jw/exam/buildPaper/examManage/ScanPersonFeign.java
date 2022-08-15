package com.yice.edu.cn.osp.feignClient.jw.exam.buildPaper.examManage;

import com.yice.edu.cn.common.pojo.jw.exam.examManage.ScanPerson;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jw",contextId = "scanPersonFeign",path = "/scanPerson")
public interface ScanPersonFeign {
    @GetMapping("/findScanPersonById/{id}")
    ScanPerson findScanPersonById(@PathVariable("id") String id);
    @PostMapping("/saveScanPerson")
    ScanPerson saveScanPerson(ScanPerson scanPerson);
    @PostMapping("/findScanPersonListByCondition")
    List<ScanPerson> findScanPersonListByCondition(ScanPerson scanPerson);
    @PostMapping("/findOneScanPersonByCondition")
    ScanPerson findOneScanPersonByCondition(ScanPerson scanPerson);
    @PostMapping("/findScanPersonCountByCondition")
    long findScanPersonCountByCondition(ScanPerson scanPerson);
    @PostMapping("/updateScanPerson")
    void updateScanPerson(ScanPerson scanPerson);
    @GetMapping("/deleteScanPerson/{id}")
    void deleteScanPerson(@PathVariable("id") String id);
    @PostMapping("/deleteScanPersonByCondition")
    void deleteScanPersonByCondition(ScanPerson scanPerson);


    @PostMapping("/find/findAllScanPerson")
    List<ScanPerson> findAllScanPerson(ScanPerson scanPerson);

    @PostMapping("/ignore/addScanPersonList")
    List<ScanPerson> batchSaveScanPerson(List<ScanPerson> scanPersons);

    @PostMapping("/ignore/findTeacherByIds")
    List<ScanPerson> findTeacherByIds(ScanPerson scanPerson);
}
