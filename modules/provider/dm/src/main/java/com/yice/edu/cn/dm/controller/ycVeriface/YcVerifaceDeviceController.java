package com.yice.edu.cn.dm.controller.ycVeriface;

import com.yice.edu.cn.common.pojo.dm.ycVeriface.device.YcVerifaceDevice;
import com.yice.edu.cn.dm.service.ycVeriface.YcVerifaceDeviceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ycVerifaceDevice")
@Api(value = "/ycVerifaceDevice",description = "模块")
public class YcVerifaceDeviceController {
    @Autowired
    private YcVerifaceDeviceService ycVerifaceDeviceService;

    @GetMapping("/findYcVerifaceDeviceById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public YcVerifaceDevice findYcVerifaceDeviceById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return ycVerifaceDeviceService.findYcVerifaceDeviceById(id);
    }

    @PostMapping("/saveYcVerifaceDevice")
    @ApiOperation(value = "保存", notes = "返回对象")
    public YcVerifaceDevice saveYcVerifaceDevice(
            @ApiParam(value = "对象", required = true)
            @RequestBody YcVerifaceDevice ycVerifaceDevice){
        ycVerifaceDeviceService.saveYcVerifaceDevice(ycVerifaceDevice);
        return ycVerifaceDevice;
    }

    @PostMapping("/findYcVerifaceDeviceListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<YcVerifaceDevice> findYcVerifaceDeviceListByCondition(
            @ApiParam(value = "对象")
            @RequestBody YcVerifaceDevice ycVerifaceDevice){
        return ycVerifaceDeviceService.findYcVerifaceDeviceListByCondition(ycVerifaceDevice);
    }
    @PostMapping("/findYcVerifaceDeviceCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findYcVerifaceDeviceCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody YcVerifaceDevice ycVerifaceDevice){
        return ycVerifaceDeviceService.findYcVerifaceDeviceCountByCondition(ycVerifaceDevice);
    }

    @PostMapping("/updateYcVerifaceDevice")
    @ApiOperation(value = "修改", notes = "对象必传")
    public void updateYcVerifaceDevice(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody YcVerifaceDevice ycVerifaceDevice){
        ycVerifaceDeviceService.updateYcVerifaceDevice(ycVerifaceDevice);
    }

    @GetMapping("/deleteYcVerifaceDevice/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteYcVerifaceDevice(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        ycVerifaceDeviceService.deleteYcVerifaceDevice(id);
    }
    @PostMapping("/deleteYcVerifaceDeviceByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deleteYcVerifaceDeviceByCondition(
            @ApiParam(value = "对象")
            @RequestBody YcVerifaceDevice ycVerifaceDevice){
        ycVerifaceDeviceService.deleteYcVerifaceDeviceByCondition(ycVerifaceDevice);
    }
    @PostMapping("/findOneYcVerifaceDeviceByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public YcVerifaceDevice findOneYcVerifaceDeviceByCondition(
            @ApiParam(value = "对象")
            @RequestBody YcVerifaceDevice ycVerifaceDevice){
        return ycVerifaceDeviceService.findOneYcVerifaceDeviceByCondition(ycVerifaceDevice);
    }


    @GetMapping("/findSchoolInAndOutDevice/{schoolId}")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    List<YcVerifaceDevice> findSchoolInAndOutDevice(String schoolId){
        return ycVerifaceDeviceService.findSchoolInAndOutDevice(schoolId);
    }
}
