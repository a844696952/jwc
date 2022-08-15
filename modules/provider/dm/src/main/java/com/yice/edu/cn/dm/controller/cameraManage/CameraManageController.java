package com.yice.edu.cn.dm.controller.cameraManage;

import com.yice.edu.cn.common.pojo.dm.cameraManage.CameraManage;
import com.yice.edu.cn.dm.service.cameraManage.CameraManageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cameraManage")
@Api(value = "/cameraManage",description = "监控设备管理模块")
public class CameraManageController {
    @Autowired
    private CameraManageService cameraManageService;

    @GetMapping("/findCameraManageById/{id}")
    @ApiOperation(value = "通过id查找监控设备管理", notes = "返回监控设备管理对象")
    public CameraManage findCameraManageById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return cameraManageService.findCameraManageById(id);
    }

    @PostMapping("/saveCameraManage")
    @ApiOperation(value = "保存监控设备管理", notes = "返回监控设备管理对象")
    public CameraManage saveCameraManage(
            @ApiParam(value = "监控设备管理对象", required = true)
            @RequestBody CameraManage cameraManage){
        cameraManageService.saveCameraManage(cameraManage);
        return cameraManage;
    }

    @PostMapping("/findCameraManageListByCondition")
    @ApiOperation(value = "根据条件查找监控设备管理", notes = "返回监控设备管理")
    public List<CameraManage> findCameraManageListByCondition(
            @ApiParam(value = "监控设备管理对象")
            @RequestBody CameraManage cameraManage){
        return cameraManageService.findCameraManageListByCondition(cameraManage);
    }
    @PostMapping("/findCameraManageCountByCondition")
    @ApiOperation(value = "根据条件查找监控设备管理个数", notes = "返回监控设备管理总个数")
    public long findCameraManageCountByCondition(
            @ApiParam(value = "监控设备管理对象")
            @RequestBody CameraManage cameraManage){
        return cameraManageService.findCameraManageCountByCondition(cameraManage);
    }

    @PostMapping("/updateCameraManage")
    @ApiOperation(value = "修改监控设备管理", notes = "监控设备管理对象必传")
    public void updateCameraManage(
            @ApiParam(value = "监控设备管理对象,对象属性不为空则修改", required = true)
            @RequestBody CameraManage cameraManage){
        cameraManageService.updateCameraManage(cameraManage);
    }

    @GetMapping("/deleteCameraManage/{id}")
    @ApiOperation(value = "通过id删除监控设备管理")
    public void deleteCameraManage(
            @ApiParam(value = "监控设备管理对象", required = true)
            @PathVariable String id){
        cameraManageService.deleteCameraManage(id);
    }
    @PostMapping("/deleteCameraManageByCondition")
    @ApiOperation(value = "根据条件删除监控设备管理")
    public void deleteCameraManageByCondition(
            @ApiParam(value = "监控设备管理对象")
            @RequestBody CameraManage cameraManage){
        cameraManageService.deleteCameraManageByCondition(cameraManage);
    }
    @PostMapping("/findOneCameraManageByCondition")
    @ApiOperation(value = "根据条件查找单个监控设备管理,结果必须为单条数据", notes = "返回单个监控设备管理,没有时为空")
    public CameraManage findOneCameraManageByCondition(
            @ApiParam(value = "监控设备管理对象")
            @RequestBody CameraManage cameraManage){
        return cameraManageService.findOneCameraManageByCondition(cameraManage);
    }


    //
    @PostMapping("/findCameraManageListByCondition2")
    @ApiOperation(value = "根据条件查找监控设备管理", notes = "返回监控设备管理")
    public List<CameraManage> findCameraManageListByCondition2(
            @ApiParam(value = "监控设备管理对象")
            @RequestBody CameraManage cameraManage){
        return cameraManageService.findCameraManageListByCondition2(cameraManage);
    }
}
