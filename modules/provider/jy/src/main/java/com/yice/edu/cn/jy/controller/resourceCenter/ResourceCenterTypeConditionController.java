package com.yice.edu.cn.jy.controller.resourceCenter;

import com.yice.edu.cn.common.pojo.jy.resourceCenter.ResourceCenterTypeCondition;
import com.yice.edu.cn.jy.service.resourceCenter.ResourceCenterTypeConditionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/resourceCenterTypeCondition")
@Api(value = "/resourceCenterTypeCondition",description = "资源分类条件关联表模块")
public class ResourceCenterTypeConditionController {
    @Autowired
    private ResourceCenterTypeConditionService resourceCenterTypeConditionService;

    @GetMapping("/findResourceCenterTypeConditionById/{id}")
    @ApiOperation(value = "通过id查找资源分类条件关联表", notes = "返回资源分类条件关联表对象")
    public ResourceCenterTypeCondition findResourceCenterTypeConditionById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return resourceCenterTypeConditionService.findResourceCenterTypeConditionById(id);
    }

    @PostMapping("/saveResourceCenterTypeCondition")
    @ApiOperation(value = "保存资源分类条件关联表", notes = "返回资源分类条件关联表对象")
    public ResourceCenterTypeCondition saveResourceCenterTypeCondition(
            @ApiParam(value = "资源分类条件关联表对象", required = true)
            @RequestBody ResourceCenterTypeCondition resourceCenterTypeCondition){
        resourceCenterTypeConditionService.saveResourceCenterTypeCondition(resourceCenterTypeCondition);
        return resourceCenterTypeCondition;
    }

    @PostMapping("/findResourceCenterTypeConditionListByCondition")
    @ApiOperation(value = "根据条件查找资源分类条件关联表列表", notes = "返回资源分类条件关联表列表")
    public List<ResourceCenterTypeCondition> findResourceCenterTypeConditionListByCondition(
            @ApiParam(value = "资源分类条件关联表对象")
            @RequestBody ResourceCenterTypeCondition resourceCenterTypeCondition){
        return resourceCenterTypeConditionService.findResourceCenterTypeConditionListByCondition(resourceCenterTypeCondition);
    }
    @PostMapping("/findResourceCenterTypeConditionCountByCondition")
    @ApiOperation(value = "根据条件查找资源分类条件关联表列表个数", notes = "返回资源分类条件关联表总个数")
    public long findResourceCenterTypeConditionCountByCondition(
            @ApiParam(value = "资源分类条件关联表对象")
            @RequestBody ResourceCenterTypeCondition resourceCenterTypeCondition){
        return resourceCenterTypeConditionService.findResourceCenterTypeConditionCountByCondition(resourceCenterTypeCondition);
    }

    @PostMapping("/updateResourceCenterTypeCondition")
    @ApiOperation(value = "修改资源分类条件关联表", notes = "资源分类条件关联表对象必传")
    public void updateResourceCenterTypeCondition(
            @ApiParam(value = "资源分类条件关联表对象,对象属性不为空则修改", required = true)
            @RequestBody ResourceCenterTypeCondition resourceCenterTypeCondition){
        resourceCenterTypeConditionService.updateResourceCenterTypeCondition(resourceCenterTypeCondition);
    }

    @GetMapping("/deleteResourceCenterTypeCondition/{id}")
    @ApiOperation(value = "通过id删除资源分类条件关联表")
    public void deleteResourceCenterTypeCondition(
            @ApiParam(value = "资源分类条件关联表对象", required = true)
            @PathVariable String id){
        resourceCenterTypeConditionService.deleteResourceCenterTypeCondition(id);
    }
    @PostMapping("/deleteResourceCenterTypeConditionByCondition")
    @ApiOperation(value = "根据条件删除资源分类条件关联表")
    public void deleteResourceCenterTypeConditionByCondition(
            @ApiParam(value = "资源分类条件关联表对象")
            @RequestBody ResourceCenterTypeCondition resourceCenterTypeCondition){
        resourceCenterTypeConditionService.deleteResourceCenterTypeConditionByCondition(resourceCenterTypeCondition);
    }
    @PostMapping("/findOneResourceCenterTypeConditionByCondition")
    @ApiOperation(value = "根据条件查找单个资源分类条件关联表,结果必须为单条数据", notes = "返回单个资源分类条件关联表,没有时为空")
    public ResourceCenterTypeCondition findOneResourceCenterTypeConditionByCondition(
            @ApiParam(value = "资源分类条件关联表对象")
            @RequestBody ResourceCenterTypeCondition resourceCenterTypeCondition){
        return resourceCenterTypeConditionService.findOneResourceCenterTypeConditionByCondition(resourceCenterTypeCondition);
    }
}
