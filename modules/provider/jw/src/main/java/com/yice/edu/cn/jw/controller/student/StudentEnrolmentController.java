package com.yice.edu.cn.jw.controller.student;

import com.yice.edu.cn.common.pojo.jw.student.StudentEnrolment;
import com.yice.edu.cn.jw.service.student.StudentEnrolmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/studentEnrolment")
@Api(value = "/studentEnrolment",description = "升学记录表模块")
public class StudentEnrolmentController {
    @Autowired
    private StudentEnrolmentService studentEnrolmentService;

    @GetMapping("/findStudentEnrolmentById/{id}")
    @ApiOperation(value = "通过id查找升学记录表", notes = "返回升学记录表对象")
    public StudentEnrolment findStudentEnrolmentById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return studentEnrolmentService.findStudentEnrolmentById(id);
    }

    @PostMapping("/saveStudentEnrolment")
    @ApiOperation(value = "保存升学记录表", notes = "返回升学记录表对象")
    public StudentEnrolment saveStudentEnrolment(
            @ApiParam(value = "升学记录表对象", required = true)
            @RequestBody StudentEnrolment studentEnrolment){
        studentEnrolmentService.saveStudentEnrolment(studentEnrolment);
        return studentEnrolment;
    }

    @PostMapping("/findStudentEnrolmentListByCondition")
    @ApiOperation(value = "根据条件查找升学记录表列表", notes = "返回升学记录表列表")
    public List<StudentEnrolment> findStudentEnrolmentListByCondition(
            @ApiParam(value = "升学记录表对象")
            @RequestBody StudentEnrolment studentEnrolment){
        return studentEnrolmentService.findStudentEnrolmentListByCondition(studentEnrolment);
    }
    @PostMapping("/findStudentEnrolmentCountByCondition")
    @ApiOperation(value = "根据条件查找升学记录表列表个数", notes = "返回升学记录表总个数")
    public long findStudentEnrolmentCountByCondition(
            @ApiParam(value = "升学记录表对象")
            @RequestBody StudentEnrolment studentEnrolment){
        return studentEnrolmentService.findStudentEnrolmentCountByCondition(studentEnrolment);
    }

    @PostMapping("/updateStudentEnrolment")
    @ApiOperation(value = "修改升学记录表", notes = "升学记录表对象必传")
    public void updateStudentEnrolment(
            @ApiParam(value = "升学记录表对象,对象属性不为空则修改", required = true)
            @RequestBody StudentEnrolment studentEnrolment){
        studentEnrolmentService.updateStudentEnrolment(studentEnrolment);
    }

    @GetMapping("/deleteStudentEnrolment/{id}")
    @ApiOperation(value = "通过id删除升学记录表")
    public void deleteStudentEnrolment(
            @ApiParam(value = "升学记录表对象", required = true)
            @PathVariable String id){
        studentEnrolmentService.deleteStudentEnrolment(id);
    }
    @PostMapping("/deleteStudentEnrolmentByCondition")
    @ApiOperation(value = "根据条件删除升学记录表")
    public void deleteStudentEnrolmentByCondition(
            @ApiParam(value = "升学记录表对象")
            @RequestBody StudentEnrolment studentEnrolment){
        studentEnrolmentService.deleteStudentEnrolmentByCondition(studentEnrolment);
    }
    @PostMapping("/findOneStudentEnrolmentByCondition")
    @ApiOperation(value = "根据条件查找单个升学记录表,结果必须为单条数据", notes = "返回单个升学记录表,没有时为空")
    public StudentEnrolment findOneStudentEnrolmentByCondition(
            @ApiParam(value = "升学记录表对象")
            @RequestBody StudentEnrolment studentEnrolment){
        return studentEnrolmentService.findOneStudentEnrolmentByCondition(studentEnrolment);
    }
}
