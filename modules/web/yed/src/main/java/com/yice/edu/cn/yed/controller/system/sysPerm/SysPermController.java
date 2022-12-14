package com.yice.edu.cn.yed.controller.system.sysPerm;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.yedAdmin.SysPerm;
import com.yice.edu.cn.yed.service.system.sysPerm.SysPermService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.yice.edu.cn.yed.interceptor.LoginInterceptor.currentAdmin;

@RestController
@RequestMapping("/sysPerm")
@Api(value = "/sysPerm",description = "系统权限模块")
public class SysPermController {
    @Autowired
    private SysPermService sysPermService;
    @GetMapping("/findSysPermById/{id}")
    @ApiOperation(value = "通过id查找系统权限", notes = "返回响应对象")
    public ResponseJson findSysPermById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        SysPerm sysPerm=sysPermService.findSysPermById(id);
        return new ResponseJson(sysPerm);
    }

    @PostMapping("/saveSysPerm")
    @ApiOperation(value = "保存系统权限对象", notes = "返回响应对象")
    public ResponseJson saveSysPerm(
            @ApiParam(value = "系统权限对象", required = true)
            @RequestBody SysPerm sysPerm){
        SysPerm s=sysPermService.saveSysPerm(sysPerm);
        return new ResponseJson(s);
    }
    @PostMapping("/updateSysPerm")
    @ApiOperation(value = "修改系统权限对象", notes = "返回响应对象")
    public ResponseJson updateSysPerm(
            @ApiParam(value = "被修改的系统权限对象,对象属性不为空则修改", required = true)
            @RequestBody SysPerm sysPerm){
        sysPermService.updateSysPerm(sysPerm);
        return new ResponseJson();
    }

    @PostMapping("/findSysPermsByCondition")
    @ApiOperation(value = "根据条件查找系统权限", notes = "返回响应对象")
    public ResponseJson findSysPermsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody SysPerm sysPerm){
        List<SysPerm> data=sysPermService.findSysPermListByCondition(sysPerm);
        long count=sysPermService.findSysPermCountByCondition(sysPerm);
        return new ResponseJson(data,count);
    }
    @GetMapping("/deleteSysPerm/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteSysPerm(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        sysPermService.deleteSysPerm(id);
        return new ResponseJson();
    }
    @GetMapping("/deleteSysPermRecursive/{id}")
    @ApiOperation(value = "根据id删除,包含子节点", notes = "返回响应对象")
    public ResponseJson deleteSysPermRecursive(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        sysPermService.deleteSysPermRecursive(id);
        return new ResponseJson();
    }

    @GetMapping("/findAllTreeMenu")
    public ResponseJson findAllTreeMenu(){
        List<SysPerm> perms = sysPermService.findAllTreeMenu();
        return new ResponseJson(perms);
    }


    @GetMapping("/findAdminTreeMenu")
    public ResponseJson findAdminTreeMenu(){
        List<SysPerm> perms = sysPermService.findAdminTreeMenu(currentAdmin().getId());
        return new ResponseJson(perms);
    }

}
