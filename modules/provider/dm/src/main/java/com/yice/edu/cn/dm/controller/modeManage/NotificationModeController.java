package com.yice.edu.cn.dm.controller.modeManage;

import com.yice.edu.cn.common.pojo.dm.modeManage.NotificationMode;
import com.yice.edu.cn.dm.service.modeManage.NotificationModeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notificationMode")
@Api(value = "/notificationMode",description = "通知模式表模块")
public class NotificationModeController {
    @Autowired
    private NotificationModeService notificationModeService;

    @GetMapping("/findNotificationModeById/{id}")
    @ApiOperation(value = "通过id查找通知模式表", notes = "返回通知模式表对象")
    public NotificationMode findNotificationModeById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return notificationModeService.findNotificationModeById(id);
    }

    @PostMapping("/saveNotificationMode")
    @ApiOperation(value = "保存通知模式表", notes = "返回通知模式表对象")
    public int saveNotificationMode(
            @ApiParam(value = "通知模式表对象", required = true)
            @RequestBody NotificationMode notificationMode){
        return notificationModeService.saveNotificationMode(notificationMode);

    }

    @PostMapping("/findNotificationModeListByCondition")
    @ApiOperation(value = "根据条件查找通知模式表列表", notes = "返回通知模式表列表")
    public List<NotificationMode> findNotificationModeListByCondition(
            @ApiParam(value = "通知模式表对象")
            @RequestBody NotificationMode notificationMode){
        return notificationModeService.findNotificationModeListByCondition(notificationMode);
    }
    @PostMapping("/findNotificationModeCountByCondition")
    @ApiOperation(value = "根据条件查找通知模式表列表个数", notes = "返回通知模式表总个数")
    public long findNotificationModeCountByCondition(
            @ApiParam(value = "通知模式表对象")
            @RequestBody NotificationMode notificationMode){
        return notificationModeService.findNotificationModeCountByCondition(notificationMode);
    }

    @PostMapping("/updateNotificationMode")
    @ApiOperation(value = "修改通知模式表", notes = "通知模式表对象必传")
    public int updateNotificationMode(
            @ApiParam(value = "通知模式表对象,对象属性不为空则修改", required = true)
            @RequestBody NotificationMode notificationMode){
        return notificationModeService.updateNotificationMode(notificationMode);
    }

    @GetMapping("/deleteNotificationMode/{id}")
    @ApiOperation(value = "通过id删除通知模式表")
    public void deleteNotificationMode(
            @ApiParam(value = "通知模式表对象", required = true)
            @PathVariable String id){
        notificationModeService.deleteNotificationMode(id);
    }
    @PostMapping("/deleteNotificationModeByCondition")
    @ApiOperation(value = "根据条件删除通知模式表")
    public void deleteNotificationModeByCondition(
            @ApiParam(value = "通知模式表对象")
            @RequestBody NotificationMode notificationMode){
        notificationModeService.deleteNotificationModeByCondition(notificationMode);
    }
    @PostMapping("/findOneNotificationModeByCondition")
    @ApiOperation(value = "根据条件查找单个通知模式表,结果必须为单条数据", notes = "返回单个通知模式表,没有时为空")
    public NotificationMode findOneNotificationModeByCondition(
            @ApiParam(value = "通知模式表对象")
            @RequestBody NotificationMode notificationMode){
        return notificationModeService.findOneNotificationModeByCondition(notificationMode);
    }


    @GetMapping("/findNotificationModeByNowTime/{schoolId}")
    @ApiOperation(value = "通过id查找通知模式表", notes = "返回通知模式表对象")
    public List<NotificationMode> findNotificationModeByNowTime(
            @ApiParam(value = "需要用到的mySchoolId", required = true)
            @PathVariable String schoolId){
        return notificationModeService.findNotificationModeByNowTime(schoolId);
    }

    @GetMapping("/closeNotificationMode/{id}")
    @ApiOperation(value = "关闭通知模式", notes = "通知模式对象必传")
    public void closeNotificationMode(
            @ApiParam(value = "通知模式表对象", required = true)
            @PathVariable String id) {
        notificationModeService.closeNotificationMode(id);
    }
}
