package com.yice.edu.cn.tap.controller.dy.classManage;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.mes.classManage.mesAppletsTeacher.MesAppletsTeacher;
import com.yice.edu.cn.tap.interceptor.LoginInterceptor;
import com.yice.edu.cn.tap.service.dy.classManage.MesAppletsTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mesAppletsTeacher")
@Api(value = "/mesAppletsTeacher",description = "德育小程序教师表模块")
public class MesAppletsTeacherController {
    @Autowired
    private MesAppletsTeacherService mesAppletsTeacherService;

    @PostMapping("/saveMesAppletsTeacher")
    @ApiOperation(value = "保存德育小程序教师表对象", notes = "返回保存好的德育小程序教师表对象", response=MesAppletsTeacher.class)
    public ResponseJson saveMesAppletsTeacher(
            @ApiParam(value = "德育小程序教师表对象", required = true)
            @RequestBody MesAppletsTeacher mesAppletsTeacher){
        mesAppletsTeacher.setSchoolId(LoginInterceptor.mySchoolId());
        MesAppletsTeacher s=mesAppletsTeacherService.saveMesAppletsTeacher(mesAppletsTeacher);
        return new ResponseJson(s);
    }


    @PostMapping("/updateMesAppletsTeacher")
    @ApiOperation(value = "修改德育小程序教师表对象", notes = "返回响应对象")
    public ResponseJson updateMesAppletsTeacher(
            @ApiParam(value = "被修改的德育小程序教师表对象,对象属性不为空则修改", required = true)
            @RequestBody MesAppletsTeacher mesAppletsTeacher){
        mesAppletsTeacherService.updateMesAppletsTeacher(mesAppletsTeacher);
        return new ResponseJson();
    }

    @GetMapping("/findMesAppletsTeacherById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找德育小程序教师表", notes = "返回响应对象", response=MesAppletsTeacher.class)
    public ResponseJson findMesAppletsTeacherById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        MesAppletsTeacher mesAppletsTeacher=mesAppletsTeacherService.findMesAppletsTeacherById(id);
        return new ResponseJson(mesAppletsTeacher);
    }

    @GetMapping("/deleteMesAppletsTeacher/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteMesAppletsTeacher(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        mesAppletsTeacherService.deleteMesAppletsTeacher(id);
        return new ResponseJson();
    }


    @PostMapping("/findMesAppletsTeacherListByCondition")
    @ApiOperation(value = "根据条件查找德育小程序教师表列表", notes = "返回响应对象,不包含总条数", response=MesAppletsTeacher.class)
    public ResponseJson findMesAppletsTeacherListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody MesAppletsTeacher mesAppletsTeacher){
        mesAppletsTeacher.setSchoolId(LoginInterceptor.mySchoolId());
        List<MesAppletsTeacher> data=mesAppletsTeacherService.findMesAppletsTeacherListByCondition(mesAppletsTeacher);
        return new ResponseJson(data);
    }



}
