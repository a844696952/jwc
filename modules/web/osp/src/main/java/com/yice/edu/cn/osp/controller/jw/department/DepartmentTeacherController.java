package com.yice.edu.cn.osp.controller.jw.department;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.department.DepartmentTeacher;
import com.yice.edu.cn.osp.service.jw.department.DepartmentTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@RestController
@RequestMapping("/departmentTeacher")
@Api(value = "/departmentTeacher",description = "模块")
public class DepartmentTeacherController {
    @Autowired
    private DepartmentTeacherService departmentTeacherService;

    @PostMapping("/saveDepartmentTeacher")
    @ApiOperation(value = "保存对象", notes = "返回响应对象")
    public ResponseJson saveDepartmentTeacher(
            @ApiParam(value = "对象", required = true)
            @RequestBody DepartmentTeacher departmentTeacher){
        DepartmentTeacher s=departmentTeacherService.saveDepartmentTeacher(departmentTeacher);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findDepartmentTeacherById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象")
    public ResponseJson findDepartmentTeacherById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        DepartmentTeacher departmentTeacher=departmentTeacherService.findDepartmentTeacherById(id);
        return new ResponseJson(departmentTeacher);
    }

    @PostMapping("/update/updateDepartmentTeacher")
    @ApiOperation(value = "修改对象", notes = "返回响应对象")
    public ResponseJson updateDepartmentTeacher(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody DepartmentTeacher departmentTeacher){
        departmentTeacherService.updateDepartmentTeacher(departmentTeacher);
        return new ResponseJson();
    }

    @GetMapping("/look/lookDepartmentTeacherById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象")
    public ResponseJson lookDepartmentTeacherById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        DepartmentTeacher departmentTeacher=departmentTeacherService.findDepartmentTeacherById(id);
        return new ResponseJson(departmentTeacher);
    }

    @PostMapping("/findDepartmentTeachersByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象")
    public ResponseJson findDepartmentTeachersByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody DepartmentTeacher departmentTeacher){
        List<DepartmentTeacher> data=departmentTeacherService.findDepartmentTeacherListByCondition(departmentTeacher);
        long count=departmentTeacherService.findDepartmentTeacherCountByCondition(departmentTeacher);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneDepartmentTeacherByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空")
    public ResponseJson findOneDepartmentTeacherByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody DepartmentTeacher departmentTeacher){
        DepartmentTeacher one=departmentTeacherService.findOneDepartmentTeacherByCondition(departmentTeacher);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteDepartmentTeacher/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteDepartmentTeacher(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        departmentTeacherService.deleteDepartmentTeacher(id);
        return new ResponseJson();
    }


    @PostMapping("/findDepartmentTeacherListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数")
    public ResponseJson findDepartmentTeacherListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody DepartmentTeacher departmentTeacher){
        List<DepartmentTeacher> data=departmentTeacherService.findDepartmentTeacherListByCondition(departmentTeacher);
        return new ResponseJson(data);
    }




}
