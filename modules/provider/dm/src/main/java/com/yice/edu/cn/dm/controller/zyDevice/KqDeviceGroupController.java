package com.yice.edu.cn.dm.controller.zyDevice;

import com.yice.edu.cn.common.pojo.dm.kqDevice.KqDeviceGroup;
import com.yice.edu.cn.dm.service.zyDevice.KqDeviceGroupService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/kqDeviceGroup")
@Api(value = "/kqDeviceGroup",description = "考勤设备分组类型模块")
public class KqDeviceGroupController {
    @Autowired
    private KqDeviceGroupService kqDeviceGroupService;

    @GetMapping("/findKqDeviceGroupById/{id}")
    @ApiOperation(value = "通过id查找考勤设备分组类型", notes = "返回考勤设备分组类型对象")
    public KqDeviceGroup findKqDeviceGroupById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return kqDeviceGroupService.findKqDeviceGroupById(id);
    }

    @PostMapping("/saveKqDeviceGroup")
    @ApiOperation(value = "保存考勤设备分组类型", notes = "返回考勤设备分组类型对象")
    public KqDeviceGroup saveKqDeviceGroup(
            @ApiParam(value = "考勤设备分组类型对象", required = true)
            @RequestBody KqDeviceGroup kqDeviceGroup){
        kqDeviceGroupService.saveKqDeviceGroup(kqDeviceGroup);
        return kqDeviceGroup;
    }

    @PostMapping("/findKqDeviceGroupListByCondition")
    @ApiOperation(value = "根据条件查找考勤设备分组类型列表", notes = "返回考勤设备分组类型列表")
    public List<KqDeviceGroup> findKqDeviceGroupListByCondition(
            @ApiParam(value = "考勤设备分组类型对象")
            @RequestBody KqDeviceGroup kqDeviceGroup){
        return kqDeviceGroupService.findKqDeviceGroupListByCondition(kqDeviceGroup);
    }
    @PostMapping("/findKqDeviceGroupCountByCondition")
    @ApiOperation(value = "根据条件查找考勤设备分组类型列表个数", notes = "返回考勤设备分组类型总个数")
    public long findKqDeviceGroupCountByCondition(
            @ApiParam(value = "考勤设备分组类型对象")
            @RequestBody KqDeviceGroup kqDeviceGroup){
        return kqDeviceGroupService.findKqDeviceGroupCountByCondition(kqDeviceGroup);
    }

    @PostMapping("/updateKqDeviceGroup")
    @ApiOperation(value = "修改考勤设备分组类型", notes = "考勤设备分组类型对象必传")
    public void updateKqDeviceGroup(
            @ApiParam(value = "考勤设备分组类型对象,对象属性不为空则修改", required = true)
            @RequestBody KqDeviceGroup kqDeviceGroup){
        kqDeviceGroupService.updateKqDeviceGroup(kqDeviceGroup);
    }

    @GetMapping("/deleteKqDeviceGroup/{id}")
    @ApiOperation(value = "通过id删除考勤设备分组类型")
    public void deleteKqDeviceGroup(
            @ApiParam(value = "考勤设备分组类型对象", required = true)
            @PathVariable String id){
        kqDeviceGroupService.deleteKqDeviceGroup(id);
    }
    @PostMapping("/deleteKqDeviceGroupByCondition")
    @ApiOperation(value = "根据条件删除考勤设备分组类型")
    public void deleteKqDeviceGroupByCondition(
            @ApiParam(value = "考勤设备分组类型对象")
            @RequestBody KqDeviceGroup kqDeviceGroup){
        kqDeviceGroupService.deleteKqDeviceGroupByCondition(kqDeviceGroup);
    }
    @PostMapping("/findOneKqDeviceGroupByCondition")
    @ApiOperation(value = "根据条件查找单个考勤设备分组类型,结果必须为单条数据", notes = "返回单个考勤设备分组类型,没有时为空")
    public KqDeviceGroup findOneKqDeviceGroupByCondition(
            @ApiParam(value = "考勤设备分组类型对象")
            @RequestBody KqDeviceGroup kqDeviceGroup){
        return kqDeviceGroupService.findOneKqDeviceGroupByCondition(kqDeviceGroup);
    }


    @PostMapping("/updateKqDeviceGroupAndDeviceType")
    @ApiOperation(value = "修改考勤设备分组类型，并修改设备里的分组类型", notes = "考勤设备分组类型对象必传")
    public void updateKqDeviceGroupAndDeviceType(
            @ApiParam(value = "考勤设备分组类型对象,对象属性不为空则修改", required = true)
            @RequestBody KqDeviceGroup kqDeviceGroup){
        kqDeviceGroupService.updateKqDeviceGroupAndDeviceType(kqDeviceGroup);
    }

}
