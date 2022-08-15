package com.yice.edu.cn.osp.controller.jw.classes;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yice.edu.cn.common.constants.CommonClassConstants;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.classes.JwClaCadresStu;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.osp.interceptor.LoginInterceptor;
import com.yice.edu.cn.osp.service.jw.classes.JwClaCadresStuService;
import com.yice.edu.cn.osp.service.jw.student.StudentService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/jwClaCadresStu")
@Api(value = "/jwClaCadresStu",description = "班级的职位的学生模块")
public class JwClaCadresStuController {
    @Autowired
    private JwClaCadresStuService jwClaCadresStuService;
    
    @Autowired
    private StudentService studentService;
    
    @GetMapping("/findJwClaCadresStuById/{id}")
    @ApiOperation(value = "通过id查找班级的职位的学生", notes = "返回响应对象")
    public ResponseJson findJwClaCadresStuById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        JwClaCadresStu jwClaCadresStu=jwClaCadresStuService.findJwClaCadresStuById(id);
        return new ResponseJson(jwClaCadresStu);
    }

    @PostMapping("/saveJwClaCadresStu")
    @ApiOperation(value = "保存班级的职位的学生对象", notes = "返回响应对象")
    public ResponseJson saveJwClaCadresStu(
            @ApiParam(value = "班级的职位的学生对象", required = true)
            @RequestBody JwClaCadresStu jwClaCadresStu){
        JwClaCadresStu s=jwClaCadresStuService.saveJwClaCadresStu(jwClaCadresStu);
        return new ResponseJson(s);
    }
    @PostMapping("/updateJwClaCadresStu")
    @ApiOperation(value = "修改班级的职位的学生对象", notes = "返回响应对象")
    public ResponseJson updateJwClaCadresStu(
            @ApiParam(value = "被修改的班级的职位的学生对象,对象属性不为空则修改", required = true)
            @RequestBody JwClaCadresStu jwClaCadresStu){
        jwClaCadresStuService.updateJwClaCadresStu(jwClaCadresStu);
        return new ResponseJson();
    }

    @PostMapping("/findJwClaCadresStusByCondition")
    @ApiOperation(value = "根据条件查找班级的职位的学生", notes = "返回响应对象")
    public ResponseJson findJwClaCadresStusByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody JwClaCadresStu jwClaCadresStu){
        List<JwClaCadresStu> data=jwClaCadresStuService.findJwClaCadresStuListByCondition(jwClaCadresStu);
        long count=jwClaCadresStuService.findJwClaCadresStuCountByCondition(jwClaCadresStu);
        return new ResponseJson(data,count);
    }
    @GetMapping("/deleteJwClaCadresStu/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteJwClaCadresStu(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        jwClaCadresStuService.deleteJwClaCadresStu(id);
        return new ResponseJson();
    }

    @GetMapping("/deleteJwClaCadresStuByCondition")
    @ApiOperation(value = "根据条件删除班级的职位的学生", notes = "返回响应对象")
    public ResponseJson deleteJwClaCadresStuByCondition(
            @ApiParam(value = "被删除的班级的职位的学生对象,对象属性不为空则作为删除条件", required = true)
            @RequestBody JwClaCadresStu jwClaCadresStu){
        jwClaCadresStuService.deleteJwClaCadresStuByCondition(jwClaCadresStu);
        return new ResponseJson();
    }
    
    
    @PostMapping("/update/findStudents")
    @ApiOperation(value = "根据条件分页查找学生信息", notes = "返回响应对象")
    public ResponseJson findStudents(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody Student student){
    	if(student.getName()!=null) {
    		student.setName("%"+student.getName()+"%");
    	}
    	student.setStatus(CommonClassConstants.STUDENTSTATUS.IS_NORMAL);
    	List<Student> data=studentService.findStudentListForClassesByCondition(student);
    	long count = studentService.findStudentCountForClassesByCondition(student);
    	
        return new ResponseJson(data,count);
    }
    
    @PostMapping("/update/findClaCadresStudents")
    @ApiOperation(value = "查询该班级和该职务的学生信息", notes = "返回响应对象")
    public ResponseJson findClaCadresStudents(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody JwClaCadresStu jwClaCadresStu){
        //查询该班级和该职务的学生信息
    	List<JwClaCadresStu> data = jwClaCadresStuService.findJwClaCadresStuListByCondition(jwClaCadresStu);
    	
        return new ResponseJson(data);
    }
    
    @PostMapping("/update/updateStudentCadres")
    @ApiOperation(value = "修改学生的职务", notes = "返回响应对象")
    public ResponseJson updateStudentCadres(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody Map<String,String> map){
    	map.put("schoolId", LoginInterceptor.mySchoolId());
    	jwClaCadresStuService.updateStudentCadres(map);
        return new ResponseJson();
    }

}
