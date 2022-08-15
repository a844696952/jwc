package com.yice.edu.cn.bmp.service.subjectSource;


import com.yice.edu.cn.bmp.feignClient.subjectSource.SubjectMaterialFeign;
import com.yice.edu.cn.common.pojo.jy.subjectSourse.SubjectMaterial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectMaterialService {
    @Autowired
    private SubjectMaterialFeign subjectMaterialFeign;

    public SubjectMaterial findSubjectMaterialById(String id) {
        return subjectMaterialFeign.findSubjectMaterialById(id);
    }

    public SubjectMaterial saveSubjectMaterial(SubjectMaterial subjectMaterial) {
        return subjectMaterialFeign.saveSubjectMaterial(subjectMaterial);
    }

    public List<SubjectMaterial> findSubjectMaterialListByCondition(SubjectMaterial subjectMaterial) {
        return subjectMaterialFeign.findSubjectMaterialListByCondition(subjectMaterial);
    }

    public SubjectMaterial findOneSubjectMaterialByCondition(SubjectMaterial subjectMaterial) {
        return subjectMaterialFeign.findOneSubjectMaterialByCondition(subjectMaterial);
    }

    public long findSubjectMaterialCountByCondition(SubjectMaterial subjectMaterial) {
        return subjectMaterialFeign.findSubjectMaterialCountByCondition(subjectMaterial);
    }

    public void updateSubjectMaterial(SubjectMaterial subjectMaterial) {
        subjectMaterialFeign.updateSubjectMaterial(subjectMaterial);
    }

    public void deleteSubjectMaterial(String id) {
        subjectMaterialFeign.deleteSubjectMaterial(id);
    }

    public void deleteSubjectMaterialByCondition(SubjectMaterial subjectMaterial) {
        subjectMaterialFeign.deleteSubjectMaterialByCondition(subjectMaterial);
    }
}
