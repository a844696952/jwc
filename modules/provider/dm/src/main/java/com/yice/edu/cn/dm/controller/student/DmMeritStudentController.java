package com.yice.edu.cn.dm.controller.student;

import com.yice.edu.cn.common.pojo.dm.student.AllStudent;
import com.yice.edu.cn.common.pojo.dm.student.DmMeritStudent;
import com.yice.edu.cn.dm.service.student.DmMeritStudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dmMeritStudent")
@Api(value = "/dmMeritStudent",description = "三好学生表模块")
public class DmMeritStudentController {
    @Autowired
    private DmMeritStudentService dmMeritStudentService;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findDmMeritStudentById/{id}")
    @ApiOperation(value = "通过id查找三好学生表", notes = "返回三好学生表对象")
    public DmMeritStudent findDmMeritStudentById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return dmMeritStudentService.findDmMeritStudentById(id);
    }

    @PostMapping("/saveDmMeritStudentOrUpdate")
    @ApiOperation(value = "保存三好学生表", notes = "返回三好学生表对象")
    public void saveDmMeritStudentOrUpdate(
            @ApiParam(value = "三好学生表对象", required = true)
            @RequestBody List<DmMeritStudent> dmMeritStudent){
        dmMeritStudentService.saveDmMeritStudentOrUpdate(dmMeritStudent);
    }

    @PostMapping("/batchSaveDmMeritStudent")
    @ApiOperation(value = "批量保存三好学生表")
    public void batchSaveDmMeritStudent(
            @ApiParam(value = "三好学生表对象集合", required = true)
            @RequestBody List<DmMeritStudent> dmMeritStudents){
        dmMeritStudentService.batchSaveDmMeritStudent(dmMeritStudents);
    }

    @PostMapping("/findDmMeritStudentListByCondition")
    @ApiOperation(value = "根据条件查找三好学生表列表", notes = "返回三好学生表列表")
    public List<DmMeritStudent> findDmMeritStudentListByCondition(
            @ApiParam(value = "三好学生表对象")
            @RequestBody DmMeritStudent dmMeritStudent){
        return dmMeritStudentService.findDmMeritStudentListByCondition(dmMeritStudent);
    }
    @PostMapping("/findDmMeritStudentCountByCondition")
    @ApiOperation(value = "根据条件查找三好学生表列表个数", notes = "返回三好学生表总个数")
    public long findDmMeritStudentCountByCondition(
            @ApiParam(value = "三好学生表对象")
            @RequestBody DmMeritStudent dmMeritStudent){
        return dmMeritStudentService.findDmMeritStudentCountByCondition(dmMeritStudent);
    }

    @PostMapping("/updateDmMeritStudent")
    @ApiOperation(value = "修改三好学生表有值的字段", notes = "三好学生表对象必传")
    public void updateDmMeritStudent(
            @ApiParam(value = "三好学生表对象,对象属性不为空则修改", required = true)
            @RequestBody DmMeritStudent dmMeritStudent){
        dmMeritStudentService.updateDmMeritStudent(dmMeritStudent);
    }
    @PostMapping("/updateDmMeritStudentForAll")
    @ApiOperation(value = "修改三好学生表所有字段", notes = "三好学生表对象必传")
    public void updateDmMeritStudentForAll(
            @ApiParam(value = "三好学生表对象", required = true)
            @RequestBody DmMeritStudent dmMeritStudent){
        dmMeritStudentService.updateDmMeritStudentForAll(dmMeritStudent);
    }

    @GetMapping("/deleteDmMeritStudent/{id}")
    @ApiOperation(value = "通过id删除三好学生表")
    public void deleteDmMeritStudent(
            @ApiParam(value = "三好学生表对象", required = true)
            @PathVariable String id){
        dmMeritStudentService.deleteDmMeritStudent(id);
    }
    @PostMapping("/deleteDmMeritStudentByCondition")
    @ApiOperation(value = "根据条件删除三好学生表")
    public void deleteDmMeritStudentByCondition(
            @ApiParam(value = "三好学生表对象")
            @RequestBody DmMeritStudent dmMeritStudent){
        dmMeritStudentService.deleteDmMeritStudentByCondition(dmMeritStudent);
    }
    @PostMapping("/findOneDmMeritStudentByCondition")
    @ApiOperation(value = "根据条件查找单个三好学生表,结果必须为单条数据", notes = "返回单个三好学生表,没有时为空")
    public DmMeritStudent findOneDmMeritStudentByCondition(
            @ApiParam(value = "三好学生表对象")
            @RequestBody DmMeritStudent dmMeritStudent){
        return dmMeritStudentService.findOneDmMeritStudentByCondition(dmMeritStudent);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

    @GetMapping("/findAllJwClassesAndStudents/{schoolId}")
    @ApiOperation(value = "学生班级和树")
    public List<AllStudent> findAllJwClassesAndStudents(
            @ApiParam(value = "学校id")
            @PathVariable("schoolId") String schoolId) {
        return dmMeritStudentService.findAllJwClassesAndStudents(schoolId);
    }


    @GetMapping("/findAll/{schoolId}")
    @ApiOperation(value = "查询数据库所有的数据")
    public List<DmMeritStudent> findAll(
            @ApiParam(value = "学校id")
            @PathVariable("schoolId") String schoolId) {
        return dmMeritStudentService.findAll(schoolId);
    }

}
