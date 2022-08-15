package com.yice.edu.cn.ws.controller.cc;

import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.cc.cloudCourse.CCWebSocketResponse;
import com.yice.edu.cn.common.pojo.cc.cloudCourse.CourseUserVo;
import com.yice.edu.cn.ws.principal.UserPrincipal;
import com.yice.edu.cn.ws.service.cc.CloudCourseCacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

@Controller
public class SubscribeController {

    private Logger log = LoggerFactory.getLogger(SubscribeController.class);

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private CloudCourseCacheService cloudCourseCacheService;

    @SubscribeMapping("/{destination}/singleMsg")
    public void singleMsg(UserPrincipal user,@DestinationVariable String destination){
        cloudCourseCacheService.addSubScribe(Constant.CCourse.SUBSCRIBE.SINGLE_MSG,user.getId(),destination);
        simpMessagingTemplate.convertAndSendToUser(destination,Constant.CCourse.SUBSCRIBE.SINGLE_MSG, CCWebSocketResponse.createSuccess(1, "个人通道订阅成功"));
        log.info("个人通道订阅成功");
    }

    @SubscribeMapping("/{destination}/roomMsg")
    public void roomMsg(UserPrincipal user,@DestinationVariable String destination){
        cloudCourseCacheService.addSubScribe(Constant.CCourse.SUBSCRIBE.ROOM_MSG,user.getId(),destination);
        String addr = cloudCourseCacheService.getSubScribe(Constant.CCourse.SUBSCRIBE.SINGLE_MSG, user.getId());
        CourseUserVo addUser = cloudCourseCacheService.addUser(user.getId(), destination);
        if(addUser==null){
            return;
        }
        simpMessagingTemplate.convertAndSendToUser(addr,Constant.CCourse.SUBSCRIBE.SINGLE_MSG, CCWebSocketResponse.createSuccess(1, "房间通道订阅成功"));
        log.info("房间通道订阅成功");
    }

    @SubscribeMapping("/{destination}/hallMsg")
    public void hallMsg(UserPrincipal user,@DestinationVariable String destination){
        cloudCourseCacheService.addSubScribe(Constant.CCourse.SUBSCRIBE.HALL_MSG,user.getId(),destination);
        String addr = cloudCourseCacheService.getSubScribe(Constant.CCourse.SUBSCRIBE.SINGLE_MSG, user.getId());
        simpMessagingTemplate.convertAndSendToUser(addr,Constant.CCourse.SUBSCRIBE.SINGLE_MSG, CCWebSocketResponse.createSuccess(1, "大厅通道订阅成功"));
        log.info("大厅通道订阅成功");
    }

    @SubscribeMapping("/{destination}/padMsg")
    public void padMsg(UserPrincipal user,@DestinationVariable String destination){
        cloudCourseCacheService.addSubScribe(Constant.CCourse.SUBSCRIBE.PAD_MSG,user.getId(),destination);
        String addr = cloudCourseCacheService.getSubScribe(Constant.CCourse.SUBSCRIBE.SINGLE_MSG, user.getId());
        simpMessagingTemplate.convertAndSendToUser(addr,Constant.CCourse.SUBSCRIBE.SINGLE_MSG, CCWebSocketResponse.createSuccess(1, "白板通道订阅成功"));
        log.info("白板通道订阅成功");
    }


    @SubscribeMapping("/{destination}/watchPeopleNum")
    public void watchPeopleNum(UserPrincipal user,@DestinationVariable String destination){
        cloudCourseCacheService.addSubScribe("watchPeopleNum",user.getId(),destination);
        cloudCourseCacheService.addWatchNum(destination);
        cloudCourseCacheService.sendWatchPeopleNum(destination);
    }


}
