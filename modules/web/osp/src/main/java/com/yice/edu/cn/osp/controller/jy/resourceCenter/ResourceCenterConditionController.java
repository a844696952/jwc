package com.yice.edu.cn.osp.controller.jy.resourceCenter;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jy.resourceCenter.ResourceCenterCondition;
import com.yice.edu.cn.osp.service.jy.resourceCenter.ResourceCenterConditionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@RestController
@RequestMapping("/resourceCenterCondition")
@Api(value = "/resourceCenterCondition",description = "资源中心:条件表模块")
public class ResourceCenterConditionController {
    @Autowired
    private ResourceCenterConditionService resourceCenterConditionService;

    @PostMapping("/saveResourceCenterCondition")
    @ApiOperation(value = "保存资源中心:条件表对象", notes = "返回保存好的资源中心:条件表对象", response= ResourceCenterCondition.class)
    public ResponseJson saveResourceCenterCondition(
            @ApiParam(value = "资源中心:条件表对象", required = true)
            @RequestBody ResourceCenterCondition resourceCenterCondition){
        ResourceCenterCondition s=resourceCenterConditionService.saveResourceCenterCondition(resourceCenterCondition);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findResourceCenterConditionById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找资源中心:条件表", notes = "返回响应对象", response=ResourceCenterCondition.class)
    public ResponseJson findResourceCenterConditionById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        ResourceCenterCondition resourceCenterCondition=resourceCenterConditionService.findResourceCenterConditionById(id);
        return new ResponseJson(resourceCenterCondition);
    }

    @PostMapping("/update/updateResourceCenterCondition")
    @ApiOperation(value = "修改资源中心:条件表对象", notes = "返回响应对象")
    public ResponseJson updateResourceCenterCondition(
            @ApiParam(value = "被修改的资源中心:条件表对象,对象属性不为空则修改", required = true)
            @RequestBody ResourceCenterCondition resourceCenterCondition){
        resourceCenterConditionService.updateResourceCenterCondition(resourceCenterCondition);
        return new ResponseJson();
    }

    @GetMapping("/look/lookResourceCenterConditionById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找资源中心:条件表", notes = "返回响应对象", response=ResourceCenterCondition.class)
    public ResponseJson lookResourceCenterConditionById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        ResourceCenterCondition resourceCenterCondition=resourceCenterConditionService.findResourceCenterConditionById(id);
        return new ResponseJson(resourceCenterCondition);
    }

    @PostMapping("/findResourceCenterConditionsByCondition")
    @ApiOperation(value = "根据条件查找资源中心:条件表", notes = "返回响应对象", response=ResourceCenterCondition.class)
    public ResponseJson findResourceCenterConditionsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody ResourceCenterCondition resourceCenterCondition){
        List<ResourceCenterCondition> data=resourceCenterConditionService.findResourceCenterConditionListByCondition(resourceCenterCondition);
        long count=resourceCenterConditionService.findResourceCenterConditionCountByCondition(resourceCenterCondition);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneResourceCenterConditionByCondition")
    @ApiOperation(value = "根据条件查找单个资源中心:条件表,结果必须为单条数据", notes = "没有时返回空", response=ResourceCenterCondition.class)
    public ResponseJson findOneResourceCenterConditionByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody ResourceCenterCondition resourceCenterCondition){
        ResourceCenterCondition one=resourceCenterConditionService.findOneResourceCenterConditionByCondition(resourceCenterCondition);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteResourceCenterCondition/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteResourceCenterCondition(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        resourceCenterConditionService.deleteResourceCenterCondition(id);
        return new ResponseJson();
    }


    @PostMapping("/findResourceCenterConditionListByCondition")
    @ApiOperation(value = "根据条件查找资源中心:条件表列表", notes = "返回响应对象,不包含总条数", response=ResourceCenterCondition.class)
    public ResponseJson findResourceCenterConditionListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody ResourceCenterCondition resourceCenterCondition){
        List<ResourceCenterCondition> data=resourceCenterConditionService.findResourceCenterConditionListByCondition(resourceCenterCondition);
        return new ResponseJson(data);
    }



}
