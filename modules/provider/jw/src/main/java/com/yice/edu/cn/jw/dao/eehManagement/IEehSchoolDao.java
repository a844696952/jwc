package com.yice.edu.cn.jw.dao.eehManagement;

import java.util.List;

import com.yice.edu.cn.common.pojo.jw.eehManagement.EehSchool;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Mapper
public interface IEehSchoolDao {
    List<EehSchool> findEehSchoolListByCondition(EehSchool eehSchool);

    long findEehSchoolCountByCondition(EehSchool eehSchool);

    EehSchool findOneEehSchoolByCondition(EehSchool eehSchool);

    EehSchool findEehSchoolById(@Param("id") String id);

    void saveEehSchool(EehSchool eehSchool);

    void updateEehSchool(EehSchool eehSchool);

    void deleteEehSchool(@Param("id") String id);

    void deleteEehSchoolByCondition(EehSchool eehSchool);

    void batchSaveEehSchool(List<EehSchool> eehSchools);

    List<EehSchool> findCheckEehSchoolListById(@Param("id") String id);

    List<EehSchool> findEehSchoolListByEehIds(@RequestBody List<String> eehids);
}
