package com.yice.edu.cn.osp.controller.dm.ycVeriface;

import cn.hutool.core.collection.CollectionUtil;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.dm.ycVeriface.device.YcVerifaceDevice;
import com.yice.edu.cn.common.pojo.dm.ycVeriface.deviceLocation.YcVerifaceDeviceLocation;
import com.yice.edu.cn.osp.service.dm.ycVeriface.YcVerifaceDeviceLocationService;
import com.yice.edu.cn.osp.service.dm.ycVeriface.YcVerifaceDeviceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/ycVerifaceDeviceLocation")
@Api(value = "/ycVerifaceDeviceLocation",description = "模块")
public class YcVerifaceDeviceLocationController {
    @Autowired
    private YcVerifaceDeviceLocationService ycVerifaceDeviceLocationService;
    @Autowired
    private YcVerifaceDeviceService ycVerifaceDeviceService;
    @PostMapping("/saveYcVerifaceDeviceLocation")
    @ApiOperation(value = "保存对象", notes = "返回保存好的对象", response= YcVerifaceDeviceLocation.class)
    public ResponseJson saveYcVerifaceDeviceLocation(
            @ApiParam(value = "对象", required = true)
            @RequestBody YcVerifaceDeviceLocation ycVerifaceDeviceLocation){
       ycVerifaceDeviceLocation.setSchoolId(mySchoolId());
        YcVerifaceDeviceLocation y1 = new YcVerifaceDeviceLocation();
        y1.setLocationName(ycVerifaceDeviceLocation.getLocationName());
        y1.setSchoolId(mySchoolId());
        List<YcVerifaceDeviceLocation> y1List = ycVerifaceDeviceLocationService.findYcVerifaceDeviceLocationListByCondition(y1);
        if (CollectionUtil.isNotEmpty(y1List)){
            return new ResponseJson(false,"请勿重复添加地址");
        }
        YcVerifaceDeviceLocation s=ycVerifaceDeviceLocationService.saveYcVerifaceDeviceLocation(ycVerifaceDeviceLocation);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findYcVerifaceDeviceLocationById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象", response=YcVerifaceDeviceLocation.class)
    public ResponseJson findYcVerifaceDeviceLocationById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        YcVerifaceDeviceLocation ycVerifaceDeviceLocation=ycVerifaceDeviceLocationService.findYcVerifaceDeviceLocationById(id);
        return new ResponseJson(ycVerifaceDeviceLocation);
    }

    @PostMapping("/update/updateYcVerifaceDeviceLocation")
    @ApiOperation(value = "修改对象", notes = "返回响应对象")
    public ResponseJson updateYcVerifaceDeviceLocation(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody YcVerifaceDeviceLocation ycVerifaceDeviceLocation){
        ycVerifaceDeviceLocationService.updateYcVerifaceDeviceLocation(ycVerifaceDeviceLocation);
        return new ResponseJson();
    }

    @GetMapping("/look/lookYcVerifaceDeviceLocationById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象", response=YcVerifaceDeviceLocation.class)
    public ResponseJson lookYcVerifaceDeviceLocationById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        YcVerifaceDeviceLocation ycVerifaceDeviceLocation=ycVerifaceDeviceLocationService.findYcVerifaceDeviceLocationById(id);
        return new ResponseJson(ycVerifaceDeviceLocation);
    }

    @PostMapping("/findYcVerifaceDeviceLocationsByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象", response=YcVerifaceDeviceLocation.class)
    public ResponseJson findYcVerifaceDeviceLocationsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody YcVerifaceDeviceLocation ycVerifaceDeviceLocation){
       ycVerifaceDeviceLocation.setSchoolId(mySchoolId());
        List<YcVerifaceDeviceLocation> data=ycVerifaceDeviceLocationService.findYcVerifaceDeviceLocationListByCondition(ycVerifaceDeviceLocation);
        long count=ycVerifaceDeviceLocationService.findYcVerifaceDeviceLocationCountByCondition(ycVerifaceDeviceLocation);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneYcVerifaceDeviceLocationByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空", response=YcVerifaceDeviceLocation.class)
    public ResponseJson findOneYcVerifaceDeviceLocationByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody YcVerifaceDeviceLocation ycVerifaceDeviceLocation){
        YcVerifaceDeviceLocation one=ycVerifaceDeviceLocationService.findOneYcVerifaceDeviceLocationByCondition(ycVerifaceDeviceLocation);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteYcVerifaceDeviceLocation/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteYcVerifaceDeviceLocation(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        YcVerifaceDevice ycVerifaceDevice = new YcVerifaceDevice();
        ycVerifaceDevice.setDeviceLocationId(id);
        long ycVerifaceDeviceCountByCondition = ycVerifaceDeviceService.findYcVerifaceDeviceCountByCondition(ycVerifaceDevice);
        if (ycVerifaceDeviceCountByCondition>0){
            return new ResponseJson(false,"该内容被引用了不允许删除！");
        }
        ycVerifaceDeviceLocationService.deleteYcVerifaceDeviceLocation(id);
        return new ResponseJson();
    }


    @PostMapping("/findYcVerifaceDeviceLocationListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数", response=YcVerifaceDeviceLocation.class)
    public ResponseJson findYcVerifaceDeviceLocationListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody YcVerifaceDeviceLocation ycVerifaceDeviceLocation){
       ycVerifaceDeviceLocation.setSchoolId(mySchoolId());
        List<YcVerifaceDeviceLocation> data=ycVerifaceDeviceLocationService.findYcVerifaceDeviceLocationListByCondition(ycVerifaceDeviceLocation);
        return new ResponseJson(data);
    }
    @GetMapping("/getYcVerifaceDeviceLocationListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数", response=YcVerifaceDeviceLocation.class)
    public ResponseJson getYcVerifaceDeviceLocationListByCondition(){
        YcVerifaceDeviceLocation ycVerifaceDeviceLocation = new YcVerifaceDeviceLocation();
        ycVerifaceDeviceLocation.setSchoolId(mySchoolId());
        List<YcVerifaceDeviceLocation> data=ycVerifaceDeviceLocationService.findYcVerifaceDeviceLocationListByCondition(ycVerifaceDeviceLocation);
        return new ResponseJson(data);
    }


}
