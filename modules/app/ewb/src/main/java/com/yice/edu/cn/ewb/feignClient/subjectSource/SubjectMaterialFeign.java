package com.yice.edu.cn.ewb.feignClient.subjectSource;


import java.util.List;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.qusBankResource.ResourceVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.yice.edu.cn.common.pojo.jy.subjectSourse.SubjectMaterial;


@FeignClient(value="jy",contextId = "subjectMaterialFeign",path = "/subjectMaterial")
public interface SubjectMaterialFeign {
    @GetMapping("/findSubjectMaterialById/{id}")
    SubjectMaterial findSubjectMaterialById(@PathVariable("id") String id);
    @PostMapping("/saveSubjectMaterial")
    SubjectMaterial saveSubjectMaterial(SubjectMaterial subjectMaterial);
    @PostMapping("/findSubjectMaterialListByCondition")
    List<SubjectMaterial> findSubjectMaterialListByCondition(SubjectMaterial subjectMaterial);
    @PostMapping("/findOneSubjectMaterialByCondition")
    SubjectMaterial findOneSubjectMaterialByCondition(SubjectMaterial subjectMaterial);
    @PostMapping("/findSubjectMaterialCountByCondition")
    long findSubjectMaterialCountByCondition(SubjectMaterial subjectMaterial);
    @PostMapping("/updateSubjectMaterial")
    void updateSubjectMaterial(SubjectMaterial subjectMaterial);
    @GetMapping("/deleteSubjectMaterial/{id}")
    void deleteSubjectMaterial(@PathVariable("id") String id);
    @PostMapping("/deleteSubjectMaterialByCondition")
    void deleteSubjectMaterialByCondition(SubjectMaterial subjectMaterial);
    @PostMapping("/findSubjectMaterialBySubject")
    ResponseJson findSubjectMaterialBySubject(ResourceVo resourceVo);
}
