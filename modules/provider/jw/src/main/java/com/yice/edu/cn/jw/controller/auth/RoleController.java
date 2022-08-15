package com.yice.edu.cn.jw.controller.auth;

import com.yice.edu.cn.common.pojo.jw.auth.Perm;
import com.yice.edu.cn.common.pojo.jw.auth.Role;
import com.yice.edu.cn.common.pojo.jw.auth.RolePerm;
import com.yice.edu.cn.jw.service.auth.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role")
@Api(value = "/role",description = "学校角色模块")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping("/findRoleById/{id}")
    @ApiOperation(value = "通过id查找学校角色", notes = "返回学校角色对象")
    public Role findRoleById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return roleService.findRoleById(id);
    }

    @PostMapping("/saveRole")
    @ApiOperation(value = "保存学校角色", notes = "返回学校角色对象")
    public Role saveRole(
            @ApiParam(value = "学校角色对象", required = true)
            @RequestBody Role role){
        roleService.saveRole(role);
        return role;
    }

    @PostMapping("/findRoleListByCondition")
    @ApiOperation(value = "根据条件查找学校角色列表", notes = "返回学校角色列表")
    public List<Role> findRoleListByCondition(
            @ApiParam(value = "学校角色对象")
            @RequestBody Role role){
        return roleService.findRoleListByCondition(role);
    }
    @PostMapping("/findRoleCountByCondition")
    @ApiOperation(value = "根据条件查找学校角色列表个数", notes = "返回学校角色总个数")
    public long findRoleCountByCondition(
            @ApiParam(value = "学校角色对象")
            @RequestBody Role role){
        return roleService.findRoleCountByCondition(role);
    }

    @PostMapping("/updateRole")
    @ApiOperation(value = "修改学校角色", notes = "学校角色对象必传")
    public void updateRole(
            @ApiParam(value = "学校角色对象,对象属性不为空则修改", required = true)
            @RequestBody Role role){
        roleService.updateRole(role);
    }

    @GetMapping("/deleteRole/{id}")
    @ApiOperation(value = "通过id删除学校角色")
    public void deleteRole(
            @ApiParam(value = "学校角色对象", required = true)
            @PathVariable String id){
        roleService.deleteRole(id);
    }
    @PostMapping("/deleteRoleByCondition")
    @ApiOperation(value = "根据条件删除学校角色")
    public void deleteRoleByCondition(
            @ApiParam(value = "学校角色对象")
            @RequestBody Role role){
        roleService.deleteRoleByCondition(role);
    }

    @GetMapping("/findAllPermTreeBySchoolId/{id}")
    @ApiOperation(value = "根据学校id获取学校的所有权限树")
    public List<Perm> findAllPermTreeBySchoolId(@PathVariable("id") String id){
        return roleService.findAllPermTreeBySchoolId(id);
    }
    @GetMapping("/findCheckedPermIdsByRoleId/{id}")
    @ApiOperation(value = "根据角色id获取拥有的权限id列表")
    public List<String> findCheckedPermIdsByRoleId(@PathVariable("id") String id){
        return roleService.findCheckedPermIdsByRoleId(id);
    }

    @PostMapping("/delsertRolePerms")
    public void delsertRolePerms(@RequestBody RolePerm rolePerm){
        roleService.delsertRolePerms(rolePerm);
    }

    @GetMapping("/deleteRoleRelated/{id}")
    public void deleteRoleRelated(@PathVariable("id") String id){
        roleService.deleteRoleRelated(id);
    }
}
