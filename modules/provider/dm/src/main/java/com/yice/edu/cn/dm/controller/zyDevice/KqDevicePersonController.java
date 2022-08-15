package com.yice.edu.cn.dm.controller.zyDevice;

import com.yice.edu.cn.common.pojo.dm.kqDevice.KqDevicePerson;
import com.yice.edu.cn.dm.service.zyDevice.KqDevicePersonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/kqDevicePerson")
@Api(value = "/kqDevicePerson",description = "考勤设备关联分组表模块")
public class KqDevicePersonController {
    @Autowired
    private KqDevicePersonService kqDevicePersonService;

    @GetMapping("/findKqDevicePersonById/{id}")
    @ApiOperation(value = "通过id查找考勤设备人员关联表", notes = "返回考勤设备人员关联表对象")
    public KqDevicePerson findKqDevicePersonById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return kqDevicePersonService.findKqDevicePersonById(id);
    }

    @PostMapping("/saveKqDevicePerson")
    @ApiOperation(value = "保存考勤设备人员关联表", notes = "返回考勤设备人员关联表对象")
    public KqDevicePerson saveKqDevicePerson(
            @ApiParam(value = "考勤设备人员关联表对象", required = true)
            @RequestBody KqDevicePerson kqDevicePerson){
        kqDevicePersonService.saveKqDevicePerson(kqDevicePerson);
        return kqDevicePerson;
    }

    @PostMapping("/findKqDevicePersonListByCondition")
    @ApiOperation(value = "根据条件查找考勤设备人员关联表列表", notes = "返回考勤设备人员关联表列表")
    public List<KqDevicePerson> findKqDevicePersonListByCondition(
            @ApiParam(value = "考勤设备人员关联表对象")
            @RequestBody KqDevicePerson kqDevicePerson){
        return kqDevicePersonService.findKqDevicePersonListByCondition(kqDevicePerson);
    }
    @PostMapping("/findKqDevicePersonCountByCondition")
    @ApiOperation(value = "根据条件查找考勤设备人员关联表列表个数", notes = "返回考勤设备人员关联表总个数")
    public long findKqDevicePersonCountByCondition(
            @ApiParam(value = "考勤设备人员关联表对象")
            @RequestBody KqDevicePerson kqDevicePerson){
        return kqDevicePersonService.findKqDevicePersonCountByCondition(kqDevicePerson);
    }

    @PostMapping("/updateKqDevicePerson")
    @ApiOperation(value = "修改考勤设备人员关联表", notes = "考勤设备人员关联表对象必传")
    public void updateKqDevicePerson(
            @ApiParam(value = "考勤设备人员关联表对象,对象属性不为空则修改", required = true)
            @RequestBody KqDevicePerson kqDevicePerson){
        kqDevicePersonService.updateKqDevicePerson(kqDevicePerson);
    }

    @GetMapping("/deleteKqDevicePerson/{id}")
    @ApiOperation(value = "通过id删除考勤设备人员关联表")
    public void deleteKqDevicePerson(
            @ApiParam(value = "考勤设备人员关联表对象", required = true)
            @PathVariable String id){
        kqDevicePersonService.deleteKqDevicePerson(id);
    }
    @PostMapping("/deleteKqDevicePersonByCondition")
    @ApiOperation(value = "根据条件删除考勤设备人员关联表")
    public void deleteKqDevicePersonByCondition(
            @ApiParam(value = "考勤设备人员关联表对象")
            @RequestBody KqDevicePerson kqDevicePerson){
        kqDevicePersonService.deleteKqDevicePersonByCondition(kqDevicePerson);
    }
    @PostMapping("/findOneKqDevicePersonByCondition")
    @ApiOperation(value = "根据条件查找单个考勤设备人员关联表,结果必须为单条数据", notes = "返回单个考勤设备人员关联表,没有时为空")
    public KqDevicePerson findOneKqDevicePersonByCondition(
            @ApiParam(value = "考勤设备人员关联表对象")
            @RequestBody KqDevicePerson kqDevicePerson){
        return kqDevicePersonService.findOneKqDevicePersonByCondition(kqDevicePerson);
    }
}
