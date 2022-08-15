package com.yice.edu.cn.jw.dao.educationBureau;

import java.util.List;

import com.yice.edu.cn.common.pojo.jw.educationBureau.EducationBureau;
import com.yice.edu.cn.common.pojo.jw.school.School;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IEducationBureauDao {
    List<EducationBureau> findEducationBureauListByCondition(EducationBureau educationBureau);

    EducationBureau findOneEducationBureauByCondition(EducationBureau educationBureau);

    long findEducationBureauCountByCondition(EducationBureau educationBureau);

    EducationBureau findEducationBureauById(@Param("id") String id);

    void saveEducationBureau(EducationBureau educationBureau);

    void updateEducationBureau(EducationBureau educationBureau);

    void deleteEducationBureau(@Param("id") String id);

    void deleteEducationBureauByCondition(EducationBureau educationBureau);

    void batchSaveEducationBureau(List<EducationBureau> educationBureaus);

    List<School> findUnSelectedSchoolsById(@Param("educationBureauId") String educationBureauId);

    List<String> findSelectedSchoolsById(@Param("educationBureauId") String educationBureauId);
}
