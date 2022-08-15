package com.yice.edu.cn.yed.controller.jw.teacher;

import cn.hutool.crypto.digest.DigestUtil;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.school.School;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherQuit;
import com.yice.edu.cn.yed.service.baseData.school.SchoolService;
import com.yice.edu.cn.yed.service.jw.teacher.TeacherQuitService;
import com.yice.edu.cn.yed.service.jw.teacher.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teacher")
@Api(value = "/teacher",description = "教师信息 模块")
public class TeacherController {
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private SchoolService schoolService;
    @Autowired
    private TeacherQuitService teacherQuitService;

    @GetMapping("/findTeacherById/{id}")
    @ApiOperation(value = "通过id查找教师信息 ", notes = "返回响应对象")
    public ResponseJson findTeacherById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        Teacher teacher=teacherService.findTeacherById(id);
        return new ResponseJson(teacher);
    }

    @PostMapping("/saveTeacher")
    @ApiOperation(value = "保存教师信息 对象", notes = "返回响应对象")
    public ResponseJson saveTeacher(
            @ApiParam(value = "教师信息 对象", required = true)
            @RequestBody Teacher teacher){
        teacher.setPassword(DigestUtil.sha1Hex(DigestUtil.md5Hex(Constant.DEFAULT_PWD)));
        Teacher s=teacherService.saveTeacher(teacher);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findTeacherById/{id}")
    @ApiOperation(value = "通过id查找教师信息 ", notes = "返回响应对象")
    public ResponseJson findTeacherById4Update(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id) {
        Teacher teacher = teacherService.findTeacherById(id);
        return new ResponseJson(teacher);
    }
    @PostMapping("/update/updateTeacher")
    @ApiOperation(value = "修改教师信息 对象", notes = "返回响应对象")
    public ResponseJson updateTeacher(
            @ApiParam(value = "被修改的教师信息 对象,对象属性不为空则修改", required = true)
            @RequestBody Teacher teacher) {
        return new ResponseJson(teacherService.updateTeacher(teacher));
    }

    @PostMapping("/findTeachersByCondition")
    @ApiOperation(value = "根据条件查找教师信息 ", notes = "返回响应对象")
    public ResponseJson findTeachersByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody Teacher teacher) {
        teacher.setPersonType(Constant.PERSON_TYPE.TEACHER);
        List<Teacher> data = teacherService.findTeacherListByCondition(teacher);
        long count = teacherService.findTeacherCountByCondition(teacher);
        return new ResponseJson(data, count);
    }

    @GetMapping("/deleteTeacher/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteTeacher(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id) {
        teacherService.deleteTeacher(id);
        return new ResponseJson();
    }
    @PostMapping("/findSchoolsBySchoolName")
    @ApiOperation(value = "根据学校名称查找学校列表", notes = "返回响应对象")
    public ResponseJson findSchoolsBySchoolName(@RequestBody School school){
        Pager pager = new Pager();
        school.setPager(pager);
        return new ResponseJson(schoolService.findSchoolListByCondition(school));
    }
    @PostMapping("/saveTeacherAdmin")
    @ApiOperation(value = "保存教师信息 对象", notes = "返回响应对象")
    public ResponseJson saveTeacherAdmin(
            @ApiParam(value = "教师信息 对象", required = true)
            @RequestBody Teacher teacher){
        teacher.setPassword(DigestUtil.sha1Hex(DigestUtil.md5Hex(Constant.DEFAULT_PWD)));
        Teacher s=teacherService.saveTeacher(teacher);
        return new ResponseJson(s);
    }
    @PostMapping("/updateTeacherAdmin")
    @ApiOperation(value = "修改教师信息 对象", notes = "返回响应对象")
    public ResponseJson updateTeacherAdmin(
            @ApiParam(value = "教师信息 对象", required = true)
            @RequestBody Teacher teacher){
        teacherService.updateTeacherAdmin(teacher);
        return new ResponseJson();
    }
    @PostMapping("/findTeacherAdminsByCondition")
    @ApiOperation(value = "根据条件查找教师信息 ", notes = "返回响应对象")
    public ResponseJson findTeacherAdminsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody Teacher teacher) {
        teacher.setStatus(Constant.STATUS.TEACHER_ADMIN);
        List<Teacher> data = teacherService.findTeacherListByCondition(teacher);
        long count = teacherService.findTeacherCountByCondition(teacher);
        return new ResponseJson(data, count);
    }
    @GetMapping("/findTeacherAdminById/{id}")
    @ApiOperation(value = "通过id查找教师信息 ", notes = "返回响应对象")
    public ResponseJson findTeacherAdminById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        Teacher teacher=teacherService.findTeacherById(id);
        return new ResponseJson(teacher);
    }
    @GetMapping("/deleteTeacherAdmin/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteTeacherAdmin(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id) {
        teacherService.deleteTeacher(id);
        return new ResponseJson();
    }

    @PostMapping("/findTeachers4Yed")
    @ApiOperation(value = "根据条件查找教师信息 ", notes = "返回响应对象")
    public ResponseJson findTeachers4Yed(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody Teacher teacher) {
        teacher.setStatus(Constant.STATUS.TEACHER_ON_LINE);
        List<Teacher> data = teacherService.findTeachers4Yed(teacher);
        long count = teacherService.findTeachersCount4Yed(teacher);
        return new ResponseJson(data, count);
    }
    @GetMapping("/look/lookTeacherById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找教师信息", notes = "返回响应对象")
    public ResponseJson lookTeacherById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id) {
        Teacher teacher = teacherService.findTeacherById(id);
        return new ResponseJson(teacher);
    }
    @GetMapping("/ignore/quit/findTeacherQuitById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找教师信息", notes = "返回响应对象")
    public ResponseJson findTeacherQuitById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id) {
        return new ResponseJson(teacherQuitService.findTeacherQuitById(id));
    }
    @PostMapping("/ignore/quit/findQuitTeachers4Yed")
    @ApiOperation(value = "根据条件查找离职教师信息", notes = "返回响应对象")
    public ResponseJson findQuitTeachers4Yed(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody TeacherQuit teacherQuit) {
        teacherQuit.setStatus(Constant.STATUS.TEACHER_QUIT_LINE);
        List<TeacherQuit> data = teacherQuitService.findQuitTeachers4Yed(teacherQuit);
        long count = teacherQuitService.findQuitTeachersCount4Yed(teacherQuit);
        return new ResponseJson(data, count);
    }
}
