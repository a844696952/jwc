package com.yice.edu.cn.tap.controller.push;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.ts.jpush.PushDetail;
import com.yice.edu.cn.common.pojo.ts.jpush.Receiver;
import com.yice.edu.cn.tap.service.push.PushDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.yice.edu.cn.tap.interceptor.LoginInterceptor.myId;

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

    @PostMapping("/findPushDetailList4Work")
    @ApiOperation(value = "查询工作内容推送消息", notes = "返回响应对象,不包含总条数", response=PushDetail.class,responseContainer = "List")
    public ResponseJson findPushDetailList4Work(
            @ApiParam(value = "receiverId、isRead必填，isRead：false未读、true已读,分页自己考量")
            @RequestBody Receiver receiver){
        List<PushDetail> data=pushDetailService.findPushDetailList4Work(receiver);
        return new ResponseJson(data);
    }
    @PostMapping("/findPushDetailList4InOutSchool")
    @ApiOperation(value = "查询出入校推送消息", notes = "返回响应对象,不包含总条数", response=PushDetail.class,responseContainer = "List")
    public ResponseJson findPushDetailList4InOutSchool(
            @ApiParam(value = "receiverId、isRead必填，isRead：false未读、true已读,分页自己考量")
            @RequestBody Receiver receiver){
        List<PushDetail> data=pushDetailService.findPushDetailList4InOutSchool(receiver);
        return new ResponseJson(data);
    }
    @PostMapping("/findPushDetailCount4Work")
    @ApiOperation(value = "查询工作内容推送消息数量", notes = "总条数", response=Long.class)
    public ResponseJson findPushDetailCount4Work(
            @ApiParam(value = "receiverId、isRead必填，isRead：false未读、true已读")
            @RequestBody Receiver receiver){
        long data=pushDetailService.findPushDetailCount4Work(receiver);
        return new ResponseJson(data);
    }
    @PostMapping("/findPushDetailCount4InOutSchool")
    @ApiOperation(value = "查询出入校推送消息数量", notes = "总条数", response=Long.class)
    public ResponseJson findPushDetailCount4InOutSchool(
            @ApiParam(value = "receiverId、isRead必填，isRead：false未读、true已读")
            @RequestBody Receiver receiver){
        long data=pushDetailService.findPushDetailCount4InOutSchool(receiver);
        return new ResponseJson(data);
    }

    @PostMapping("/findPushActiveList")
    @ApiOperation(value = "查询活动推送列表", notes = "总条数", response=Long.class)
    public ResponseJson findPushActiveList(
            @ApiParam(value = "receiverId、isRead必填，isRead：false未读、true已读,type 30活动 31公文 32学习")
            @RequestBody Receiver receiver){
        List<PushDetail> pushActiveList = pushDetailService.findPushActiveList (receiver);
        long data=pushDetailService.findPushActiveCount (receiver);
        return new ResponseJson (pushActiveList,data);
    }


    @PostMapping("/findPushListAll")
    @ApiOperation(value = "查询所有推送列表", notes = "查询活动推送列表", response=Long.class)
    public ResponseJson findPushListAll(
            @ApiParam(value = "receiverId、isRead必填，isRead：false未读、true已读")
            @RequestBody Receiver receiver){
        List<PushDetail> pushActiveList = pushDetailService.findPushDetailAllList (receiver);
        long data=pushDetailService.findPushDetailCount (receiver);
        return new ResponseJson (pushActiveList,data);
    }


    @PostMapping("/findSchoolActivePushDetailListByConditionTeacherReceiver")
    @ApiOperation(value = "查询校园活动推送消息", notes = "返回响应对象,不包含总条数", response=PushDetail.class,responseContainer = "List")
    public ResponseJson findSchoolActivePushDetailListByConditionTeacherReceiver(
            @ApiParam(value = "pager")
            @RequestBody Receiver receiver){
        receiver.setReceiverId(myId());
        List<PushDetail> data=pushDetailService.findSchoolActivePushDetailListByConditionTeacherReceiver(receiver);
        return new ResponseJson(data);
    }

    @PostMapping("/updatePushDetailRead")
    @ApiOperation(value = "标记消息为已读", notes = "返回响应对象")
    public ResponseJson updatePushDetailRead(
            @ApiParam(value = "receiverId、pushId必填", required = true)
            @RequestBody Receiver receiver){
        receiver.setReceiverId(myId());
        pushDetailService.updatePushDetail2Read(receiver);
        return new ResponseJson();
    }

}
