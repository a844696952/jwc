package com.yice.edu.cn.xw.dao.dj.djLedger;

import java.util.List;

import com.yice.edu.cn.common.pojo.xw.dj.djLedgerTemplate.DjSchoolLedger;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IDjSchoolLedgerDao {
    List<DjSchoolLedger> findDjSchoolLedgerListByCondition(DjSchoolLedger djSchoolLedger);

    long findDjSchoolLedgerCountByCondition(DjSchoolLedger djSchoolLedger);

    DjSchoolLedger findOneDjSchoolLedgerByCondition(DjSchoolLedger djSchoolLedger);

    DjSchoolLedger findDjSchoolLedgerById(@Param("id") String id);

    void saveDjSchoolLedger(DjSchoolLedger djSchoolLedger);

    void updateDjSchoolLedger(DjSchoolLedger djSchoolLedger);

    void deleteDjSchoolLedger(@Param("id") String id);

    void deleteDjSchoolLedgerByCondition(DjSchoolLedger djSchoolLedger);

    void batchSaveDjSchoolLedger(List<DjSchoolLedger> djSchoolLedgers);
}
