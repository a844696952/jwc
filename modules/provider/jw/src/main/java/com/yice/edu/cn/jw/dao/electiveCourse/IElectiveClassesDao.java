package com.yice.edu.cn.jw.dao.electiveCourse;

import com.yice.edu.cn.common.pojo.jw.electiveCourse.ElectiveClasses;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IElectiveClassesDao {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    List<ElectiveClasses> findElectiveClassesListByCondition(ElectiveClasses electiveClasses);

    long findElectiveClassesCountByCondition(ElectiveClasses electiveClasses);

    ElectiveClasses findOneElectiveClassesByCondition(ElectiveClasses electiveClasses);

    ElectiveClasses findElectiveClassesById(@Param("id") String id);

    void saveElectiveClasses(ElectiveClasses electiveClasses);

    void updateElectiveClasses(ElectiveClasses electiveClasses);

    void deleteElectiveClasses(@Param("id") String id);

    void deleteElectiveClassesByCondition(ElectiveClasses electiveClasses);

    void batchSaveElectiveClasses(List<ElectiveClasses> electiveClassess);
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
