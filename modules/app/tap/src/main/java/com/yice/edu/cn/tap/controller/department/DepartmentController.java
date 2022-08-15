package com.yice.edu.cn.tap.controller.department;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.department.Department;
import com.yice.edu.cn.common.pojo.jw.department.DepartmentTeacher;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.tap.interceptor.LoginInterceptor;
import com.yice.edu.cn.tap.service.department.DepartmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@RestController
@RequestMapping("/department")
@Api(value = "/department",description = "组织架构模块")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    @PostMapping("/saveDepartment")
    @ApiOperation(value = "保存组织架构对象", notes = "返回响应对象")
    public ResponseJson saveDepartment(
            @ApiParam(value = "组织架构对象", required = true)
            @RequestBody Department department){
       department.setSchoolId(LoginInterceptor.mySchoolId());
        Department s=departmentService.saveDepartment(department);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findDepartmentById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找组织架构", notes = "返回响应对象")
    public ResponseJson findDepartmentById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        Department department=departmentService.findDepartmentById(id);
        return new ResponseJson(department);
    }

    @PostMapping("/update/updateDepartment")
    @ApiOperation(value = "修改组织架构对象", notes = "返回响应对象")
    public ResponseJson updateDepartment(
            @ApiParam(value = "被修改的组织架构对象,对象属性不为空则修改", required = true)
            @RequestBody Department department){
        departmentService.updateDepartment(department);
        return new ResponseJson();
    }

    @GetMapping("/look/lookDepartmentById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找组织架构", notes = "返回响应对象")
    public ResponseJson lookDepartmentById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        Department department=departmentService.findDepartmentById(id);
        return new ResponseJson(department);
    }

    @PostMapping("/findDepartmentsByCondition")
    @ApiOperation(value = "根据条件查找组织架构", notes = "返回响应对象")
    public ResponseJson findDepartmentsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody Department department){
       department.setSchoolId(LoginInterceptor.mySchoolId());
        List<Department> data=departmentService.findDepartmentListByCondition(department);
        long count=departmentService.findDepartmentCountByCondition(department);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneDepartmentByCondition")
    @ApiOperation(value = "根据条件查找单个组织架构,结果必须为单条数据", notes = "没有时返回空")
    public ResponseJson findOneDepartmentByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody Department department){
        Department one=departmentService.findOneDepartmentByCondition(department);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteDepartment/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteDepartment(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        departmentService.deleteDepartment(id);
        return new ResponseJson();
    }


    @PostMapping("/findDepartmentListByCondition")
    @ApiOperation(value = "根据条件查找组织架构列表", notes = "返回响应对象,不包含总条数")
    public ResponseJson findDepartmentListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody Department department){
       department.setSchoolId(LoginInterceptor.mySchoolId());
        List<Department> data=departmentService.findDepartmentListByCondition(department);
        return new ResponseJson(data);
    }

    @GetMapping("/findDepartmentForTree")
    @ApiOperation(value = "查找组织架构树,包含了成员（教师）")
    public ResponseJson findDepartmentForTree(){
        return new ResponseJson(departmentService.findDepartmentTreeBySchoolId(LoginInterceptor.mySchoolId(),0));
    }

    @GetMapping("/setMember/findSelectAndUnSelect/{departmentId}")
    public ResponseJson findSelectAndUnSelect(@PathVariable String departmentId){
        List<Teacher> select=departmentService.findSelectTeachers(departmentId);
        List<String> selectIds = select.stream().flatMap((Function<Teacher, Stream<String>>) teacher -> Stream.of(teacher.getId())).collect(Collectors.toList());
        List<Teacher> all=departmentService.findAllTeacherBySchoolId(LoginInterceptor.mySchoolId());
        return new ResponseJson(selectIds,all);
    }
    @PostMapping("/setMember/updateDepartmentMember/{departmentId}")
    public ResponseJson updateDepartmentMember(@PathVariable String departmentId, @RequestBody List<DepartmentTeacher> departmentTeachers){
        departmentService.updateDepartmentMember(departmentId,departmentTeachers);
        return new ResponseJson();
    }

    @GetMapping("/ignore/findDepartmentForTree/{forDepartment}/{personType}")
    @ApiOperation(value = "查找组织架构树,包含了成员（教师）")
    public ResponseJson findDepartmentHaveOrNotTeacherForTree(@PathVariable boolean forDepartment,@PathVariable int personType){
        List<Department> departments=departmentService.findDepartmentHaveOrNotTeacherForTree(LoginInterceptor.mySchoolId(),forDepartment,personType);
        return new ResponseJson(departments);
    }




}
