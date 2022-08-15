package com.yice.edu.cn.jw.controller.loc;

import com.yice.edu.cn.common.pojo.loc.LocVersion;
import com.yice.edu.cn.jw.service.loc.LocVersionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/locVersion")
@Api(value = "/locVersion",description = "模块")
public class LocVersionController {
    @Autowired
    private LocVersionService locVersionService;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findLocVersionById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public LocVersion findLocVersionById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return locVersionService.findLocVersionById(id);
    }

    @PostMapping("/saveLocVersion")
    @ApiOperation(value = "保存", notes = "返回对象")
    public LocVersion saveLocVersion(
            @ApiParam(value = "对象", required = true)
            @RequestBody LocVersion locVersion){
        locVersionService.saveLocVersion(locVersion);
        return locVersion;
    }

    @PostMapping("/batchSaveLocVersion")
    @ApiOperation(value = "批量保存")
    public void batchSaveLocVersion(
            @ApiParam(value = "对象集合", required = true)
            @RequestBody List<LocVersion> locVersions){
        locVersionService.batchSaveLocVersion(locVersions);
    }

    @PostMapping("/findLocVersionListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<LocVersion> findLocVersionListByCondition(
            @ApiParam(value = "对象")
            @RequestBody LocVersion locVersion){
        return locVersionService.findLocVersionListByCondition(locVersion);
    }
    @PostMapping("/findLocVersionCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findLocVersionCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody LocVersion locVersion){
        return locVersionService.findLocVersionCountByCondition(locVersion);
    }

    @PostMapping("/updateLocVersion")
    @ApiOperation(value = "修改有值的字段", notes = "对象必传")
    public void updateLocVersion(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody LocVersion locVersion){
        locVersionService.updateLocVersion(locVersion);
    }
    @PostMapping("/updateLocVersionForAll")
    @ApiOperation(value = "修改所有字段", notes = "对象必传")
    public void updateLocVersionForAll(
            @ApiParam(value = "对象", required = true)
            @RequestBody LocVersion locVersion){
        locVersionService.updateLocVersionForAll(locVersion);
    }

    @GetMapping("/deleteLocVersion/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteLocVersion(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        locVersionService.deleteLocVersion(id);
    }
    @PostMapping("/deleteLocVersionByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deleteLocVersionByCondition(
            @ApiParam(value = "对象")
            @RequestBody LocVersion locVersion){
        locVersionService.deleteLocVersionByCondition(locVersion);
    }
    @PostMapping("/findOneLocVersionByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public LocVersion findOneLocVersionByCondition(
            @ApiParam(value = "对象")
            @RequestBody LocVersion locVersion){
        return locVersionService.findOneLocVersionByCondition(locVersion);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    @PostMapping("/findVersionNameRepetition")
    public long findVersionNameRepetition(@RequestBody LocVersion locVersion){
        return locVersionService.findVersionNameRepetition(locVersion);
    }
}
