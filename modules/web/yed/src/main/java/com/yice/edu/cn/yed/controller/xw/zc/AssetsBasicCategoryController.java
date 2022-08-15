package com.yice.edu.cn.yed.controller.xw.zc;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.xw.zc.assetsBasicCategory.AssetsBasicCategory;
import com.yice.edu.cn.yed.service.xw.zc.AssetsBasicCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/assetsBasicCategory")
@Api(value = "/assetsBasicCategory",description = "资产类型基础表模块")
public class AssetsBasicCategoryController {
    @Autowired
    private AssetsBasicCategoryService assetsBasicCategoryService;

    @PostMapping("/save/saveAssetsBasicCategory")
    @ApiOperation(value = "保存资产类型基础表对象", notes = "返回保存好的资产类型基础表对象", response=AssetsBasicCategory.class)
    public ResponseJson saveAssetsBasicCategory(
            @ApiParam(value = "资产类型基础表对象", required = true)
            @RequestBody AssetsBasicCategory assetsBasicCategory){

//        添加之前先判断当前名称是否存在
        long count = assetsBasicCategoryService.findAssetsBasicCategoryCountByCondition(assetsBasicCategory);
        if(count == 0){
            AssetsBasicCategory s=assetsBasicCategoryService.saveAssetsBasicCategory(assetsBasicCategory);
            return new ResponseJson(s);
        }
        return new ResponseJson(false,"资产名称已存在！");
    }

    @GetMapping("/update/findAssetsBasicCategoryById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找资产类型基础表", notes = "返回响应对象", response=AssetsBasicCategory.class)
    public ResponseJson findAssetsBasicCategoryById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        AssetsBasicCategory assetsBasicCategory=assetsBasicCategoryService.findAssetsBasicCategoryById(id);
        return new ResponseJson(assetsBasicCategory);
    }

    @PostMapping("/update/updateAssetsBasicCategory")
    @ApiOperation(value = "修改资产类型基础表对象所有非@Transient注释的字段", notes = "返回响应对象")
    public ResponseJson updateAssetsBasicCategory(
            @ApiParam(value = "被修改的资产类型基础表对象", required = true)
            @RequestBody AssetsBasicCategory assetsBasicCategory){
        assetsBasicCategoryService.updateAssetsBasicCategory(assetsBasicCategory);
        return new ResponseJson();
    }

    @PostMapping("/update/updateAssetsBasicCategoryForNotNull")
    @ApiOperation(value = "修改资产类型基础表对象非空字段", notes = "返回响应对象")
    public ResponseJson updateAssetsBasicCategoryForNotNull(
            @ApiParam(value = "被修改的资产类型基础表对象,对象属性不为空则修改", required = true)
            @RequestBody AssetsBasicCategory assetsBasicCategory){
        assetsBasicCategoryService.updateAssetsBasicCategoryForNotNull(assetsBasicCategory);
        return new ResponseJson();
    }

    @GetMapping("/look/lookAssetsBasicCategoryById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找资产类型基础表", notes = "返回响应对象", response=AssetsBasicCategory.class)
    public ResponseJson lookAssetsBasicCategoryById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        AssetsBasicCategory assetsBasicCategory=assetsBasicCategoryService.findAssetsBasicCategoryById(id);
        return new ResponseJson(assetsBasicCategory);
    }

    @PostMapping("/find/findAssetsBasicCategorysByCondition")
    @ApiOperation(value = "根据条件查找资产类型基础表", notes = "返回响应对象", response=AssetsBasicCategory.class)
    public ResponseJson findAssetsBasicCategorysByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody AssetsBasicCategory assetsBasicCategory){
        List<AssetsBasicCategory> data=assetsBasicCategoryService.findAssetsBasicCategoryListByCondition(assetsBasicCategory);
        long count=assetsBasicCategoryService.findAssetsBasicCategoryCountByCondition(assetsBasicCategory);
        return new ResponseJson(data,count);
    }
    @PostMapping("/find/findOneAssetsBasicCategoryByCondition")
    @ApiOperation(value = "根据条件查找单个资产类型基础表,结果必须为单条数据", notes = "没有时返回空", response=AssetsBasicCategory.class)
    public ResponseJson findOneAssetsBasicCategoryByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody AssetsBasicCategory assetsBasicCategory){
        AssetsBasicCategory one=assetsBasicCategoryService.findOneAssetsBasicCategoryByCondition(assetsBasicCategory);
        return new ResponseJson(one);
    }
    @GetMapping("/delete/deleteAssetsBasicCategory/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteAssetsBasicCategory(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        assetsBasicCategoryService.deleteAssetsBasicCategory(id);
        return new ResponseJson();
    }


    @PostMapping("/find/findAssetsBasicCategoryListByCondition")
    @ApiOperation(value = "根据条件查找资产类型基础表列表", notes = "返回响应对象,不包含总条数", response=AssetsBasicCategory.class)
    public ResponseJson findAssetsBasicCategoryListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody AssetsBasicCategory assetsBasicCategory){
        List<AssetsBasicCategory> data=assetsBasicCategoryService.findAssetsBasicCategoryListByCondition(assetsBasicCategory);
        return new ResponseJson(data);
    }



}
