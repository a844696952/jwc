package com.yice.edu.cn.bmp.controller.push;

import com.yice.edu.cn.bmp.service.push.PushDetailService;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.ts.jpush.PushDetail;
import com.yice.edu.cn.common.pojo.ts.jpush.Receiver;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.yice.edu.cn.bmp.interceptor.LoginInterceptor.currentParent;

@RestController
@RequestMapping("/pushDetail")
@Api(value = "/pushDetail",description = "推送详情模块")
public class PushDetailController {
    @Autowired
    private PushDetailService pushDetailService;

    @PostMapping("/updatePushDetail2Read")
    @ApiOperation(value = "标记消息为已读", notes = "返回响应对象")
    public ResponseJson updatePushDetail2Read(
            @ApiParam(value = "receiverId、pushId必填", required = true)
            @RequestBody Receiver receiver){
        pushDetailService.updatePushDetail2Read(receiver);
        return new ResponseJson();
    }

    @PostMapping("/findPushDetailListByCondition4Receiver")
    @ApiOperation(value = "查询工作内容推送消息", notes = "返回响应对象,不包含总条数", response=PushDetail.class,responseContainer = "List")
    public ResponseJson findPushDetailListByCondition4Receiver(
            @ApiParam(value = "receiverId、isRead必填，isRead：false未读、true已读,分页自己考量")
            @RequestBody Receiver receiver){
        List<PushDetail> data=pushDetailService.findPushDetailListByCondition4Receiver(receiver);
        return new ResponseJson(data);
    }
    @PostMapping("/findPushDetailCountByCondition4Receiver")
    @ApiOperation(value = "查询工作内容推送消息数量", notes = "总条数", response=Long.class)
    public ResponseJson findPushDetailCountByCondition4Receiver(
            @ApiParam(value = "receiverId、isRead必填，isRead：false未读、true已读")
            @RequestBody Receiver receiver){
        long data=pushDetailService.findPushDetailCountByCondition4Receiver(receiver);
        return new ResponseJson(data);
    }


    @PostMapping("/findDyPushDetailListByConditionReceiver")
    @ApiOperation(value = "查询德育工作内容推送消息", notes = "返回响应对象,不包含总条数", response=PushDetail.class,responseContainer = "List")
    public ResponseJson findDyPushDetailListByConditionReceiver(
            @ApiParam(value = "pager")
            @RequestBody Receiver receiver){
        receiver.setReceiverId(currentParent().getId());
        List<PushDetail> data=pushDetailService.findDyPushDetailListByConditionReceiver(receiver);
        return new ResponseJson(data);
    }

    @PostMapping("/findSchoolActivePushDetailListByConditionReceiver")
    @ApiOperation(value = "查询校园活动推送消息", notes = "返回响应对象,不包含总条数", response=PushDetail.class,responseContainer = "List")
    public ResponseJson findSchoolActivePushDetailListByConditionReceiver(
            @ApiParam(value = "pager")
            @RequestBody Receiver receiver){
        receiver.setReceiverId(currentParent().getId());
        List<PushDetail> data=pushDetailService.findSchoolActivePushDetailListByConditionReceiver(receiver);
        return new ResponseJson(data);
    }

    @PostMapping("/updateSchoolActivePushDetailRead")
    @ApiOperation(value = "标记学校活动通知消息为已读", notes = "返回响应对象")
    public ResponseJson updateSchoolActivePushDetailRead(
            @ApiParam(value = "receiverId、pushId必填", required = true)
            @RequestBody Receiver receiver){
        receiver.setReceiverId(currentParent().getId());
        pushDetailService.updatePushDetail2Read(receiver);
        return new ResponseJson();
    }

}
