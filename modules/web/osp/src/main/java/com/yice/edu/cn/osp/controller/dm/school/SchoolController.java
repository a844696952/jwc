package com.yice.edu.cn.osp.controller.dm.school;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.school.School;
import com.yice.edu.cn.common.pojo.jw.school.SchoolExt;
import com.yice.edu.cn.osp.service.dm.school.SchoolService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/school")
@Api(value = "/school",description = "学校简介编辑")
public class SchoolController {
    @Autowired
    private SchoolService schoolService;

    @PostMapping("/saveSchool")
    @ApiOperation(value = "保存学校对象", notes = "返回响应对象")
    public ResponseJson saveSchool(
            @ApiParam(value = "学校对象", required = true)
            @RequestBody School school){
        School s=schoolService.saveSchool(school);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findSchoolById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找学校", notes = "返回响应对象")
    public ResponseJson findSchoolById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        School school=schoolService.findSchoolById(id);
        return new ResponseJson(school);
    }
    @GetMapping("/findSchool")
    @ApiOperation(value = "去更新页面,通过id查找学校", notes = "返回响应对象")
    public ResponseJson findSchool(){
        School school=schoolService.findSchoolById(mySchoolId());
        return new ResponseJson(school);
    }

    @PostMapping("/update/updateSchool")
    @ApiOperation(value = "修改学校对象", notes = "返回响应对象")
    public ResponseJson updateSchool(
            @ApiParam(value = "被修改的学校对象,对象属性不为空则修改", required = true)
            @RequestBody School school){
        school.setId(mySchoolId());
        schoolService.updateSchool(school,school.getId());
        return new ResponseJson();
    }

    @GetMapping("/look/lookSchoolById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找学校", notes = "返回响应对象")
    public ResponseJson lookSchoolById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        School school=schoolService.findSchoolById(id);
        return new ResponseJson(school);
    }

    @PostMapping("/findSchoolsByCondition")
    @ApiOperation(value = "根据条件查找学校", notes = "返回响应对象")
    public ResponseJson findSchoolsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody School school){
        List<School> data=schoolService.findSchoolListByCondition(school);
        long count=schoolService.findSchoolCountByCondition(school);
        return new ResponseJson(data,count);
    }
    @GetMapping("/deleteSchool/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteSchool(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        schoolService.deleteSchool(id);
        return new ResponseJson();
    }


    @PostMapping("/findSchoolListByCondition")
    @ApiOperation(value = "根据条件查找学校列表", notes = "返回响应对象,不包含总条数")
    public ResponseJson findSchoolListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody School school){
        List<School> data=schoolService.findSchoolListByCondition(school);
        return new ResponseJson(data);
    }

    @PostMapping("/updateSchoolAndSaveSchoolYear")
    public ResponseJson updateSchoolAndSaveSchoolYear(@RequestBody SchoolExt schoolExt) {
    	schoolService.updateSchoolAndSaveSchoolYear(schoolExt);
    	 return new ResponseJson();
    }

}
