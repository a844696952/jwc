package com.yice.edu.cn.ecc.controller.dmCamera;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.dm.dmCamera.DmCamera;
import com.yice.edu.cn.common.pojo.dm.dmClassCardArea.DmClassCardArea;
import com.yice.edu.cn.ecc.service.dmCamera.DmCameraService;
import com.yice.edu.cn.ecc.service.dmCamera.DmClassCardAreaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.yice.edu.cn.ecc.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/dmCamera")
@Api(value = "/dmCamera",description = "云班牌摄像头管理模块")
public class DmCameraController {
    @Autowired
    private DmCameraService dmCameraService;
    @Autowired
    DmClassCardAreaService dmClassCardAreaService;

    @GetMapping("/update/findDmCameraById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找云班牌摄像头管理", notes = "返回响应对象", response=DmCamera.class)
    public ResponseJson findDmCameraById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        DmCamera dmCamera=dmCameraService.findDmCameraById(id);
        return new ResponseJson(dmCamera);
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

    @GetMapping("/findDmCameraListByCondition/{deviceId}")
    @ApiOperation(value = "根据条件查找云班牌摄像头管理列表，不分页查询,只需要传设备MAC地址deviceId", notes = "返回响应对象,不包含总条数", response=DmCamera.class)
    public ResponseJson findDmCameraListByCondition(
            @ApiParam(value = "设备编号deviceId（mac地址）")
            @Validated
            @PathVariable("deviceId")String deviceId){
        if(StringUtils.isEmpty(deviceId)){
            return new ResponseJson(false,"设备MAC地址不能为空");
        }else{
            DmClassCardArea dmClassCardArea=new DmClassCardArea();
            dmClassCardArea.setDeviceId(deviceId);
            DmClassCardArea d= dmClassCardAreaService.findOneDmClassCardAreaByCondition(dmClassCardArea);

            if (Objects.isNull(d)) {
                return new ResponseJson(false,"入校设备班牌MAC地址填写错误或不存在，请联系管理员");
            }


            if(Objects.nonNull(d)){
                DmCamera dmCamera = new DmCamera();
                dmCamera.setPager(new Pager().setPaging(false));
                dmCamera.setSchoolId(mySchoolId());
                dmCamera.setAreaId(d.getAreaId());
                List<DmCamera> data=dmCameraService.findDmCameraListByCondition(dmCamera);
                return new ResponseJson(data);
            }
           return new ResponseJson(new ArrayList<>());
        }
    }

}
