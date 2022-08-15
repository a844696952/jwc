package com.yice.edu.cn.jw.dao.exam.examManage;

import java.util.List;

import com.yice.edu.cn.common.pojo.jw.exam.examManage.ScanPerson;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PostMapping;

@Mapper
public interface IScanPersonDao {
    List<ScanPerson> findScanPersonListByCondition(ScanPerson scanPerson);

    long findScanPersonCountByCondition(ScanPerson scanPerson);

    ScanPerson findOneScanPersonByCondition(ScanPerson scanPerson);

    ScanPerson findScanPersonById(@Param("id") String id);

    void saveScanPerson(ScanPerson scanPerson);

    void updateScanPerson(ScanPerson scanPerson);

    void deleteScanPerson(@Param("id") String id);

    void deleteScanPersonByCondition(ScanPerson scanPerson);

    void batchSaveScanPerson(List<ScanPerson> scanPersons);


    List<ScanPerson> findAllScanPerson(ScanPerson scanPerson);

    List<ScanPerson> findTeacherByIds(ScanPerson scanPerson);
}
