package com.yice.edu.cn.jy.controller.titleQuota;

import com.yice.edu.cn.common.pojo.jy.titleQuota.PlatformTopicQuRe;
import com.yice.edu.cn.jy.service.titleQuota.PlatformTopicQuReService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/platformTopicQuRe")
@Api(value = "/platformTopicQuRe",description = "模块")
public class PlatformTopicQuReController {
    @Autowired
    private PlatformTopicQuReService platformTopicQuReService;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findPlatformTopicQuReById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public PlatformTopicQuRe findPlatformTopicQuReById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return platformTopicQuReService.findPlatformTopicQuReById(id);
    }

    @PostMapping("/savePlatformTopicQuRe")
    @ApiOperation(value = "保存", notes = "返回对象")
    public PlatformTopicQuRe savePlatformTopicQuRe(
            @ApiParam(value = "对象", required = true)
            @RequestBody PlatformTopicQuRe platformTopicQuRe){
        platformTopicQuReService.savePlatformTopicQuRe(platformTopicQuRe);
        return platformTopicQuRe;
    }

    @PostMapping("/batchSavePlatformTopicQuRe")
    @ApiOperation(value = "批量保存")
    public void batchSavePlatformTopicQuRe(
            @ApiParam(value = "对象集合", required = true)
            @RequestBody List<PlatformTopicQuRe> platformTopicQuRes){
        platformTopicQuReService.batchSavePlatformTopicQuRe(platformTopicQuRes);
    }

    @PostMapping("/findPlatformTopicQuReListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<PlatformTopicQuRe> findPlatformTopicQuReListByCondition(
            @ApiParam(value = "对象")
            @RequestBody PlatformTopicQuRe platformTopicQuRe){
        return platformTopicQuReService.findPlatformTopicQuReListByCondition(platformTopicQuRe);
    }
    @PostMapping("/findPlatformTopicQuReCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findPlatformTopicQuReCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody PlatformTopicQuRe platformTopicQuRe){
        return platformTopicQuReService.findPlatformTopicQuReCountByCondition(platformTopicQuRe);
    }

    @PostMapping("/updatePlatformTopicQuRe")
    @ApiOperation(value = "修改有值的字段", notes = "对象必传")
    public void updatePlatformTopicQuRe(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody PlatformTopicQuRe platformTopicQuRe){
        platformTopicQuReService.updatePlatformTopicQuRe(platformTopicQuRe);
    }
    @PostMapping("/updatePlatformTopicQuReForAll")
    @ApiOperation(value = "修改所有字段", notes = "对象必传")
    public void updatePlatformTopicQuReForAll(
            @ApiParam(value = "对象", required = true)
            @RequestBody PlatformTopicQuRe platformTopicQuRe){
        platformTopicQuReService.updatePlatformTopicQuReForAll(platformTopicQuRe);
    }

    @GetMapping("/deletePlatformTopicQuRe/{id}")
    @ApiOperation(value = "通过id删除")
    public void deletePlatformTopicQuRe(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        platformTopicQuReService.deletePlatformTopicQuRe(id);
    }
    @PostMapping("/deletePlatformTopicQuReByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deletePlatformTopicQuReByCondition(
            @ApiParam(value = "对象")
            @RequestBody PlatformTopicQuRe platformTopicQuRe){
        platformTopicQuReService.deletePlatformTopicQuReByCondition(platformTopicQuRe);
    }
    @PostMapping("/findOnePlatformTopicQuReByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public PlatformTopicQuRe findOnePlatformTopicQuReByCondition(
            @ApiParam(value = "对象")
            @RequestBody PlatformTopicQuRe platformTopicQuRe){
        return platformTopicQuReService.findOnePlatformTopicQuReByCondition(platformTopicQuRe);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
