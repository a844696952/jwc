package com.yice.edu.cn.ecc.controller.modeManage;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.dm.modeManage.NotificationMode;
import com.yice.edu.cn.ecc.service.modeManage.NotificationModeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.yice.edu.cn.ecc.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/notificationMode")
@Api(value = "/notificationMode",description = "通知模式表模块")
public class NotificationModeController {
    @Autowired
    private NotificationModeService notificationModeService;

    @PostMapping("/saveNotificationMode")
    @ApiOperation(value = "保存通知模式表对象", notes = "返回保存好的通知模式表对象", response= NotificationMode.class)
    public ResponseJson saveNotificationMode(
            @ApiParam(value = "通知模式表对象", required = true)
            @RequestBody NotificationMode notificationMode){
       notificationMode.setSchoolId(mySchoolId());
        NotificationMode s=notificationModeService.saveNotificationMode(notificationMode);
        return new ResponseJson(s);
    }


    @PostMapping("/updateNotificationMode")
    @ApiOperation(value = "修改通知模式表对象", notes = "返回响应对象")
    public ResponseJson updateNotificationMode(
            @ApiParam(value = "被修改的通知模式表对象,对象属性不为空则修改", required = true)
            @RequestBody NotificationMode notificationMode){
        notificationModeService.updateNotificationMode(notificationMode);
        return new ResponseJson();
    }

    @GetMapping("/findNotificationModeById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找通知模式表", notes = "返回响应对象", response=NotificationMode.class)
    public ResponseJson findNotificationModeById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        NotificationMode notificationMode=notificationModeService.findNotificationModeById(id);
        return new ResponseJson(notificationMode);
    }

    @GetMapping("/deleteNotificationMode/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteNotificationMode(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        notificationModeService.deleteNotificationMode(id);
        return new ResponseJson();
    }


    @PostMapping("/findNotificationModeListByCondition")
    @ApiOperation(value = "根据条件查找通知模式表列表", notes = "返回响应对象,不包含总条数", response=NotificationMode.class)
    public ResponseJson findNotificationModeListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody NotificationMode notificationMode){
       notificationMode.setSchoolId(mySchoolId());
        List<NotificationMode> data=notificationModeService.findNotificationModeListByCondition(notificationMode);
        return new ResponseJson(data);
    }


    @GetMapping("/findNotificationModeByNowTime")
    @ApiOperation(value = "通过当前时间查找通知", notes = "返回响应对象", response=NotificationMode.class)
    public ResponseJson findNotificationModeByNowTime(
            ){
        List<NotificationMode> notificationMode=notificationModeService.findNotificationModeByNowTime(mySchoolId());
        return new ResponseJson(notificationMode);
    }



}
