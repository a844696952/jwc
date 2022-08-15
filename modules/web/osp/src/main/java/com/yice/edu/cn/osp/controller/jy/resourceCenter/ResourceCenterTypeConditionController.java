package com.yice.edu.cn.osp.controller.jy.resourceCenter;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jy.resourceCenter.ResourceCenterTypeCondition;
import com.yice.edu.cn.osp.service.jy.resourceCenter.ResourceCenterTypeConditionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@RestController
@RequestMapping("/resourceCenterTypeCondition")
@Api(value = "/resourceCenterTypeCondition",description = "资源分类条件关联表模块")
public class ResourceCenterTypeConditionController {
    @Autowired
    private ResourceCenterTypeConditionService resourceCenterTypeConditionService;

    @PostMapping("/saveResourceCenterTypeCondition")
    @ApiOperation(value = "保存资源分类条件关联表对象", notes = "返回保存好的资源分类条件关联表对象", response=ResourceCenterTypeCondition.class)
    public ResponseJson saveResourceCenterTypeCondition(
            @ApiParam(value = "资源分类条件关联表对象", required = true)
            @RequestBody ResourceCenterTypeCondition resourceCenterTypeCondition){
        ResourceCenterTypeCondition s=resourceCenterTypeConditionService.saveResourceCenterTypeCondition(resourceCenterTypeCondition);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findResourceCenterTypeConditionById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找资源分类条件关联表", notes = "返回响应对象", response=ResourceCenterTypeCondition.class)
    public ResponseJson findResourceCenterTypeConditionById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        ResourceCenterTypeCondition resourceCenterTypeCondition=resourceCenterTypeConditionService.findResourceCenterTypeConditionById(id);
        return new ResponseJson(resourceCenterTypeCondition);
    }

    @PostMapping("/update/updateResourceCenterTypeCondition")
    @ApiOperation(value = "修改资源分类条件关联表对象", notes = "返回响应对象")
    public ResponseJson updateResourceCenterTypeCondition(
            @ApiParam(value = "被修改的资源分类条件关联表对象,对象属性不为空则修改", required = true)
            @RequestBody ResourceCenterTypeCondition resourceCenterTypeCondition){
        resourceCenterTypeConditionService.updateResourceCenterTypeCondition(resourceCenterTypeCondition);
        return new ResponseJson();
    }

    @GetMapping("/look/lookResourceCenterTypeConditionById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找资源分类条件关联表", notes = "返回响应对象", response=ResourceCenterTypeCondition.class)
    public ResponseJson lookResourceCenterTypeConditionById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        ResourceCenterTypeCondition resourceCenterTypeCondition=resourceCenterTypeConditionService.findResourceCenterTypeConditionById(id);
        return new ResponseJson(resourceCenterTypeCondition);
    }

    @PostMapping("/findResourceCenterTypeConditionsByCondition")
    @ApiOperation(value = "根据条件查找资源分类条件关联表", notes = "返回响应对象", response=ResourceCenterTypeCondition.class)
    public ResponseJson findResourceCenterTypeConditionsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody ResourceCenterTypeCondition resourceCenterTypeCondition){
        List<ResourceCenterTypeCondition> data=resourceCenterTypeConditionService.findResourceCenterTypeConditionListByCondition(resourceCenterTypeCondition);
        long count=resourceCenterTypeConditionService.findResourceCenterTypeConditionCountByCondition(resourceCenterTypeCondition);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneResourceCenterTypeConditionByCondition")
    @ApiOperation(value = "根据条件查找单个资源分类条件关联表,结果必须为单条数据", notes = "没有时返回空", response=ResourceCenterTypeCondition.class)
    public ResponseJson findOneResourceCenterTypeConditionByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody ResourceCenterTypeCondition resourceCenterTypeCondition){
        ResourceCenterTypeCondition one=resourceCenterTypeConditionService.findOneResourceCenterTypeConditionByCondition(resourceCenterTypeCondition);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteResourceCenterTypeCondition/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteResourceCenterTypeCondition(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        resourceCenterTypeConditionService.deleteResourceCenterTypeCondition(id);
        return new ResponseJson();
    }


    @PostMapping("/findResourceCenterTypeConditionListByCondition")
    @ApiOperation(value = "根据条件查找资源分类条件关联表列表", notes = "返回响应对象,不包含总条数", response=ResourceCenterTypeCondition.class)
    public ResponseJson findResourceCenterTypeConditionListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody ResourceCenterTypeCondition resourceCenterTypeCondition){
        List<ResourceCenterTypeCondition> data=resourceCenterTypeConditionService.findResourceCenterTypeConditionListByCondition(resourceCenterTypeCondition);
        return new ResponseJson(data);
    }



}
