package com.yice.edu.cn.mes.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.general.dd.Dd;
import com.yice.edu.cn.common.pojo.jw.school.School;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.mes.service.DdService;
import com.yice.edu.cn.mes.service.MesInspectRecordService;
import com.yice.edu.cn.mes.service.SchoolService;
import com.yice.edu.cn.mes.service.StudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.yice.edu.cn.mes.LoginInterceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/school")
@Api(value = "/school", description = "学校相关")
public class SchoolController {
    @Autowired
    private SchoolService schoolService;
    @Autowired
    private DdService ddService;
    @Autowired
    private MesInspectRecordService mesInspectRecordService;
    @Autowired
    private StudentService studentService;

    @PostMapping("/ignore/findSchoolListByCondition")
    @ApiOperation(value = "根据条件查找学校列表", notes = "返回响应对象,不包含总条数")
    public ResponseJson findSchoolListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody School school) {
        List<School> data = schoolService.findSchoolListByCondition(school);
        return new ResponseJson(data);
    }

    @GetMapping("/findGradesAndClassCurrentBySchool")
    @ApiOperation(value = "查找当前学校包含的年级班级树结构", notes = "返回响应对象")
    public ResponseJson findGradesAndClassCurrentBySchool() {
        List<Dd> data = mesInspectRecordService.findDdListBySchoolId(mySchoolId());
        if (CollectionUtil.isNotEmpty(data)) {
            data.forEach(s -> {
                Dd d = new Dd();
                d.setSchoolId(mySchoolId());
                d.setGradeId(s.getId());
                s.setChildren(ddService.findJwClassesList(d));
            });
        }
        return new ResponseJson(data);
    }

    @PostMapping("/findStudentListByCondition")
    @ApiOperation(value = "dy根据条件查找学生信息", notes = "返回响应对象")
    public ResponseJson findStudentListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody Student student) {
        student.setSchoolId(mySchoolId());
        List<Student> data = studentService.findStudentsByCondition(student);
        return new ResponseJson(data);
    }


    @GetMapping("/look/lookStudentById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找学生信息", notes = "返回响应对象")
    public ResponseJson lookStudentById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable  String id){
        Student student=studentService.findStudentById(id);
        return new ResponseJson(student);
    }


}
