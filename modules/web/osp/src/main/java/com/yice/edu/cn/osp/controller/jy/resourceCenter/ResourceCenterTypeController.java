package com.yice.edu.cn.osp.controller.jy.resourceCenter;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jy.resourceCenter.ResourceCenterCondition;
import com.yice.edu.cn.common.pojo.jy.resourceCenter.ResourceCenterType;
import com.yice.edu.cn.osp.service.jy.resourceCenter.ResourceCenterConditionService;
import com.yice.edu.cn.osp.service.jy.resourceCenter.ResourceCenterTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/resourceCenterType")
@Api(value = "/resourceCenterType",description = "资源中心：分类表模块")
public class ResourceCenterTypeController {
    @Autowired
    private ResourceCenterConditionService resourceCenterConditionService;
    @Autowired
    private ResourceCenterTypeService resourceCenterTypeService;

    @PostMapping("/saveResourceCenterType")//2
    @ApiOperation(value = "保存资源中心：分类表对象", notes = "返回保存好的资源中心：分类表对象", response= ResourceCenterType.class)
    public ResponseJson saveResourceCenterType(
            @ApiParam(value = "资源中心：分类表对象", required = true)
            @RequestBody ResourceCenterType resourceCenterType){
        resourceCenterType.setSchoolId(mySchoolId());
        ResourceCenterType s=resourceCenterTypeService.saveResourceCenterType4Like(resourceCenterType);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findResourceCenterTypeById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找资源中心：分类表", notes = "返回响应对象", response=ResourceCenterType.class)
    public ResponseJson findResourceCenterTypeById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        ResourceCenterType resourceCenterType=resourceCenterTypeService.findResourceCenterTypeById(id);
        return new ResponseJson(resourceCenterType);
    }

    @PostMapping("/update/updateResourceCenterType")//3
    @ApiOperation(value = "修改资源中心：分类表对象", notes = "返回响应对象")
    public ResponseJson updateResourceCenterType(
            @ApiParam(value = "被修改的资源中心：分类表对象,对象属性不为空则修改", required = true)
            @RequestBody ResourceCenterType resourceCenterType){
        resourceCenterType.setSchoolId(mySchoolId());
        resourceCenterTypeService.updateResourceCenterType4Like(resourceCenterType);
        return new ResponseJson();
    }

    @GetMapping("/look/lookResourceCenterTypeById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找资源中心：分类表", notes = "返回响应对象", response=ResourceCenterType.class)
    public ResponseJson lookResourceCenterTypeById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        ResourceCenterType resourceCenterType=resourceCenterTypeService.findResourceCenterTypeById(id);
        return new ResponseJson(resourceCenterType);
    }

    @PostMapping("/findResourceCenterTypesByCondition")//11
    @ApiOperation(value = "查询分类数据", notes = "返回响应对象", response=ResourceCenterType.class)
    public ResponseJson findResourceCenterTypesByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody ResourceCenterType resourceCenterType){
        resourceCenterType.setSchoolId(mySchoolId());
        List<ResourceCenterType> data=resourceCenterTypeService.findResourceCenterTypeListByCondition4Like(resourceCenterType);
        long count=resourceCenterTypeService.findResourceCenterTypeCountByCondition(resourceCenterType);
        return new ResponseJson(data,count);
    }

    @GetMapping("/deleteResourceCenterType/{id}")//4
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteResourceCenterType(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        ResourceCenterType resourceCenterType =
                resourceCenterTypeService.deleteResourceCenterType4Like(id);
        return new ResponseJson(resourceCenterType);
    }


    @PostMapping("/look/lookResourceCenterTypeById/findResourceCenterTypeListByCondition")
    @ApiOperation(value = "根据条件查找资源中心：分类表列表", notes = "返回响应对象,不包含总条数", response=ResourceCenterType.class)
    public ResponseJson findResourceCenterTypeListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody ResourceCenterType resourceCenterType) {
        resourceCenterType.setSchoolId(mySchoolId());
        List<ResourceCenterType> data = resourceCenterTypeService.findResourceCenterTypeListByCondition(resourceCenterType);
        return new ResponseJson(data);
    }
    @GetMapping("/look/lookResourceCenterTypeById/findResourceCenterConditionListByCondition/{id}")//6
    @ApiOperation(value = "根据条件查找资源中心:条件表列表", notes = "返回响应对象,不包含总条数", response=ResourceCenterCondition.class)
    public ResponseJson findResourceCenterConditionListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @PathVariable String id){
        ResourceCenterCondition resourceCenterCondition = new ResourceCenterCondition();
        resourceCenterCondition.setTypeId(id);
        resourceCenterCondition.setSchoolId(mySchoolId());
        List<ResourceCenterCondition> data=resourceCenterConditionService.findResourceCenterConditionListByCondition(resourceCenterCondition);
        return new ResponseJson(data);
    }

    @PostMapping("/save/saveResourceCenterCondition")//5
    @ApiOperation(value = "保存资源中心:条件表对象", notes = "返回保存好的资源中心:条件表对象", response= ResourceCenterCondition.class)
    public ResponseJson saveResourceCenterCondition(
            @ApiParam(value = "资源中心:条件表对象", required = true)
            @RequestBody ResourceCenterCondition resourceCenterCondition){
        resourceCenterCondition.setSchoolId(mySchoolId());
        resourceCenterCondition.setSort(100);
        ResourceCenterCondition s=resourceCenterConditionService.saveResourceCenterCondition(resourceCenterCondition);
        return new ResponseJson(s);
    }

    @PostMapping("/update/deleteResourceCenterCondition4Like")//7
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteResourceCenterCondition(
            @ApiParam(value = "被删除记录的id", required = true)
            @RequestBody  ResourceCenterType resourceCenterType){
        resourceCenterType.setSchoolId(mySchoolId());
        resourceCenterType = resourceCenterConditionService.deleteResourceCenterCondition4Like(resourceCenterType);
        return new ResponseJson(resourceCenterType);
    }

    @PostMapping("/look/lookResourceCenterTypeById/findTypeAll")
    @ApiOperation(value = "查询分类", notes = "返回响应对象,不包含总条数", response= ResourceCenterCondition.class)
    public ResponseJson findTypeAndCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody ResourceCenterType resourceCenterType){
        resourceCenterType.setSchoolId(mySchoolId());
        List<ResourceCenterType> type =
                resourceCenterTypeService.findResourceCenterTypeListByCondition(resourceCenterType);
        return new ResponseJson(type);
    }

    @PostMapping("/look/lookResourceCenterTypeById/findCondtionAll")
    @ApiOperation(value = "查询条件", notes = "返回响应对象,不包含总条数", response=ResourceCenterCondition.class)
    public ResponseJson findCondtionAll(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody ResourceCenterType resourceCenterType){
        ResourceCenterCondition resourceCenterCondition = new ResourceCenterCondition();
        resourceCenterCondition.setSchoolId(mySchoolId());
        resourceCenterCondition.setTypeId(resourceCenterType.getId());
        List<ResourceCenterCondition> condition =
                resourceCenterConditionService.findResourceCenterConditionListByCondition(resourceCenterCondition);
        return new ResponseJson(condition);
    }
}
