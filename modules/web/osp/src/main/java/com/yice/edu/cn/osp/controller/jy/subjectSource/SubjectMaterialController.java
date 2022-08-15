package com.yice.edu.cn.osp.controller.jy.subjectSource;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yice.edu.cn.common.constants.KnowledgeConstants;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jy.subjectSourse.Material;
import com.yice.edu.cn.common.pojo.jy.subjectSourse.SubjectMaterial;
import com.yice.edu.cn.common.pojo.jy.subjectSourse.vo.SelTopicsVo;
import com.yice.edu.cn.common.util.object.ObjectKit;
import com.yice.edu.cn.osp.service.jy.subjectSource.MaterialService;
import com.yice.edu.cn.osp.service.jy.subjectSource.SubjectMaterialService;

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

    @PostMapping("/saveSubjectMaterial")
    @ApiOperation(value = "保存科目教材", notes = "返回响应对象")
    public ResponseJson saveSubjectMaterial(
            @ApiParam(value = "科目教材对象{parentId:树节点的父id(顶级节点值为-1),name:名称 ,sort:排序,ddId:科目/年级的数据字典的id,annualPeriodId:年段id,level:树级别,path:父节点path}", required = true)
            @RequestBody SubjectMaterial subjectMaterial){
    	if(subjectMaterial.getParentId()==null) {
    		subjectMaterial.setParentId("-1");
    	}
    	
		SubjectMaterial querySubjectMarterial = new SubjectMaterial();
		querySubjectMarterial.setLevel(subjectMaterial.getLevel());
		querySubjectMarterial.setParentId(subjectMaterial.getParentId());
		querySubjectMarterial.setDdId(subjectMaterial.getDdId());
		querySubjectMarterial.setAnnualPeriodId(subjectMaterial.getAnnualPeriodId());
		long count = subjectMaterialService.findSubjectMaterialCountByCondition(querySubjectMarterial);
    	if(subjectMaterial.getLevel().intValue()==1) {
    		//同一个年段不能有一样的科目
    		if(count>0) {
    			return new ResponseJson(false, "该科目名称已经重复!");
    		}
    	}else {
    		//同一个课程不能有一样的年级
    		if(count>0) {
    			return new ResponseJson(false, "该年级名称已经重复!");
    		}
    	}

    	subjectMaterial.setLeaf(Integer.parseInt(KnowledgeConstants.LEAF.IS_LEAF));
        SubjectMaterial s=subjectMaterialService.saveSubjectMaterial(subjectMaterial);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findSubjectMaterialById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找科目教材管理", notes = "返回响应对象")
    public ResponseJson findSubjectMaterialById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        SubjectMaterial subjectMaterial=subjectMaterialService.findSubjectMaterialById(id);
        return new ResponseJson(subjectMaterial);
    }

    @PostMapping("/update/updateSubjectMaterial")
    @ApiOperation(value = "修改科目教材管理对象", notes = "返回响应对象")
    public ResponseJson updateSubjectMaterial(
            @ApiParam(value = "被修改的科目教材管理对象,对象属性不为空则修改", required = true)
            @RequestBody SubjectMaterial subjectMaterial){
        subjectMaterialService.updateSubjectMaterial(subjectMaterial);
        return new ResponseJson();
    }

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
    @GetMapping("/deleteSubjectMaterial/{id}")
    @ApiOperation(value = "根据id删除科目/年级", notes = "返回响应对象")
    public ResponseJson deleteSubjectMaterial(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
    	//判断是否还有子节点
    	SubjectMaterial childrenModel = new SubjectMaterial();
    	childrenModel.setParentId(id);
    	long childreCount = subjectMaterialService.findSubjectMaterialCountByCondition(childrenModel);
        if(childreCount>0) {
        	return new ResponseJson(false,"请删除年级!");
        }
    	//判断是否绑定了教材
        Material materialModel = new Material();
        materialModel.setSubjectMaterialId(id);
        long materialCount = materialService.findMaterialCountByCondition(materialModel);
        if(materialCount>0) {
        	return new ResponseJson(false,"请删除关联的教材!");
        }
        subjectMaterialService.deleteSubjectMaterial(id);
        return new ResponseJson();
    }

    @PostMapping("/ignore/findSubjectMaterialListByCondition")
    @ApiOperation(value = "根据条件查找科目教材管理列表", notes = "返回响应对象,不包含总条数")
    public ResponseJson findSubjectMaterialListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody SubjectMaterial subjectMaterial){
        List<SubjectMaterial> data=subjectMaterialService.findSubjectMaterialListByCondition(subjectMaterial);
        return new ResponseJson(data);
    }
 
    @GetMapping("/ignore/findSubjectMaterialTreeByAnnualPeriodId/{annualPeriodId}")
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
    
    @PostMapping("/ignore/findMaterialBySubjectIdAndGradeId")
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
