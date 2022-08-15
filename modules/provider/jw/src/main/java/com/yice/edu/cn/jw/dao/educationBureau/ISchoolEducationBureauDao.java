package com.yice.edu.cn.jw.dao.educationBureau;

import java.util.List;

import com.yice.edu.cn.common.pojo.jw.educationBureau.SchoolEducationBureau;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ISchoolEducationBureauDao {
    List<SchoolEducationBureau> findSchoolEducationBureauListByCondition(SchoolEducationBureau schoolEducationBureau);

    SchoolEducationBureau findOneSchoolEducationBureauByCondition(SchoolEducationBureau schoolEducationBureau);

    long findSchoolEducationBureauCountByCondition(SchoolEducationBureau schoolEducationBureau);

    SchoolEducationBureau findSchoolEducationBureauById(@Param("id") String id);

    void saveSchoolEducationBureau(SchoolEducationBureau schoolEducationBureau);

    void updateSchoolEducationBureau(SchoolEducationBureau schoolEducationBureau);

    void deleteSchoolEducationBureau(@Param("id") String id);

    void deleteSchoolEducationBureauByCondition(SchoolEducationBureau schoolEducationBureau);

    void batchSaveSchoolEducationBureau(List<SchoolEducationBureau> schoolEducationBureaus);
}
