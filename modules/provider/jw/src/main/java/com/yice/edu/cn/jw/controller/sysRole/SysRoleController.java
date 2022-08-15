package com.yice.edu.cn.jw.controller.sysRole;

import com.yice.edu.cn.common.pojo.yedAdmin.SysRole;
import com.yice.edu.cn.jw.service.sysRole.SysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sysRole")
@Api(value = "/sysRole",description = "系统角色模块")
public class SysRoleController {
    @Autowired
    private SysRoleService sysRoleService;

    @GetMapping("/findSysRoleById/{id}")
    @ApiOperation(value = "通过id查找系统角色", notes = "返回系统角色对象")
    public SysRole findSysRoleById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return sysRoleService.findSysRoleById(id);
    }

    @PostMapping("/saveSysRole")
    @ApiOperation(value = "保存系统角色", notes = "返回系统角色对象")
    public SysRole saveSysRole(
            @ApiParam(value = "系统角色对象", required = true)
            @RequestBody SysRole sysRole){
        sysRoleService.saveSysRole(sysRole);
        return sysRole;
    }

    @PostMapping("/findSysRoleListByCondition")
    @ApiOperation(value = "根据条件查找系统角色列表", notes = "返回系统角色列表")
    public List<SysRole> findSysRoleListByCondition(
            @ApiParam(value = "系统角色对象")
            @RequestBody SysRole sysRole){
        return sysRoleService.findSysRoleListByCondition(sysRole);
    }
    @PostMapping("/findSysRoleCountByCondition")
    @ApiOperation(value = "根据条件查找系统角色列表个数", notes = "返回系统角色总个数")
    public long findSysRoleCountByCondition(
            @ApiParam(value = "系统角色对象")
            @RequestBody SysRole sysRole){
        return sysRoleService.findSysRoleCountByCondition(sysRole);
    }

    @PostMapping("/updateSysRole")
    @ApiOperation(value = "修改系统角色", notes = "系统角色对象必传")
    public void updateSysRole(
            @ApiParam(value = "系统角色对象,对象属性不为空则修改", required = true)
            @RequestBody SysRole sysRole){
        sysRoleService.updateSysRole(sysRole);
    }

    @GetMapping("/deleteSysRole/{id}")
    @ApiOperation(value = "通过id删除系统角色")
    public void deleteSysRole(
            @ApiParam(value = "系统角色对象", required = true)
            @PathVariable String id){
        sysRoleService.deleteSysRole(id);
    }

    @PostMapping("/delsertRolePerms")
    public void delsertRolePerms(@RequestBody Map<String, String> map){
        sysRoleService.delsertRolePerms(map);
    }


}
