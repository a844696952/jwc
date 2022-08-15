package com.yice.edu.cn.yed.controller.jw.student;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.jw.student.StudentFamily;
import com.yice.edu.cn.yed.service.jw.student.StudentFamilyService;
import com.yice.edu.cn.yed.service.jw.student.StudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
@Api(value = "/student",description = "学生信息模块")
public class StudentController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private StudentFamilyService studentFamilyService;

    @PostMapping("/saveStudent")
    @ApiOperation(value = "保存学生信息对象", notes = "返回响应对象")
    public ResponseJson saveStudent(
            @ApiParam(value = "学生信息对象", required = true)
            @RequestBody Student student){
        Student s=studentService.saveStudent(student);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findStudentById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找学生信息", notes = "返回响应对象")
    public ResponseJson findStudentById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        Student student=studentService.findStudentById(id);
        return new ResponseJson(student);
    }

    @PostMapping("/update/updateStudent")
    @ApiOperation(value = "修改学生信息对象", notes = "返回响应对象")
    public ResponseJson updateStudent(
            @ApiParam(value = "被修改的学生信息对象,对象属性不为空则修改", required = true)
            @RequestBody Student student){
        studentService.updateStudent(student);
        return new ResponseJson();
    }

    @GetMapping("/look/lookStudentById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找学生信息", notes = "返回响应对象")
    public ResponseJson lookStudentById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        Student student=studentService.findStudentById(id);
        return new ResponseJson(student);
    }

    @PostMapping("/findStudentsByCondition")
    @ApiOperation(value = "根据条件查找学生信息", notes = "返回响应对象")
    public ResponseJson findStudentsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody Student student){
        List<Student> data=studentService.findStudentListByCondition(student);
        long count=studentService.findStudentCountByCondition(student);
        return new ResponseJson(data,count);
    }
    @GetMapping("/deleteStudent/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteStudent(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        studentService.deleteStudent(id);
        return new ResponseJson();
    }

    @GetMapping("/deleteStudentByCondition")
    @ApiOperation(value = "根据条件删除学生信息", notes = "返回响应对象")
    public ResponseJson deleteStudentByCondition(
            @ApiParam(value = "被删除的学生信息对象,对象属性不为空则作为删除条件", required = true)
            @RequestBody Student student){
        studentService.deleteStudentByCondition(student);
        return new ResponseJson();
    }
    @PostMapping("/findAllSchoolStudentListByCondition")
    @ApiOperation(value = "根据条件查找所有学校的学生列表", notes = "返回响应对象")
    public ResponseJson findAllSchoolStudentListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody Student student){
        List<Student> data = studentService.findAllSchoolStudentListByCondition(student);
        long count = studentService.findAllSchoolStudentCountByCondition(student);
        return new ResponseJson(data,count);
    }

    @GetMapping("/look/lookStudentFamilyById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找学生家庭信息", notes = "返回响应对象")
    public ResponseJson lookStudentFamilyById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        List<StudentFamily> studentFamily=studentFamilyService.findStudentFamilyById(id);
        return new ResponseJson(studentFamily);
    }


}
