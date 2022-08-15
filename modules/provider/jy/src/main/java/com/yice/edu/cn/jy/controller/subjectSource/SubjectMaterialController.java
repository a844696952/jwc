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

import com.yice.edu.cn.common.pojo.jy.subjectSourse.SubjectMaterial;
import com.yice.edu.cn.jy.service.subjectSource.SubjectMaterialService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/subjectMaterial")
@Api(value = "/subjectMaterial",description = "科目教材管理模块")
public class SubjectMaterialController {
    @Autowired
    private SubjectMaterialService subjectMaterialService;

    @GetMapping("/findSubjectMaterialById/{id}")
    @ApiOperation(value = "通过id查找科目教材管理", notes = "返回科目教材管理对象")
    public SubjectMaterial findSubjectMaterialById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return subjectMaterialService.findSubjectMaterialById(id);
    }

    @PostMapping("/saveSubjectMaterial")
    @ApiOperation(value = "保存科目教材管理", notes = "返回科目教材管理对象")
    public SubjectMaterial saveSubjectMaterial(
            @ApiParam(value = "科目教材管理对象", required = true)
            @RequestBody SubjectMaterial subjectMaterial){
        subjectMaterialService.saveSubjectMaterial(subjectMaterial);
        return subjectMaterial;
    }

    @PostMapping("/findSubjectMaterialListByCondition")
    @ApiOperation(value = "根据条件查找科目教材管理列表", notes = "返回科目教材管理列表")
    public List<SubjectMaterial> findSubjectMaterialListByCondition(
            @ApiParam(value = "科目教材管理对象")
            @RequestBody SubjectMaterial subjectMaterial){
        return subjectMaterialService.findSubjectMaterialListByCondition(subjectMaterial);
    }
    @PostMapping("/findSubjectMaterialCountByCondition")
    @ApiOperation(value = "根据条件查找科目教材管理列表个数", notes = "返回科目教材管理总个数")
    public long findSubjectMaterialCountByCondition(
            @ApiParam(value = "科目教材管理对象")
            @RequestBody SubjectMaterial subjectMaterial){
        return subjectMaterialService.findSubjectMaterialCountByCondition(subjectMaterial);
    }

    @PostMapping("/updateSubjectMaterial")
    @ApiOperation(value = "修改科目教材管理", notes = "科目教材管理对象必传")
    public void updateSubjectMaterial(
            @ApiParam(value = "科目教材管理对象,对象属性不为空则修改", required = true)
            @RequestBody SubjectMaterial subjectMaterial){
        subjectMaterialService.updateSubjectMaterial(subjectMaterial);
    }

    @GetMapping("/deleteSubjectMaterial/{id}")
    @ApiOperation(value = "通过id删除科目教材管理")
    public void deleteSubjectMaterial(
            @ApiParam(value = "科目教材管理对象", required = true)
            @PathVariable String id){
        subjectMaterialService.deleteSubjectMaterial(id);
    }
    @PostMapping("/deleteSubjectMaterialByCondition")
    @ApiOperation(value = "根据条件删除科目教材管理")
    public void deleteSubjectMaterialByCondition(
            @ApiParam(value = "科目教材管理对象")
            @RequestBody SubjectMaterial subjectMaterial){
        subjectMaterialService.deleteSubjectMaterialByCondition(subjectMaterial);
    }
    @PostMapping("/findOneSubjectMaterialByCondition")
    @ApiOperation(value = "根据条件查找单个科目教材管理,结果必须为单条数据", notes = "返回单个科目教材管理,没有时为空")
    public SubjectMaterial findOneSubjectMaterialByCondition(
            @ApiParam(value = "科目教材管理对象")
            @RequestBody SubjectMaterial subjectMaterial){
        return subjectMaterialService.findOneSubjectMaterialByCondition(subjectMaterial);
    }
    @PostMapping("/findSubjectMaterialBySubject")
    @ApiOperation(value = "根据条件查找单个科目教材管理,结果必须为单条数据", notes = "返回单个科目教材管理,没有时为空")
    public ResponseJson findSubjectMaterialBySubject(
            @ApiParam(value = "科目教材管理对象")
            @RequestBody ResourceVo resourceVo){
        return subjectMaterialService.findSubjectMaterialBySubject(resourceVo);
    }
}
