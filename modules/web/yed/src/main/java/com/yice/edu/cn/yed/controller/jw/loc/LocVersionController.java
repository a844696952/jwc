package com.yice.edu.cn.yed.controller.jw.loc;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.loc.LocVersion;
import com.yice.edu.cn.yed.service.jw.loc.LocVersionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@RestController
@RequestMapping("/locVersion")
@Api(value = "/locVersion",description = "模块")
public class LocVersionController {
    @Autowired
    private LocVersionService locVersionService;
    @PostMapping("/saveLocVersion")
    @ApiOperation(value = "保存对象", notes = "返回保存好的对象", response=LocVersion.class)
    public ResponseJson saveLocVersion(
            @ApiParam(value = "对象", required = true)
            @RequestBody LocVersion locVersion){
        long l = locVersionService.findLocVersionCountByCondition(locVersion);
        if(l!=0){
            return new ResponseJson(false,"名称重复!");
        }
        locVersionService.saveLocVersion(locVersion);
        return new ResponseJson();
    }

    @GetMapping("/findLocVersionById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象", response=LocVersion.class)
    public ResponseJson findLocVersionById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        LocVersion locVersion=locVersionService.findLocVersionById(id);
        return new ResponseJson(locVersion);
    }

    @PostMapping("/updateLocVersion")
    @ApiOperation(value = "修改对象非空字段", notes = "返回响应对象")
    public ResponseJson updateLocVersion(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody LocVersion locVersion){
        locVersionService.updateLocVersion(locVersion);
        return new ResponseJson();
    }

    @PostMapping("/updateLocVersionForAll")
    @ApiOperation(value = "修改对象所有字段", notes = "返回响应对象")
    public ResponseJson updateLocVersionForAll(
            @ApiParam(value = "被修改的对象", required = true)
            @RequestBody LocVersion locVersion){
        long count = locVersionService.findVersionNameRepetition(locVersion);
        if(count!=0){
            return new ResponseJson(false,"名称重复!");
        }
        locVersionService.updateLocVersionForAll(locVersion);
        return new ResponseJson();
    }


    @PostMapping("/findLocVersionsByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象", response=LocVersion.class)
    public ResponseJson findLocVersionsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody LocVersion locVersion){
        Pager pager = locVersion.getPager();
        pager.setRangeField("createTime");
        pager.setRangeArray(locVersion.getSearchTimeZone());
        List<LocVersion> data=locVersionService.findLocVersionListByCondition(locVersion);
        long count=locVersionService.findLocVersionCountByCondition(locVersion);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneLocVersionByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空", response=LocVersion.class)
    public ResponseJson findOneLocVersionByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody LocVersion locVersion){
        LocVersion one=locVersionService.findOneLocVersionByCondition(locVersion);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteLocVersion/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteLocVersion(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        locVersionService.deleteLocVersion(id);
        return new ResponseJson();
    }


    @PostMapping("/findLocVersionListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数", response=LocVersion.class)
    public ResponseJson findLocVersionListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody LocVersion locVersion){
        List<LocVersion> data=locVersionService.findLocVersionListByCondition(locVersion);
        return new ResponseJson(data);
    }



}
