package com.yice.edu.cn.jw.controller.department;

import com.yice.edu.cn.common.pojo.jw.department.Department;
import com.yice.edu.cn.common.pojo.jw.department.DepartmentTeacher;
import com.yice.edu.cn.jw.service.department.DepartmentTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departmentTeacher")
@Api(value = "/departmentTeacher",description = "模块")
public class DepartmentTeacherController {
    @Autowired
    private DepartmentTeacherService departmentTeacherService;

    @GetMapping("/findDepartmentTeacherById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public DepartmentTeacher findDepartmentTeacherById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return departmentTeacherService.findDepartmentTeacherById(id);
    }

    @PostMapping("/saveDepartmentTeacher")
    @ApiOperation(value = "保存", notes = "返回对象")
    public DepartmentTeacher saveDepartmentTeacher(
            @ApiParam(value = "对象", required = true)
            @RequestBody DepartmentTeacher departmentTeacher){
        departmentTeacherService.saveDepartmentTeacher(departmentTeacher);
        return departmentTeacher;
    }

    @PostMapping("/findDepartmentTeacherListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<DepartmentTeacher> findDepartmentTeacherListByCondition(
            @ApiParam(value = "对象")
            @RequestBody DepartmentTeacher departmentTeacher){
        return departmentTeacherService.findDepartmentTeacherListByCondition(departmentTeacher);
    }
    @PostMapping("/findDepartmentTeacherCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findDepartmentTeacherCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody DepartmentTeacher departmentTeacher){
        return departmentTeacherService.findDepartmentTeacherCountByCondition(departmentTeacher);
    }

    @PostMapping("/updateDepartmentTeacher")
    @ApiOperation(value = "修改", notes = "对象必传")
    public void updateDepartmentTeacher(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody DepartmentTeacher departmentTeacher){
        departmentTeacherService.updateDepartmentTeacher(departmentTeacher);
    }

    @GetMapping("/deleteDepartmentTeacher/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteDepartmentTeacher(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        departmentTeacherService.deleteDepartmentTeacher(id);
    }
    @PostMapping("/deleteDepartmentTeacherByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deleteDepartmentTeacherByCondition(
            @ApiParam(value = "对象")
            @RequestBody DepartmentTeacher departmentTeacher){
        departmentTeacherService.deleteDepartmentTeacherByCondition(departmentTeacher);
    }
    @PostMapping("/findOneDepartmentTeacherByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public DepartmentTeacher findOneDepartmentTeacherByCondition(
            @ApiParam(value = "对象")
            @RequestBody DepartmentTeacher departmentTeacher){
        return departmentTeacherService.findOneDepartmentTeacherByCondition(departmentTeacher);
    }
    @PostMapping("/updateTeacherDepartments/{teacherId}")
    @ApiOperation(value = "根据教师id维护所属部门")
    public void updateDepartmentMember(@PathVariable("teacherId") String teacherId,@RequestBody List<DepartmentTeacher> departmentTeachers){
        departmentTeacherService.updateDepartmentMember(teacherId, departmentTeachers);
    }
    @GetMapping("/findDepartmentByTeacherId/{teacherId}")
    @ApiOperation(value = "根据教师查询所属部门", notes = "返回列表,没有时为空")
    public List<DepartmentTeacher> findDepartmentByTeacherId(@PathVariable("teacherId") String teacherId){
        return departmentTeacherService.findDepartmentByTeacherId(teacherId);
    }
    @GetMapping("/findDepartmentBySchoolId4Staff/{schoolId}")
    @ApiOperation(value = "查询学校有职工的部门", notes = "返回列表,没有时为空")
    public List<Department> findDepartmentBySchoolId4Staff(@PathVariable("schoolId") String schoolId){
        return departmentTeacherService.findDepartmentBySchoolId4Staff(schoolId);
    }
}
