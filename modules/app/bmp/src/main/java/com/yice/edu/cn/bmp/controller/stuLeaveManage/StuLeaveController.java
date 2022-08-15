package com.yice.edu.cn.bmp.controller.stuLeaveManage;

import cn.hutool.core.date.DateUtil;
import com.yice.edu.cn.bmp.feignClient.teacher.TeacherClassesFeign;
import com.yice.edu.cn.bmp.feignClient.teacher.TeacherFeign;
import com.yice.edu.cn.bmp.service.stuLeaveManage.StuLeaveService;
import com.yice.edu.cn.bmp.service.student.StudentService;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherClasses;
import com.yice.edu.cn.common.pojo.xw.stuLeaveManage.StuLeave;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.yice.edu.cn.bmp.interceptor.LoginInterceptor.myParentId;
import static com.yice.edu.cn.bmp.interceptor.LoginInterceptor.mySchoolId;
import static com.yice.edu.cn.bmp.interceptor.LoginInterceptor.myStudentId;


@RestController
@RequestMapping("/stuLeave")
@Api(value = "/stuLeave", description = "学生请假模块")
public class StuLeaveController {
    @Autowired
    private StuLeaveService stuLeaveService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherClassesFeign teacherClassesFeign;

    @PostMapping("/saveStuLeave")
    @ApiOperation(value = "家长端申请", notes = "家长端申请", response = StuLeave.class)
    public ResponseJson saveStuLeave(
            @ApiParam(value = "对象", required = true)
            @RequestBody StuLeave stuLeave) {
        Student student = studentService.findStudentById(myStudentId());
        TeacherClasses teacherClasses = new TeacherClasses();
        teacherClasses.setClassesId(student.getClassesId());
        Teacher teacher = teacherClassesFeign.findHeadmasterByClasses(teacherClasses);
        if (teacher == null) {
            return new ResponseJson(false, "该学生所在班级未绑定班主任");
        } else {
            stuLeave.setSchoolId(mySchoolId());
            stuLeaveService.saveStuLeave(stuLeave);
            return new ResponseJson();
        }
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
    @ApiOperation(value = "修改对象非空字段", notes = "返回响应对象")
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
    @ApiOperation(value = "我申请的", notes = "返回响应对象", response = StuLeave.class)
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
        stuLeave.setParentsId(myParentId());
        Student s1 = new Student();
        s1.setId(myStudentId());
        stuLeave.setStudent(s1);
        stuLeave.setApplicatiorType("0");
        List<StuLeave> list = stuLeaveService.findStuLeaveListByCondition(stuLeave);
        long count = stuLeaveService.findStuLeaveCountByCondition(stuLeave);
        return new ResponseJson(list, count);
    }

    @PostMapping("/findStuLeavesByCondition4")
    @ApiOperation(value = "抄送我的", notes = "抄送我的", response = StuLeave.class)
    public ResponseJson findStuLeavesByCondition4(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody StuLeave stuLeave) {
        stuLeave.setSchoolId(mySchoolId());
        stuLeave.getPager().setSortField("createTime");
        stuLeave.getPager().setSortOrder(Pager.DESC);
        stuLeave.setApplicatiorType("1");
        Student s = new Student();
        s.setId(myStudentId());
        stuLeave.setStudent(s);
        stuLeave.setParentsId(myParentId());
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

}
