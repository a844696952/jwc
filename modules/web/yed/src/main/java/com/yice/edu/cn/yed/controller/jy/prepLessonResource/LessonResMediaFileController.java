package com.yice.edu.cn.yed.controller.jy.prepLessonResource;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jy.prepLessonResource.LessonResMediaFile;
import com.yice.edu.cn.common.pojo.jy.subjectSourse.Material;
import com.yice.edu.cn.common.pojo.jy.subjectSourse.MaterialItem;
import com.yice.edu.cn.common.pojo.jy.subjectSourse.SubjectMaterial;
import com.yice.edu.cn.common.util.object.ObjectKit;
import com.yice.edu.cn.yed.service.jy.prepLessonResource.LessonResMediaFileService;
import com.yice.edu.cn.yed.service.jy.subjectSource.MaterialItemService;
import com.yice.edu.cn.yed.service.jy.subjectSource.MaterialService;
import com.yice.edu.cn.yed.service.jy.subjectSource.SubjectMaterialService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

import static com.yice.edu.cn.yed.interceptor.LoginInterceptor.currentAdmin;
import static com.yice.edu.cn.yed.interceptor.LoginInterceptor.myId;

@RestController
@RequestMapping("/lessonResMediaFile")
@Api(value = "/lessonResMediaFile",description = "模块")
public class LessonResMediaFileController {
    @Autowired
    private LessonResMediaFileService lessonResMediaFileService;
    @Autowired
    private MaterialItemService materialItemService;
    @Autowired
    private MaterialService materialService;
    @Autowired
    private SubjectMaterialService subjectMaterialService;
    @PostMapping("/saveLessonResMediaFile")
    @ApiOperation(value = "保存对象", notes = "返回响应对象")
    public ResponseJson saveLessonResMediaFile(
            @ApiParam(value = "对象", required = true)
            @RequestBody LessonResMediaFile lessonResMediaFile){

        lessonResMediaFile.setAdminId(myId());
        lessonResMediaFile.setAdminName(currentAdmin().getRealName());
        LessonResMediaFile s=lessonResMediaFileService.saveLessonResMediaFile(lessonResMediaFile);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findLessonResMediaFileById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象")
    public ResponseJson findLessonResMediaFileById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        LessonResMediaFile lessonResMediaFile=lessonResMediaFileService.findLessonResMediaFileById(id);
        return new ResponseJson(lessonResMediaFile);
    }

    @PostMapping("/update/updateLessonResMediaFile")
    @ApiOperation(value = "修改对象", notes = "返回响应对象")
    public ResponseJson updateLessonResMediaFile(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody LessonResMediaFile lessonResMediaFile){
        lessonResMediaFileService.updateLessonResMediaFile(lessonResMediaFile);
        return new ResponseJson();
    }

    @GetMapping("/look/lookLessonResMediaFileById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象")
    public ResponseJson lookLessonResMediaFileById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        LessonResMediaFile lessonResMediaFile=lessonResMediaFileService.findLessonResMediaFileById(id);
        return new ResponseJson(lessonResMediaFile);
    }

    @PostMapping("/find/findLessonResMediaFilesByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象")
    public ResponseJson findLessonResMediaFilesByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody LessonResMediaFile lessonResMediaFile){
        List<LessonResMediaFile> data=lessonResMediaFileService.findLessonResMediaFileListByCondition(lessonResMediaFile);
        long count=lessonResMediaFileService.findLessonResMediaFileCountByCondition(lessonResMediaFile);
        return new ResponseJson(data,count);
    }
    @PostMapping("/find/findLessonResMediaFilesByCondition2")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象")
    public ResponseJson findLessonResMediaFilesByCondition2(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody LessonResMediaFile lessonResMediaFile){
        List<LessonResMediaFile> data=lessonResMediaFileService.findLessonResMediaFileListByCondition2(lessonResMediaFile);
        long count=lessonResMediaFileService.findLessonResMediaFileCountByCondition2(lessonResMediaFile);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneLessonResMediaFileByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空")
    public ResponseJson findOneLessonResMediaFileByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody LessonResMediaFile lessonResMediaFile){
        LessonResMediaFile one=lessonResMediaFileService.findOneLessonResMediaFileByCondition(lessonResMediaFile);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteLessonResMediaFile/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteLessonResMediaFile(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        lessonResMediaFileService.deleteLessonResMediaFile(id);
        return new ResponseJson();
    }


    @PostMapping("/findLessonResMediaFileListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数")
    public ResponseJson findLessonResMediaFileListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody LessonResMediaFile lessonResMediaFile){
        List<LessonResMediaFile> data=lessonResMediaFileService.findLessonResMediaFileListByCondition(lessonResMediaFile);
        return new ResponseJson(data);
    }


    @GetMapping("/update/downloadCountChange/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson downloadCountChange(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        lessonResMediaFileService.downloadCountChange(id);
        return new ResponseJson();
    }

    @GetMapping("/update/numLookChange/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson numLookChange(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        lessonResMediaFileService.numLookChange(id);
        return new ResponseJson();
    }
    @GetMapping("/find/findSubjectMaterialTreeByAnnualPeriodId/{annualPeriodId}")
    @ApiOperation(value = "根据年段id查找科目教材树", notes = "返回响应对象,不包含总条数")
    public ResponseJson findSubjectMaterialTreeByAnnualPeriodId(
            @PathVariable String annualPeriodId
    ){
        SubjectMaterial subjectMaterial  = new SubjectMaterial();
        subjectMaterial.setAnnualPeriodId(annualPeriodId);
        Pager pager = new Pager();
        pager.setSortField("ddId");
        pager.setSortOrder("asc");
        pager.setPaging(false);
        subjectMaterial.setPager(pager);
        List<SubjectMaterial> data=subjectMaterialService.findSubjectMaterialListByCondition(subjectMaterial);
        List<SubjectMaterial> treeData = ObjectKit.buildTree(data,"-1");

        return new ResponseJson(treeData);
    }
    @GetMapping("/find/findMaterialItemTreeByGradeId/{materialId}")
    @ApiOperation(value = "根据年级Id查找章节树", notes = "返回响应对象,不包含总条数")
    public ResponseJson findMaterialItemTreeByGradeId(
            @ApiParam(value = "教材id")
            @PathVariable String materialId){

        MaterialItem materialItem = new MaterialItem();
        materialItem.setMaterialId(materialId);

        List<MaterialItem> data=materialItemService.findMaterialItemListByCondition(materialItem);
        List<MaterialItem> treeData = ObjectKit.buildTree(data, "-1");

        return new ResponseJson(treeData);
    }
    @PostMapping("/find/findMaterialListByCondition")
    @ApiOperation(value = "根据条件查找教材列表", notes = "返回响应对象,不包含总条数")
    public ResponseJson findMaterialListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询{subjectMaterialId:树id}")
            @Validated
            @RequestBody Material material) {
        List<Material> data = materialService.findMaterialListByCondition(material);
        return new ResponseJson(data);
    }
}
