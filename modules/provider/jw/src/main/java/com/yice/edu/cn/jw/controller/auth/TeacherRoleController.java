package com.yice.edu.cn.jw.controller.auth;

import com.yice.edu.cn.common.pojo.jw.auth.TeacherRole;
import com.yice.edu.cn.jw.service.auth.TeacherRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teacherRole")
@Api(value = "/teacherRole",description = "教师角色关联模块")
public class TeacherRoleController {
    @Autowired
    private TeacherRoleService teacherRoleService;

    @GetMapping("/findTeacherRoleById/{id}")
    @ApiOperation(value = "通过id查找教师角色关联", notes = "返回教师角色关联对象")
    public TeacherRole findTeacherRoleById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return teacherRoleService.findTeacherRoleById(id);
    }

    @PostMapping("/saveTeacherRole")
    @ApiOperation(value = "保存教师角色关联", notes = "返回教师角色关联对象")
    public TeacherRole saveTeacherRole(
            @ApiParam(value = "教师角色关联对象", required = true)
            @RequestBody TeacherRole teacherRole){
        teacherRoleService.saveTeacherRole(teacherRole);
        return teacherRole;
    }

    @PostMapping("/findTeacherRoleListByCondition")
    @ApiOperation(value = "根据条件查找教师角色关联列表", notes = "返回教师角色关联列表")
    public List<TeacherRole> findTeacherRoleListByCondition(
            @ApiParam(value = "教师角色关联对象")
            @RequestBody TeacherRole teacherRole){
        return teacherRoleService.findTeacherRoleListByCondition(teacherRole);
    }
    @PostMapping("/findTeacherRoleCountByCondition")
    @ApiOperation(value = "根据条件查找教师角色关联列表个数", notes = "返回教师角色关联总个数")
    public long findTeacherRoleCountByCondition(
            @ApiParam(value = "教师角色关联对象")
            @RequestBody TeacherRole teacherRole){
        return teacherRoleService.findTeacherRoleCountByCondition(teacherRole);
    }

    @PostMapping("/updateTeacherRole")
    @ApiOperation(value = "修改教师角色关联", notes = "教师角色关联对象必传")
    public void updateTeacherRole(
            @ApiParam(value = "教师角色关联对象,对象属性不为空则修改", required = true)
            @RequestBody TeacherRole teacherRole){
        teacherRoleService.updateTeacherRole(teacherRole);
    }

    @GetMapping("/deleteTeacherRole/{id}")
    @ApiOperation(value = "通过id删除教师角色关联")
    public void deleteTeacherRole(
            @ApiParam(value = "教师角色关联对象", required = true)
            @PathVariable String id){
        teacherRoleService.deleteTeacherRole(id);
    }
    @PostMapping("/deleteTeacherRoleByCondition")
    @ApiOperation(value = "根据条件删除教师角色关联")
    public void deleteTeacherRoleByCondition(
            @ApiParam(value = "教师角色关联对象")
            @RequestBody TeacherRole teacherRole){
        teacherRoleService.deleteTeacherRoleByCondition(teacherRole);
    }

    @PostMapping("/batchDelsertTeacherRoles")
    public void batchDelsertTeacherRoles(@RequestBody TeacherRole teacherRole){
        teacherRoleService.batchDelsertTeacherRoles(teacherRole);
    }
}
