package com.yice.edu.cn.api.controller.student;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.api.service.student.StudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/student")
@Api(value = "/student",description = "学生信息模块")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @PostMapping("/findStudentsBySchoolId")
    @ApiOperation(value = "根据条件查找学生信息", notes = "返回响应对象")
    public ResponseJson findStudentsBySchoolId(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestParam String schoolId){
        Student student  = new Student();
        student.setPager(new Pager());
        student.getPager().setPaging(false);
        student.setSchoolId(schoolId);
        List<Student> data=studentService.findStudentListByCondition(student);
        return new ResponseJson(data);
    }

}
