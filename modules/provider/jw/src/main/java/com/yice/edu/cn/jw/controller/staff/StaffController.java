package com.yice.edu.cn.jw.controller.staff;

import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.jw.service.staff.StaffService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/staff")
@Api(value = "/staff",description = "职工信息模块")
public class StaffController {
    @Autowired
    private StaffService staffService;

    @GetMapping("/findStaffById/{id}")
    @ApiOperation(value = "通过id查找职工 信息", notes = "返回职工 信息对象")
    public Teacher findStaffById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return staffService.findStaffById(id);
    }

    @PostMapping("/saveStaff")
    @ApiOperation(value = "保存职工 信息", notes = "返回职工 信息对象")
    public Teacher saveStaff(
            @ApiParam(value = "职工 信息对象", required = true)
            @RequestBody Teacher staff){
        staffService.saveStaff(staff);
        return staff;
    }

    @PostMapping("/findStaffListByCondition")
    @ApiOperation(value = "根据条件查找职工 信息列表", notes = "返回职工 信息列表")
    public List<Teacher> findStaffListByCondition(
            @ApiParam(value = "职工 信息对象")
            @RequestBody Teacher staff){
        return staffService.findStaffListByCondition(staff);
    }
    @PostMapping("/findStaffCountByCondition")
    @ApiOperation(value = "根据条件查找职工 信息列表个数", notes = "返回职工 信息总个数")
    public long findStaffCountByCondition(
            @ApiParam(value = "职工 信息对象")
            @RequestBody Teacher staff){
        return staffService.findStaffCountByCondition(staff);
    }

    @PostMapping("/updateStaff")
    @ApiOperation(value = "修改职工 信息", notes = "职工 信息对象必传")
    public Teacher updateStaff(
            @ApiParam(value = "职工 信息对象,对象属性不为空则修改", required = true)
            @RequestBody Teacher staff){
        return staffService.updateStaff(staff);
    }

    @GetMapping("/deleteStaff/{id}")
    @ApiOperation(value = "通过id删除职工 信息")
    public void deleteStaff(
            @ApiParam(value = "职工 信息对象", required = true)
            @PathVariable String id){
        staffService.deleteStaff(id);
    }
    @PostMapping("/deleteStaffByCondition")
    @ApiOperation(value = "根据条件删除职工 信息")
    public void deleteStaffByCondition(
            @ApiParam(value = "职工 信息对象")
            @RequestBody Teacher staff){
        staffService.deleteStaffByCondition(staff);
    }
    @PostMapping("/findOneStaffByCondition")
    @ApiOperation(value = "根据条件查找单个职工 信息,结果必须为单条数据", notes = "返回单个职工 信息,没有时为空")
    public Teacher findOneStaffByCondition(
            @ApiParam(value = "职工 信息对象")
            @RequestBody Teacher staff){
        return staffService.findOneStaffByCondition(staff);
    }
}
