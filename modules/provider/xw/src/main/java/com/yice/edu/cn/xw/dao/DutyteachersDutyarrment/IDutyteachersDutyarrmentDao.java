package com.yice.edu.cn.xw.dao.DutyteachersDutyarrment;

import java.util.List;

import com.yice.edu.cn.common.pojo.xw.dutyteachersDutyarrment.DutyteachersDutyarrment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IDutyteachersDutyarrmentDao {
    List<DutyteachersDutyarrment> findDutyteachersDutyarrmentListByCondition(DutyteachersDutyarrment dutyteachersDutyarrment);

    long findDutyteachersDutyarrmentCountByCondition(DutyteachersDutyarrment dutyteachersDutyarrment);

    DutyteachersDutyarrment findOneDutyteachersDutyarrmentByCondition(DutyteachersDutyarrment dutyteachersDutyarrment);

    DutyteachersDutyarrment findDutyteachersDutyarrmentById(@Param("id") String id);

    void saveDutyteachersDutyarrment(DutyteachersDutyarrment dutyteachersDutyarrment);

    void updateDutyteachersDutyarrment(DutyteachersDutyarrment dutyteachersDutyarrment);

    void deleteDutyteachersDutyarrment(@Param("id") String id);

    void deleteDutyteachersDutyarrmentByCondition(DutyteachersDutyarrment dutyteachersDutyarrment);

    void batchSaveDutyteachersDutyarrment(List<DutyteachersDutyarrment> dutyteachersDutyarrments);
}
