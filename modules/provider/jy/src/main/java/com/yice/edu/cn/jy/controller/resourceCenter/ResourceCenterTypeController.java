package com.yice.edu.cn.jy.controller.resourceCenter;

import com.yice.edu.cn.common.pojo.jy.resourceCenter.ResourceCenterType;
import com.yice.edu.cn.jy.service.resourceCenter.ResourceCenterTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/resourceCenterType")
@Api(value = "/resourceCenterType",description = "资源中心：分类表模块")
public class ResourceCenterTypeController {
    @Autowired
    private ResourceCenterTypeService resourceCenterTypeService;
    @GetMapping("/findResourceCenterTypeById/{id}")
    @ApiOperation(value = "通过id查找资源中心：分类表", notes = "返回资源中心：分类表对象")
    public ResourceCenterType findResourceCenterTypeById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return resourceCenterTypeService.findResourceCenterTypeById(id);
    }

    @PostMapping("/saveResourceCenterType")
    @ApiOperation(value = "保存资源中心：分类表", notes = "返回资源中心：分类表对象")
    public ResourceCenterType saveResourceCenterType(
            @ApiParam(value = "资源中心：分类表对象", required = true)
            @RequestBody ResourceCenterType resourceCenterType){
        resourceCenterTypeService.saveResourceCenterType(resourceCenterType);
        return resourceCenterType;
    }

    @PostMapping("/findResourceCenterTypeListByCondition")
    @ApiOperation(value = "根据条件查找资源中心：分类表列表", notes = "返回资源中心：分类表列表")
    public List<ResourceCenterType> findResourceCenterTypeListByCondition(
            @ApiParam(value = "资源中心：分类表对象")
            @RequestBody ResourceCenterType resourceCenterType){
        return resourceCenterTypeService.findResourceCenterTypeListByCondition(resourceCenterType);
    }
    @PostMapping("/findResourceCenterTypeCountByCondition")
    @ApiOperation(value = "根据条件查找资源中心：分类表列表个数", notes = "返回资源中心：分类表总个数")
    public long findResourceCenterTypeCountByCondition(
            @ApiParam(value = "资源中心：分类表对象")
            @RequestBody ResourceCenterType resourceCenterType){
        return resourceCenterTypeService.findResourceCenterTypeCountByCondition(resourceCenterType);
    }

    @PostMapping("/updateResourceCenterType")
    @ApiOperation(value = "修改资源中心：分类表", notes = "资源中心：分类表对象必传")
    public void updateResourceCenterType(
            @ApiParam(value = "资源中心：分类表对象,对象属性不为空则修改", required = true)
            @RequestBody ResourceCenterType resourceCenterType){
        resourceCenterTypeService.updateResourceCenterType(resourceCenterType);
    }

    @GetMapping("/deleteResourceCenterType/{id}")
    @ApiOperation(value = "通过id删除资源中心：分类表")
    public void deleteResourceCenterType(
            @ApiParam(value = "资源中心：分类表对象", required = true)
            @PathVariable String id){
        resourceCenterTypeService.deleteResourceCenterType(id);
    }
    @PostMapping("/deleteResourceCenterTypeByCondition")
    @ApiOperation(value = "根据条件删除资源中心：分类表")
    public void deleteResourceCenterTypeByCondition(
            @ApiParam(value = "资源中心：分类表对象")
            @RequestBody ResourceCenterType resourceCenterType){
        resourceCenterTypeService.deleteResourceCenterTypeByCondition(resourceCenterType);
    }
    @PostMapping("/findOneResourceCenterTypeByCondition")
    @ApiOperation(value = "根据条件查找单个资源中心：分类表,结果必须为单条数据", notes = "返回单个资源中心：分类表,没有时为空")
    public ResourceCenterType findOneResourceCenterTypeByCondition(
            @ApiParam(value = "资源中心：分类表对象")
            @RequestBody ResourceCenterType resourceCenterType){
        return resourceCenterTypeService.findOneResourceCenterTypeByCondition(resourceCenterType);
    }

    @PostMapping("/deleteResourceCenterCondition4Like")
    @ApiOperation(value = "根据条件删除资源中心：分类表")
    public void deleteResourceCenterCondition4Like(
            @ApiParam(value = "资源中心：分类表对象")
            @RequestBody ResourceCenterType resourceCenterType){
        resourceCenterTypeService.deleteResourceCenterCondition4Like(resourceCenterType);
    }

    @PostMapping("/saveResourceCenterType4Like")
    @ApiOperation(value = "保存资源中心：分类表", notes = "返回资源中心：分类表对象")
    public ResourceCenterType saveResourceCenterType4Like(
            @ApiParam(value = "资源中心：分类表对象", required = true)
            @RequestBody ResourceCenterType resourceCenterType){
        resourceCenterTypeService.saveResourceCenterType4Like(resourceCenterType);
        return resourceCenterType;
    }

    @PostMapping("/findResourceCenterTypeListByCondition4Like")
    @ApiOperation(value = "根据条件查找资源中心：分类表列表", notes = "返回资源中心：分类表列表")
    public List<ResourceCenterType> findResourceCenterTypeListByCondition4Like(
            @ApiParam(value = "资源中心：分类表对象")
            @RequestBody ResourceCenterType resourceCenterType){
        return resourceCenterTypeService.findResourceCenterTypeListByCondition4Like(resourceCenterType);
    }

    @PostMapping("/updateResourceCenterType4Like")
    @ApiOperation(value = "修改资源中心：分类表", notes = "资源中心：分类表对象必传")
    public void updateResourceCenterType4Like(
            @ApiParam(value = "资源中心：分类表对象,对象属性不为空则修改", required = true)
            @RequestBody ResourceCenterType resourceCenterType){
        resourceCenterTypeService.updateResourceCenterType4Like(resourceCenterType);
    }

    @GetMapping("/deleteResourceCenterType4Like/{id}")
    @ApiOperation(value = "通过id删除资源中心：分类表")
    public ResourceCenterType deleteResourceCenterType4Like(
            @ApiParam(value = "资源中心：分类表对象", required = true)
            @PathVariable String id){
        return resourceCenterTypeService.deleteResourceCenterType4Like(id);
    }
}
