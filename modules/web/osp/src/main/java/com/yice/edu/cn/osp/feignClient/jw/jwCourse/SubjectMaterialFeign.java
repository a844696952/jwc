package com.yice.edu.cn.osp.feignClient.jw.jwCourse;

import com.yice.edu.cn.common.pojo.jy.subjectSourse.SubjectMaterial;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Repository
@FeignClient(value = "jy",contextId = "subjectMaterialFeign",path = "/subjectMaterial")
public interface SubjectMaterialFeign {
    @PostMapping("/findSubjectMaterialListByCondition")
    List<SubjectMaterial> findSubjectMaterialListByCondition(SubjectMaterial subjectMaterial);

}
