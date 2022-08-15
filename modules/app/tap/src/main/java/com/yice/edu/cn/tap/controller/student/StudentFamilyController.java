package com.yice.edu.cn.tap.controller.student;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.student.StudentFamily;
import com.yice.edu.cn.tap.service.student.StudentFamilyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.yice.edu.cn.tap.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/studentFamily")
@Api(value = "/studentFamily",description = "学生家庭信息模块")
public class StudentFamilyController {
    @Autowired
    private StudentFamilyService studentFamilyService;

    @PostMapping("/saveStudentFamily")
    @ApiOperation(value = "保存学生家庭信息对象", notes = "返回响应对象")
    public ResponseJson saveStudentFamily(
            @ApiParam(value = "学生家庭信息对象", required = true)
            @RequestBody StudentFamily[] studentFamilies){

        for(int i=0;i<studentFamilies.length;i++){
            studentFamilies[i].setSchoolId(mySchoolId());

            studentFamilyService.saveStudentFamily(studentFamilies[i]);
        }
        return new ResponseJson();
    }

    @GetMapping("/update/findStudentFamilyById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找学生家庭信息", notes = "返回响应对象")
    public ResponseJson findStudentFamilyById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        List<StudentFamily> studentFamily=studentFamilyService.findStudentFamilyById(id);
        return new ResponseJson(studentFamily);
    }

    @PostMapping("/update/updateStudentFamily")
    @ApiOperation(value = "修改学生家庭信息对象", notes = "返回响应对象")
    public ResponseJson updateStudentFamily(
            @ApiParam(value = "被修改的学生家庭信息对象,对象属性不为空则修改", required = true)
            @PathVariable StudentFamily studentFamily){
        studentFamilyService.updateStudentFamily(studentFamily);
        return new ResponseJson();
    }

    @PostMapping("/update/updateStudentFamilyArray")
    @ApiOperation(value = "修改家长的信息",notes = "不返回对象")
    public ResponseJson  updateStudentFamily(
            @ApiParam(value = "家长的信息",required = true)
            @RequestBody StudentFamily[] studentFamilies
    ) {
        for (int i = 0; i < studentFamilies.length; i++) {
            //long c = studentFamilyService.findStudentFamilyCountByCondition(studentFamilies[i]);

                studentFamilyService.updateStudentFamily(studentFamilies[i]);



        }
        return new ResponseJson();
    }
    @GetMapping("/look/lookStudentFamilyById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找学生家庭信息", notes = "返回响应对象")
    public ResponseJson lookStudentFamilyById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        List<StudentFamily> studentFamily=studentFamilyService.findStudentFamilyById(id);
        return new ResponseJson(studentFamily);
    }

    @PostMapping("/findStudentFamilysByCondition")
    @ApiOperation(value = "根据条件查找学生家庭信息", notes = "返回响应对象")
    public ResponseJson findStudentFamilysByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody StudentFamily studentFamily){
        List<StudentFamily> data=studentFamilyService.findStudentFamilyListByCondition(studentFamily);
        long count=studentFamilyService.findStudentFamilyCountByCondition(studentFamily);
        return new ResponseJson(data,count);
    }
    @GetMapping("/deleteStudentFamily/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteStudentFamily(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        studentFamilyService.deleteStudentFamily(id);
        return new ResponseJson();
    }

    @GetMapping("/deleteStudentFamilyByCondition")
    @ApiOperation(value = "根据条件删除学生家庭信息", notes = "返回响应对象")
    public ResponseJson deleteStudentFamilyByCondition(
            @ApiParam(value = "被删除的学生家庭信息对象,对象属性不为空则作为删除条件", required = true)
            @RequestBody StudentFamily studentFamily){
        studentFamilyService.deleteStudentFamilyByCondition(studentFamily);
        return new ResponseJson();
    }

    @PostMapping("/batchSaveStudentFamily")
    @ApiOperation(value = "批量保存学生家庭信息对象", notes = "返回响应对象")
    public ResponseJson batchSaveStudentFamily(
            @ApiParam(value = "学生家庭信息对象", required = true)
            @RequestBody List<StudentFamily> studentFamilyList){
        List<StudentFamily> s=studentFamilyService.batchSaveStudentFamily(studentFamilyList);
        return new ResponseJson(s);
    }




}
