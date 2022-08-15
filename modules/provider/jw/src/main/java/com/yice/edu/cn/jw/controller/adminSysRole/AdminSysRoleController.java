package com.yice.edu.cn.jw.controller.adminSysRole;

import com.yice.edu.cn.common.pojo.yedAdmin.AdminSysRole;
import com.yice.edu.cn.jw.service.adminSysRole.AdminSysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/adminSysRole")
@Api(value = "/adminSysRole",description = "模块")
public class AdminSysRoleController {
    @Autowired
    private AdminSysRoleService adminSysRoleService;

    @GetMapping("/findAdminSysRoleById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public AdminSysRole findAdminSysRoleById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return adminSysRoleService.findAdminSysRoleById(id);
    }

    @PostMapping("/saveAdminSysRole")
    @ApiOperation(value = "保存", notes = "返回对象")
    public AdminSysRole saveAdminSysRole(
            @ApiParam(value = "对象", required = true)
            @RequestBody AdminSysRole adminSysRole){
        adminSysRoleService.saveAdminSysRole(adminSysRole);
        return adminSysRole;
    }

    @PostMapping("/findAdminSysRoleListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<AdminSysRole> findAdminSysRoleListByCondition(
            @ApiParam(value = "对象")
            @RequestBody AdminSysRole adminSysRole){
        return adminSysRoleService.findAdminSysRoleListByCondition(adminSysRole);
    }
    @PostMapping("/findAdminSysRoleCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findAdminSysRoleCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody AdminSysRole adminSysRole){
        return adminSysRoleService.findAdminSysRoleCountByCondition(adminSysRole);
    }

    @PostMapping("/updateAdminSysRole")
    @ApiOperation(value = "修改", notes = "对象必传")
    public void updateAdminSysRole(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody AdminSysRole adminSysRole){
        adminSysRoleService.updateAdminSysRole(adminSysRole);
    }

    @GetMapping("/deleteAdminSysRole/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteAdminSysRole(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        adminSysRoleService.deleteAdminSysRole(id);
    }

    @PostMapping("/batchSaveAdminSysRole")
    @ApiOperation(value = "批量增加用户角色")
    public void batchSaveAdminSysRole(
            @ApiParam(value = "用户角色集合", required = true)
            List<AdminSysRole> adminSysRoles){
        adminSysRoleService.batchSaveAdminSysRole(adminSysRoles);
    }
    @PostMapping("/delsertAdminSysRoles")
    @ApiOperation(value = "删除后批量增加用户角色")
    public void delsertAdminSysRoles(
            @ApiParam(value = "用户角色集合", required = true)
            @RequestBody Map<String,String> map){
        adminSysRoleService.delsertAdminSysRoles(map);
    }
}
