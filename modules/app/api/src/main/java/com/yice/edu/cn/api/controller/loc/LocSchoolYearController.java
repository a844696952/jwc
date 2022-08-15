package com.yice.edu.cn.api.controller.loc;

import com.yice.edu.cn.api.service.loc.LocSchoolYearService;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.loc.LocSchoolYear;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/locSchoolYear")
@Api(value = "/locSchoolYear",description = "学年模块")
public class LocSchoolYearController {
    @Autowired
    private LocSchoolYearService locSchoolYearService;
   /* @PostMapping("/saveLocSchoolYear")
    @ApiOperation(value = "保存学年对象", notes = "返回保存好的学年对象", response=LocSchoolYear.class)
    public ResponseJson saveLocSchoolYear(
            @ApiParam(value = "学年对象", required = true)
            @RequestBody LocSchoolYear locSchoolYear){
        LocSchoolYear s=locSchoolYearService.saveLocSchoolYear(locSchoolYear);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findLocSchoolYearById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找学年", notes = "返回响应对象", response=LocSchoolYear.class)
    public ResponseJson findLocSchoolYearById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        LocSchoolYear locSchoolYear=locSchoolYearService.findLocSchoolYearById(id);
        return new ResponseJson(locSchoolYear);
    }

    @PostMapping("/update/updateLocSchoolYear")
    @ApiOperation(value = "修改学年对象非空字段", notes = "返回响应对象")
    public ResponseJson updateLocSchoolYear(
            @ApiParam(value = "被修改的学年对象,对象属性不为空则修改", required = true)
            @RequestBody LocSchoolYear locSchoolYear){
        locSchoolYearService.updateLocSchoolYear(locSchoolYear);
        return new ResponseJson();
    }

    @PostMapping("/update/updateLocSchoolYearForAll")
    @ApiOperation(value = "修改学年对象所有字段", notes = "返回响应对象")
    public ResponseJson updateLocSchoolYearForAll(
            @ApiParam(value = "被修改的学年对象", required = true)
            @RequestBody LocSchoolYear locSchoolYear){
        locSchoolYearService.updateLocSchoolYearForAll(locSchoolYear);
        return new ResponseJson();
    }

    @GetMapping("/look/lookLocSchoolYearById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找学年", notes = "返回响应对象", response=LocSchoolYear.class)
    public ResponseJson lookLocSchoolYearById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        LocSchoolYear locSchoolYear=locSchoolYearService.findLocSchoolYearById(id);
        return new ResponseJson(locSchoolYear);
    }

    @PostMapping("/findLocSchoolYearsByCondition")
    @ApiOperation(value = "根据条件查找学年", notes = "返回响应对象", response=LocSchoolYear.class)
    public ResponseJson findLocSchoolYearsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody LocSchoolYear locSchoolYear){
        List<LocSchoolYear> data=locSchoolYearService.findLocSchoolYearListByCondition(locSchoolYear);
        long count=locSchoolYearService.findLocSchoolYearCountByCondition(locSchoolYear);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneLocSchoolYearByCondition")
    @ApiOperation(value = "根据条件查找单个学年,结果必须为单条数据", notes = "没有时返回空", response=LocSchoolYear.class)
    public ResponseJson findOneLocSchoolYearByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody LocSchoolYear locSchoolYear){
        LocSchoolYear one=locSchoolYearService.findOneLocSchoolYearByCondition(locSchoolYear);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteLocSchoolYear/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteLocSchoolYear(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        locSchoolYearService.deleteLocSchoolYear(id);
        return new ResponseJson();
    }


    @PostMapping("/findLocSchoolYearListByCondition")
    @ApiOperation(value = "根据条件查找学年列表", notes = "返回响应对象,不包含总条数", response=LocSchoolYear.class)
    public ResponseJson findLocSchoolYearListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody LocSchoolYear locSchoolYear){
        List<LocSchoolYear> data=locSchoolYearService.findLocSchoolYearListByCondition(locSchoolYear);
        return new ResponseJson(data);
    }*/



}
