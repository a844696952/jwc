package com.yice.edu.cn.tap.service.subjectSource;


import java.util.List;
import java.util.stream.Collectors;

import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.qusBankResource.ResourceVo;
import com.yice.edu.cn.common.pojo.jy.source21.model.common.Version;
import com.yice.edu.cn.tap.interceptor.LoginInterceptor;
import com.yice.edu.cn.tap.service.course.JwCourseService;
import com.yice.edu.cn.tap.service.source21.Source21Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yice.edu.cn.common.pojo.jy.subjectSourse.SubjectMaterial;
import com.yice.edu.cn.tap.feignClient.subjectSource.SubjectMaterialFeign;

@Service
public class SubjectMaterialService {
    @Autowired
    private SubjectMaterialFeign subjectMaterialFeign;
    @Autowired
    private Source21Service source21Service;

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
        if(Constant.TOPIC_SOURCE.TWENTYONESHIJI.equals(resourceVo.getPlatform())){
            List<Version> versions = source21Service.getVersionsBySubject(LoginInterceptor.currentTeacher().getSchool().getTypeId(), JwCourseService.getSubjectToMap(resourceVo.getTempId()));
            return new ResponseJson(versions.stream().map(version -> {
                SubjectMaterial sm = new SubjectMaterial();
                sm.setId(String.valueOf(version.getVersionId()));
                sm.setName(version.getVersionName());
                return sm;
            }).collect(Collectors.toList()));
        }else{
            SubjectMaterial subjectMaterial = new SubjectMaterial();
            subjectMaterial.setDdId(resourceVo.getTempId());
            subjectMaterial = subjectMaterialFeign.findOneSubjectMaterialByCondition(subjectMaterial);
            if(subjectMaterial!=null){
                SubjectMaterial temp = new SubjectMaterial();
                temp.setParentId(subjectMaterial.getId());
                temp.setPager(new Pager().setPaging(false).setIncludes("id","name"));
                return new ResponseJson(subjectMaterialFeign.findSubjectMaterialListByCondition(temp));
            }else{
                return new ResponseJson();
            }
        }
    }
}
