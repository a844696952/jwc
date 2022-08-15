package com.yice.edu.cn.yed.controller.jw.yedHomePage;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.yed.SchoolNotified;
import com.yice.edu.cn.yed.service.jw.yedHomePage.SchoolNotifiedService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@RestController
@RequestMapping("/schoolNotified")
@Api(value = "/schoolNotified",description = "模块")
public class SchoolNotifiedController {
    @Autowired
    private SchoolNotifiedService schoolNotifiedService;

    @PostMapping("/saveSchoolNotified")
    @ApiOperation(value = "保存对象", notes = "返回响应对象")
    public ResponseJson saveSchoolNotified(
            @ApiParam(value = "对象", required = true)
            @RequestBody SchoolNotified schoolNotified){
        SchoolNotified s=schoolNotifiedService.saveSchoolNotified(schoolNotified);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findSchoolNotifiedById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象")
    public ResponseJson findSchoolNotifiedById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        SchoolNotified schoolNotified=schoolNotifiedService.findSchoolNotifiedById(id);
        return new ResponseJson(schoolNotified);
    }

    @PostMapping("/update/updateSchoolNotified")
    @ApiOperation(value = "修改对象", notes = "返回响应对象")
    public ResponseJson updateSchoolNotified(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody SchoolNotified schoolNotified){
        schoolNotifiedService.updateSchoolNotified(schoolNotified);
        return new ResponseJson();
    }

    @GetMapping("/look/lookSchoolNotifiedById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象")
    public ResponseJson lookSchoolNotifiedById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        SchoolNotified schoolNotified=schoolNotifiedService.findSchoolNotifiedById(id);
        return new ResponseJson(schoolNotified);
    }

    @PostMapping("/ignore/findSchoolNotifiedsByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象")
    public ResponseJson findSchoolNotifiedsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody SchoolNotified schoolNotified){
        List<SchoolNotified> data=schoolNotifiedService.findSchoolNotifiedListByCondition(schoolNotified);
        long count=schoolNotifiedService.findSchoolNotifiedCountByCondition(schoolNotified);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneSchoolNotifiedByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空")
    public ResponseJson findOneSchoolNotifiedByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody SchoolNotified schoolNotified){
        SchoolNotified one=schoolNotifiedService.findOneSchoolNotifiedByCondition(schoolNotified);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteSchoolNotified/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteSchoolNotified(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        schoolNotifiedService.deleteSchoolNotified(id);
        return new ResponseJson();
    }


    @PostMapping("/findSchoolNotifiedListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数")
    public ResponseJson findSchoolNotifiedListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody SchoolNotified schoolNotified){
        List<SchoolNotified> data=schoolNotifiedService.findSchoolNotifiedListByCondition(schoolNotified);
        return new ResponseJson(data);
    }



}
