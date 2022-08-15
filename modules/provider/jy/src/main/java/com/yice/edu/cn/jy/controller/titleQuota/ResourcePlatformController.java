package com.yice.edu.cn.jy.controller.titleQuota;

import com.yice.edu.cn.common.pojo.jy.titleQuota.ResourcePlatform;
import com.yice.edu.cn.jy.service.titleQuota.ResourcePlatformService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/resourcePlatform")
@Api(value = "/resourcePlatform",description = "运营平台（题库资源平台）模块")
public class ResourcePlatformController {
    @Autowired
    private ResourcePlatformService resourcePlatformService;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findResourcePlatformById/{id}")
    @ApiOperation(value = "通过id查找运营平台（题库资源平台）", notes = "返回运营平台（题库资源平台）对象")
    public ResourcePlatform findResourcePlatformById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return resourcePlatformService.findResourcePlatformById(id);
    }

    @PostMapping("/saveResourcePlatform")
    @ApiOperation(value = "保存运营平台（题库资源平台）", notes = "返回运营平台（题库资源平台）对象")
    public ResourcePlatform saveResourcePlatform(
            @ApiParam(value = "运营平台（题库资源平台）对象", required = true)
            @RequestBody ResourcePlatform resourcePlatform){
        resourcePlatformService.saveResourcePlatform(resourcePlatform);
        return resourcePlatform;
    }

    @PostMapping("/batchSaveResourcePlatform")
    @ApiOperation(value = "批量保存运营平台（题库资源平台）")
    public void batchSaveResourcePlatform(
            @ApiParam(value = "运营平台（题库资源平台）对象集合", required = true)
            @RequestBody List<ResourcePlatform> resourcePlatforms){
        resourcePlatformService.batchSaveResourcePlatform(resourcePlatforms);
    }

    @PostMapping("/findResourcePlatformListByCondition")
    @ApiOperation(value = "根据条件查找运营平台（题库资源平台）列表", notes = "返回运营平台（题库资源平台）列表")
    public List<ResourcePlatform> findResourcePlatformListByCondition(
            @ApiParam(value = "运营平台（题库资源平台）对象")
            @RequestBody ResourcePlatform resourcePlatform){
        return resourcePlatformService.findResourcePlatformListByCondition(resourcePlatform);
    }
    @PostMapping("/findResourcePlatformCountByCondition")
    @ApiOperation(value = "根据条件查找运营平台（题库资源平台）列表个数", notes = "返回运营平台（题库资源平台）总个数")
    public long findResourcePlatformCountByCondition(
            @ApiParam(value = "运营平台（题库资源平台）对象")
            @RequestBody ResourcePlatform resourcePlatform){
        return resourcePlatformService.findResourcePlatformCountByCondition(resourcePlatform);
    }

    @PostMapping("/updateResourcePlatform")
    @ApiOperation(value = "修改运营平台（题库资源平台）有值的字段", notes = "运营平台（题库资源平台）对象必传")
    public void updateResourcePlatform(
            @ApiParam(value = "运营平台（题库资源平台）对象,对象属性不为空则修改", required = true)
            @RequestBody ResourcePlatform resourcePlatform){
        resourcePlatformService.updateResourcePlatform(resourcePlatform);
    }
    @PostMapping("/updateResourcePlatformForAll")
    @ApiOperation(value = "修改运营平台（题库资源平台）所有字段", notes = "运营平台（题库资源平台）对象必传")
    public ResourcePlatform updateResourcePlatformForAll(
            @ApiParam(value = "运营平台（题库资源平台）对象", required = true)
            @RequestBody ResourcePlatform resourcePlatform){
        resourcePlatformService.updateResourcePlatformForAll(resourcePlatform);
        return  resourcePlatform;
    }

    @GetMapping("/deleteResourcePlatform/{id}")
    @ApiOperation(value = "通过id删除运营平台（题库资源平台）")
    public void deleteResourcePlatform(
            @ApiParam(value = "运营平台（题库资源平台）对象", required = true)
            @PathVariable String id){
        resourcePlatformService.deleteResourcePlatform(id);
    }
    @PostMapping("/deleteResourcePlatformByCondition")
    @ApiOperation(value = "根据条件删除运营平台（题库资源平台）")
    public void deleteResourcePlatformByCondition(
            @ApiParam(value = "运营平台（题库资源平台）对象")
            @RequestBody ResourcePlatform resourcePlatform){
        resourcePlatformService.deleteResourcePlatformByCondition(resourcePlatform);
    }
    @PostMapping("/findOneResourcePlatformByCondition")
    @ApiOperation(value = "根据条件查找单个运营平台（题库资源平台）,结果必须为单条数据", notes = "返回单个运营平台（题库资源平台）,没有时为空")
    public ResourcePlatform findOneResourcePlatformByCondition(
            @ApiParam(value = "运营平台（题库资源平台）对象")
            @RequestBody ResourcePlatform resourcePlatform){
        return resourcePlatformService.findOneResourcePlatformByCondition(resourcePlatform);
    }


/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
