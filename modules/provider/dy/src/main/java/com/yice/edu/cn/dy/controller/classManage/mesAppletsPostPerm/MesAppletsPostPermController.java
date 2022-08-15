package com.yice.edu.cn.dy.controller.classManage.mesAppletsPostPerm;

import com.yice.edu.cn.common.pojo.general.dd.Dd;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherPost;
import com.yice.edu.cn.common.pojo.mes.classManage.mesAppletsPostPerm.MesAppletsPostPerm;
import com.yice.edu.cn.dy.feign.DdFeign;
import com.yice.edu.cn.dy.service.classManage.mesAppletsPostPerm.MesAppletsPostPermService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/mesAppletsPostPerm")
@Api(value = "/mesAppletsPostPerm",description = "德育小程序职务权限表模块")
public class MesAppletsPostPermController {
    @Autowired
    private MesAppletsPostPermService mesAppletsPostPermService;
    @Autowired
    private DdFeign ddFeign;

    @GetMapping("/findMesAppletsPostPermById/{id}")
    @ApiOperation(value = "通过id查找德育小程序职务权限表", notes = "返回德育小程序职务权限表对象")
    public MesAppletsPostPerm findMesAppletsPostPermById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return mesAppletsPostPermService.findMesAppletsPostPermById(id);
    }

    @PostMapping("/saveMesAppletsPostPerm")
    @ApiOperation(value = "保存德育小程序职务权限表", notes = "返回德育小程序职务权限表对象")
    public MesAppletsPostPerm saveMesAppletsPostPerm(
            @ApiParam(value = "德育小程序职务权限表对象", required = true)
            @RequestBody MesAppletsPostPerm mesAppletsPostPerm){
        mesAppletsPostPermService.saveMesPermission(mesAppletsPostPerm);
        return mesAppletsPostPerm;
    }

    @PostMapping("/findMesAppletsPostPermListByCondition")
    @ApiOperation(value = "根据条件查找德育小程序职务权限表列表", notes = "返回德育小程序职务权限表列表")
    public List<MesAppletsPostPerm> findMesAppletsPostPermListByCondition(
            @ApiParam(value = "德育小程序职务权限表对象")
            @RequestBody MesAppletsPostPerm mesAppletsPostPerm){
        return mesAppletsPostPermService.findMesAppletsPostPermListByCondition(mesAppletsPostPerm);
    }
    @PostMapping("/findMesAppletsPostPermCountByCondition")
    @ApiOperation(value = "根据条件查找德育小程序职务权限表列表个数", notes = "返回德育小程序职务权限表总个数")
    public long findMesAppletsPostPermCountByCondition(
            @ApiParam(value = "德育小程序职务权限表对象")
            @RequestBody MesAppletsPostPerm mesAppletsPostPerm){
        return mesAppletsPostPermService.findMesAppletsPostPermCountByCondition(mesAppletsPostPerm);
    }

    @PostMapping("/updateMesAppletsPostPerm")
    @ApiOperation(value = "修改德育小程序职务权限表", notes = "德育小程序职务权限表对象必传")
    public void updateMesAppletsPostPerm(
            @ApiParam(value = "德育小程序职务权限表对象,对象属性不为空则修改", required = true)
            @RequestBody MesAppletsPostPerm mesAppletsPostPerm){
        mesAppletsPostPermService.updateMesPerm(mesAppletsPostPerm);
    }

    @GetMapping("/deleteMesAppletsPostPerm/{id}")
    @ApiOperation(value = "通过id删除德育小程序职务权限表")
    public void deleteMesAppletsPostPerm(
            @ApiParam(value = "德育小程序职务权限表对象", required = true)
            @PathVariable String id){
        mesAppletsPostPermService.deleteMesAppletsPostPerm(id);
    }
    @PostMapping("/deleteMesAppletsPostPermByCondition")
    @ApiOperation(value = "根据条件删除德育小程序职务权限表")
    public void deleteMesAppletsPostPermByCondition(
            @ApiParam(value = "德育小程序职务权限表对象")
            @RequestBody MesAppletsPostPerm mesAppletsPostPerm){
        mesAppletsPostPermService.deleteMesAppletsPostPermByCondition(mesAppletsPostPerm);
    }
    @PostMapping("/findOneMesAppletsPostPermByCondition")
    @ApiOperation(value = "根据条件查找单个德育小程序职务权限表,结果必须为单条数据", notes = "返回单个德育小程序职务权限表,没有时为空")
    public MesAppletsPostPerm findOneMesAppletsPostPermByCondition(
            @ApiParam(value = "德育小程序职务权限表对象")
            @RequestBody MesAppletsPostPerm mesAppletsPostPerm){
        return mesAppletsPostPermService.findOneMesAppletsPostPermByCondition(mesAppletsPostPerm);
    }

    @PostMapping("/findDdListByCondition")
    @ApiOperation(value = "根据条件查找德育小程序职务权限数据字典", notes = "返回德育小程序职务权限数据字典")
    public List<Dd> findDdListByCondition(
            @ApiParam(value = "数据字典对象")
            @RequestBody Dd dd) {
        return ddFeign.findDdListByCondition(dd);
    }
    @PostMapping("/findMesAppletsPostPermByPostId")
    @ApiOperation(value = "根据条件查询德育小程序职务权限表")
    public Set<Integer> findMesAppletsPostPermByPostId(
            @ApiParam(value = "德育小程序职务权限表对象")
            @RequestBody List<TeacherPost> mesAppletsPostPerms){
        return mesAppletsPostPermService.findMesAppletsPostPermByPostId(mesAppletsPostPerms);
    }
}
