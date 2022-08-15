package com.yice.edu.cn.jw.controller.auth;

import com.yice.edu.cn.common.pojo.jw.auth.Perm;
import com.yice.edu.cn.jw.service.auth.PermService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/perm")
@Api(value = "/perm",description = "学校权限模块")
public class PermController {
    @Autowired
    private PermService permService;

    @GetMapping("/findPermById/{id}")
    @ApiOperation(value = "通过id查找学校权限", notes = "返回学校权限对象")
    public Perm findPermById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return permService.findPermById(id);
    }

    @PostMapping("/savePerm")
    @ApiOperation(value = "保存学校权限", notes = "返回学校权限对象")
    public Perm savePerm(
            @ApiParam(value = "学校权限对象", required = true)
            @RequestBody Perm perm){
        permService.savePerm(perm);
        return perm;
    }

    @PostMapping("/findPermListByCondition")
    @ApiOperation(value = "根据条件查找学校权限列表", notes = "返回学校权限列表")
    public List<Perm> findPermListByCondition(
            @ApiParam(value = "学校权限对象")
            @RequestBody Perm perm){
        return permService.findPermListByCondition(perm);
    }
    @PostMapping("/findPermCountByCondition")
    @ApiOperation(value = "根据条件查找学校权限列表个数", notes = "返回学校权限总个数")
    public long findPermCountByCondition(
            @ApiParam(value = "学校权限对象")
            @RequestBody Perm perm){
        return permService.findPermCountByCondition(perm);
    }

    @PostMapping("/updatePerm")
    @ApiOperation(value = "修改学校权限", notes = "学校权限对象必传")
    public void updatePerm(
            @ApiParam(value = "学校权限对象,对象属性不为空则修改", required = true)
            @RequestBody Perm perm){
        permService.updatePerm(perm);
    }

    @GetMapping("/deletePerm/{id}")
    @ApiOperation(value = "通过id删除学校权限")
    public void deletePerm(
            @ApiParam(value = "学校权限对象", required = true)
            @PathVariable String id){
        permService.deletePerm(id);
    }
    @PostMapping("/deletePermByCondition")
    @ApiOperation(value = "根据条件删除学校权限")
    public void deletePermByCondition(
            @ApiParam(value = "学校权限对象")
            @RequestBody Perm perm){
        permService.deletePermByCondition(perm);
    }

    @GetMapping("/findPermsForH5BySchoolId/{schoolId}")
    public List<Perm> findPermsForH5BySchoolId(@PathVariable String schoolId){
        return permService.findPermsForH5BySchoolId(schoolId);


    }
}
