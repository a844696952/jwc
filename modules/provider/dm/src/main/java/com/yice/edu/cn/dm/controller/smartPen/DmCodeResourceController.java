package com.yice.edu.cn.dm.controller.smartPen;

import com.yice.edu.cn.common.pojo.dm.smartPen.DmCodeResource;
import com.yice.edu.cn.dm.service.smartPen.DmCodeResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dmCodeResource")
@Api(value = "/dmCodeResource", description = "设备管理-智能笔-铺码资源表模块")
public class DmCodeResourceController {
    @Autowired
    private DmCodeResourceService dmCodeResourceService;

    @GetMapping("/findDmCodeResourceById/{id}")
    @ApiOperation(value = "通过id查找设备管理-智能笔-铺码资源表", notes = "返回设备管理-智能笔-铺码资源表对象")
    public DmCodeResource findDmCodeResourceById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id) {
        return dmCodeResourceService.findDmCodeResourceById(id);
    }

    @PostMapping("/saveDmCodeResource")
    @ApiOperation(value = "保存设备管理-智能笔-铺码资源表", notes = "返回设备管理-智能笔-铺码资源表对象")
    public DmCodeResource saveDmCodeResource(
            @ApiParam(value = "设备管理-智能笔-铺码资源表对象", required = true)
            @RequestBody DmCodeResource dmCodeResource) {
        dmCodeResourceService.saveDmCodeResource(dmCodeResource);
        return dmCodeResource;
    }

    @PostMapping("/findDmCodeResourceListByCondition")
    @ApiOperation(value = "根据条件查找设备管理-智能笔-铺码资源表列表", notes = "返回设备管理-智能笔-铺码资源表列表")
    public List<DmCodeResource> findDmCodeResourceListByCondition(
            @ApiParam(value = "设备管理-智能笔-铺码资源表对象")
            @RequestBody DmCodeResource dmCodeResource) {
        return dmCodeResourceService.findDmCodeResourceListByCondition(dmCodeResource);
    }

    @PostMapping("/findDmCodeResourceCountByCondition")
    @ApiOperation(value = "根据条件查找设备管理-智能笔-铺码资源表列表个数", notes = "返回设备管理-智能笔-铺码资源表总个数")
    public long findDmCodeResourceCountByCondition(
            @ApiParam(value = "设备管理-智能笔-铺码资源表对象")
            @RequestBody DmCodeResource dmCodeResource) {
        return dmCodeResourceService.findDmCodeResourceCountByCondition(dmCodeResource);
    }

    @PostMapping("/updateDmCodeResource")
    @ApiOperation(value = "修改设备管理-智能笔-铺码资源表", notes = "设备管理-智能笔-铺码资源表对象必传")
    public void updateDmCodeResource(
            @ApiParam(value = "设备管理-智能笔-铺码资源表对象,对象属性不为空则修改", required = true)
            @RequestBody DmCodeResource dmCodeResource) {
        dmCodeResourceService.updateDmCodeResource(dmCodeResource);
    }

    @GetMapping("/deleteDmCodeResource/{id}")
    @ApiOperation(value = "通过id删除设备管理-智能笔-铺码资源表")
    public void deleteDmCodeResource(
            @ApiParam(value = "设备管理-智能笔-铺码资源表对象", required = true)
            @PathVariable String id) {
        dmCodeResourceService.deleteDmCodeResource(id);
    }

    @PostMapping("/deleteDmCodeResourceByCondition")
    @ApiOperation(value = "根据条件删除设备管理-智能笔-铺码资源表")
    public void deleteDmCodeResourceByCondition(
            @ApiParam(value = "设备管理-智能笔-铺码资源表对象")
            @RequestBody DmCodeResource dmCodeResource) {
        dmCodeResourceService.deleteDmCodeResourceByCondition(dmCodeResource);
    }

    @PostMapping("/findOneDmCodeResourceByCondition")
    @ApiOperation(value = "根据条件查找单个设备管理-智能笔-铺码资源表,结果必须为单条数据", notes = "返回单个设备管理-智能笔-铺码资源表,没有时为空")
    public DmCodeResource findOneDmCodeResourceByCondition(
            @ApiParam(value = "设备管理-智能笔-铺码资源表对象")
            @RequestBody DmCodeResource dmCodeResource) {
        return dmCodeResourceService.findOneDmCodeResourceByCondition(dmCodeResource);
    }

    @PostMapping("/batchSaveDmCodeResource")
    @ApiOperation(value = "批量保存设备管理-智能笔-铺码资源表", notes = "返回设备管理-智能笔-铺码资源表对象")
    public List<DmCodeResource> batchSaveDmCodeResource(
            @ApiParam(value = "多个设备管理-智能笔-铺码资源表对象", required = true)
            @RequestBody List<DmCodeResource> dmCodeResources) {
        dmCodeResourceService.batchSaveDmCodeResource(dmCodeResources);
        return dmCodeResources;
    }
}
