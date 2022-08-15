package com.yice.edu.cn.dm.controller.dmClassCardArea;

import com.yice.edu.cn.common.pojo.dm.dmClassCardArea.DmClassCardArea;
import com.yice.edu.cn.dm.service.dmClassCardArea.DmClassCardAreaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dmClassCardArea")
@Api(value = "/dmClassCardArea",description = "电子班牌场地设备管理模块")
public class DmClassCardAreaController {
    @Autowired
    private DmClassCardAreaService dmClassCardAreaService;

    @GetMapping("/findDmClassCardAreaById/{id}")
    @ApiOperation(value = "通过id查找电子班牌场地设备管理", notes = "返回电子班牌场地设备管理对象")
    public DmClassCardArea findDmClassCardAreaById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return dmClassCardAreaService.findDmClassCardAreaById(id);
    }

    @PostMapping("/saveDmClassCardArea")
    @ApiOperation(value = "保存电子班牌场地设备管理", notes = "返回电子班牌场地设备管理对象")
    public DmClassCardArea saveDmClassCardArea(
            @ApiParam(value = "电子班牌场地设备管理对象", required = true)
            @RequestBody DmClassCardArea dmClassCardArea){
        dmClassCardAreaService.saveDmClassCardArea(dmClassCardArea);
        return dmClassCardArea;
    }

    @PostMapping("/findDmClassCardAreaListByCondition")
    @ApiOperation(value = "根据条件查找电子班牌场地设备管理列表", notes = "返回电子班牌场地设备管理列表")
    public List<DmClassCardArea> findDmClassCardAreaListByCondition(
            @ApiParam(value = "电子班牌场地设备管理对象")
            @RequestBody DmClassCardArea dmClassCardArea){
        return dmClassCardAreaService.findDmClassCardAreaListByCondition(dmClassCardArea);
    }
    @PostMapping("/findDmClassCardAreaCountByCondition")
    @ApiOperation(value = "根据条件查找电子班牌场地设备管理列表个数", notes = "返回电子班牌场地设备管理总个数")
    public long findDmClassCardAreaCountByCondition(
            @ApiParam(value = "电子班牌场地设备管理对象")
            @RequestBody DmClassCardArea dmClassCardArea){
        return dmClassCardAreaService.findDmClassCardAreaCountByCondition(dmClassCardArea);
    }

    @PostMapping("/updateDmClassCardArea")
    @ApiOperation(value = "修改电子班牌场地设备管理", notes = "电子班牌场地设备管理对象必传")
    public void updateDmClassCardArea(
            @ApiParam(value = "电子班牌场地设备管理对象,对象属性不为空则修改", required = true)
            @RequestBody DmClassCardArea dmClassCardArea){
        dmClassCardAreaService.updateDmClassCardArea(dmClassCardArea);
    }

    @GetMapping("/deleteDmClassCardArea/{id}")
    @ApiOperation(value = "通过id删除电子班牌场地设备管理")
    public void deleteDmClassCardArea(
            @ApiParam(value = "电子班牌场地设备管理对象", required = true)
            @PathVariable String id){
        dmClassCardAreaService.deleteDmClassCardArea(id);
    }
    @PostMapping("/deleteDmClassCardAreaByCondition")
    @ApiOperation(value = "根据条件删除电子班牌场地设备管理")
    public void deleteDmClassCardAreaByCondition(
            @ApiParam(value = "电子班牌场地设备管理对象")
            @RequestBody DmClassCardArea dmClassCardArea){
        dmClassCardAreaService.deleteDmClassCardAreaByCondition(dmClassCardArea);
    }
    @PostMapping("/findOneDmClassCardAreaByCondition")
    @ApiOperation(value = "根据条件查找单个电子班牌场地设备管理,结果必须为单条数据", notes = "返回单个电子班牌场地设备管理,没有时为空")
    public DmClassCardArea findOneDmClassCardAreaByCondition(
            @ApiParam(value = "电子班牌场地设备管理对象")
            @RequestBody DmClassCardArea dmClassCardArea){
        return dmClassCardAreaService.findOneDmClassCardAreaByCondition(dmClassCardArea);
    }
}
