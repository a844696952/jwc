package com.yice.edu.cn.dy.controller.classManage.mesAppletsTeacher;

import com.yice.edu.cn.common.pojo.mes.classManage.mesAppletsTeacher.MesAppletsTeacher;
import com.yice.edu.cn.dy.service.classManage.mesAppletsTeacher.MesAppletsTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mesAppletsTeacher")
@Api(value = "/mesAppletsTeacher",description = "德育小程序教师表模块")
public class MesAppletsTeacherController {
    @Autowired
    private MesAppletsTeacherService mesAppletsTeacherService;

    @GetMapping("/findMesAppletsTeacherById/{id}")
    @ApiOperation(value = "通过id查找德育小程序教师表", notes = "返回德育小程序教师表对象")
    public MesAppletsTeacher findMesAppletsTeacherById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return mesAppletsTeacherService.findMesAppletsTeacherById(id);
    }

    @PostMapping("/saveMesAppletsTeacher")
    @ApiOperation(value = "保存德育小程序教师表", notes = "返回德育小程序教师表对象")
    public MesAppletsTeacher saveMesAppletsTeacher(
            @ApiParam(value = "德育小程序教师表对象", required = true)
            @RequestBody MesAppletsTeacher mesAppletsTeacher){
        mesAppletsTeacherService.saveMesAppletsTeacher(mesAppletsTeacher);
        return mesAppletsTeacher;
    }

    @PostMapping("/findMesAppletsTeacherListByCondition")
    @ApiOperation(value = "根据条件查找德育小程序教师表列表", notes = "返回德育小程序教师表列表")
    public List<MesAppletsTeacher> findMesAppletsTeacherListByCondition(
            @ApiParam(value = "德育小程序教师表对象")
            @RequestBody MesAppletsTeacher mesAppletsTeacher){
        return mesAppletsTeacherService.findMesAppletsTeacherListByCondition(mesAppletsTeacher);
    }
    @PostMapping("/findMesAppletsTeacherCountByCondition")
    @ApiOperation(value = "根据条件查找德育小程序教师表列表个数", notes = "返回德育小程序教师表总个数")
    public long findMesAppletsTeacherCountByCondition(
            @ApiParam(value = "德育小程序教师表对象")
            @RequestBody MesAppletsTeacher mesAppletsTeacher){
        return mesAppletsTeacherService.findMesAppletsTeacherCountByCondition(mesAppletsTeacher);
    }

    @PostMapping("/updateMesAppletsTeacher")
    @ApiOperation(value = "修改德育小程序教师表", notes = "德育小程序教师表对象必传")
    public void updateMesAppletsTeacher(
            @ApiParam(value = "德育小程序教师表对象,对象属性不为空则修改", required = true)
            @RequestBody MesAppletsTeacher mesAppletsTeacher){
        mesAppletsTeacherService.updateMesAppletsTeacher(mesAppletsTeacher);
    }

    @GetMapping("/deleteMesAppletsTeacher/{id}")
    @ApiOperation(value = "通过id删除德育小程序教师表")
    public void deleteMesAppletsTeacher(
            @ApiParam(value = "德育小程序教师表对象", required = true)
            @PathVariable String id){
        mesAppletsTeacherService.deleteMesAppletsTeacher(id);
    }
    @PostMapping("/deleteMesAppletsTeacherByCondition")
    @ApiOperation(value = "根据条件删除德育小程序教师表")
    public void deleteMesAppletsTeacherByCondition(
            @ApiParam(value = "德育小程序教师表对象")
            @RequestBody MesAppletsTeacher mesAppletsTeacher){
        mesAppletsTeacherService.deleteMesAppletsTeacherByCondition(mesAppletsTeacher);
    }
    @PostMapping("/findOneMesAppletsTeacherByCondition")
    @ApiOperation(value = "根据条件查找单个德育小程序教师表,结果必须为单条数据", notes = "返回单个德育小程序教师表,没有时为空")
    public MesAppletsTeacher findOneMesAppletsTeacherByCondition(
            @ApiParam(value = "德育小程序教师表对象")
            @RequestBody MesAppletsTeacher mesAppletsTeacher){
        return mesAppletsTeacherService.findOneMesAppletsTeacherByCondition(mesAppletsTeacher);
    }
}
