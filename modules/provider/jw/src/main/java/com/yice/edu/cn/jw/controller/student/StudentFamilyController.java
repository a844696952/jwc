package com.yice.edu.cn.jw.controller.student;

import com.yice.edu.cn.common.pojo.jw.student.StudentFamily;
import com.yice.edu.cn.jw.service.student.StudentFamilyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/studentFamily")
@Api(value = "/studentFamily",description = "学生家庭信息模块")
public class StudentFamilyController {
    @Autowired
    private StudentFamilyService studentFamilyService;

    @GetMapping("/findStudentFamilyById/{id}")
    @ApiOperation(value = "通过id查找学生家庭信息", notes = "返回学生家庭信息对象")
    public List<StudentFamily> findStudentFamilyById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return studentFamilyService.findStudentFamilyById(id);
    }

    @PostMapping("/saveStudentFamily")
    @ApiOperation(value = "保存学生家庭信息", notes = "返回学生家庭信息对象")
    public StudentFamily saveStudentFamily(
            @ApiParam(value = "学生家庭信息对象", required = true)
            @RequestBody StudentFamily studentFamily){
        studentFamilyService.saveStudentFamily(studentFamily);
        return studentFamily;
    }

    @PostMapping("/findStudentFamilyListByCondition")
    @ApiOperation(value = "根据条件查找学生家庭信息列表", notes = "返回学生家庭信息列表")
    public List<StudentFamily> findStudentFamilyListByCondition(
            @ApiParam(value = "学生家庭信息对象")
            @RequestBody StudentFamily studentFamily){
        return studentFamilyService.findStudentFamilyListByCondition(studentFamily);
    }
    @PostMapping("/findStudentFamilyCountByCondition")
    @ApiOperation(value = "根据条件查找学生家庭信息列表个数", notes = "返回学生家庭信息总个数")
    public long findStudentFamilyCountByCondition(
            @ApiParam(value = "学生家庭信息对象")
            @RequestBody StudentFamily studentFamily){
        return studentFamilyService.findStudentFamilyCountByCondition(studentFamily);
    }

    @PostMapping("/updateStudentFamily")
    @ApiOperation(value = "修改学生家庭信息", notes = "学生家庭信息对象必传")
    public void updateStudentFamily(
            @ApiParam(value = "学生家庭信息对象,对象属性不为空则修改", required = true)
            @RequestBody StudentFamily studentFamily){
        studentFamilyService.updateStudentFamily(studentFamily);
    }

    @GetMapping("/deleteStudentFamily/{id}")
    @ApiOperation(value = "通过id删除学生家庭信息")
    public void deleteStudentFamily(
            @ApiParam(value = "学生家庭信息对象", required = true)
            @PathVariable String id){
        studentFamilyService.deleteStudentFamily(id);
    }
    @PostMapping("/deleteStudentFamilyByCondition")
    @ApiOperation(value = "根据条件删除学生家庭信息")
    public void deleteStudentFamilyByCondition(
            @ApiParam(value = "学生家庭信息对象")
            @RequestBody StudentFamily studentFamily){
        studentFamilyService.deleteStudentFamilyByCondition(studentFamily);
    }

    @PostMapping("/batchSaveStudentFamily")
    @ApiOperation(value = "批量保存学生家庭信息", notes = "返回学生家庭信息对象")
    public List<StudentFamily> saveStudentFamily(
            @ApiParam(value = "学生家庭信息对象", required = true)
            @RequestBody List<StudentFamily> studentFamilyList){
        studentFamilyService.batchSaveStudentFamily(studentFamilyList);
        return studentFamilyList;
    }

}
