package com.yice.edu.cn.osp.service.jw.exam.buildPaper.paper;

import com.yice.edu.cn.common.pojo.jy.subjectSourse.SubjectMaterial;
import com.yice.edu.cn.osp.feignClient.jw.exam.buildPaper.paper.SubjectMaterialFeignClone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SubjectMaterialServerClone {
    @Autowired
    private SubjectMaterialFeignClone subjectMaterialFeignClone;

    public List<SubjectMaterial> findSubjectMaterialListByCondition(SubjectMaterial subjectMaterial) {
        return subjectMaterialFeignClone.findSubjectMaterialListByCondition(subjectMaterial);
    }

    public  SubjectMaterial findSubjectMaterialById(String id){
        return subjectMaterialFeignClone.findSubjectMaterialById(id);
    }
}
