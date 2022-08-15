package com.yice.edu.cn.dm.controller.zyDevice;

import com.yice.edu.cn.common.pojo.dm.kqDevice.KqDeviceGroupPerson;
import com.yice.edu.cn.dm.service.zyDevice.KqDeviceGroupPersonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/kqDeviceGroupPerson")
@Api(value = "/kqDeviceGroupPerson",description = "模块")
public class KqDeviceGroupPersonController {
    @Autowired
    private KqDeviceGroupPersonService kqDeviceGroupPersonService;

    @GetMapping("/findKqDeviceGroupPersonById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public KqDeviceGroupPerson findKqDeviceGroupPersonById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return kqDeviceGroupPersonService.findKqDeviceGroupPersonById(id);
    }

    @PostMapping("/saveKqDeviceGroupPerson")
    @ApiOperation(value = "保存", notes = "返回对象")
    public KqDeviceGroupPerson saveKqDeviceGroupPerson(
            @ApiParam(value = "对象", required = true)
            @RequestBody KqDeviceGroupPerson kqDeviceGroupPerson){
        kqDeviceGroupPersonService.saveKqDeviceGroupPerson(kqDeviceGroupPerson);
        return kqDeviceGroupPerson;
    }

    @PostMapping("/findKqDeviceGroupPersonListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<KqDeviceGroupPerson> findKqDeviceGroupPersonListByCondition(
            @ApiParam(value = "对象")
            @RequestBody KqDeviceGroupPerson kqDeviceGroupPerson){
        return kqDeviceGroupPersonService.findKqDeviceGroupPersonListByCondition(kqDeviceGroupPerson);
    }
    @PostMapping("/findKqDeviceGroupPersonCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findKqDeviceGroupPersonCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody KqDeviceGroupPerson kqDeviceGroupPerson){
        return kqDeviceGroupPersonService.findKqDeviceGroupPersonCountByCondition(kqDeviceGroupPerson);
    }

    @PostMapping("/updateKqDeviceGroupPerson")
    @ApiOperation(value = "修改", notes = "对象必传")
    public void updateKqDeviceGroupPerson(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody KqDeviceGroupPerson kqDeviceGroupPerson){
        kqDeviceGroupPersonService.updateKqDeviceGroupPerson(kqDeviceGroupPerson);
    }

    @GetMapping("/deleteKqDeviceGroupPerson/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteKqDeviceGroupPerson(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        kqDeviceGroupPersonService.deleteKqDeviceGroupPerson(id);
    }
    @PostMapping("/deleteKqDeviceGroupPersonByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deleteKqDeviceGroupPersonByCondition(
            @ApiParam(value = "对象")
            @RequestBody KqDeviceGroupPerson kqDeviceGroupPerson){
        kqDeviceGroupPersonService.deleteKqDeviceGroupPersonByCondition(kqDeviceGroupPerson);
    }
    @PostMapping("/findOneKqDeviceGroupPersonByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public KqDeviceGroupPerson findOneKqDeviceGroupPersonByCondition(
            @ApiParam(value = "对象")
            @RequestBody KqDeviceGroupPerson kqDeviceGroupPerson){
        return kqDeviceGroupPersonService.findOneKqDeviceGroupPersonByCondition(kqDeviceGroupPerson);
    }

    @PostMapping("/findPersonsBoundDevices")
    @ApiOperation(value = "根据人员列表查找列表中每个人员已经绑定的所有设备", notes = "返回单个,没有时为空")
    public List<KqDeviceGroupPerson> findPersonsBoundDevices(
            @ApiParam(value = "对象")
            @RequestBody KqDeviceGroupPerson kqDeviceGroupPerson){
        return kqDeviceGroupPersonService.findPersonsBoundDevices(kqDeviceGroupPerson);
    }

    @PostMapping("/batchSaveKqDeviceGroupPerson")
    @ApiOperation(value = "批量保存", notes = "返回单个,没有时为空")
    public void batchSaveKqDeviceGroupPerson(
            @ApiParam(value = "对象")
            @RequestBody List<KqDeviceGroupPerson> kqDeviceGroupPersonList){
         kqDeviceGroupPersonService.batchSaveKqDeviceGroupPerson(kqDeviceGroupPersonList);
    }
}
