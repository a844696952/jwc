package com.yice.edu.cn.tap.controller.student;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.tap.service.student.StudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.yice.edu.cn.tap.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/student")
@Api(value = "/student",description = "学生信息模块")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @GetMapping("/look/lookStudentById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找学生信息", notes = "返回响应对象")
    public ResponseJson lookStudentById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable  String id){
        Student student=studentService.findStudentById(id);
        return new ResponseJson(student);
    }

    @PostMapping("/findStudentsByCondition")
    @ApiOperation(value = "根据条件查找学生信息", notes = "返回响应对象")
    public ResponseJson findStudentsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody  Student student){
        student.setSchoolId(mySchoolId());
        List<Student> data=studentService.findStudentListByCondition(student);
        long count=studentService.findStudentCountByCondition(student);
        return new ResponseJson(data,count);
    }


    @PostMapping("/find/findStudentListByCondition")
    @ApiOperation(value = "dy根据条件查找学生信息", notes = "返回响应对象")
    public ResponseJson findStudentListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody   Student student){

        student.setSchoolId(mySchoolId());
        List<Student> data=studentService.findStudentsByCondition(student);
        long count=studentService.findStudentListCountByCondition(student);
        return new ResponseJson(data,count);
    }








}
