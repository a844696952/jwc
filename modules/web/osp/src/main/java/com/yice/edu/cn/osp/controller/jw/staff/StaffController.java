package com.yice.edu.cn.osp.controller.jw.staff;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.osp.service.jw.department.DepartmentTeacherService;
import com.yice.edu.cn.osp.service.jw.staff.StaffService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/staff")
@Api(value = "/staff", description = "职工信息模块")
public class StaffController {
    @Autowired
    private StaffService staffService;
    @Autowired
    private DepartmentTeacherService departmentTeacherService;

    @PostMapping("/saveStaff")
    @ApiOperation(value = "保存职工信息对象", notes = "返回保存好的职工信息对象", response = Teacher.class)
    public ResponseJson saveStaff(
            @ApiParam(value = "职工信息对象", required = true)
            @RequestBody Teacher staff) {
        staff.setSchoolId(mySchoolId());
        Teacher s = staffService.saveStaff(staff);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findStaffById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找职工信息", notes = "返回响应对象", response = Teacher.class)
    public ResponseJson findStaffById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id) {
        Teacher staff = staffService.findStaffById(id);
        return new ResponseJson(staff);
    }

    @PostMapping("/update/updateStaff")
    @ApiOperation(value = "修改职工信息对象", notes = "返回响应对象")
    public ResponseJson updateStaff(
            @ApiParam(value = "被修改的职工信息对象,对象属性不为空则修改", required = true)
            @RequestBody Teacher staff) {
        return new ResponseJson(staffService.updateStaff(staff));
    }

    @GetMapping("/look/lookStaffById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找职工信息", notes = "返回响应对象", response = Teacher.class)
    public ResponseJson lookStaffById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id) {
        Teacher staff = staffService.findStaffById(id);
        return new ResponseJson(staff);
    }

    @PostMapping("/findStaffsByCondition")
    @ApiOperation(value = "根据条件查找职工信息", notes = "返回响应对象", response = Teacher.class)
    public ResponseJson findStaffsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody Teacher staff) {
        staff.setSchoolId(mySchoolId());
        Pager pager = staff.getPager() == null ? new Pager().setPaging(false) : staff.getPager();
        pager.setLike("name");
        List<Teacher> data = staffService.findStaffListByCondition(staff);
        long count = staffService.findStaffCountByCondition(staff);
        return new ResponseJson(data, count);
    }

    @PostMapping("/findOneStaffByCondition")
    @ApiOperation(value = "根据条件查找单个职工信息,结果必须为单条数据", notes = "没有时返回空", response = Teacher.class)
    public ResponseJson findOneStaffByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody Teacher staff) {
        Teacher one = staffService.findOneStaffByCondition(staff);
        return new ResponseJson(one);
    }

    @GetMapping("/deleteStaff/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteStaff(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id) {
        staffService.deleteStaff(id);
        return new ResponseJson();
    }


    @PostMapping("/findStaffListByCondition")
    @ApiOperation(value = "根据条件查找职工信息列表", notes = "返回响应对象,不包含总条数", response = Teacher.class)
    public ResponseJson findStaffListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody Teacher staff) {
        staff.setSchoolId(mySchoolId());
        List<Teacher> data = staffService.findStaffListByCondition(staff);
        return new ResponseJson(data);
    }

    @GetMapping("/ignore/findDepartmentByTeacherId/{teacherId}")
    @ApiOperation(value = "通过教师id查询教师所属部门", notes = "返回响应对象")
    public ResponseJson findDepartmentByTeacherId(@PathVariable("teacherId") String teacherId) {
        return new ResponseJson(departmentTeacherService.findDepartmentByTeacherId(teacherId));
    }
    @GetMapping("/ignore/findDepartmentBySchoolId4Staff")
    @ApiOperation(value = "查询学校有职工的部门", notes = "返回响应对象")
    public ResponseJson findDepartmentBySchoolId4Staff() {
        return new ResponseJson(departmentTeacherService.findDepartmentBySchoolId4Staff(mySchoolId()));
    }

}
