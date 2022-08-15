package com.yice.edu.cn.yed.controller.dy.mesAppletsPostPerm;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.general.dd.Dd;
import com.yice.edu.cn.common.pojo.mes.classManage.mesAppletsPostPerm.MesAppletsPostPerm;
import com.yice.edu.cn.yed.service.dy.mesAppletsPostPerm.MesAppletsPostPermService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mesAppletsPostPerm")
@Api(value = "/mesAppletsPostPerm", description = "德育小程序职务权限表模块")
public class MesAppletsPostPermController {
    @Autowired
    private MesAppletsPostPermService mesAppletsPostPermService;

    @PostMapping("/saveMesAppletsPostPerm")
    @ApiOperation(value = "保存德育小程序职务权限表对象", notes = "返回保存好的德育小程序职务权限表对象", response = MesAppletsPostPerm.class)
    public ResponseJson saveMesAppletsPostPerm(
            @ApiParam(value = "德育小程序职务权限表对象", required = true)
            @RequestBody MesAppletsPostPerm mesAppletsPostPerm) {
        MesAppletsPostPerm s = mesAppletsPostPermService.saveMesAppletsPostPerm(mesAppletsPostPerm);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findMesAppletsPostPermById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找德育小程序职务权限表", notes = "返回响应对象", response = MesAppletsPostPerm.class)
    public ResponseJson findMesAppletsPostPermById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id) {
        MesAppletsPostPerm mesAppletsPostPerm = mesAppletsPostPermService.findMesAppletsPostPermById(id);
        return new ResponseJson(mesAppletsPostPerm);
    }

    @PostMapping("/update/updateMesAppletsPostPerm")
    @ApiOperation(value = "修改德育小程序职务权限表对象", notes = "返回响应对象")
    public ResponseJson updateMesAppletsPostPerm(
            @ApiParam(value = "被修改的德育小程序职务权限表对象,对象属性不为空则修改", required = true)
            @RequestBody MesAppletsPostPerm mesAppletsPostPerm) {
        mesAppletsPostPermService.updateMesAppletsPostPerm(mesAppletsPostPerm);
        return new ResponseJson();
    }

    @GetMapping("/look/lookMesAppletsPostPermById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找德育小程序职务权限表", notes = "返回响应对象", response = MesAppletsPostPerm.class)
    public ResponseJson lookMesAppletsPostPermById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id) {
        MesAppletsPostPerm mesAppletsPostPerm = mesAppletsPostPermService.findMesAppletsPostPermById(id);
        return new ResponseJson(mesAppletsPostPerm);
    }

    @PostMapping("/findMesAppletsPostPermsByCondition")
    @ApiOperation(value = "根据条件查找德育小程序职务权限表", notes = "返回响应对象", response = MesAppletsPostPerm.class)
    public ResponseJson findMesAppletsPostPermsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody MesAppletsPostPerm mesAppletsPostPerm) {
        List<MesAppletsPostPerm> data = mesAppletsPostPermService.findMesAppletsPostPermListByCondition(mesAppletsPostPerm);
        long count = mesAppletsPostPermService.findMesAppletsPostPermCountByCondition(mesAppletsPostPerm);
        return new ResponseJson(data, count);
    }

    @PostMapping("/findOneMesAppletsPostPermByCondition")
    @ApiOperation(value = "根据条件查找单个德育小程序职务权限表,结果必须为单条数据", notes = "没有时返回空", response = MesAppletsPostPerm.class)
    public ResponseJson findOneMesAppletsPostPermByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody MesAppletsPostPerm mesAppletsPostPerm) {
        MesAppletsPostPerm one = mesAppletsPostPermService.findOneMesAppletsPostPermByCondition(mesAppletsPostPerm);
        return new ResponseJson(one);
    }

    @GetMapping("/deleteMesAppletsPostPerm/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteMesAppletsPostPerm(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id) {
        mesAppletsPostPermService.deleteMesAppletsPostPerm(id);
        return new ResponseJson();
    }

    @PostMapping("/deleteMesAppletsPostPermByCondition")
    @ApiOperation(value = "根据条件删除德育小程序职务权限表")
    public void deleteMesAppletsPostPermByCondition(
            @ApiParam(value = "德育小程序职务权限表对象")
            @RequestBody MesAppletsPostPerm mesAppletsPostPerm) {
        mesAppletsPostPermService.deleteMesAppletsPostPermByCondition(mesAppletsPostPerm);
    }

    @PostMapping("/findMesAppletsPostPermListByCondition")
    @ApiOperation(value = "根据条件查找德育小程序职务权限表列表", notes = "返回响应对象,不包含总条数", response = MesAppletsPostPerm.class)
    public ResponseJson findMesAppletsPostPermListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody MesAppletsPostPerm mesAppletsPostPerm) {
        mesAppletsPostPerm.getPager().setPaging(false);
        List<MesAppletsPostPerm> data = mesAppletsPostPermService.findMesAppletsPostPermListByCondition(mesAppletsPostPerm);
        return new ResponseJson(data);
    }

    @PostMapping("/findDdListByCondition")
    @ApiOperation(value = "根据条件查找德育小程序职务权限数据字典", notes = "返回响应对象,不包含总条数", response = Dd.class)
    public ResponseJson findDdListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody Dd dd) {
        List<Dd> data = mesAppletsPostPermService.findDdListByCondition(dd);
        return new ResponseJson(data);
    }
}
