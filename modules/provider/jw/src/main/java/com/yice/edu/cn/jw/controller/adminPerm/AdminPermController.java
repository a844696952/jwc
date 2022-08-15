package com.yice.edu.cn.jw.controller.adminPerm;

import com.yice.edu.cn.common.pojo.jw.adminPerm.AdminPerm;
import com.yice.edu.cn.jw.service.adminPerm.AdminPermService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/adminPerm")
@Api(value = "/adminPerm",description = "校管理员权限模块")
public class AdminPermController {
    @Autowired
    private AdminPermService adminPermService;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findAdminPermById/{id}")
    @ApiOperation(value = "通过id查找校管理员权限", notes = "返回校管理员权限对象")
    public AdminPerm findAdminPermById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return adminPermService.findAdminPermById(id);
    }

    @PostMapping("/saveAdminPerm")
    @ApiOperation(value = "保存校管理员权限", notes = "返回校管理员权限对象")
    public AdminPerm saveAdminPerm(
            @ApiParam(value = "校管理员权限对象", required = true)
            @RequestBody AdminPerm adminPerm){
        adminPermService.saveAdminPerm(adminPerm);
        return adminPerm;
    }

    @PostMapping("/batchSaveAdminPerm")
    @ApiOperation(value = "批量保存校管理员权限")
    public void batchSaveAdminPerm(
            @ApiParam(value = "校管理员权限对象集合", required = true)
            @RequestBody List<AdminPerm> adminPerms){
        adminPermService.batchSaveAdminPerm(adminPerms);
    }

    @PostMapping("/findAdminPermListByCondition")
    @ApiOperation(value = "根据条件查找校管理员权限列表", notes = "返回校管理员权限列表")
    public List<AdminPerm> findAdminPermListByCondition(
            @ApiParam(value = "校管理员权限对象")
            @RequestBody AdminPerm adminPerm){
        return adminPermService.findAdminPermListByCondition(adminPerm);
    }
    @PostMapping("/findAdminPermCountByCondition")
    @ApiOperation(value = "根据条件查找校管理员权限列表个数", notes = "返回校管理员权限总个数")
    public long findAdminPermCountByCondition(
            @ApiParam(value = "校管理员权限对象")
            @RequestBody AdminPerm adminPerm){
        return adminPermService.findAdminPermCountByCondition(adminPerm);
    }

    @PostMapping("/updateAdminPerm")
    @ApiOperation(value = "修改校管理员权限有值的字段", notes = "校管理员权限对象必传")
    public void updateAdminPerm(
            @ApiParam(value = "校管理员权限对象,对象属性不为空则修改", required = true)
            @RequestBody AdminPerm adminPerm){
        adminPermService.updateAdminPerm(adminPerm);
    }
    @PostMapping("/updateAdminPermForAll")
    @ApiOperation(value = "修改校管理员权限所有字段", notes = "校管理员权限对象必传")
    public void updateAdminPermForAll(
            @ApiParam(value = "校管理员权限对象", required = true)
            @RequestBody AdminPerm adminPerm){
        adminPermService.updateAdminPermForAll(adminPerm);
    }

    @GetMapping("/deleteAdminPerm/{id}")
    @ApiOperation(value = "通过id删除校管理员权限")
    public void deleteAdminPerm(
            @ApiParam(value = "校管理员权限对象", required = true)
            @PathVariable String id){
        adminPermService.deleteAdminPerm(id);
    }
    @PostMapping("/deleteAdminPermByCondition")
    @ApiOperation(value = "根据条件删除校管理员权限")
    public void deleteAdminPermByCondition(
            @ApiParam(value = "校管理员权限对象")
            @RequestBody AdminPerm adminPerm){
        adminPermService.deleteAdminPermByCondition(adminPerm);
    }
    @PostMapping("/findOneAdminPermByCondition")
    @ApiOperation(value = "根据条件查找单个校管理员权限,结果必须为单条数据", notes = "返回单个校管理员权限,没有时为空")
    public AdminPerm findOneAdminPermByCondition(
            @ApiParam(value = "校管理员权限对象")
            @RequestBody AdminPerm adminPerm){
        return adminPermService.findOneAdminPermByCondition(adminPerm);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

    @GetMapping("/deleteAdminPermRecursive/{id}")
    public void deleteAdminPermRecursive(@PathVariable String id){
        adminPermService.deleteAdminPermRecursive(id);
    }
    @PostMapping("/batchUpdateSortNum")
    public void batchUpdateSortNum(@RequestBody List<AdminPerm> adminPerms){
        adminPermService.batchUpdateSortNum(adminPerms);
    }
}
