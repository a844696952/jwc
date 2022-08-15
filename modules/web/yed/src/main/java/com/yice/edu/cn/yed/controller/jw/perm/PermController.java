package com.yice.edu.cn.yed.controller.jw.perm;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.auth.Perm;
import com.yice.edu.cn.yed.service.jw.perm.PermService;
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
    @ApiOperation(value = "通过id查找学校权限", notes = "返回响应对象")
    public ResponseJson findPermById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        Perm perm=permService.findPermById(id);
        return new ResponseJson(perm);
    }

    @PostMapping("/savePerm")
    @ApiOperation(value = "保存学校权限对象", notes = "返回响应对象")
    public ResponseJson savePerm(
            @ApiParam(value = "学校权限对象", required = true)
            @RequestBody Perm perm){
        Perm s=permService.savePerm(perm);
        return new ResponseJson(s);
    }
    @PostMapping("/updatePerm")
    @ApiOperation(value = "修改学校权限对象", notes = "返回响应对象")
    public ResponseJson updatePerm(
            @ApiParam(value = "被修改的学校权限对象,对象属性不为空则修改", required = true)
            @RequestBody Perm perm){
        permService.updatePerm(perm);
        return new ResponseJson();
    }

    @PostMapping("/findPermsByCondition")
    @ApiOperation(value = "根据条件查找学校权限", notes = "返回响应对象")
    public ResponseJson findPermsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody Perm perm){
        List<Perm> data=permService.findPermListByCondition(perm);
        long count=permService.findPermCountByCondition(perm);
        return new ResponseJson(data,count);
    }
    @GetMapping("/deletePerm/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deletePerm(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        permService.deletePerm(id);
        return new ResponseJson();
    }

    @GetMapping("/deletePermByCondition")
    @ApiOperation(value = "根据条件删除学校权限", notes = "返回响应对象")
    public ResponseJson deletePermByCondition(
            @ApiParam(value = "被删除的学校权限对象,对象属性不为空则作为删除条件", required = true)
            @RequestBody Perm perm){
        permService.deletePermByCondition(perm);
        return new ResponseJson();
    }

}
