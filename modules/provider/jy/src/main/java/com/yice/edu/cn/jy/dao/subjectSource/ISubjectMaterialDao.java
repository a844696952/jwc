package com.yice.edu.cn.jy.dao.subjectSource;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.yice.edu.cn.common.pojo.jy.subjectSourse.SubjectMaterial;

@Mapper
public interface ISubjectMaterialDao {
    List<SubjectMaterial> findSubjectMaterialListByCondition(SubjectMaterial subjectMaterial);

    SubjectMaterial findOneSubjectMaterialByCondition(SubjectMaterial subjectMaterial);

    long findSubjectMaterialCountByCondition(SubjectMaterial subjectMaterial);

    SubjectMaterial findSubjectMaterialById(@Param("id") String id);

    void saveSubjectMaterial(SubjectMaterial subjectMaterial);

    void updateSubjectMaterial(SubjectMaterial subjectMaterial);

    void deleteSubjectMaterial(@Param("id") String id);

    void deleteSubjectMaterialByCondition(SubjectMaterial subjectMaterial);

    void batchSaveSubjectMaterial(List<SubjectMaterial> subjectMaterials);
}
