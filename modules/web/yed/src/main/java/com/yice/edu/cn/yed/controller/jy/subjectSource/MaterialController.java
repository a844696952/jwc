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

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jy.subjectSourse.Material;
import com.yice.edu.cn.common.pojo.jy.subjectSourse.MaterialItem;
import com.yice.edu.cn.yed.service.jy.subjectSource.MaterialService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/material")
@Api(value = "/material",description = "教材模块")
public class MaterialController {
    @Autowired
    private MaterialService materialService;

    @PostMapping("/saveMaterial")
    @ApiOperation(value = "保存教材对象", notes = "返回响应对象")
    public ResponseJson saveMaterial(
            @ApiParam(value = "教材对象{image:封面,name:名称,subjectMaterialId:树Id}", required = true)
            @RequestBody Material material){
    	
    	Material queryModel = new Material();
		queryModel.setName(material.getName());
		queryModel.setSubjectMaterialId(material.getSubjectMaterialId());
		long count = materialService.findMaterialCountByCondition(queryModel);
		if(count>0) {
			return new ResponseJson(false, "该教材名称已经重复!");
		}
    	
        Material s=materialService.saveMaterial(material);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findMaterialById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找教材", notes = "返回响应对象")
    public ResponseJson findMaterialById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        Material material=materialService.findMaterialById(id);
        return new ResponseJson(material);
    }

    @PostMapping("/update/updateMaterial")
    @ApiOperation(value = "修改教材对象", notes = "返回响应对象")
    public ResponseJson updateMaterial(
            @ApiParam(value = "被修改的教材对象,对象属性不为空则修改{id:教材id,name:教材名称,image:封面}", required = true)
            @RequestBody Material material){
        materialService.updateMaterial(material);
        return new ResponseJson();
    }

    @GetMapping("/look/lookMaterialById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找教材", notes = "返回响应对象")
    public ResponseJson lookMaterialById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        Material material=materialService.findMaterialById(id);
        return new ResponseJson(material);
    }

    @PostMapping("/findMaterialsByCondition")
    @ApiOperation(value = "根据条件查找教材", notes = "返回响应对象")
    public ResponseJson findMaterialsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody Material material){
        List<Material> data=materialService.findMaterialListByCondition(material);
        long count=materialService.findMaterialCountByCondition(material);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneMaterialByCondition")
    @ApiOperation(value = "根据条件查找单个教材,结果必须为单条数据", notes = "没有时返回空")
    public ResponseJson findOneMaterialByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody Material material){
        Material one=materialService.findOneMaterialByCondition(material);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteMaterial/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteMaterial(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        materialService.deleteMaterial(id);
        return new ResponseJson();
    }


    @PostMapping("/findMaterialListByCondition")
    @ApiOperation(value = "根据条件查找教材列表", notes = "返回响应对象,不包含总条数")
    public ResponseJson findMaterialListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询{subjectMaterialId:树id}")
            @Validated
            @RequestBody Material material){
        List<Material> data=materialService.findMaterialListByCondition(material);
        return new ResponseJson(data);
    }
    @GetMapping("/textbookSynchronize/{bookId}")
    public ResponseJson textbookSynchronize(@PathVariable("bookId") String bookId){
        return new ResponseJson(materialService.getCategorys(bookId));
    }
}
