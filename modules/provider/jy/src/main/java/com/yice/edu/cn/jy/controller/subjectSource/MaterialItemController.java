package com.yice.edu.cn.jy.controller.subjectSource;


import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.qusBankResource.ResourceVo;
import com.yice.edu.cn.common.pojo.jy.knowledgePoint.KnowledgePoint;
import com.yice.edu.cn.common.pojo.jy.subjectSourse.MaterialItem;
import com.yice.edu.cn.common.pojo.jy.subjectSourse.MaterialItemKnowledge;
import com.yice.edu.cn.jy.service.subjectSource.MaterialItemService;
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

    @GetMapping("/findMaterialItemById/{id}")
    @ApiOperation(value = "通过id查找教材章节表", notes = "返回教材章节表对象")
    public MaterialItem findMaterialItemById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return materialItemService.findMaterialItemById(id);
    }

    @PostMapping("/saveMaterialItem")
    @ApiOperation(value = "保存教材章节表", notes = "返回教材章节表对象")
    public MaterialItem saveMaterialItem(
            @ApiParam(value = "教材章节表对象", required = true)
            @RequestBody MaterialItem materialItem){
        materialItemService.saveMaterialItem(materialItem);
        return materialItem;
    }

    @PostMapping("/findMaterialItemListByCondition")
    @ApiOperation(value = "根据条件查找教材章节表列表", notes = "返回教材章节表列表")
    public List<MaterialItem> findMaterialItemListByCondition(
            @ApiParam(value = "教材章节表对象")
            @RequestBody MaterialItem materialItem){
        return materialItemService.findMaterialItemListByCondition(materialItem);
    }
    @PostMapping("/findMaterialItemCountByCondition")
    @ApiOperation(value = "根据条件查找教材章节表列表个数", notes = "返回教材章节表总个数")
    public long findMaterialItemCountByCondition(
            @ApiParam(value = "教材章节表对象")
            @RequestBody MaterialItem materialItem){
        return materialItemService.findMaterialItemCountByCondition(materialItem);
    }

    @PostMapping("/updateMaterialItem")
    @ApiOperation(value = "修改教材章节表", notes = "教材章节表对象必传")
    public void updateMaterialItem(
            @ApiParam(value = "教材章节表对象,对象属性不为空则修改", required = true)
            @RequestBody MaterialItem materialItem){
        materialItemService.updateMaterialItem(materialItem);
    }

    @GetMapping("/deleteMaterialItem/{id}")
    @ApiOperation(value = "通过id删除教材章节表")
    public void deleteMaterialItem(
            @ApiParam(value = "教材章节表对象", required = true)
            @PathVariable String id){
        materialItemService.deleteMaterialItem(id);
    }
    @PostMapping("/batchSaveMaterialItem")
    @ApiOperation(value = "批量添加章节")
    public void batchSaveMaterialItem(
            @RequestBody List<MaterialItem> materialItems){
        materialItemService.batchSaveMaterialItem(materialItems);
    }
    @PostMapping("/deleteMaterialItemByCondition")
    @ApiOperation(value = "根据条件删除教材章节表")
    public void deleteMaterialItemByCondition(
            @ApiParam(value = "教材章节表对象")
            @RequestBody MaterialItem materialItem){
        materialItemService.deleteMaterialItemByCondition(materialItem);
    }
    @PostMapping("/findOneMaterialItemByCondition")
    @ApiOperation(value = "根据条件查找单个教材章节表,结果必须为单条数据", notes = "返回单个教材章节表,没有时为空")
    public MaterialItem findOneMaterialItemByCondition(
            @ApiParam(value = "教材章节表对象")
            @RequestBody MaterialItem materialItem){
        return materialItemService.findOneMaterialItemByCondition(materialItem);
    }
    
    @PostMapping("/findKnowledgePointListByItem")
    @ApiOperation(value = "根据条件查找教材章节表关联的知识点列表", notes = "返回知识点列表")
    public List<KnowledgePoint> findKnowledgePointListByItem(
            @ApiParam(value = "教材章节表对象")
            @RequestBody MaterialItemKnowledge materialItemKnowledge){
        return materialItemService.findKnowledgePointListByItem(materialItemKnowledge);
    }
    
    @PostMapping("/findKnowledgePointCountByItem")
    @ApiOperation(value = "根据条件查找教材章节表关联的知识点列表个数", notes = "返回知识点总个数")
    public long findKnowledgePointCountByItem(
            @ApiParam(value = "教材章节表对象")
            @RequestBody MaterialItemKnowledge materialItemKnowledge){
        return materialItemService.findKnowledgePointCountByItem(materialItemKnowledge);
    }

    @PostMapping("/findMaterialItemTree")
    @ApiOperation(value = "根据学段id查询科目年级树结构列表", notes = "返回科目年级树结构列表")
    public List<MaterialItem> findMaterialItemTree(
            @ApiParam(value = "学段id")
            @RequestBody MaterialItem materialItem){
        return materialItemService.findMaterialItemTree(materialItem);
    }
    @PostMapping("/findChapterTree")
    @ApiOperation(value = "查询教材对应的章节树", notes = "返回科目年级树结构列表")
    public ResponseJson findChapterTree(
            @ApiParam(value = "学段id")
            @RequestBody ResourceVo resourceVo){
        return materialItemService.findChapterTree(resourceVo);
    }

    @GetMapping("/checkMaterialItemIsNull/{materialId}")
    @ApiOperation(value = "校验章节是否存在", notes = "返回布尔值")
    public Boolean checkMaterialItemIsNull(
            @ApiParam(value = "章节id", required = true)
            @PathVariable String materialId){
        return materialItemService.checkMaterialItemIsNull(materialId);
    }
}
