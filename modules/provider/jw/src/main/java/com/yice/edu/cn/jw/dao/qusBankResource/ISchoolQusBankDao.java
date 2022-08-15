package com.yice.edu.cn.jw.dao.qusBankResource;

import java.util.List;

import com.yice.edu.cn.common.pojo.jw.qusBankResource.SchoolQusBank;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ISchoolQusBankDao {
    List<SchoolQusBank> findSchoolQusBankListByCondition(SchoolQusBank schoolQusBank);

    SchoolQusBank findOneSchoolQusBankByCondition(SchoolQusBank schoolQusBank);

    long findSchoolQusBankCountByCondition(SchoolQusBank schoolQusBank);

    SchoolQusBank findSchoolQusBankById(@Param("id") String id);

    void saveSchoolQusBank(SchoolQusBank schoolQusBank);

    void updateSchoolQusBank(SchoolQusBank schoolQusBank);

    void deleteSchoolQusBank(@Param("id") String id);

    void deleteSchoolQusBankByCondition(SchoolQusBank schoolQusBank);

    void batchSaveSchoolQusBank(List<SchoolQusBank> schoolQusBanks);
}
