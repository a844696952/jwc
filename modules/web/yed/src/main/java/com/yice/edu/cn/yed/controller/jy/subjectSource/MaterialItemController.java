package com.yice.edu.cn.yed.controller.jy.subjectSource;


import java.util.List;
import java.util.Optional;

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
import com.yice.edu.cn.common.pojo.jy.knowledgePoint.KnowledgePoint;
import com.yice.edu.cn.common.pojo.jy.subjectSourse.ExamPointKnowledge;
import com.yice.edu.cn.common.pojo.jy.subjectSourse.Material;
import com.yice.edu.cn.common.pojo.jy.subjectSourse.MaterialItem;
import com.yice.edu.cn.common.pojo.jy.subjectSourse.MaterialItemKnowledge;
import com.yice.edu.cn.common.pojo.jy.subjectSourse.SubjectMaterial;
import com.yice.edu.cn.common.util.object.ObjectKit;
import com.yice.edu.cn.yed.service.jy.knowledgePoint.KnowledgePointService;
import com.yice.edu.cn.yed.service.jy.subjectSource.MaterialItemKnowledgeService;
import com.yice.edu.cn.yed.service.jy.subjectSource.MaterialItemService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/materialItem")
@Api(value = "/materialItem",description = "教材章节表模块")
public class MaterialItemController {
    @Autowired
    private MaterialItemService materialItemService;
    
    @Autowired
    private MaterialItemKnowledgeService materialItemKnowledgeService;
    
    @Autowired
    private KnowledgePointService knowledgePointService;

    @PostMapping("/saveMaterialItem")
    @ApiOperation(value = "保存教材章节表对象", notes = "返回响应对象")
    public ResponseJson saveMaterialItem(
            @ApiParam(value = "教材章节表对象{parentId:树节点的父id(顶级节点值为-1),name:名称 ,sort:排序,materialId:教材id,level:树级别,path:父节点path}", required = true)
            @RequestBody MaterialItem materialItem){
    	if(materialItem.getParentId()==null) {
    		materialItem.setParentId("-1");
    	}
    	
    	MaterialItem queryModel = new MaterialItem();
		queryModel.setLevel(materialItem.getLevel());
		queryModel.setParentId(materialItem.getParentId());
		queryModel.setMaterialId(materialItem.getMaterialId());
		queryModel.setName(materialItem.getName());
		long count = materialItemService.findMaterialItemCountByCondition(queryModel);
		if(count>0) {
			return new ResponseJson(false, "该章节名称已经重复!");
		}
    	
    	materialItem.setLeaf(Integer.parseInt(KnowledgeConstants.LEAF.IS_LEAF));
        MaterialItem s=materialItemService.saveMaterialItem(materialItem);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findMaterialItemById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找教材章节表", notes = "返回响应对象")
    public ResponseJson findMaterialItemById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        MaterialItem materialItem=materialItemService.findMaterialItemById(id);
        return new ResponseJson(materialItem);
    }

    @PostMapping("/update/updateMaterialItem")
    @ApiOperation(value = "修改教材章节表对象", notes = "返回响应对象")
    public ResponseJson updateMaterialItem(
            @ApiParam(value = "被修改的教材章节表对象,对象属性不为空则修改", required = true)
            @RequestBody MaterialItem materialItem){
        materialItemService.updateMaterialItem(materialItem);
        return new ResponseJson();
    }

