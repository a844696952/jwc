package com.yice.edu.cn.tap.controller.subjectSource;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jy.subjectSourse.Material;
import com.yice.edu.cn.common.pojo.jy.subjectSourse.SubjectMaterial;
import com.yice.edu.cn.common.pojo.jy.subjectSourse.vo.SelTopicsVo;
import com.yice.edu.cn.tap.service.subjectSource.MaterialService;
import com.yice.edu.cn.tap.service.subjectSource.SubjectMaterialService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/material")
@Api(value = "/material",description = "教材模块")
public class MaterialController {
    @Autowired
    private MaterialService materialService;
    @Autowired
    private SubjectMaterialService subjectMaterialService;

//    @GetMapping("/look/lookMaterialById/{id}")
//    @ApiOperation(value = "去查看页面,通过id查找教材", notes = "返回响应对象")
//    public ResponseJson lookMaterialById(
//            @ApiParam(value = "去查看页面,需要用到的id", required = true)
//            @PathVariable String id){
//        Material material=materialService.findMaterialById(id);
//        return new ResponseJson(material);
//    }
//
//    @PostMapping("/findMaterialsByCondition")
//    @ApiOperation(value = "根据条件查找教材", notes = "返回响应对象")
//    public ResponseJson findMaterialsByCondition(
//            @ApiParam(value = "属性不为空则作为条件查询")
//            @Validated
//            @RequestBody Material material){
//        List<Material> data=materialService.findMaterialListByCondition(material);
//        long count=materialService.findMaterialCountByCondition(material);
//        return new ResponseJson(data,count);
//    }
//    @PostMapping("/findOneMaterialByCondition")
//    @ApiOperation(value = "根据条件查找单个教材,结果必须为单条数据", notes = "没有时返回空")
//    public ResponseJson findOneMaterialByCondition(
//            @ApiParam(value = "属性不为空则作为条件查询")
//            @RequestBody Material material){
//        Material one=materialService.findOneMaterialByCondition(material);
//        return new ResponseJson(one);
//    }
//
//    @PostMapping("/findMaterialListByCondition")
//    @ApiOperation(value = "根据条件查找教材列表", notes = "返回响应对象,不包含总条数")
//    public ResponseJson findMaterialListByCondition(
//            @ApiParam(value = "属性不为空则作为条件查询{subjectMaterialId:树id}")
//            @Validated
//            @RequestBody Material material){
//        List<Material> data=materialService.findMaterialListByCondition(material);
//        return new ResponseJson(data);
//    }
    
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
