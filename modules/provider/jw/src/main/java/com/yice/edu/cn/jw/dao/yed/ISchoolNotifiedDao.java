package com.yice.edu.cn.jw.dao.yed;

import java.util.List;

import com.yice.edu.cn.common.pojo.jw.yed.SchoolNotified;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ISchoolNotifiedDao {
    List<SchoolNotified> findSchoolNotifiedListByCondition(SchoolNotified schoolNotified);

    SchoolNotified findOneSchoolNotifiedByCondition(SchoolNotified schoolNotified);

    long findSchoolNotifiedCountByCondition(SchoolNotified schoolNotified);

    SchoolNotified findSchoolNotifiedById(@Param("id") String id);

    void saveSchoolNotified(SchoolNotified schoolNotified);

    void updateSchoolNotified(SchoolNotified schoolNotified);

    void deleteSchoolNotified(@Param("id") String id);

    void deleteSchoolNotifiedByCondition(SchoolNotified schoolNotified);

    void batchSaveSchoolNotified(List<SchoolNotified> schoolNotifieds);
}
