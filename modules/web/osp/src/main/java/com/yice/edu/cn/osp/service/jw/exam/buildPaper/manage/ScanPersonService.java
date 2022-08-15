package com.yice.edu.cn.osp.service.jw.exam.buildPaper.manage;

import com.yice.edu.cn.common.pojo.jw.exam.examManage.ScanPerson;
import com.yice.edu.cn.osp.feignClient.jw.exam.buildPaper.examManage.ScanPersonFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ScanPersonService {
    @Autowired
    private ScanPersonFeign scanPersonFeign;

    public ScanPerson findScanPersonById(String id) {
        return scanPersonFeign.findScanPersonById(id);
    }

    public ScanPerson saveScanPerson(ScanPerson scanPerson) {
        return scanPersonFeign.saveScanPerson(scanPerson);
    }

    public List<ScanPerson> findScanPersonListByCondition(ScanPerson scanPerson) {
        return scanPersonFeign.findScanPersonListByCondition(scanPerson);
    }

    public ScanPerson findOneScanPersonByCondition(ScanPerson scanPerson) {
        return scanPersonFeign.findOneScanPersonByCondition(scanPerson);
    }

    public long findScanPersonCountByCondition(ScanPerson scanPerson) {
        return scanPersonFeign.findScanPersonCountByCondition(scanPerson);
    }

    public void updateScanPerson(ScanPerson scanPerson) {
        scanPersonFeign.updateScanPerson(scanPerson);
    }

    public void deleteScanPerson(String id) {
        scanPersonFeign.deleteScanPerson(id);
    }

    public void deleteScanPersonByCondition(ScanPerson scanPerson) {
        scanPersonFeign.deleteScanPersonByCondition(scanPerson);
    }

    public List<ScanPerson>  findAllScanPerson(ScanPerson scanPerson){
        return scanPersonFeign.findAllScanPerson(scanPerson);
    }

    public List<ScanPerson> batchSaveScanPerson(List<ScanPerson> scanPersons){
        return scanPersonFeign.batchSaveScanPerson(scanPersons);
    }

    public List<ScanPerson> findTeacherByIds(ScanPerson scanPersons){
        return scanPersonFeign.findTeacherByIds(scanPersons);
    }


}
