package com.yice.edu.cn.jw.dao.electiveCourse;

import com.yice.edu.cn.common.pojo.jw.electiveCourse.ElectiveSet;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IElectiveSetDao {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    List<ElectiveSet> findElectiveSetListByCondition(ElectiveSet electiveSet);

    long findElectiveSetCountByCondition(ElectiveSet electiveSet);

    ElectiveSet findOneElectiveSetByCondition(ElectiveSet electiveSet);

    ElectiveSet findElectiveSetById(@Param("id") String id);

    void saveElectiveSet(ElectiveSet electiveSet);

    void updateElectiveSet(ElectiveSet electiveSet);

    void deleteElectiveSet(@Param("id") String id);

    void deleteElectiveSetByCondition(ElectiveSet electiveSet);

    void batchSaveElectiveSet(List<ElectiveSet> electiveSets);
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
