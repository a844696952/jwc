package com.yice.edu.cn.osp.controller.jy.titleQuota;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jy.titleQuota.ResourcePlatform;
import com.yice.edu.cn.osp.service.jy.titleQuota.ResourcePlatformService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/resourcePlatform")
@Api(value = "/resourcePlatform",description = "运营平台（题库资源平台）模块")
public class ResourcePlatformController {
    @Autowired
    private ResourcePlatformService resourcePlatformService;

    @PostMapping("/saveResourcePlatform")
    @ApiOperation(value = "保存运营平台（题库资源平台）对象", notes = "返回保存好的运营平台（题库资源平台）对象", response=ResourcePlatform.class)
    public ResponseJson saveResourcePlatform(
            @ApiParam(value = "运营平台（题库资源平台）对象", required = true)
            @RequestBody ResourcePlatform resourcePlatform){
        ResourcePlatform s=resourcePlatformService.saveResourcePlatform(resourcePlatform);
        return new ResponseJson(s);
    }

    @PostMapping("/findResourcePlatformsByCondition")
    @ApiOperation(value = "根据条件查找运营平台（题库资源平台）", notes = "返回响应对象", response=ResourcePlatform.class)
    public ResponseJson findResourcePlatformsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody ResourcePlatform resourcePlatform){
        List<ResourcePlatform> data=resourcePlatformService.findResourcePlatformListByCondition(resourcePlatform);
        long count=resourcePlatformService.findResourcePlatformCountByCondition(resourcePlatform);
        return new ResponseJson(data,count);
    }

    @GetMapping("/deleteResourcePlatform/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteResourcePlatform(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        resourcePlatformService.deleteResourcePlatform(id);
        return new ResponseJson();
    }
}
