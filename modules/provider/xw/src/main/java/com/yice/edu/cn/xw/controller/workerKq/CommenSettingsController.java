package com.yice.edu.cn.xw.controller.workerKq;

import com.yice.edu.cn.common.pojo.xw.workerKq.CommenSettings;
import com.yice.edu.cn.xw.service.workerKq.CommenSettingsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/commenSettings")
@Api(value = "/commenSettings",description = "模块")
public class CommenSettingsController {
    @Autowired
    private CommenSettingsService commenSettingsService;

    @GetMapping("/findCommenSettingsById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public CommenSettings findCommenSettingsById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return commenSettingsService.findCommenSettingsById(id);
    }

    @PostMapping("/saveCommenSettings")
    @ApiOperation(value = "保存", notes = "返回对象")
    public CommenSettings saveCommenSettings(
            @ApiParam(value = "对象", required = true)
            @RequestBody CommenSettings commenSettings){
        commenSettingsService.saveCommenSettings(commenSettings);
        return commenSettings;
    }

    @PostMapping("/findCommenSettingsListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<CommenSettings> findCommenSettingsListByCondition(
            @ApiParam(value = "对象")
            @RequestBody CommenSettings commenSettings){
        return commenSettingsService.findCommenSettingsListByCondition(commenSettings);
    }
    @PostMapping("/findCommenSettingsCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findCommenSettingsCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody CommenSettings commenSettings){
        return commenSettingsService.findCommenSettingsCountByCondition(commenSettings);
    }

    @PostMapping("/updateCommenSettings")
    @ApiOperation(value = "修改", notes = "对象必传")
    public void updateCommenSettings(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody CommenSettings commenSettings){
        commenSettingsService.updateCommenSettings(commenSettings);
    }

    @GetMapping("/deleteCommenSettings/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteCommenSettings(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        commenSettingsService.deleteCommenSettings(id);
    }
    @PostMapping("/deleteCommenSettingsByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deleteCommenSettingsByCondition(
            @ApiParam(value = "对象")
            @RequestBody CommenSettings commenSettings){
        commenSettingsService.deleteCommenSettingsByCondition(commenSettings);
    }
    @PostMapping("/findOneCommenSettingsByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public CommenSettings findOneCommenSettingsByCondition(
            @ApiParam(value = "对象")
            @RequestBody CommenSettings commenSettings){
        return commenSettingsService.findOneCommenSettingsByCondition(commenSettings);
    }
}
