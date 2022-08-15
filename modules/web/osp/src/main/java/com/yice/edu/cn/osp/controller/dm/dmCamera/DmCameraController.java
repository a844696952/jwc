package com.yice.edu.cn.osp.controller.dm.dmCamera;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.dm.dmCamera.DmCamera;
import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import com.yice.edu.cn.osp.service.dm.dmCamera.DmCameraService;
import com.yice.edu.cn.osp.service.jw.classes.JwClassesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collector;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.myId;
import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/dmCamera")
@Api(value = "/dmCamera",description = "云班牌摄像头管理模块")
public class DmCameraController {
    @Autowired
    private DmCameraService dmCameraService;
    @Autowired
    private JwClassesService jwClassesService;

    @PostMapping("/saveDmCamera")
    @ApiOperation(value = "保存云班牌摄像头管理对象", notes = "返回保存好的云班牌摄像头管理对象", response=DmCamera.class)
    public ResponseJson saveDmCamera(
            @ApiParam(value = "云班牌摄像头管理对象", required = true)
            @RequestBody DmCamera dmCamera){
        dmCamera.setTeacherId(myId());
       dmCamera.setSchoolId(mySchoolId());
        DmCamera s=dmCameraService.saveDmCamera(dmCamera);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findDmCameraById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找云班牌摄像头管理", notes = "返回响应对象", response=DmCamera.class)
    public ResponseJson findDmCameraById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        DmCamera dmCamera=dmCameraService.findDmCameraById(id);
        return new ResponseJson(dmCamera);
    }

    @PostMapping("/update/updateDmCamera")
    @ApiOperation(value = "修改云班牌摄像头管理对象", notes = "返回响应对象")
    public ResponseJson updateDmCamera(
            @ApiParam(value = "被修改的云班牌摄像头管理对象,对象属性不为空则修改", required = true)
            @RequestBody DmCamera dmCamera){
        dmCameraService.updateDmCamera(dmCamera);
        return new ResponseJson();
    }

    @GetMapping("/look/lookDmCameraById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找云班牌摄像头管理", notes = "返回响应对象", response=DmCamera.class)
    public ResponseJson lookDmCameraById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        DmCamera dmCamera=dmCameraService.findDmCameraById(id);
        return new ResponseJson(dmCamera);
    }

    @PostMapping("/findDmCamerasByCondition")
    @ApiOperation(value = "根据条件查找云班牌摄像头管理", notes = "返回响应对象", response=DmCamera.class)
    public ResponseJson findDmCamerasByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody DmCamera dmCamera){
       dmCamera.setSchoolId(mySchoolId());
        List<DmCamera> data=dmCameraService.findDmCameraListByCondition(dmCamera);
        long count=dmCameraService.findDmCameraCountByCondition(dmCamera);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneDmCameraByCondition")
    @ApiOperation(value = "根据条件查找单个云班牌摄像头管理,结果必须为单条数据", notes = "没有时返回空", response=DmCamera.class)
    public ResponseJson findOneDmCameraByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody DmCamera dmCamera){
        DmCamera one=dmCameraService.findOneDmCameraByCondition(dmCamera);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteDmCamera/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteDmCamera(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        dmCameraService.deleteDmCamera(id);
        return new ResponseJson();
    }


    @PostMapping("/findDmCameraListByCondition")
    @ApiOperation(value = "根据条件查找云班牌摄像头管理列表", notes = "返回响应对象,不包含总条数", response=DmCamera.class)
    public ResponseJson findDmCameraListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody DmCamera dmCamera){
       dmCamera.setSchoolId(mySchoolId());
        List<DmCamera> data=dmCameraService.findDmCameraListByCondition(dmCamera);
        return new ResponseJson(data);
    }
    @GetMapping("/findJwClassesListByCondition")
    @ApiOperation(value = "根据条件查找班级管理列表", notes = "返回响应对象,不包含总条数", response=DmCamera.class)
    public ResponseJson findJwClassesListByCondition(){
        JwClasses jwClasses = new JwClasses();
        jwClasses.setSchoolId(mySchoolId());
        List<JwClasses> data=jwClassesService.findJwClassesListByCondition(jwClasses);
        data.sort(Comparator.comparing(JwClasses::getGradeId).thenComparing(Comparator.comparing(JwClasses::getNumber)));
        return new ResponseJson(data);
    }



}
