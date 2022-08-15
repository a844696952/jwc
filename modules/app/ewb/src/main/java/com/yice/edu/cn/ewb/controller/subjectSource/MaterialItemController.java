package com.yice.edu.cn.ewb.controller.subjectSource;


import com.yice.edu.cn.common.constants.KnowledgeConstants;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.general.dd.Dd;
import com.yice.edu.cn.common.pojo.jy.knowledgePoint.KnowledgePoint;
import com.yice.edu.cn.common.pojo.jy.subjectSourse.MaterialItem;
import com.yice.edu.cn.common.pojo.jy.subjectSourse.MaterialItemKnowledge;
import com.yice.edu.cn.common.util.object.ObjectKit;
import com.yice.edu.cn.ewb.service.subjectSource.MaterialItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/materialItem")
@Api(value = "/materialItem",description = "教材章节表模块")
public class MaterialItemController {
    @Autowired
    private MaterialItemService materialItemService;

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


    @PostMapping("/findMaterialItemListByCondition")
    @ApiOperation(value = "根据条件查找教材章节表列表", notes = "返回响应对象,不包含总条数")
    public ResponseJson findMaterialItemListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody MaterialItem materialItem){
        List<MaterialItem> data=materialItemService.findMaterialItemListByCondition(materialItem);
        return new ResponseJson(data);
    }

    @GetMapping("/ignore/findMaterialItemTreeByGradeId/{materialId}")
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
    
    @PostMapping("/ignore/findKnowledgePointListByItem")
    @ApiOperation(value = "根据条件查找教材章节表关联的知识点列表", notes = "返回知识点列表")
    public ResponseJson findKnowledgePointListByItem(
            @ApiParam(value = "教材章节表对象{materialItemId:章节id}")
            @RequestBody MaterialItemKnowledge materialItemKnowledge){
         List<KnowledgePoint> data = materialItemService.findKnowledgePointListByItem(materialItemKnowledge);
         long count = materialItemService.findKnowledgePointCountByItem(materialItemKnowledge);
         
         return new ResponseJson(data,count);
    }

    @PostMapping("/findMaterialItemTree")
    @ApiOperation(value = "根据学段id查询科目年级树结构列表-----------------------------------", notes = "返回科目年级树结构列表")
    public ResponseJson findMaterialItemTree(
            @ApiParam(value = "学段")
            @RequestBody MaterialItem materialItem){
        List<Dd> data=materialItemService.findMaterialItemTree(materialItem);
        return new ResponseJson(data);
    }

}
