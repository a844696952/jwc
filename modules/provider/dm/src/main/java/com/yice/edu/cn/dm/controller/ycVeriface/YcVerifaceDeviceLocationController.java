package com.yice.edu.cn.dm.controller.ycVeriface;

import cn.hutool.core.collection.CollectionUtil;
import com.yice.edu.cn.common.pojo.dm.ycVeriface.deviceLocation.YcVerifaceDeviceLocation;
import com.yice.edu.cn.dm.service.ycVeriface.YcVerifaceDeviceLocationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ycVerifaceDeviceLocation")
@Api(value = "/ycVerifaceDeviceLocation",description = "人脸识别设备安装位置")
public class YcVerifaceDeviceLocationController {
    @Autowired
    private YcVerifaceDeviceLocationService ycVerifaceDeviceLocationService;

    @GetMapping("/findYcVerifaceDeviceLocationById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public YcVerifaceDeviceLocation findYcVerifaceDeviceLocationById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return ycVerifaceDeviceLocationService.findYcVerifaceDeviceLocationById(id);
    }

    @PostMapping("/saveYcVerifaceDeviceLocation")
    @ApiOperation(value = "保存", notes = "返回对象")
    public YcVerifaceDeviceLocation saveYcVerifaceDeviceLocation(
            @ApiParam(value = "对象", required = true)
            @RequestBody YcVerifaceDeviceLocation ycVerifaceDeviceLocation){
        ycVerifaceDeviceLocationService.saveYcVerifaceDeviceLocation(ycVerifaceDeviceLocation);
        return ycVerifaceDeviceLocation;
    }

    @PostMapping("/findYcVerifaceDeviceLocationListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<YcVerifaceDeviceLocation> findYcVerifaceDeviceLocationListByCondition(
            @ApiParam(value = "对象")
            @RequestBody YcVerifaceDeviceLocation ycVerifaceDeviceLocation){
        return ycVerifaceDeviceLocationService.findYcVerifaceDeviceLocationListByCondition(ycVerifaceDeviceLocation);
    }
    @PostMapping("/findYcVerifaceDeviceLocationCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findYcVerifaceDeviceLocationCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody YcVerifaceDeviceLocation ycVerifaceDeviceLocation){
        return ycVerifaceDeviceLocationService.findYcVerifaceDeviceLocationCountByCondition(ycVerifaceDeviceLocation);
    }

    @PostMapping("/updateYcVerifaceDeviceLocation")
    @ApiOperation(value = "修改", notes = "对象必传")
    public void updateYcVerifaceDeviceLocation(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody YcVerifaceDeviceLocation ycVerifaceDeviceLocation){
        ycVerifaceDeviceLocationService.updateYcVerifaceDeviceLocation(ycVerifaceDeviceLocation);
    }

    @GetMapping("/deleteYcVerifaceDeviceLocation/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteYcVerifaceDeviceLocation(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        ycVerifaceDeviceLocationService.deleteYcVerifaceDeviceLocation(id);
    }
    @PostMapping("/deleteYcVerifaceDeviceLocationByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deleteYcVerifaceDeviceLocationByCondition(
            @ApiParam(value = "对象")
            @RequestBody YcVerifaceDeviceLocation ycVerifaceDeviceLocation){
        ycVerifaceDeviceLocationService.deleteYcVerifaceDeviceLocationByCondition(ycVerifaceDeviceLocation);
    }
    @PostMapping("/findOneYcVerifaceDeviceLocationByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public YcVerifaceDeviceLocation findOneYcVerifaceDeviceLocationByCondition(
            @ApiParam(value = "对象")
            @RequestBody YcVerifaceDeviceLocation ycVerifaceDeviceLocation){
        return ycVerifaceDeviceLocationService.findOneYcVerifaceDeviceLocationByCondition(ycVerifaceDeviceLocation);
    }
}
