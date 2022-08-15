package com.yice.edu.cn.tap.controller.appVersionControl;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.appVersionControl.AppVersionControl;
import com.yice.edu.cn.tap.service.appVersionControl.AppVersionControlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@RestController
@RequestMapping("/appVersionControl")
@Api(value = "/appVersionControl",description = "模块")
public class AppVersionControlController {
    @Autowired
    private AppVersionControlService appVersionControlService;

   /* @PostMapping("/saveAppVersionControl")
    @ApiOperation(value = "保存对象", notes = "返回保存好的对象", response=AppVersionControl.class)
    public ResponseJson saveAppVersionControl(
            @ApiParam(value = "对象", required = true)
            @RequestBody AppVersionControl appVersionControl){
        AppVersionControl s=appVersionControlService.saveAppVersionControl(appVersionControl);
        return new ResponseJson(s);
    }


    @PostMapping("/updateAppVersionControl")
    @ApiOperation(value = "修改对象", notes = "返回响应对象")
    public ResponseJson updateAppVersionControl(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody AppVersionControl appVersionControl){
        appVersionControlService.updateAppVersionControl(appVersionControl);
        return new ResponseJson();
    }

    @GetMapping("/findAppVersionControlById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象", response=AppVersionControl.class)
    public ResponseJson findAppVersionControlById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        AppVersionControl appVersionControl=appVersionControlService.findAppVersionControlById(id);
        return new ResponseJson(appVersionControl);
    }

    @GetMapping("/deleteAppVersionControl/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteAppVersionControl(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        appVersionControlService.deleteAppVersionControl(id);
        return new ResponseJson();
    }


    @PostMapping("/findAppVersionControlListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数", response=AppVersionControl.class)
    public ResponseJson findAppVersionControlListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody AppVersionControl appVersionControl){
        List<AppVersionControl> data=appVersionControlService.findAppVersionControlListByCondition(appVersionControl);
        return new ResponseJson(data);
    }
*/

    @PostMapping("/ingore/findVersionControlUptodate")
    @ApiOperation(value = "根据App类型 type（IOS1或安卓2）,应用端 item（教师端1或者家长端2）,versionNumber版本号查询是否需要更新",notes = "返回单个或者null")
    public ResponseJson findVersionControlUptodate(
            @ApiParam(value = "对象")
            @RequestBody AppVersionControl appVersionControl
    ){
        AppVersionControl data =  appVersionControlService.findVersionControlUptodate(appVersionControl);
        return new ResponseJson(data);
    }
}
