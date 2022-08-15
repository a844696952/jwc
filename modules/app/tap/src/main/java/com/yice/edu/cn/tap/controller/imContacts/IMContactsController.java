package com.yice.edu.cn.tap.controller.imContacts;

import java.util.List;

import com.yice.edu.cn.common.pojo.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.tap.service.student.StudentService;
import com.yice.edu.cn.tap.service.teacher.TeacherService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/imContacts")
@Api(value = "/imContacts",description = "IM通讯录模块")
public class IMContactsController {
    @Autowired
    private StudentService studentService;
    
    @Autowired
    private TeacherService teacherService;

    @GetMapping("/lookStudentById/{id}")
    @ApiOperation(value = "查看学生详情", notes = "返回响应对象")
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

        List<Student> data=studentService.findStudentListByCondition(student);
        long count=studentService.findStudentCountByCondition(student);
        return new ResponseJson(data,count);
    }
    
    @GetMapping("/lookTeacherById/{id}")
    @ApiOperation(value = "查看学生详情", notes = "返回响应对象")
    public ResponseJson lookTeacherById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable  String id){
    	Teacher teacher=teacherService.findTeacherById(id);
        return new ResponseJson(teacher);
    }

    @PostMapping("/findTeachersByCondition")
    @ApiOperation(value = "根据条件查找学生信息", notes = "返回响应对象")
    public ResponseJson findTeachersByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody  Teacher teacher){
        teacher.setPersonType(Constant.PERSON_TYPE.TEACHER);
        List<Teacher> data=teacherService.findTeacherListByCondition(teacher);
        long count=teacherService.findTeacherCountByCondition(teacher);
        return new ResponseJson(data,count);
    }

}
