package com.yice.edu.cn.osp.controller.dm.modeManage;

import com.yice.edu.cn.common.annotation.EccJpush;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.dm.modeManage.NotificationMode;
import com.yice.edu.cn.common.pojo.ts.jpush.Extras;
import com.yice.edu.cn.osp.service.dm.modeManage.NotificationModeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

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
        if(Objects.isNull(notificationMode.getStartDate())&&Objects.isNull(notificationMode.getEndDate())){
            return new ResponseJson(false,"参数错误");
        }
        notificationMode.setSchoolId(mySchoolId());
        int count = notificationModeService.saveNotificationMode(notificationMode);
        return new ResponseJson(count);

    }

    @GetMapping("/update/findNotificationModeById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找通知模式表", notes = "返回响应对象", response=NotificationMode.class)
    public ResponseJson findNotificationModeById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        NotificationMode notificationMode=notificationModeService.findNotificationModeById(id);
        return new ResponseJson(notificationMode);
    }

    @PostMapping("/update/updateNotificationMode")
    @ApiOperation(value = "修改通知模式表对象", notes = "返回响应对象")
    public ResponseJson updateNotificationMode(
            @ApiParam(value = "被修改的通知模式表对象,对象属性不为空则修改", required = true)
            @RequestBody NotificationMode notificationMode){
        if(Objects.isNull(notificationMode.getStartDate())&&Objects.isNull(notificationMode.getEndDate())){
            return new ResponseJson(false,"参数错误");
        }
        notificationMode.setSchoolId(mySchoolId());
        int count = notificationModeService.updateNotificationMode(notificationMode);
        return new ResponseJson(count);
    }

    @GetMapping("/look/lookNotificationModeById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找通知模式表", notes = "返回响应对象", response=NotificationMode.class)
    public ResponseJson lookNotificationModeById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        NotificationMode notificationMode=notificationModeService.findNotificationModeById(id);
        return new ResponseJson(notificationMode);
    }

    @PostMapping("/findNotificationModesByCondition")
    @ApiOperation(value = "根据条件查找通知模式表", notes = "返回响应对象", response=NotificationMode.class)
    public ResponseJson findNotificationModesByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody NotificationMode notificationMode){
       notificationMode.setSchoolId(mySchoolId());
        List<NotificationMode> data=notificationModeService.findNotificationModeListByCondition(notificationMode);
        long count=notificationModeService.findNotificationModeCountByCondition(notificationMode);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneNotificationModeByCondition")
    @ApiOperation(value = "根据条件查找单个通知模式表,结果必须为单条数据", notes = "没有时返回空", response=NotificationMode.class)
    public ResponseJson findOneNotificationModeByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody NotificationMode notificationMode){
        NotificationMode one=notificationModeService.findOneNotificationModeByCondition(notificationMode);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteNotificationMode/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    @EccJpush(type = 0,content = "删除通知")
    public ResponseJson deleteNotificationMode(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        notificationModeService.deleteNotificationMode(id);
        return new ResponseJson();
    }

    @PostMapping("/deleteNotificationModeByCondition")
    @ApiOperation(value = "根据条件删除通知模式表")
    @EccJpush(type = 0,content = "删除通知")
    public ResponseJson deleteNotificationModeByCondition(
            @ApiParam(value = "通知模式表对象")
            @RequestBody NotificationMode notificationMode) {
        notificationModeService.deleteNotificationModeByCondition(notificationMode);
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

    @GetMapping("/closeNotificationMode/{id}")
    @ApiOperation(value = "关闭通知模式", notes = "返回响应对象")
    public ResponseJson closeNotificationMode(
            @ApiParam(value = "被关闭记录的id", required = true)
            @PathVariable String id) {
        notificationModeService.closeNotificationMode(id);
        return new ResponseJson();
    }

}
