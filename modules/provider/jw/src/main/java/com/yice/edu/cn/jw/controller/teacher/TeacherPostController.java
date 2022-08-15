package com.yice.edu.cn.jw.controller.teacher;

import com.yice.edu.cn.common.pojo.general.dd.Dd;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherPost;
import com.yice.edu.cn.jw.service.teacher.TeacherPostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/teacherPost")
@Api(value = "/teacherPost",description = "教师职务模块")
public class TeacherPostController {
    @Autowired
    private TeacherPostService teacherPostService;

    @GetMapping("/findTeacherPostById/{id}")
    @ApiOperation(value = "通过id查找教师职务", notes = "返回教师职务对象")
    public TeacherPost findTeacherPostById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return teacherPostService.findTeacherPostById(id);
    }

    @PostMapping("/saveTeacherPost")
    @ApiOperation(value = "保存教师职务", notes = "返回教师职务对象")
    public TeacherPost saveTeacherPost(
            @ApiParam(value = "教师职务对象", required = true)
            @RequestBody TeacherPost teacherPost){
        teacherPostService.saveTeacherPost(teacherPost);
        return teacherPost;
    }

    @PostMapping("/findTeacherPostListByCondition")
    @ApiOperation(value = "根据条件查找教师职务列表", notes = "返回教师职务列表")
    public List<TeacherPost> findTeacherPostListByCondition(
            @ApiParam(value = "教师职务对象")
            @RequestBody TeacherPost teacherPost){
        return teacherPostService.findTeacherPostListByCondition(teacherPost);
    }

    @PostMapping("/findTeachersByPost")
    @ApiOperation(value = "根据条件查找教师职务列表", notes = "返回教师职务列表")
    public List<Teacher> findTeachersByPost(
            @ApiParam(value = "教师职务对象")
            @RequestBody TeacherPost teacherPost){
        return teacherPostService.findTeachersByPost(teacherPost);
    }
    @PostMapping("/findTeacherListByPost")
    @ApiOperation(value = "根据条件查找教师职务列表", notes = "返回教师职务列表")
    public List<TeacherPost> findTeacherListByPost(
            @ApiParam(value = "教师职务对象")
            @RequestBody TeacherPost teacherPost){
        return teacherPostService.findTeacherListByPost(teacherPost);
    }

    @PostMapping("/findTeacherPostCountByCondition")
    @ApiOperation(value = "根据条件查找教师职务列表个数", notes = "返回教师职务总个数")
    public long findTeacherPostCountByCondition(
            @ApiParam(value = "教师职务对象")
            @RequestBody TeacherPost teacherPost){
        return teacherPostService.findTeacherPostCountByCondition(teacherPost);
    }

    @PostMapping("/updateTeacherPost")
    @ApiOperation(value = "修改教师职务", notes = "教师职务对象必传")
    public void updateTeacherPost(
            @ApiParam(value = "教师职务对象,对象属性不为空则修改", required = true)
            @RequestBody TeacherPost teacherPost){
        teacherPostService.updateTeacherPost(teacherPost);
    }

    @GetMapping("/deleteTeacherPost/{id}")
    @ApiOperation(value = "通过id删除教师职务")
    public void deleteTeacherPost(
            @ApiParam(value = "教师职务对象", required = true)
            @PathVariable String id){
        teacherPostService.deleteTeacherPost(id);
    }
    @PostMapping("/deleteTeacherPostByCondition")
    @ApiOperation(value = "根据条件删除教师职务")
    public void deleteTeacherPostByCondition(
            @ApiParam(value = "教师职务对象")
            @RequestBody TeacherPost teacherPost){
        teacherPostService.deleteTeacherPostByCondition(teacherPost);
    }
    @PostMapping("/editTeacherPost")
    @ApiOperation(value = "编辑教师职务")
    public int editTeacherPost(@ApiParam(value = "教师职务列表", required = true)@RequestBody TeacherPost teacherPost){
        return teacherPostService.editTeacherPost(teacherPost);
    }
    @PostMapping("/findOneTeacherPostByCondition")
    @ApiOperation(value = "根据条件查找单个教师职务,结果必须为单条数据", notes = "返回单个教师职务,没有时为空")
    public TeacherPost findOneTeacherPostByCondition(
            @ApiParam(value = "教师职务对象")
            @RequestBody TeacherPost teacherPost){
        return teacherPostService.findOneTeacherPostByCondition(teacherPost);
    }
    @PostMapping("/findGradeTeacherBySchool")
    public List<TeacherPost> findGradeTeacherBySchool(
            @RequestParam("schoolId") String school,@RequestBody List<Dd> gradeList){
        return teacherPostService.findGradeTeacherBySchool(school,gradeList);
    }
    @GetMapping("/findTeachers4Grade/{schoolId}")
    public List<TeacherPost> findTeachers4Grade(
            @PathVariable("schoolId") String schoolId){
        return teacherPostService.findTeachers4Grade(schoolId);
    }
    @PostMapping("/findTeachers4Class")
    public List<TeacherPost> findTeachers4Class(
            @RequestBody TeacherPost teacherPost){
        return teacherPostService.findTeachers4Class(teacherPost);
    }
}
