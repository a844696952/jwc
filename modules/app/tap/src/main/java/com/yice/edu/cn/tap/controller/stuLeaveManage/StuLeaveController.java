package com.yice.edu.cn.tap.controller.stuLeaveManage;

import cn.hutool.core.date.DateUtil;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.xw.stuLeaveManage.StuLeave;
import com.yice.edu.cn.tap.service.stuLeaveManage.StuLeaveService;
import com.yice.edu.cn.tap.service.student.StudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import static com.yice.edu.cn.tap.interceptor.LoginInterceptor.myId;
import static com.yice.edu.cn.tap.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/stuLeave")
@Api(value = "/stuLeave", description = "学生请假模块")
public class StuLeaveController {
    @Autowired
    private StuLeaveService stuLeaveService;
    @Autowired
    private StudentService studentService;

    @PostMapping("/saveStuLeave")
    @ApiOperation(value = "保存我发起", notes = "返回保存好的对象", response = StuLeave.class)
    public ResponseJson saveStuLeave(
            @ApiParam(value = "对象", required = true)
            @RequestBody StuLeave stuLeave) {
        stuLeave.setSchoolId(mySchoolId());
        StuLeave s = stuLeaveService.saveStuLeave(stuLeave);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findStuLeaveById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象", response = StuLeave.class)
    public ResponseJson findStuLeaveById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id) {
        StuLeave stuLeave = stuLeaveService.findStuLeaveById(id);
        return new ResponseJson(stuLeave);
    }

    @PostMapping("/update/updateStuLeave")
    @ApiOperation(value = "教师审批", notes = "返回响应对象")
    public ResponseJson updateStuLeave(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody StuLeave stuLeave) {
        stuLeaveService.updateStuLeave(stuLeave);
        return new ResponseJson();
    }

    @PostMapping("/update/updateStuLeaveForAll")
    @ApiOperation(value = "修改对象所有字段", notes = "返回响应对象")
    public ResponseJson updateStuLeaveForAll(
            @ApiParam(value = "被修改的对象", required = true)
            @RequestBody StuLeave stuLeave) {
        stuLeaveService.updateStuLeaveForAll(stuLeave);
        return new ResponseJson();
    }

    @GetMapping("/look/lookStuLeaveById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象", response = StuLeave.class)
    public ResponseJson lookStuLeaveById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id) {
        StuLeave stuLeave = stuLeaveService.findStuLeaveById(id);
        return new ResponseJson(stuLeave);
    }

    @PostMapping("/findStuLeavesByCondition")
    @ApiOperation(value = "我审批的", notes = "返回响应对象", response = StuLeave.class)
    public ResponseJson findStuLeavesByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody StuLeave stuLeave) {
        StuLeave s = new StuLeave();
        s.setSchoolId(mySchoolId());
        List<StuLeave> data = stuLeaveService.findStuLeaveListByCondition(s);
        if (data.size() > 0 && data != null) {
            for (StuLeave ss : data) {
                if (DateUtil.parse(DateUtil.now()).isAfterOrEquals(DateUtil.parse(ss.getEndTime() + ":00"))
                        && ss.getApplicatiorType().equals("0") && ss.getApproveStatus().equals("2")) {
                    ss.setApproveStatus("3");
                    stuLeaveService.updateStuLeave(ss);
                }
            }
        }
        stuLeave.setSchoolId(mySchoolId());
        stuLeave.getPager().setSortField("createTime");
        stuLeave.getPager().setSortOrder(Pager.DESC);
        stuLeave.setTeacherId(myId());
        stuLeave.setApplicatiorType("0");
        List<StuLeave> list = stuLeaveService.findStuLeaveListByCondition(stuLeave);
        long count = stuLeaveService.findStuLeaveCountByCondition(stuLeave);
        return new ResponseJson(list, count);
    }

    @PostMapping("/findStuLeavesByCondition4")
    @ApiOperation(value = "我发起的", notes = "返回响应对象", response = StuLeave.class)
    public ResponseJson findStuLeavesByCondition4(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody StuLeave stuLeave) {
        stuLeave.setSchoolId(mySchoolId());
        stuLeave.getPager().setSortField("createTime");
        stuLeave.getPager().setSortOrder(Pager.DESC);
        stuLeave.setTeacherId(myId());
        stuLeave.setApplicatiorType("1");
        List<StuLeave> list = stuLeaveService.findStuLeaveListByCondition(stuLeave);
        long count = stuLeaveService.findStuLeaveCountByCondition(stuLeave);
        return new ResponseJson(list, count);
    }

    @PostMapping("/findOneStuLeaveByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空", response = StuLeave.class)
    public ResponseJson findOneStuLeaveByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody StuLeave stuLeave) {
        StuLeave one = stuLeaveService.findOneStuLeaveByCondition(stuLeave);
        return new ResponseJson(one);
    }

    @GetMapping("/deleteStuLeave/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteStuLeave(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id) {
        stuLeaveService.deleteStuLeave(id);
        return new ResponseJson();
    }


    @PostMapping("/findStuLeaveListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数", response = StuLeave.class)
    public ResponseJson findStuLeaveListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody StuLeave stuLeave) {
        stuLeave.setSchoolId(mySchoolId());
        List<StuLeave> data = stuLeaveService.findStuLeaveListByCondition(stuLeave);
        return new ResponseJson(data);
    }

    @PostMapping("/findStudentsByClassId")
    @ApiOperation(value = "根据classId 查找班级学生", notes = "返回响应对象,不包含总条数", response = Student.class)
    public ResponseJson findStudentsByTeacherId(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody Student student) {
        student.setSchoolId(mySchoolId());
        student.getPager().setSortField("seatNumber");
        student.getPager().setSortOrder(Pager.ASC);
        List<Student> data = studentService.findStudentListByCondition(student);
        long count = studentService.findStudentCountByCondition(student);
        return new ResponseJson(data, count);
    }

    @PostMapping("/findStuLeavesByCondition5")
    @ApiOperation(value = "推送详情列表", notes = "返回响应对象", response = StuLeave.class)
    public ResponseJson findStuLeavesByCondition5(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody StuLeave stuLeave) {
        stuLeave.setSchoolId(mySchoolId());
        stuLeave.getPager().setSortField("createTime");
        stuLeave.getPager().setSortOrder(Pager.DESC);
        stuLeave.setApplicatiorType("1");
        stuLeave.setInOrOutSchool("0");
        List<StuLeave> list = stuLeaveService.findStuLeaveListByCondition(stuLeave);
        long count = stuLeaveService.findStuLeaveCountByCondition(stuLeave);
        return new ResponseJson(list, count);
    }
}
