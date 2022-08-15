package com.yice.edu.cn.xw.dao.dutyArrment;

import java.util.List;

import com.yice.edu.cn.common.pojo.xw.dutyArrment.DutyArrment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IDutyArrmentDao {
    List<DutyArrment> findDutyArrmentListByCondition(DutyArrment dutyArrment);

    long findDutyArrmentCountByCondition(DutyArrment dutyArrment);

    DutyArrment findOneDutyArrmentByCondition(DutyArrment dutyArrment);

    DutyArrment findDutyArrmentById(@Param("id") String id);

    void saveDutyArrment(DutyArrment dutyArrment);

    void updateDutyArrment(DutyArrment dutyArrment);

    void deleteDutyArrment(@Param("id") String id);

    void deleteDutyArrmentByCondition(DutyArrment dutyArrment);

    void batchSaveDutyArrment(List<DutyArrment> dutyArrments);

    List<DutyArrment> findDutyArrmentListByConditionForLike(DutyArrment dutyArrment);

    long findDutyArrmentCountByConditionForLike(DutyArrment dutyArrment);
}
