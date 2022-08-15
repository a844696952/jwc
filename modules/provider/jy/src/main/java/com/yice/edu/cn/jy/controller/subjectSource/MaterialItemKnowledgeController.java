package com.yice.edu.cn.jy.controller.subjectSource;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yice.edu.cn.common.pojo.jy.subjectSourse.MaterialItemKnowledge;
import com.yice.edu.cn.jy.service.subjectSource.MaterialItemKnowledgeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/materialItemKnowledge")
@Api(value = "/materialItemKnowledge",description = "章节和知识点的中间表模块")
public class MaterialItemKnowledgeController {
    @Autowired
    private MaterialItemKnowledgeService materialItemKnowledgeService;

    @GetMapping("/findMaterialItemKnowledgeById/{id}")
    @ApiOperation(value = "通过id查找章节和知识点的中间表", notes = "返回章节和知识点的中间表对象")
    public MaterialItemKnowledge findMaterialItemKnowledgeById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return materialItemKnowledgeService.findMaterialItemKnowledgeById(id);
    }

    @PostMapping("/saveMaterialItemKnowledge")
    @ApiOperation(value = "保存章节和知识点的中间表", notes = "返回章节和知识点的中间表对象")
    public MaterialItemKnowledge saveMaterialItemKnowledge(
            @ApiParam(value = "章节和知识点的中间表对象", required = true)
            @RequestBody MaterialItemKnowledge materialItemKnowledge){
        materialItemKnowledgeService.saveMaterialItemKnowledge(materialItemKnowledge);
        return materialItemKnowledge;
    }

    @PostMapping("/findMaterialItemKnowledgeListByCondition")
    @ApiOperation(value = "根据条件查找章节和知识点的中间表列表", notes = "返回章节和知识点的中间表列表")
    public List<MaterialItemKnowledge> findMaterialItemKnowledgeListByCondition(
            @ApiParam(value = "章节和知识点的中间表对象")
            @RequestBody MaterialItemKnowledge materialItemKnowledge){
        return materialItemKnowledgeService.findMaterialItemKnowledgeListByCondition(materialItemKnowledge);
    }
    @PostMapping("/findMaterialItemKnowledgeCountByCondition")
    @ApiOperation(value = "根据条件查找章节和知识点的中间表列表个数", notes = "返回章节和知识点的中间表总个数")
    public long findMaterialItemKnowledgeCountByCondition(
            @ApiParam(value = "章节和知识点的中间表对象")
            @RequestBody MaterialItemKnowledge materialItemKnowledge){
        return materialItemKnowledgeService.findMaterialItemKnowledgeCountByCondition(materialItemKnowledge);
    }

    @PostMapping("/updateMaterialItemKnowledge")
    @ApiOperation(value = "修改章节和知识点的中间表", notes = "章节和知识点的中间表对象必传")
    public void updateMaterialItemKnowledge(
            @ApiParam(value = "章节和知识点的中间表对象,对象属性不为空则修改", required = true)
            @RequestBody MaterialItemKnowledge materialItemKnowledge){
        materialItemKnowledgeService.updateMaterialItemKnowledge(materialItemKnowledge);
    }

    @GetMapping("/deleteMaterialItemKnowledge/{id}")
    @ApiOperation(value = "通过id删除章节和知识点的中间表")
    public void deleteMaterialItemKnowledge(
            @ApiParam(value = "章节和知识点的中间表对象", required = true)
            @PathVariable String id){
        materialItemKnowledgeService.deleteMaterialItemKnowledge(id);
    }
    @PostMapping("/deleteMaterialItemKnowledgeByCondition")
    @ApiOperation(value = "根据条件删除章节和知识点的中间表")
    public void deleteMaterialItemKnowledgeByCondition(
            @ApiParam(value = "章节和知识点的中间表对象")
            @RequestBody MaterialItemKnowledge materialItemKnowledge){
        materialItemKnowledgeService.deleteMaterialItemKnowledgeByCondition(materialItemKnowledge);
    }
    @PostMapping("/batchSaveMaterialItemKnowledge")
    @ApiOperation(value = "批量添加章节对应知识点信息")
    public void batchSaveMaterialItemKnowledge(
            @RequestBody List<MaterialItemKnowledge> materialItemKnowledges){
        materialItemKnowledgeService.batchSaveMaterialItemKnowledge(materialItemKnowledges);
    }
    @PostMapping("/findOneMaterialItemKnowledgeByCondition")
    @ApiOperation(value = "根据条件查找单个章节和知识点的中间表,结果必须为单条数据", notes = "返回单个章节和知识点的中间表,没有时为空")
    public MaterialItemKnowledge findOneMaterialItemKnowledgeByCondition(
            @ApiParam(value = "章节和知识点的中间表对象")
            @RequestBody MaterialItemKnowledge materialItemKnowledge){
        return materialItemKnowledgeService.findOneMaterialItemKnowledgeByCondition(materialItemKnowledge);
    }
}
