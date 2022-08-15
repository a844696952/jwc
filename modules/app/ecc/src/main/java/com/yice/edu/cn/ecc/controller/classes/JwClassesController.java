package com.yice.edu.cn.ecc.controller.classes;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.classes.JwClaCadresStu;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher4Classes;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherClasses;
import com.yice.edu.cn.ecc.interceptor.LoginInterceptor;
import com.yice.edu.cn.ecc.service.classes.JwClaCadresStuService;
import com.yice.edu.cn.ecc.service.classes.JwClassesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/jwClasses")
@Api(value = "/jwClasses", description = "班级信息模块")
public class JwClassesController {

    @Autowired
    private JwClassesService jwClassesService;
    @Autowired
    private JwClaCadresStuService jwClaCadresStuService;
    
    @GetMapping("/update/queryJwTeacherByClassesId/{id}")
    @ApiOperation(value = "通过班级id查找老师职务信息", notes = "返回响应对象")
    public ResponseJson queryJwTeacherByClassesId(
            @ApiParam(value = "需要用到的id", required = true) @PathVariable String id) {
        TeacherClasses teacherClasses = new TeacherClasses();
        teacherClasses.setClassesId(id);
        teacherClasses.setSchoolId(LoginInterceptor.mySchoolId());
        List<Teacher4Classes> list = jwClassesService.findClassTeacherListByClasses(teacherClasses);
        return new ResponseJson(list);
    }

    @GetMapping("/update/queryJwStudentByClassesId/{id}")
    @ApiOperation(value = "通过班级id查找学生职务信息", notes = "返回响应对象")
    public ResponseJson queryJwStudentByClassesId(
            @ApiParam(value = "需要用到的id", required = true) @PathVariable String id) {
        List<JwClaCadresStu> jwClaCadresStuList = jwClaCadresStuService.queryJwStudentByClassesId(id);
        return new ResponseJson(jwClaCadresStuList);
    }

}
