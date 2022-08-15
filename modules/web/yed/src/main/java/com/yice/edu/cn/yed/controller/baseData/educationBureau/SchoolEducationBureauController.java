package com.yice.edu.cn.yed.controller.baseData.educationBureau;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.educationBureau.SchoolEducationBureau;
import com.yice.edu.cn.common.pojo.validateClass.GroupOne;
import com.yice.edu.cn.yed.service.baseData.educationBureau.SchoolEducationBureauService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schoolEducationBureau")
@Api(value = "/schoolEducationBureau",description = "学校教育局关联表模块")
public class SchoolEducationBureauController {
    @Autowired
    private SchoolEducationBureauService schoolEducationBureauService;

    @PostMapping("/saveSchoolEducationBureau")
    @ApiOperation(value = "保存学校教育局关联表对象", notes = "返回响应对象")
    public ResponseJson saveSchoolEducationBureau(
            @ApiParam(value = "学校教育局关联表对象", required = true)
            @RequestBody SchoolEducationBureau schoolEducationBureau){
        SchoolEducationBureau s=schoolEducationBureauService.saveSchoolEducationBureau(schoolEducationBureau);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findSchoolEducationBureauById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找学校教育局关联表", notes = "返回响应对象")
    public ResponseJson findSchoolEducationBureauById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        SchoolEducationBureau schoolEducationBureau=schoolEducationBureauService.findSchoolEducationBureauById(id);
        return new ResponseJson(schoolEducationBureau);
    }

    @PostMapping("/update/updateSchoolEducationBureau")
    @ApiOperation(value = "修改学校教育局关联表对象", notes = "返回响应对象")
    public ResponseJson updateSchoolEducationBureau(
            @ApiParam(value = "被修改的学校教育局关联表对象,对象属性不为空则修改", required = true)
            @RequestBody SchoolEducationBureau schoolEducationBureau){
        schoolEducationBureauService.updateSchoolEducationBureau(schoolEducationBureau);
        return new ResponseJson();
    }

    @GetMapping("/look/lookSchoolEducationBureauById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找学校教育局关联表", notes = "返回响应对象")
    public ResponseJson lookSchoolEducationBureauById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        SchoolEducationBureau schoolEducationBureau=schoolEducationBureauService.findSchoolEducationBureauById(id);
        return new ResponseJson(schoolEducationBureau);
    }

    @PostMapping("/findSchoolEducationBureausByCondition")
    @ApiOperation(value = "根据条件查找学校教育局关联表", notes = "返回响应对象")
    public ResponseJson findSchoolEducationBureausByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody SchoolEducationBureau schoolEducationBureau){
        List<SchoolEducationBureau> data=schoolEducationBureauService.findSchoolEducationBureauListByCondition(schoolEducationBureau);
        long count=schoolEducationBureauService.findSchoolEducationBureauCountByCondition(schoolEducationBureau);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneSchoolEducationBureauByCondition")
    @ApiOperation(value = "根据条件查找单个学校教育局关联表,结果必须为单条数据", notes = "没有时返回空")
    public ResponseJson findOneSchoolEducationBureauByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody SchoolEducationBureau schoolEducationBureau){
        SchoolEducationBureau one=schoolEducationBureauService.findOneSchoolEducationBureauByCondition(schoolEducationBureau);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteSchoolEducationBureau/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteSchoolEducationBureau(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        schoolEducationBureauService.deleteSchoolEducationBureau(id);
        return new ResponseJson();
    }


    @PostMapping("/findSchoolEducationBureauListByCondition")
    @ApiOperation(value = "根据条件查找学校教育局关联表列表", notes = "返回响应对象,不包含总条数")
    public ResponseJson findSchoolEducationBureauListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody SchoolEducationBureau schoolEducationBureau){
        List<SchoolEducationBureau> data=schoolEducationBureauService.findSchoolEducationBureauListByCondition(schoolEducationBureau);
        return new ResponseJson(data);
    }

    @PostMapping("/resetSchoolEducationBureaus")
    @ApiOperation(value = "重置某个教育局的学校关联")
    public ResponseJson resetSchoolEducationBureaus(@RequestBody @Validated(GroupOne.class) SchoolEducationBureau schoolEducationBureau){
        schoolEducationBureauService.resetSchoolEducationBureaus(schoolEducationBureau);
        return new ResponseJson();
    }

}
