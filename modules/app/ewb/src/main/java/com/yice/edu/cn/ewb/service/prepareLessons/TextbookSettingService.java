package com.yice.edu.cn.ewb.service.prepareLessons;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yice.edu.cn.common.pojo.jy.prepareLessons.TextbookSetting;
import com.yice.edu.cn.common.pojo.jy.subjectSourse.Material;
import com.yice.edu.cn.common.pojo.jy.subjectSourse.SubjectMaterial;
import com.yice.edu.cn.ewb.feignClient.prepareLessons.TextbookSettingFeign;
import com.yice.edu.cn.ewb.feignClient.subjectSource.MaterialFeign;
import com.yice.edu.cn.ewb.feignClient.subjectSource.SubjectMaterialFeign;



@Service
public class TextbookSettingService {
	
	@Autowired
	private TextbookSettingFeign textbookSettingFeign;
	
	@Autowired
	private SubjectMaterialFeign subjectMaterialFeign;
	
	@Autowired
    private MaterialFeign materialFeign;
	
    public List<SubjectMaterial> findSubjectByAnnualPeriodId(String annualPeriodId){
    	SubjectMaterial subjectMaterial=new SubjectMaterial();
    	subjectMaterial.setAnnualPeriodId(annualPeriodId);
    	subjectMaterial.setLevel(1);
        return subjectMaterialFeign.findSubjectMaterialListByCondition(subjectMaterial);
    }
    
    public List<SubjectMaterial> findGradeBySubjectId(String subjectId){
    	SubjectMaterial subjectMaterial=new SubjectMaterial();
    	subjectMaterial.setParentId(subjectId);
        return subjectMaterialFeign.findSubjectMaterialListByCondition(subjectMaterial);
    }
    
    public List<Material> findMaterialBySubjectMaterialId(String subjectMaterialId){
    	Material material=new Material(); 
    	material.setSubjectMaterialId(subjectMaterialId);
    	List<Material> materials=materialFeign.findMaterialListByCondition(material);
        return materials;
    }
	
	
    public List<TextbookSetting> findTextbookSettingByTeacherId(String id){
    	return textbookSettingFeign.findTextbookSettingByTeacherId(id);
    }

    
    public TextbookSetting findLastSettingbyTeacherId(String teacherId){
        return textbookSettingFeign.findLastSettingbyTeacherId(teacherId);
    }
    
}
