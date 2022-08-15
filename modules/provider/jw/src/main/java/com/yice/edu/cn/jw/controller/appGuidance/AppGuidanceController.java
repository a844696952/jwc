package com.yice.edu.cn.jw.controller.appGuidance;

import com.yice.edu.cn.common.pojo.jw.appGuidance.AppGuidance;
import com.yice.edu.cn.jw.service.appGuidance.AppGuidanceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appGuidance")
@Api(value = "/appGuidance",description = "模块")
public class AppGuidanceController {
    @Autowired
    private AppGuidanceService appGuidanceService;

    @GetMapping("/findAppGuidanceById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public AppGuidance findAppGuidanceById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return appGuidanceService.findAppGuidanceById(id);
    }

    @PostMapping("/saveAppGuidance")
    @ApiOperation(value = "保存", notes = "返回对象")
    public AppGuidance saveAppGuidance(
            @ApiParam(value = "对象", required = true)
            @RequestBody AppGuidance appGuidance){
        appGuidanceService.saveAppGuidance(appGuidance);
        return appGuidance;
    }

    @PostMapping("/findAppGuidanceListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<AppGuidance> findAppGuidanceListByCondition(
            @ApiParam(value = "对象")
            @RequestBody AppGuidance appGuidance){
        return appGuidanceService.findAppGuidanceListByCondition(appGuidance);
    }
    @PostMapping("/findAppGuidanceCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findAppGuidanceCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody AppGuidance appGuidance){
        return appGuidanceService.findAppGuidanceCountByCondition(appGuidance);
    }

    @PostMapping("/updateAppGuidance")
    @ApiOperation(value = "修改", notes = "对象必传")
    public void updateAppGuidance(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody AppGuidance appGuidance){
        appGuidanceService.updateAppGuidance(appGuidance);
    }

    @GetMapping("/deleteAppGuidance/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteAppGuidance(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        appGuidanceService.deleteAppGuidance(id);
    }
    @PostMapping("/deleteAppGuidanceByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deleteAppGuidanceByCondition(
            @ApiParam(value = "对象")
            @RequestBody AppGuidance appGuidance){
        appGuidanceService.deleteAppGuidanceByCondition(appGuidance);
    }
    @PostMapping("/findOneAppGuidanceByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public AppGuidance findOneAppGuidanceByCondition(
            @ApiParam(value = "对象")
            @RequestBody AppGuidance appGuidance){
        return appGuidanceService.findOneAppGuidanceByCondition(appGuidance);
    }
}
