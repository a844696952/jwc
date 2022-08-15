package com.yice.edu.cn.yed.controller.jy.subjectSource;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jy.subjectSourse.MaterialItemKnowledge;
import com.yice.edu.cn.yed.service.jy.subjectSource.MaterialItemKnowledgeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/materialItemKnowledge")
@Api(value = "/materialItemKnowledge",description = "章节和知识点的关联模块")
public class MaterialItemKnowledgeController {
    @Autowired
    private MaterialItemKnowledgeService materialItemKnowledgeService;

    @PostMapping("/saveMaterialItemKnowledge")
    @ApiOperation(value = "保存章节和知识点的中间表对象", notes = "返回响应对象")
    public ResponseJson saveMaterialItemKnowledge(
            @ApiParam(value = "章节和知识点的中间表对象", required = true)
            @RequestBody MaterialItemKnowledge materialItemKnowledge){
        MaterialItemKnowledge s=materialItemKnowledgeService.saveMaterialItemKnowledge(materialItemKnowledge);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findMaterialItemKnowledgeById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找章节和知识点的中间表", notes = "返回响应对象")
    public ResponseJson findMaterialItemKnowledgeById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        MaterialItemKnowledge materialItemKnowledge=materialItemKnowledgeService.findMaterialItemKnowledgeById(id);
        return new ResponseJson(materialItemKnowledge);
    }

    @PostMapping("/update/updateMaterialItemKnowledge")
    @ApiOperation(value = "修改章节和知识点的中间表对象", notes = "返回响应对象")
    public ResponseJson updateMaterialItemKnowledge(
            @ApiParam(value = "被修改的章节和知识点的中间表对象,对象属性不为空则修改", required = true)
            @RequestBody MaterialItemKnowledge materialItemKnowledge){
        materialItemKnowledgeService.updateMaterialItemKnowledge(materialItemKnowledge);
        return new ResponseJson();
    }

    @GetMapping("/look/lookMaterialItemKnowledgeById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找章节和知识点的中间表", notes = "返回响应对象")
    public ResponseJson lookMaterialItemKnowledgeById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        MaterialItemKnowledge materialItemKnowledge=materialItemKnowledgeService.findMaterialItemKnowledgeById(id);
        return new ResponseJson(materialItemKnowledge);
    }

    @PostMapping("/findMaterialItemKnowledgesByCondition")
    @ApiOperation(value = "根据条件查找章节和知识点的中间表", notes = "返回响应对象")
    public ResponseJson findMaterialItemKnowledgesByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody MaterialItemKnowledge materialItemKnowledge){
        List<MaterialItemKnowledge> data=materialItemKnowledgeService.findMaterialItemKnowledgeListByCondition(materialItemKnowledge);
        long count=materialItemKnowledgeService.findMaterialItemKnowledgeCountByCondition(materialItemKnowledge);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneMaterialItemKnowledgeByCondition")
    @ApiOperation(value = "根据条件查找单个章节和知识点的中间表,结果必须为单条数据", notes = "没有时返回空")
    public ResponseJson findOneMaterialItemKnowledgeByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody MaterialItemKnowledge materialItemKnowledge){
        MaterialItemKnowledge one=materialItemKnowledgeService.findOneMaterialItemKnowledgeByCondition(materialItemKnowledge);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteMaterialItemKnowledge/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteMaterialItemKnowledge(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        materialItemKnowledgeService.deleteMaterialItemKnowledge(id);
        return new ResponseJson();
    }


    @PostMapping("/findMaterialItemKnowledgeListByCondition")
    @ApiOperation(value = "根据条件查找章节和知识点的中间表列表", notes = "返回响应对象,不包含总条数")
    public ResponseJson findMaterialItemKnowledgeListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody MaterialItemKnowledge materialItemKnowledge){
        List<MaterialItemKnowledge> data=materialItemKnowledgeService.findMaterialItemKnowledgeListByCondition(materialItemKnowledge);
        return new ResponseJson(data);
    }



}