    @GetMapping("/look/lookMaterialItemById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找教材章节表", notes = "返回响应对象")
    public ResponseJson lookMaterialItemById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        MaterialItem materialItem=materialItemService.findMaterialItemById(id);
        return new ResponseJson(materialItem);
    }

    @PostMapping("/findMaterialItemsByCondition")
    @ApiOperation(value = "根据条件查找教材章节表", notes = "返回响应对象")
    public ResponseJson findMaterialItemsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody MaterialItem materialItem){
        List<MaterialItem> data=materialItemService.findMaterialItemListByCondition(materialItem);
        long count=materialItemService.findMaterialItemCountByCondition(materialItem);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneMaterialItemByCondition")
    @ApiOperation(value = "根据条件查找单个教材章节表,结果必须为单条数据", notes = "没有时返回空")
    public ResponseJson findOneMaterialItemByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody MaterialItem materialItem){
        MaterialItem one=materialItemService.findOneMaterialItemByCondition(materialItem);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteMaterialItem/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteMaterialItem(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
    	//判断是否还有子节点
    	MaterialItem childrenModel = new MaterialItem();
    	childrenModel.setParentId(id);
    	long childreCount = materialItemService.findMaterialItemCountByCondition(childrenModel);
        if(childreCount>0) {
        	return new ResponseJson(false,"请删除子章节!");
        }
    	//判断是否绑定了知识点
    	MaterialItemKnowledge materialItemKnowledge = new MaterialItemKnowledge();
    	materialItemKnowledge.setMaterialItemId(id);
        long count = materialItemKnowledgeService.findMaterialItemKnowledgeCountByCondition(materialItemKnowledge);
        if(count>0) {
        	return new ResponseJson(false,"请先移除关联的知识点!");
        }
    	
        materialItemService.deleteMaterialItem(id);
        return new ResponseJson();
    }


    @PostMapping("/findMaterialItemListByCondition")
    @ApiOperation(value = "根据条件查找教材章节表列表", notes = "返回响应对象,不包含总条数")
    public ResponseJson findMaterialItemListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody MaterialItem materialItem){
        List<MaterialItem> data=materialItemService.findMaterialItemListByCondition(materialItem);
        return new ResponseJson(data);
    }

    @GetMapping("/findMaterialItemTreeByGradeId/{materialId}")
    @ApiOperation(value = "根据教材Id查找章节树", notes = "返回响应对象,不包含总条数")
    public ResponseJson findMaterialItemTreeByGradeId(
            @ApiParam(value = "教材id")
            @PathVariable String materialId){
    	
    	MaterialItem materialItem = new MaterialItem();
    	materialItem.setMaterialId(materialId);
    	Pager pager = new Pager();
    	pager.setSortField("sort");
    	pager.setSortOrder("desc");
    	pager.setPaging(false);
    	materialItem.setPager(pager);
        List<MaterialItem> data=materialItemService.findMaterialItemListByCondition(materialItem);
        List<MaterialItem> treeData = ObjectKit.buildTree(data, "-1");
        
        return new ResponseJson(treeData);
    }
    
    @PostMapping("/ignore/saveMaterialItemKnowledge")
    @ApiOperation(value = "保存章节和知识点的中间表对象", notes = "返回响应对象")
    public ResponseJson saveMaterialItemKnowledge(
            @ApiParam(value= "章节和知识点的中间表对象考点和知识点的关联对象{materialItemId:章节id,knowledgePointId:知识点id,materialItemPath:章节点的path}", required = true)
            @RequestBody MaterialItemKnowledge materialItemKnowledge){
    	//判断是否重复关联
    	long count = materialItemKnowledgeService.findMaterialItemKnowledgeCountByCondition(materialItemKnowledge);
    	if(count>0) {
    		return new ResponseJson(false,"该知识点已经关联");
    	}
    	
        MaterialItemKnowledge s=materialItemKnowledgeService.saveMaterialItemKnowledge(materialItemKnowledge);
        return new ResponseJson(s);
    }

    @PostMapping("/ignore/deleteMaterialItemKnowledge")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteMaterialItemKnowledge(
            @ApiParam(value = "章节和知识点的关联对象id{materialItemId:章节id,knowledgePointId:知识点id}", required = true)
            @RequestBody MaterialItemKnowledge materialItemKnowledge){
    	if(org.apache.commons.lang.StringUtils.isEmpty(materialItemKnowledge.getKnowledgePointId()) || org.apache.commons.lang.StringUtils.isEmpty(materialItemKnowledge.getMaterialItemId())) {
    		new ResponseJson(false,"知识点id或者章节Id不能为空!");
    	}
        materialItemKnowledgeService.deleteMaterialItemKnowledgeByCondition(materialItemKnowledge);
        return new ResponseJson();
    }
    
    @PostMapping("/ignore/findKnowledgePointListByItem")
    @ApiOperation(value = "根据条件查找教材章节表关联的知识点列表", notes = "返回知识点列表")
    public ResponseJson findKnowledgePointListByItem(
            @ApiParam(value = "教材章节表对象{materialItemId:章节id}")
            @RequestBody MaterialItemKnowledge materialItemKnowledge){
        materialItemKnowledge.setPager(Optional.ofNullable(materialItemKnowledge.getPager()).orElse(new Pager()).setPaging(false));
        List<KnowledgePoint> data = materialItemService.findKnowledgePointListByItem(materialItemKnowledge);
        return new ResponseJson(data);
    }
    
    @PostMapping("/ignore/findKnowledgePointTreeByCondition")
    @ApiOperation(value = "根据条件查找知识点表模糊查询", notes = "返回响应对象")
    public ResponseJson findKnowledgePointTreeByCondition(
            @ApiParam(value = "属性不为空则作为条件查询{name:知识点名称(可为空),typeId:年段id,subjectId:科目的ddId,page:page对象}")
            @RequestBody KnowledgePoint knowledgePoint){
        knowledgePoint.setPager(Optional.ofNullable(knowledgePoint.getPager()).orElse(new Pager()).setPaging(false));
        List<KnowledgePoint> data=knowledgePointService.findKnowledgePointTreeByCondition(knowledgePoint);
        return new ResponseJson(data);
    }
}
