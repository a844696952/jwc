package com.yice.edu.cn.yed.controller.jw.adminPerm;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.adminPerm.AdminPerm;
import com.yice.edu.cn.common.util.object.ObjectKit;
import com.yice.edu.cn.yed.service.jw.adminPerm.AdminPermService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@RestController
@RequestMapping("/adminPerm")
@Api(value = "/adminPerm",description = "校管理员权限模块")
public class AdminPermController {
    @Autowired
    private AdminPermService adminPermService;
    @PostMapping("/saveAdminPerm")
    @ApiOperation(value = "保存校管理员权限对象", notes = "返回保存好的校管理员权限对象", response= AdminPerm.class)
    public ResponseJson saveAdminPerm(
            @ApiParam(value = "校管理员权限对象", required = true)
            @RequestBody AdminPerm adminPerm){
        adminPermService.saveAdminPerm(adminPerm);
        return new ResponseJson();
    }

    @GetMapping("/findAdminPermById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找校管理员权限", notes = "返回响应对象", response=AdminPerm.class)
    public ResponseJson findAdminPermById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        AdminPerm adminPerm=adminPermService.findAdminPermById(id);
        return new ResponseJson(adminPerm);
    }

    @PostMapping("/updateAdminPerm")
    @ApiOperation(value = "修改校管理员权限对象非空字段", notes = "返回响应对象")
    public ResponseJson updateAdminPerm(
            @ApiParam(value = "被修改的校管理员权限对象,对象属性不为空则修改", required = true)
            @RequestBody AdminPerm adminPerm){
        adminPermService.updateAdminPerm(adminPerm);
        return new ResponseJson();
    }

    @PostMapping("/updateAdminPermForAll")
    @ApiOperation(value = "修改校管理员权限对象所有字段", notes = "返回响应对象")
    public ResponseJson updateAdminPermForAll(
            @ApiParam(value = "被修改的校管理员权限对象", required = true)
            @RequestBody AdminPerm adminPerm){
        adminPermService.updateAdminPermForAll(adminPerm);
        return new ResponseJson();
    }


    @PostMapping("/findAdminPermsByCondition")
    @ApiOperation(value = "根据条件查找校管理员权限", notes = "返回响应对象", response=AdminPerm.class)
    public ResponseJson findAdminPermsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody AdminPerm adminPerm){
        List<AdminPerm> data=adminPermService.findAdminPermListByCondition(adminPerm);
        long count=adminPermService.findAdminPermCountByCondition(adminPerm);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneAdminPermByCondition")
    @ApiOperation(value = "根据条件查找单个校管理员权限,结果必须为单条数据", notes = "没有时返回空", response=AdminPerm.class)
    public ResponseJson findOneAdminPermByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody AdminPerm adminPerm){
        AdminPerm one=adminPermService.findOneAdminPermByCondition(adminPerm);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteAdminPerm/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteAdminPerm(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        adminPermService.deleteAdminPerm(id);
        return new ResponseJson();
    }
    @GetMapping("/deleteAdminPermRecursive/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteAdminPermRecursive(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        adminPermService.deleteAdminPermRecursive(id);
        return new ResponseJson();
    }


    @PostMapping("/findAdminPermListByCondition")
    @ApiOperation(value = "根据条件查找校管理员权限列表", notes = "返回响应对象,不包含总条数", response=AdminPerm.class)
    public ResponseJson findAdminPermListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody AdminPerm adminPerm){
        List<AdminPerm> data=adminPermService.findAdminPermListByCondition(adminPerm);
        return new ResponseJson(data);
    }
    @GetMapping("/findAdminPermsTree")
    public ResponseJson findAdminPermsTree(){
        AdminPerm adminPerm = new AdminPerm();
        adminPerm.setPager(new Pager().setPaging(false).setIncludes("id","parentId","title","icon","schoolPermId").setSortField("sortNum").setSortOrder(Pager.ASC));
        List<AdminPerm> list = adminPermService.findAdminPermListByCondition(adminPerm);
        return new ResponseJson(ObjectKit.buildTree(list,"-1"));
    }

    @PostMapping("/batchUpdateSortNum")
    public ResponseJson batchUpdateSortNum(@RequestBody List<AdminPerm> adminPerms){
        adminPermService.batchUpdateSortNum(adminPerms);
        return new ResponseJson();
    }


}
