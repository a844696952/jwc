package com.yice.edu.cn.jw.dao.loc.locSchool;

import java.util.List;

import com.yice.edu.cn.common.pojo.loc.LocSchool;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LocSchoolDao {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    List<LocSchool> findLocSchoolListByCondition(LocSchool locSchool);

    long findLocSchoolCountByCondition(LocSchool locSchool);

    LocSchool findOneLocSchoolByCondition(LocSchool locSchool);

    LocSchool findLocSchoolById(@Param("id") String id);

    void saveLocSchool(LocSchool locSchool);

    void updateLocSchool(LocSchool locSchool);

    void updateLocSchoolForAll(LocSchool locSchool);

    void deleteLocSchool(@Param("id") String id);

    void deleteLocSchoolByCondition(LocSchool locSchool);

    void batchSaveLocSchool(List<LocSchool> locSchools);
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

    long findIpRepetitionCount(LocSchool locSchool);
}
