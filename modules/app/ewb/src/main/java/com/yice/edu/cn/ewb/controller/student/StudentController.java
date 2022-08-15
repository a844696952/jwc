package com.yice.edu.cn.ewb.controller.student;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.department.Department;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.wb.studentAccount.StudentAccount;
import com.yice.edu.cn.ewb.service.student.StudentService;
import com.yice.edu.cn.ewb.interceptor.LoginInterceptor;
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
        student.setPager(null);
        List<Student> data=studentService.findStudentListByCondition(student);
        //long count=studentService.findStudentCountByCondition(student);
        return new ResponseJson(data);
    }

    /**
     * 查询学生IMEI
     * @param studentAccount
     * @return
     */
    @PostMapping("/findStudentAccountImei")
    @ApiOperation(value = "获取当前班级学生imei：传入学生所在年级(gradeId)，班级(classId)", notes = "返回学生信息")
    public ResponseJson findStudentAccountImei(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody StudentAccount studentAccount){
        studentAccount.setSchoolId(LoginInterceptor.mySchoolId());
        List<StudentAccount> data=studentService.findStudentAccountImei(studentAccount);
        return new ResponseJson(data);
    }








}
