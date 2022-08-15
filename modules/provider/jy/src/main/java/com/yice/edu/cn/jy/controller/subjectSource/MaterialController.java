package com.yice.edu.cn.jy.controller.subjectSource;


import java.util.List;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.qusBankResource.ResourceVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yice.edu.cn.common.pojo.jy.subjectSourse.Material;
import com.yice.edu.cn.jy.service.subjectSource.MaterialService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/material")
@Api(value = "/material",description = "教材模块")
public class MaterialController {
    @Autowired
    private MaterialService materialService;

    @GetMapping("/findMaterialById/{id}")
    @ApiOperation(value = "通过id查找教材", notes = "返回教材对象")
    public Material findMaterialById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return materialService.findMaterialById(id);
    }

    @PostMapping("/saveMaterial")
    @ApiOperation(value = "保存教材", notes = "返回教材对象")
    public Material saveMaterial(
            @ApiParam(value = "教材对象", required = true)
            @RequestBody Material material){
        materialService.saveMaterial(material);
        return material;
    }

    @PostMapping("/findMaterialListByCondition")
    @ApiOperation(value = "根据条件查找教材列表", notes = "返回教材列表")
    public List<Material> findMaterialListByCondition(
            @ApiParam(value = "教材对象")
            @RequestBody Material material){
        return materialService.findMaterialListByCondition(material);
    }
    @PostMapping("/findMaterialCountByCondition")
    @ApiOperation(value = "根据条件查找教材列表个数", notes = "返回教材总个数")
    public long findMaterialCountByCondition(
            @ApiParam(value = "教材对象")
            @RequestBody Material material){
        return materialService.findMaterialCountByCondition(material);
    }

    @PostMapping("/updateMaterial")
    @ApiOperation(value = "修改教材", notes = "教材对象必传")
    public void updateMaterial(
            @ApiParam(value = "教材对象,对象属性不为空则修改", required = true)
            @RequestBody Material material){
        materialService.updateMaterial(material);
    }

    @GetMapping("/deleteMaterial/{id}")
    @ApiOperation(value = "通过id删除教材")
    public void deleteMaterial(
            @ApiParam(value = "教材对象", required = true)
            @PathVariable String id){
        materialService.deleteMaterial(id);
    }
    @PostMapping("/deleteMaterialByCondition")
    @ApiOperation(value = "根据条件删除教材")
    public void deleteMaterialByCondition(
            @ApiParam(value = "教材对象")
            @RequestBody Material material){
        materialService.deleteMaterialByCondition(material);
    }
    @PostMapping("/findOneMaterialByCondition")
    @ApiOperation(value = "根据条件查找单个教材,结果必须为单条数据", notes = "返回单个教材,没有时为空")
    public Material findOneMaterialByCondition(
            @ApiParam(value = "教材对象")
            @RequestBody Material material){
        return materialService.findOneMaterialByCondition(material);
    }
    @PostMapping("/findMaterialsByCondition")
    @ApiOperation(value = "查询教材的第二级别目录", notes = "返回单个教材,没有时为空")
    public ResponseJson findMaterialsByCondition(
            @ApiParam(value = "教材对象")
            @RequestBody ResourceVo resourceVo){
        return materialService.findMaterialsByCondition(resourceVo);
    }
}
