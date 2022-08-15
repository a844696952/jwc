package com.yice.edu.cn.jw.controller.systemPush;

import com.yice.edu.cn.common.pojo.jw.systemPush.SystemPush;
import com.yice.edu.cn.jw.service.systemPush.SystemPushService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/systemPush")
@Api(value = "/systemPush",description = "系统推送模块")
public class SystemPushController {
    @Autowired
    private SystemPushService systemPushService;

    @GetMapping("/findSystemPushById/{id}")
    @ApiOperation(value = "通过id查找系统推送", notes = "返回系统推送对象")
    public SystemPush findSystemPushById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return systemPushService.findSystemPushById(id);
    }

    @PostMapping("/saveSystemPush")
    @ApiOperation(value = "保存系统推送", notes = "返回系统推送对象")
    public SystemPush saveSystemPush(
            @ApiParam(value = "系统推送对象", required = true)
            @RequestBody SystemPush systemPush){
        systemPushService.saveSystemPush(systemPush);
        return systemPush;
    }

    @PostMapping("/findSystemPushListByCondition")
    @ApiOperation(value = "根据条件查找系统推送列表", notes = "返回系统推送列表")
    public List<SystemPush> findSystemPushListByCondition(
            @ApiParam(value = "系统推送对象")
            @RequestBody SystemPush systemPush){
        return systemPushService.findSystemPushListByCondition(systemPush);
    }
    @PostMapping("/findSystemPushCountByCondition")
    @ApiOperation(value = "根据条件查找系统推送列表个数", notes = "返回系统推送总个数")
    public long findSystemPushCountByCondition(
            @ApiParam(value = "系统推送对象")
            @RequestBody SystemPush systemPush){
        return systemPushService.findSystemPushCountByCondition(systemPush);
    }

    @PostMapping("/updateSystemPush")
    @ApiOperation(value = "修改系统推送", notes = "系统推送对象必传")
    public void updateSystemPush(
            @ApiParam(value = "系统推送对象,对象属性不为空则修改", required = true)
            @RequestBody SystemPush systemPush){
        systemPushService.updateSystemPush(systemPush);
    }

    @GetMapping("/deleteSystemPush/{id}")
    @ApiOperation(value = "通过id删除系统推送")
    public void deleteSystemPush(
            @ApiParam(value = "系统推送对象", required = true)
            @PathVariable String id){
        systemPushService.deleteSystemPush(id);
    }
    @PostMapping("/deleteSystemPushByCondition")
    @ApiOperation(value = "根据条件删除系统推送")
    public void deleteSystemPushByCondition(
            @ApiParam(value = "系统推送对象")
            @RequestBody SystemPush systemPush){
        systemPushService.deleteSystemPushByCondition(systemPush);
    }
    @PostMapping("/findOneSystemPushByCondition")
    @ApiOperation(value = "根据条件查找单个系统推送,结果必须为单条数据", notes = "返回单个系统推送,没有时为空")
    public SystemPush findOneSystemPushByCondition(
            @ApiParam(value = "系统推送对象")
            @RequestBody SystemPush systemPush){
        return systemPushService.findOneSystemPushByCondition(systemPush);
    }
}
