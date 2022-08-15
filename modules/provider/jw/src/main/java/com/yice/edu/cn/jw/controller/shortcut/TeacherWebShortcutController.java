package com.yice.edu.cn.jw.controller.shortcut;

import com.yice.edu.cn.common.pojo.jw.shortcut.TeacherWebShortcut;
import com.yice.edu.cn.jw.service.shortcut.TeacherWebShortcutService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teacherWebShortcut")
@Api(value = "/teacherWebShortcut",description = "用户首页快捷应用模块")
public class TeacherWebShortcutController {
    @Autowired
    private TeacherWebShortcutService teacherWebShortcutService;

    @GetMapping("/findTeacherWebShortcutById/{id}")
    @ApiOperation(value = "通过id查找用户首页快捷应用", notes = "返回用户首页快捷应用对象")
    public TeacherWebShortcut findTeacherWebShortcutById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return teacherWebShortcutService.findTeacherWebShortcutById(id);
    }

    @PostMapping("/saveTeacherWebShortcut")
    @ApiOperation(value = "保存用户首页快捷应用", notes = "返回用户首页快捷应用对象")
    public TeacherWebShortcut saveTeacherWebShortcut(
            @ApiParam(value = "用户首页快捷应用对象", required = true)
            @RequestBody TeacherWebShortcut teacherWebShortcut){
        teacherWebShortcutService.saveTeacherWebShortcut(teacherWebShortcut);
        return teacherWebShortcut;
    }

    @PostMapping("/findTeacherWebShortcutListByCondition")
    @ApiOperation(value = "根据条件查找用户首页快捷应用列表", notes = "返回用户首页快捷应用列表")
    public List<TeacherWebShortcut> findTeacherWebShortcutListByCondition(
            @ApiParam(value = "用户首页快捷应用对象")
            @RequestBody TeacherWebShortcut teacherWebShortcut){
        return teacherWebShortcutService.findTeacherWebShortcutListByCondition(teacherWebShortcut);
    }
    @PostMapping("/findTeacherWebShortcutCountByCondition")
    @ApiOperation(value = "根据条件查找用户首页快捷应用列表个数", notes = "返回用户首页快捷应用总个数")
    public long findTeacherWebShortcutCountByCondition(
            @ApiParam(value = "用户首页快捷应用对象")
            @RequestBody TeacherWebShortcut teacherWebShortcut){
        return teacherWebShortcutService.findTeacherWebShortcutCountByCondition(teacherWebShortcut);
    }

    @PostMapping("/updateTeacherWebShortcut")
    @ApiOperation(value = "修改用户首页快捷应用所有非@Transient注释的字段", notes = "用户首页快捷应用对象必传")
    public void updateTeacherWebShortcut(
            @ApiParam(value = "用户首页快捷应用对象,对象属性不为空则修改", required = true)
            @RequestBody TeacherWebShortcut teacherWebShortcut){
        teacherWebShortcutService.updateTeacherWebShortcut(teacherWebShortcut);
    }
    @PostMapping("/updateTeacherWebShortcutForNotNull")
    @ApiOperation(value = "修改用户首页快捷应用有值的字段", notes = "用户首页快捷应用对象必传")
    public void updateTeacherWebShortcutForNotNull(
            @ApiParam(value = "用户首页快捷应用对象,对象属性不为空则修改", required = true)
            @RequestBody TeacherWebShortcut teacherWebShortcut){
        teacherWebShortcutService.updateTeacherWebShortcutForNotNull(teacherWebShortcut);
    }

    @GetMapping("/deleteTeacherWebShortcut/{id}")
    @ApiOperation(value = "通过id删除用户首页快捷应用")
    public void deleteTeacherWebShortcut(
            @ApiParam(value = "用户首页快捷应用对象", required = true)
            @PathVariable String id){
        teacherWebShortcutService.deleteTeacherWebShortcut(id);
    }
    @PostMapping("/deleteTeacherWebShortcutByCondition")
    @ApiOperation(value = "根据条件删除用户首页快捷应用")
    public void deleteTeacherWebShortcutByCondition(
            @ApiParam(value = "用户首页快捷应用对象")
            @RequestBody TeacherWebShortcut teacherWebShortcut){
        teacherWebShortcutService.deleteTeacherWebShortcutByCondition(teacherWebShortcut);
    }
    @PostMapping("/findOneTeacherWebShortcutByCondition")
    @ApiOperation(value = "根据条件查找单个用户首页快捷应用,结果必须为单条数据", notes = "返回单个用户首页快捷应用,没有时为空")
    public TeacherWebShortcut findOneTeacherWebShortcutByCondition(
            @ApiParam(value = "用户首页快捷应用对象")
            @RequestBody TeacherWebShortcut teacherWebShortcut){
        return teacherWebShortcutService.findOneTeacherWebShortcutByCondition(teacherWebShortcut);
    }


    @PostMapping("/updateTeacherWebShortcut4List/{teacherId}")
    @ApiOperation(value = "批量修改教师快捷方式")
    public void updateTeacherWebShortcut4List(
            @ApiParam(value = "用户首页快捷应用对象")
            @PathVariable String teacherId,
            @RequestBody List<TeacherWebShortcut> teacherWebShortcutList){
        teacherWebShortcutService.updateTeacherWebShortcut4List(teacherId,teacherWebShortcutList);
    }
}
