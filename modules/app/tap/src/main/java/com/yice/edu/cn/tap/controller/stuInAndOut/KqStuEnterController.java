package com.yice.edu.cn.tap.controller.stuInAndOut;


import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.tap.service.stuInAndOut.KqStuEnterService;
import com.yice.edu.cn.tap.service.student.StudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.yice.edu.cn.tap.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/kqStuEnter")
@Api(value = "/kqStuEnter", description = "考勤管理人像录入")
public class KqStuEnterController {
    @Autowired
    private KqStuEnterService kqStuAddService;
    @Autowired
    private StudentService studentService;

    //批量校验

    //按条件查找学生
    @PostMapping("/find/findStudentsByCondition")
    @ApiOperation(value = "根据条件查找学生信息", notes = "返回响应对象")
    public ResponseJson findStudentsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody   Student student){
        student.setSchoolId(mySchoolId());
        //student.setStatus("50");
        List<Student> data=studentService.findStudentListByCondition(student);
        long count=studentService.findStudentCountByCondition(student);
        return new ResponseJson(data,count);
    }
    //按更新学生在校状态
    @PostMapping("/update/updateStudentNowStatus")
    @ApiOperation(value = "更新学生在校状态", notes = "返回响应对象,data=0失败（非班主任）")
    public ResponseJson updateStudentNowStatus(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody   Student student){
        //是否是该班班主任
        Student student1 = new Student();
        student1.setId(student.getId());
        student1.setNowStatus(student.getNowStatus());
        boolean isHeadmaster =  kqStuAddService.checkIsHeadmaster(student);
       if (!isHeadmaster){
           return new ResponseJson(false,"您不是本班班主任，不能修改学生状态");
       }
        studentService.updateStudent(student1);
        //发推送给家长
        kqStuAddService.sendStuNowStatusToParent(student);
        return new ResponseJson();
    }





}
