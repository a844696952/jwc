package com.yice.edu.cn.jw.controller.sysPerm;

import com.yice.edu.cn.common.pojo.yedAdmin.SysPerm;
import com.yice.edu.cn.jw.service.sysPerm.SysPermService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sysPerm")
@Api(value = "/sysPerm",description = "系统权限模块")
public class SysPermController {
    @Autowired
    private SysPermService sysPermService;

    @GetMapping("/findSysPermById/{id}")
    @ApiOperation(value = "通过id查找系统权限", notes = "返回系统权限对象")
    public SysPerm findSysPermById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return sysPermService.findSysPermById(id);
    }

    @PostMapping("/saveSysPerm")
    @ApiOperation(value = "保存系统权限", notes = "返回系统权限对象")
    public SysPerm saveSysPerm(
            @ApiParam(value = "系统权限对象", required = true)
            @RequestBody SysPerm sysPerm){
        sysPermService.saveSysPerm(sysPerm);
        return sysPerm;
    }

    @PostMapping("/findSysPermListByCondition")
    @ApiOperation(value = "根据条件查找系统权限列表", notes = "返回系统权限列表")
    public List<SysPerm> findSysPermListByCondition(
            @ApiParam(value = "系统权限对象")
            @RequestBody SysPerm sysPerm){
        return sysPermService.findSysPermListByCondition(sysPerm);
    }
    @PostMapping("/findSysPermCountByCondition")
    @ApiOperation(value = "根据条件查找系统权限列表个数", notes = "返回系统权限总个数")
    public long findSysPermCountByCondition(
            @ApiParam(value = "系统权限对象")
            @RequestBody SysPerm sysPerm){
        return sysPermService.findSysPermCountByCondition(sysPerm);
    }

    @PostMapping("/updateSysPerm")
    @ApiOperation(value = "修改系统权限", notes = "系统权限对象必传")
    public void updateSysPerm(
            @ApiParam(value = "系统权限对象,对象属性不为空则修改", required = true)
            @RequestBody SysPerm sysPerm){
        sysPermService.updateSysPerm(sysPerm);
    }

    @GetMapping("/deleteSysPerm/{id}")
    @ApiOperation(value = "通过id删除系统权限")
    public void deleteSysPerm(
            @ApiParam(value = "系统权限对象", required = true)
            @PathVariable String id){
        sysPermService.deleteSysPerm(id);
    }
    @GetMapping("/deleteSysPermRecursive/{id}")
    @ApiOperation(value = "通过id删除系统权限,包含子节点")
    public void deleteSysPermRecursive(
            @ApiParam(value = "系统权限对象", required = true)
            @PathVariable String id){
        sysPermService.deleteSysPermRecursive(id);
    }

    @GetMapping("/findAllTreeMenu")
    public List<SysPerm> findAllTreeMenu(){
        List<SysPerm> perms=sysPermService.findAllTreeMenu();
        return perms;
    }

    @GetMapping("/findAdminTreeMenu/{adminId}")
    public List<SysPerm> findAdminTreeMenu(@PathVariable("adminId") String adminId){
        List<SysPerm> perms=sysPermService.findAdminTreeMenuV2(adminId);
        return perms;

    }
    @GetMapping("/findAllSysPermTree")
    @ApiOperation(value = "查找所有的权限,形成树")
    public List<SysPerm> findAllSysPermTree(){
        return sysPermService.findAllSysPermTree();
    }
    @GetMapping("/findSysPermChecked/{roleId}")
    @ApiOperation(value = "根据角色id获取被角色选中的权限列表")
    public List<String> findSysPermChecked(@PathVariable("roleId") String roleId){
        return sysPermService.findSysPermChecked(roleId);
    }
}
