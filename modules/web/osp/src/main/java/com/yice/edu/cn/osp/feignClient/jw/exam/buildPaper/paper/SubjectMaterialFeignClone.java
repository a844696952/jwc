package com.yice.edu.cn.osp.feignClient.jw.exam.buildPaper.paper;

import com.yice.edu.cn.common.pojo.jy.subjectSourse.SubjectMaterial;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jy",contextId = "subjectMaterialFeignClone",path = "/subjectMaterial")
public interface SubjectMaterialFeignClone {

    @PostMapping("/findSubjectMaterialListByCondition")
    List<SubjectMaterial> findSubjectMaterialListByCondition(SubjectMaterial subjectMaterial);

    @GetMapping("/findSubjectMaterialById/{id}")
    SubjectMaterial findSubjectMaterialById(@PathVariable("id") String id);
}
