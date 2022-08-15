package com.yice.edu.cn.dm.controller.dmCamera;

import com.yice.edu.cn.common.pojo.dm.dmCamera.DmCamera;
import com.yice.edu.cn.dm.service.dmCamera.DmCameraService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dmCamera")
@Api(value = "/dmCamera",description = "云班牌摄像头管理模块")
public class DmCameraController {
    @Autowired
    private DmCameraService dmCameraService;

    @GetMapping("/findDmCameraById/{id}")
    @ApiOperation(value = "通过id查找云班牌摄像头管理", notes = "返回云班牌摄像头管理对象")
    public DmCamera findDmCameraById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return dmCameraService.findDmCameraById(id);
    }

    @PostMapping("/saveDmCamera")
    @ApiOperation(value = "保存云班牌摄像头管理", notes = "返回云班牌摄像头管理对象")
    public DmCamera saveDmCamera(
            @ApiParam(value = "云班牌摄像头管理对象", required = true)
            @RequestBody DmCamera dmCamera){
        dmCameraService.saveDmCamera(dmCamera);
        return dmCamera;
    }

    @PostMapping("/findDmCameraListByCondition")
    @ApiOperation(value = "根据条件查找云班牌摄像头管理列表", notes = "返回云班牌摄像头管理列表")
    public List<DmCamera> findDmCameraListByCondition(
            @ApiParam(value = "云班牌摄像头管理对象")
            @RequestBody DmCamera dmCamera){
        return dmCameraService.findDmCameraListByCondition(dmCamera);
    }
    @PostMapping("/findDmCameraCountByCondition")
    @ApiOperation(value = "根据条件查找云班牌摄像头管理列表个数", notes = "返回云班牌摄像头管理总个数")
    public long findDmCameraCountByCondition(
            @ApiParam(value = "云班牌摄像头管理对象")
            @RequestBody DmCamera dmCamera){
        return dmCameraService.findDmCameraCountByCondition(dmCamera);
    }

    @PostMapping("/updateDmCamera")
    @ApiOperation(value = "修改云班牌摄像头管理", notes = "云班牌摄像头管理对象必传")
    public void updateDmCamera(
            @ApiParam(value = "云班牌摄像头管理对象,对象属性不为空则修改", required = true)
            @RequestBody DmCamera dmCamera){
        dmCameraService.updateDmCamera(dmCamera);
    }

    @GetMapping("/deleteDmCamera/{id}")
    @ApiOperation(value = "通过id删除云班牌摄像头管理")
    public void deleteDmCamera(
            @ApiParam(value = "云班牌摄像头管理对象", required = true)
            @PathVariable String id){
        dmCameraService.deleteDmCamera(id);
    }
    @PostMapping("/deleteDmCameraByCondition")
    @ApiOperation(value = "根据条件删除云班牌摄像头管理")
    public void deleteDmCameraByCondition(
            @ApiParam(value = "云班牌摄像头管理对象")
            @RequestBody DmCamera dmCamera){
        dmCameraService.deleteDmCameraByCondition(dmCamera);
    }
    @PostMapping("/findOneDmCameraByCondition")
    @ApiOperation(value = "根据条件查找单个云班牌摄像头管理,结果必须为单条数据", notes = "返回单个云班牌摄像头管理,没有时为空")
    public DmCamera findOneDmCameraByCondition(
            @ApiParam(value = "云班牌摄像头管理对象")
            @RequestBody DmCamera dmCamera){
        return dmCameraService.findOneDmCameraByCondition(dmCamera);
    }
}
