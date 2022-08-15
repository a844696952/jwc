package com.yice.edu.cn.jw.controller.appVersionControl;

import com.yice.edu.cn.common.pojo.jw.appVersionControl.AppVersionControl;
import com.yice.edu.cn.jw.service.appVersionControl.AppVersionControlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appVersionControl")
@Api(value = "/appVersionControl",description = "模块")
public class AppVersionControlController {
    @Autowired
    private AppVersionControlService appVersionControlService;

    @GetMapping("/findAppVersionControlById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public AppVersionControl findAppVersionControlById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return appVersionControlService.findAppVersionControlById(id);
    }

    @PostMapping("/saveAppVersionControl")
    @ApiOperation(value = "保存", notes = "返回对象")
    public String saveAppVersionControl(
            @ApiParam(value = "对象", required = true)
            @RequestBody AppVersionControl appVersionControl){
        String message = appVersionControlService.saveAppVersionControl(appVersionControl);
        return message;
    }

    @PostMapping("/findAppVersionControlListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<AppVersionControl> findAppVersionControlListByCondition(
            @ApiParam(value = "对象")
            @RequestBody AppVersionControl appVersionControl){
        return appVersionControlService.findAppVersionControlListByCondition(appVersionControl);
    }
    @PostMapping("/findAppVersionControlCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findAppVersionControlCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody AppVersionControl appVersionControl){
        return appVersionControlService.findAppVersionControlCountByCondition(appVersionControl);
    }

    @PostMapping("/updateAppVersionControl")
    @ApiOperation(value = "修改", notes = "对象必传")
    public void updateAppVersionControl(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody AppVersionControl appVersionControl){
        appVersionControlService.updateAppVersionControl(appVersionControl);
    }

    @GetMapping("/deleteAppVersionControl/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteAppVersionControl(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        appVersionControlService.deleteAppVersionControl(id);
    }
    @PostMapping("/deleteAppVersionControlByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deleteAppVersionControlByCondition(
            @ApiParam(value = "对象")
            @RequestBody AppVersionControl appVersionControl){
        appVersionControlService.deleteAppVersionControlByCondition(appVersionControl);
    }
    @PostMapping("/findOneAppVersionControlByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public AppVersionControl findOneAppVersionControlByCondition(
            @ApiParam(value = "对象")
            @RequestBody AppVersionControl appVersionControl){
        return appVersionControlService.findOneAppVersionControlByCondition(appVersionControl);
    }

    @PostMapping("/findVersionControlUptodate")
    @ApiOperation(value = "根据App类型（IOS1或安卓2）,应用端（教师端1或者家长端2）,版本号查询是否需要更新",notes = "返回单个或者null")
    public AppVersionControl findVersionControlUptodate(
            @ApiParam(value = "对象")
            @RequestBody AppVersionControl appVersionControl
    ){
        return appVersionControlService.findVersionControlUptodate(appVersionControl);
    }

}
