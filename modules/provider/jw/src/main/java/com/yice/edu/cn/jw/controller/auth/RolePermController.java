package com.yice.edu.cn.jw.controller.auth;

import com.yice.edu.cn.common.pojo.jw.auth.RolePerm;
import com.yice.edu.cn.jw.service.auth.RolePermService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rolePerm")
@Api(value = "/rolePerm",description = "教师角色权限模块")
public class RolePermController {
    @Autowired
    private RolePermService rolePermService;

    @GetMapping("/findRolePermById/{id}")
    @ApiOperation(value = "通过id查找教师角色权限", notes = "返回教师角色权限对象")
    public RolePerm findRolePermById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return rolePermService.findRolePermById(id);
    }

    @PostMapping("/saveRolePerm")
    @ApiOperation(value = "保存教师角色权限", notes = "返回教师角色权限对象")
    public RolePerm saveRolePerm(
            @ApiParam(value = "教师角色权限对象", required = true)
            @RequestBody RolePerm rolePerm){
        rolePermService.saveRolePerm(rolePerm);
        return rolePerm;
    }

    @PostMapping("/findRolePermListByCondition")
    @ApiOperation(value = "根据条件查找教师角色权限列表", notes = "返回教师角色权限列表")
    public List<RolePerm> findRolePermListByCondition(
            @ApiParam(value = "教师角色权限对象")
            @RequestBody RolePerm rolePerm){
        return rolePermService.findRolePermListByCondition(rolePerm);
    }
    @PostMapping("/findRolePermCountByCondition")
    @ApiOperation(value = "根据条件查找教师角色权限列表个数", notes = "返回教师角色权限总个数")
    public long findRolePermCountByCondition(
            @ApiParam(value = "教师角色权限对象")
            @RequestBody RolePerm rolePerm){
        return rolePermService.findRolePermCountByCondition(rolePerm);
    }

    @PostMapping("/updateRolePerm")
    @ApiOperation(value = "修改教师角色权限", notes = "教师角色权限对象必传")
    public void updateRolePerm(
            @ApiParam(value = "教师角色权限对象,对象属性不为空则修改", required = true)
            @RequestBody RolePerm rolePerm){
        rolePermService.updateRolePerm(rolePerm);
    }

    @GetMapping("/deleteRolePerm/{id}")
    @ApiOperation(value = "通过id删除教师角色权限")
    public void deleteRolePerm(
            @ApiParam(value = "教师角色权限对象", required = true)
            @PathVariable String id){
        rolePermService.deleteRolePerm(id);
    }
    @PostMapping("/deleteRolePermByCondition")
    @ApiOperation(value = "根据条件删除教师角色权限")
    public void deleteRolePermByCondition(
            @ApiParam(value = "教师角色权限对象")
            @RequestBody RolePerm rolePerm){
        rolePermService.deleteRolePermByCondition(rolePerm);
    }
}
