package com.yice.edu.cn.bmp.controller.subjectSource;


import com.yice.edu.cn.bmp.service.subjectSource.MaterialItemService;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jy.knowledgePoint.KnowledgePoint;
import com.yice.edu.cn.common.pojo.jy.subjectSourse.MaterialItem;
import com.yice.edu.cn.common.pojo.jy.subjectSourse.MaterialItemKnowledge;
import com.yice.edu.cn.common.util.object.ObjectKit;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/materialItem")
@Api(value = "/materialItem",description = "教材章节表模块")
public class MaterialItemController {
    @Autowired
    private MaterialItemService materialItemService;


    @PostMapping("/findMaterialItemListByCondition")
    @ApiOperation(value = "根据条件查找教材章节表列表", notes = "返回响应对象,不包含总条数",response=MaterialItem.class)
    public ResponseJson findMaterialItemListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询,{顶节点parentId为-1,materialId(子节点不用传):教材Id}")
            @RequestBody MaterialItem materialItem){
        List<MaterialItem> data=materialItemService.findMaterialItemListByCondition(materialItem);
        return new ResponseJson(data);
    }

    @GetMapping("/findMaterialItemTreeByGradeId/{materialId}")
    @ApiOperation(value = "根据教材Id查找章节树", notes = "返回响应对象,不包含总条数",response=MaterialItem.class)
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
    
    @PostMapping("/findKnowledgePointListByItem")
    @ApiOperation(value = "根据条件查找教材章节表关联的知识点列表", notes = "返回知识点列表",response=KnowledgePoint.class)
    public ResponseJson findKnowledgePointListByItem(
            @ApiParam(value = "教材章节表对象{materialItemId:章节id}")
            @RequestBody MaterialItemKnowledge materialItemKnowledge){
         List<KnowledgePoint> data = materialItemService.findKnowledgePointListByItem(materialItemKnowledge);
         long count = materialItemService.findKnowledgePointCountByItem(materialItemKnowledge);
         
         return new ResponseJson(data,count);
    }

}
