package com.yice.edu.cn.osp.controller.dm.cameraManage;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.dm.cameraManage.CameraManage;
import com.yice.edu.cn.osp.service.dm.cameraManage.CameraManageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/cameraManage")
@Api(value = "/cameraManage",description = "监控设备管理模块")
public class CameraManageController {
    @Autowired
    private CameraManageService cameraManageService;

    @PostMapping("/saveCameraManage")
    @ApiOperation(value = "保存监控设备管理对象", notes = "返回保存好的监控设备管理对象", response=CameraManage.class)
    public ResponseJson saveCameraManage(
            @ApiParam(value = "监控设备管理对象", required = true)
            @RequestBody CameraManage cameraManage){
        cameraManage.setSchoolId(mySchoolId());
        cameraManage.setUrlA(cameraManage.getUrlA().trim());
        if (cameraManage.getUrlB() != null){
            cameraManage.setUrlB(cameraManage.getUrlB().trim());
        }
        CameraManage s=cameraManageService.saveCameraManage(cameraManage);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findCameraManageById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找监控设备管理", notes = "返回响应对象", response=CameraManage.class)
    public ResponseJson findCameraManageById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        CameraManage cameraManage=cameraManageService.findCameraManageById(id);
        return new ResponseJson(cameraManage);
    }

    @PostMapping("/update/updateCameraManage")
    @ApiOperation(value = "修改监控设备管理对象", notes = "返回响应对象")
    public ResponseJson updateCameraManage(
            @ApiParam(value = "被修改的监控设备管理对象,对象属性不为空则修改", required = true)
            @RequestBody CameraManage cameraManage){
        cameraManage.setUrlA(cameraManage.getUrlA().trim());
        if (cameraManage.getUrlB() != null){
            cameraManage.setUrlB(cameraManage.getUrlB().trim());
        }
        cameraManageService.updateCameraManage(cameraManage);
        return new ResponseJson();
    }

    @GetMapping("/look/lookCameraManageById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找监控设备管理", notes = "返回响应对象", response=CameraManage.class)
    public ResponseJson lookCameraManageById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        CameraManage cameraManage=cameraManageService.findCameraManageById(id);
        return new ResponseJson(cameraManage);
    }

    @PostMapping("/findCameraManagesByCondition")
    @ApiOperation(value = "根据条件查找监控设备管理", notes = "返回响应对象", response=CameraManage.class)
    public ResponseJson findCameraManagesByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody CameraManage cameraManage){
        cameraManage.setSchoolId(mySchoolId());
        /*Pager pager=cameraManage.getPager();
        pager.setLike("cameraName");
        pager.setLike("searchStearTime");
        pager.setLike("searchEndTime");
        cameraManage.setPager(pager);*/
        List<CameraManage> data=cameraManageService.findCameraManageListByCondition(cameraManage);
        long count=cameraManageService.findCameraManageCountByCondition(cameraManage);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneCameraManageByCondition")
    @ApiOperation(value = "根据条件查找单个监控设备管理,结果必须为单条数据", notes = "没有时返回空", response=CameraManage.class)
    public ResponseJson findOneCameraManageByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody CameraManage cameraManage){
        CameraManage one=cameraManageService.findOneCameraManageByCondition(cameraManage);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteCameraManage/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteCameraManage(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        cameraManageService.deleteCameraManage(id);
        return new ResponseJson();
    }


    @GetMapping("/findCameraManageListByCondition")
    @ApiOperation(value = "根据条件查找监控设备管理", notes = "返回响应对象,不包含总条数", response=CameraManage.class)
    public ResponseJson findCameraManageListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody CameraManage cameraManage){
       cameraManage.setSchoolId(mySchoolId());
        List<CameraManage> data=cameraManageService.findCameraManageListByCondition(cameraManage);
        return new ResponseJson(data);
    }


    @PostMapping("/update/findCameraManagesByCondition2")
    @ApiOperation(value = "根据条件查找监控设备管理", notes = "返回响应对象")
    public ResponseJson findCameraManagesByCondition2(){
        CameraManage cameraManage = new CameraManage();
        cameraManage.setSchoolId(mySchoolId());
        List<CameraManage> data=cameraManageService.findCameraManageListByCondition2(cameraManage);
        return new ResponseJson(data);
    }

}
