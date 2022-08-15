package com.yice.edu.cn.jw.controller.department;

import com.yice.edu.cn.common.pojo.jw.department.Department;
import com.yice.edu.cn.common.pojo.jw.department.DepartmentTeacher;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.jw.service.department.DepartmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/department")
@Api(value = "/department",description = "组织架构模块")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/findDepartmentById/{id}")
    @ApiOperation(value = "通过id查找组织架构", notes = "返回组织架构对象")
    public Department findDepartmentById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return departmentService.findDepartmentById(id);
    }

    @PostMapping("/saveDepartment")
    @ApiOperation(value = "保存组织架构", notes = "返回组织架构对象")
    public Department saveDepartment(
            @ApiParam(value = "组织架构对象", required = true)
            @RequestBody Department department){
        departmentService.saveDepartment(department);
        return department;
    }

    @PostMapping("/findDepartmentListByCondition")
    @ApiOperation(value = "根据条件查找组织架构列表", notes = "返回组织架构列表")
    public List<Department> findDepartmentListByCondition(
            @ApiParam(value = "组织架构对象")
            @RequestBody Department department){
        return departmentService.findDepartmentListByCondition(department);
    }
    @PostMapping("/findDepartmentCountByCondition")
    @ApiOperation(value = "根据条件查找组织架构列表个数", notes = "返回组织架构总个数")
    public long findDepartmentCountByCondition(
            @ApiParam(value = "组织架构对象")
            @RequestBody Department department){
        return departmentService.findDepartmentCountByCondition(department);
    }

    @PostMapping("/updateDepartment")
    @ApiOperation(value = "修改组织架构", notes = "组织架构对象必传")
    public void updateDepartment(
            @ApiParam(value = "组织架构对象,对象属性不为空则修改", required = true)
            @RequestBody Department department){
        departmentService.updateDepartment(department);
    }

    @GetMapping("/deleteDepartment/{id}")
    @ApiOperation(value = "通过id删除组织架构")
    public void deleteDepartment(
            @ApiParam(value = "组织架构对象", required = true)
            @PathVariable String id){
        departmentService.deleteDepartment(id);
    }
    @PostMapping("/deleteDepartmentByCondition")
    @ApiOperation(value = "根据条件删除组织架构")
    public void deleteDepartmentByCondition(
            @ApiParam(value = "组织架构对象")
            @RequestBody Department department){
        departmentService.deleteDepartmentByCondition(department);
    }
    @PostMapping("/findOneDepartmentByCondition")
    @ApiOperation(value = "根据条件查找单个组织架构,结果必须为单条数据", notes = "返回单个组织架构,没有时为空")
    public Department findOneDepartmentByCondition(
            @ApiParam(value = "组织架构对象")
            @RequestBody Department department){
        return departmentService.findOneDepartmentByCondition(department);
    }

    /**
     *
     * @param schoolId
     * @param notEmpty 是否只显示非空部门，就是部门里含有人员
     * @param personType 人员类型,0全部，1教师，2非教师职工
     * @return
     */
    @GetMapping("/findDepartmentTreeBySchoolId/{schoolId}/{notEmpty}/{personType}")
    public List<Department> findDepartmentTreeBySchoolId(@PathVariable("schoolId") String schoolId,@PathVariable boolean notEmpty,@PathVariable int personType){
        return departmentService.findDepartmentTreeBySchoolId(schoolId,notEmpty,personType);
    }

    @GetMapping("/findDepartmentTreeToSchoolNotifyBySchoolId/{schoolId}")
    public List<Department> findDepartmentTreeToSchoolNotifyBySchoolId(@PathVariable("schoolId") String schoolId){
        return departmentService.findDepartmentTreeToSchoolNotifyBySchoolId(schoolId);
    }
    @GetMapping("/findSelectTeachers/{departmentId}")
    public List<Teacher> findSelectTeachers(@PathVariable("departmentId")String departmentId){
        return departmentService.findSelectTeachers(departmentId);
    }
    @GetMapping("/findAllTeacherBySchoolId/{schoolId}")
    public List<Teacher> findAllTeacherBySchoolId(@PathVariable("schoolId") String schoolId){
        return departmentService.findAllTeacherBySchoolId(schoolId);
    }

    @PostMapping("/updateDepartmentMember/{departmentId}")
    public void updateDepartmentMember(@PathVariable("departmentId") String departmentId,@RequestBody List<DepartmentTeacher> departmentTeachers){
        departmentService.updateDepartmentMember(departmentId, departmentTeachers);
    }

    @PostMapping("/findDepartmentInIds/{schoolId}")
    public List<Department> findDepartmentInIds(@RequestBody List<String> ids,@PathVariable String schoolId){
        return departmentService.findDepartmentInIds(ids,schoolId);
    }
    @PostMapping("/findTeacherInIds/{schoolId}")
    public List<Department> findTeacherInIds(@RequestBody List<String> ids,@PathVariable String schoolId){
        return departmentService.findTeacherInIds(ids,schoolId);
    }
}
