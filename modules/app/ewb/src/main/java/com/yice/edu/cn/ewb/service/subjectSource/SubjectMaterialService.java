package com.yice.edu.cn.ewb.service.subjectSource;


import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.qusBankResource.ResourceVo;
import com.yice.edu.cn.common.pojo.jy.subjectSourse.SubjectMaterial;
import com.yice.edu.cn.ewb.feignClient.subjectSource.SubjectMaterialFeign;
import com.yice.edu.cn.ewb.interceptor.LoginInterceptor;
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

    /**
     * 平台通过科目查询第一级节点
     * 1、我们平台是教材对应的年级
     * 2、21世纪是教材对应的版本
     * @param resourceVo
     * @return
     */
    public ResponseJson findSubjectMaterialBySubject(ResourceVo resourceVo) {
        resourceVo.setStage(LoginInterceptor.currentTeacher().getSchool().getTypeId());
        return subjectMaterialFeign.findSubjectMaterialBySubject(resourceVo);
    }
}
