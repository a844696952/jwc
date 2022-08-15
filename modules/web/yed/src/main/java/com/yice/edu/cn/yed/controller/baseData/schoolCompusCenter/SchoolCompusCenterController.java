package com.yice.edu.cn.yed.controller.baseData.schoolCompusCenter;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.schoolCompusCenter.SchoolCompusCenter;
import com.yice.edu.cn.yed.service.baseData.schoolCompusCenter.SchoolCompusCenterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@RestController
@RequestMapping("/schoolCompusCenter")
@Api(value = "/schoolCompusCenter",description = "模块")
public class SchoolCompusCenterController {
    @Autowired
    private SchoolCompusCenterService schoolCompusCenterService;

    @PostMapping("/saveSchoolCompusCenter")
    @ApiOperation(value = "保存对象", notes = "返回保存好的对象", response=SchoolCompusCenter.class)
    public ResponseJson saveSchoolCompusCenter(
            @ApiParam(value = "对象", required = true)
            @RequestBody SchoolCompusCenter schoolCompusCenter){
        SchoolCompusCenter s=schoolCompusCenterService.saveSchoolCompusCenter(schoolCompusCenter);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findSchoolCompusCenterById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象", response=SchoolCompusCenter.class)
    public ResponseJson findSchoolCompusCenterById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        SchoolCompusCenter schoolCompusCenter=schoolCompusCenterService.findSchoolCompusCenterById(id);
        return new ResponseJson(schoolCompusCenter);
    }

    @PostMapping("/update/updateSchoolCompusCenter")
    @ApiOperation(value = "修改对象", notes = "返回响应对象")
    public ResponseJson updateSchoolCompusCenter(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody SchoolCompusCenter schoolCompusCenter){
        schoolCompusCenterService.updateSchoolCompusCenter(schoolCompusCenter);
        return new ResponseJson();
    }

    @GetMapping("/look/lookSchoolCompusCenterById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象", response=SchoolCompusCenter.class)
    public ResponseJson lookSchoolCompusCenterById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        SchoolCompusCenter schoolCompusCenter=schoolCompusCenterService.findSchoolCompusCenterById(id);
        return new ResponseJson(schoolCompusCenter);
    }

    @PostMapping("/findSchoolCompusCentersByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象", response=SchoolCompusCenter.class)
    public ResponseJson findSchoolCompusCentersByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody SchoolCompusCenter schoolCompusCenter){
        List<SchoolCompusCenter> data=schoolCompusCenterService.findSchoolCompusCenterListByCondition(schoolCompusCenter);
        long count=schoolCompusCenterService.findSchoolCompusCenterCountByCondition(schoolCompusCenter);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneSchoolCompusCenterByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空", response=SchoolCompusCenter.class)
    public ResponseJson findOneSchoolCompusCenterByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody SchoolCompusCenter schoolCompusCenter){
        SchoolCompusCenter one=schoolCompusCenterService.findOneSchoolCompusCenterByCondition(schoolCompusCenter);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteSchoolCompusCenter/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteSchoolCompusCenter(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        schoolCompusCenterService.deleteSchoolCompusCenter(id);
        return new ResponseJson();
    }


    @PostMapping("/findSchoolCompusCenterListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数", response=SchoolCompusCenter.class)
    public ResponseJson findSchoolCompusCenterListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody SchoolCompusCenter schoolCompusCenter){
        List<SchoolCompusCenter> data=schoolCompusCenterService.findSchoolCompusCenterListByCondition(schoolCompusCenter);
        return new ResponseJson(data);
    }


    @GetMapping("/schoolCampusCenterFindList/{schoolId}")
    @ApiOperation(value = "根据学校id查找列表",notes = "返回学校对应对象")
    public ResponseJson schoolCampusCenterFindList(@PathVariable String schoolId){
        return new ResponseJson(schoolCompusCenterService.findSchoolCompusCenter(schoolId));
    }

    @PostMapping("/ignore/saveSchoolCompusCenterData")
    public ResponseJson saveSchoolCompusCenterData(@RequestBody SchoolCompusCenter schoolCompusCenter){
        schoolCompusCenterService.saveSchoolCompusCenterData(schoolCompusCenter);
        return new ResponseJson();
    }
}
