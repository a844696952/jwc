package com.yice.edu.cn.jw.controller.teacher;

import com.yice.edu.cn.common.pojo.jw.teacher.TeacherQuit;
import com.yice.edu.cn.jw.service.teacher.TeacherQuitService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teacherQuit")
@Api(value = "/teacherQuit",description = "离职教师信息模块")
public class TeacherQuitController {
    @Autowired
    private TeacherQuitService teacherQuitService;

    @GetMapping("/findTeacherQuitById/{id}")
    @ApiOperation(value = "通过id查找离职教师信息", notes = "返回离职教师信息对象")
    public TeacherQuit findTeacherQuitById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return teacherQuitService.findTeacherQuitById(id);
    }

    @PostMapping("/saveTeacherQuit")
    @ApiOperation(value = "保存离职教师信息", notes = "返回离职教师信息对象")
    public TeacherQuit saveTeacherQuit(
            @ApiParam(value = "离职教师信息对象", required = true)
            @RequestBody TeacherQuit teacherQuit){
        teacherQuitService.saveTeacherQuit(teacherQuit);
        return teacherQuit;
    }

    @PostMapping("/findTeacherQuitListByCondition")
    @ApiOperation(value = "根据条件查找离职教师信息列表", notes = "返回离职教师信息列表")
    public List<TeacherQuit> findTeacherQuitListByCondition(
            @ApiParam(value = "离职教师信息对象")
            @RequestBody TeacherQuit teacherQuit){
        return teacherQuitService.findTeacherQuitListByCondition(teacherQuit);
    }
    @PostMapping("/findTeacherQuitCountByCondition")
    @ApiOperation(value = "根据条件查找离职教师信息列表个数", notes = "返回离职教师信息总个数")
    public long findTeacherQuitCountByCondition(
            @ApiParam(value = "离职教师信息对象")
            @RequestBody TeacherQuit teacherQuit){
        return teacherQuitService.findTeacherQuitCountByCondition(teacherQuit);
    }

    @PostMapping("/updateTeacherQuit")
    @ApiOperation(value = "修改离职教师信息", notes = "离职教师信息对象必传")
    public void updateTeacherQuit(
            @ApiParam(value = "离职教师信息对象,对象属性不为空则修改", required = true)
            @RequestBody TeacherQuit teacherQuit){
        teacherQuitService.updateTeacherQuit(teacherQuit);
    }

    @GetMapping("/deleteTeacherQuit/{id}")
    @ApiOperation(value = "通过id删除离职教师信息")
    public void deleteTeacherQuit(
            @ApiParam(value = "离职教师信息对象", required = true)
            @PathVariable String id){
        teacherQuitService.deleteTeacherQuit(id);
    }
    @PostMapping("/deleteTeacherQuitByCondition")
    @ApiOperation(value = "根据条件删除离职教师信息")
    public void deleteTeacherQuitByCondition(
            @ApiParam(value = "离职教师信息对象")
            @RequestBody TeacherQuit teacherQuit){
        teacherQuitService.deleteTeacherQuitByCondition(teacherQuit);
    }
    @PostMapping("/findQuitTeachers4Yed")
    @ApiOperation(value = "根据条件查找离职教师信息列表", notes = "返回离职教师信息列表")
    public List<TeacherQuit> findQuitTeachers4Yed(
            @ApiParam(value = "离职教师信息对象")
            @RequestBody TeacherQuit teacherQuit){
        return teacherQuitService.findQuitTeachers4Yed(teacherQuit);
    }
    @PostMapping("/findQuitTeachersCount4Yed")
    @ApiOperation(value = "根据条件查找离职教师信息列表个数", notes = "返回离职教师信息总个数")
    public long findQuitTeachersCount4Yed(
            @ApiParam(value = "离职教师信息对象")
            @RequestBody TeacherQuit teacherQuit){
        return teacherQuitService.findQuitTeachersCount4Yed(teacherQuit);
    }
}
