package com.yice.edu.cn.tap.controller.subjectSource;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jy.subjectSourse.Material;
import com.yice.edu.cn.common.pojo.jy.subjectSourse.SubjectMaterial;
import com.yice.edu.cn.common.pojo.jy.subjectSourse.vo.SelTopicsVo;
import com.yice.edu.cn.common.util.object.ObjectKit;
import com.yice.edu.cn.tap.service.subjectSource.MaterialService;
import com.yice.edu.cn.tap.service.subjectSource.SubjectMaterialService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/subjectMaterial")
@Api(value = "/subjectMaterial",description = "科目教材管理模块")
public class SubjectMaterialController {
    @Autowired
    private SubjectMaterialService subjectMaterialService;
    @Autowired
    private MaterialService materialService;

    @GetMapping("/look/lookSubjectMaterialById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找科目教材管理", notes = "返回响应对象")
    public ResponseJson lookSubjectMaterialById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        SubjectMaterial subjectMaterial=subjectMaterialService.findSubjectMaterialById(id);
        return new ResponseJson(subjectMaterial);
    }

    @PostMapping("/findSubjectMaterialsByCondition")
    @ApiOperation(value = "根据条件查找科目教材管理", notes = "返回响应对象")
    public ResponseJson findSubjectMaterialsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody SubjectMaterial subjectMaterial){
        List<SubjectMaterial> data=subjectMaterialService.findSubjectMaterialListByCondition(subjectMaterial);
        long count=subjectMaterialService.findSubjectMaterialCountByCondition(subjectMaterial);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneSubjectMaterialByCondition")
    @ApiOperation(value = "根据条件查找单个科目教材管理,结果必须为单条数据", notes = "没有时返回空")
    public ResponseJson findOneSubjectMaterialByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody SubjectMaterial subjectMaterial){
        SubjectMaterial one=subjectMaterialService.findOneSubjectMaterialByCondition(subjectMaterial);
        return new ResponseJson(one);
    }

    @PostMapping("/findSubjectMaterialListByCondition")
    @ApiOperation(value = "根据条件查找科目教材管理列表", notes = "返回响应对象,不包含总条数")
    public ResponseJson findSubjectMaterialListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody SubjectMaterial subjectMaterial){
        List<SubjectMaterial> data=subjectMaterialService.findSubjectMaterialListByCondition(subjectMaterial);
        return new ResponseJson(data);
    }
 
    @GetMapping("/findSubjectMaterialTreeByAnnualPeriodId/{annualPeriodId}")
    @ApiOperation(value = "根据年段id查找科目教材树", notes = "返回响应对象,不包含总条数")
    public ResponseJson findSubjectMaterialTreeByAnnualPeriodId(
    		@PathVariable String annualPeriodId
    		){
    	SubjectMaterial subjectMaterial  = new SubjectMaterial();
    	subjectMaterial.setAnnualPeriodId(annualPeriodId);
    	Pager pager = new Pager();
    	pager.setSortField("sort");
    	pager.setSortOrder("desc");
    	pager.setPaging(false);
    	subjectMaterial.setPager(pager);
        List<SubjectMaterial> data=subjectMaterialService.findSubjectMaterialListByCondition(subjectMaterial);
        List<SubjectMaterial> treeData = ObjectKit.buildTree(data,"-1"); 
        
        return new ResponseJson(treeData);
    }
    
    @PostMapping("/findMaterialBySubjectIdAndGradeId")
    @ApiOperation(value = "根据数据字典的年级和科目id查找教材", notes = "返回响应对象,不包含总条数")
    public ResponseJson findSubjectMaterialTreeByAnnualPeriodId(@RequestBody SelTopicsVo vo){

    	SubjectMaterial querySubject = new SubjectMaterial();
    	querySubject.setDdId(vo.getSubjectId());
    	SubjectMaterial subjectModel = subjectMaterialService.findOneSubjectMaterialByCondition(querySubject);
    	if(subjectModel==null) {
    		return new ResponseJson();
    	}
    	
    	SubjectMaterial queryGrade = new SubjectMaterial();
    	queryGrade.setDdId(vo.getGradeId());
    	queryGrade.setParentId(subjectModel.getId());
    	SubjectMaterial materialModel = subjectMaterialService.findOneSubjectMaterialByCondition(queryGrade);
    	if(materialModel==null) {
    		return new ResponseJson();
    	}
    	
    	Material material = new Material();
    	material.setSubjectMaterialId(materialModel.getId());
    	List<Material> materialList = materialService.findMaterialListByCondition(material);
    	
        return new ResponseJson(materialList);
    }
    
}
