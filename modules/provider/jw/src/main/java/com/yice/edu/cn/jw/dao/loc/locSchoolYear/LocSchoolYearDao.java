package com.yice.edu.cn.jw.dao.loc.locSchoolYear;

import java.util.List;

import com.yice.edu.cn.common.pojo.loc.LocSchoolYear;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LocSchoolYearDao {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    List<LocSchoolYear> findLocSchoolYearListByCondition(LocSchoolYear locSchoolYear);

    long findLocSchoolYearCountByCondition(LocSchoolYear locSchoolYear);

    LocSchoolYear findOneLocSchoolYearByCondition(LocSchoolYear locSchoolYear);

    LocSchoolYear findLocSchoolYearById(@Param("id") String id);

    void saveLocSchoolYear(LocSchoolYear locSchoolYear);

    void updateLocSchoolYear(LocSchoolYear locSchoolYear);

    void updateLocSchoolYearForAll(LocSchoolYear locSchoolYear);

    void deleteLocSchoolYear(@Param("id") String id);

    void deleteLocSchoolYearByCondition(LocSchoolYear locSchoolYear);

    void batchSaveLocSchoolYear(List<LocSchoolYear> locSchoolYears);
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
