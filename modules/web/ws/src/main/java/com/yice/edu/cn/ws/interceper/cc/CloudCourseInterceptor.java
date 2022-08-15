package com.yice.edu.cn.ws.interceper.cc;

import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.cc.cloudCourse.CloudSubCourse;
import com.yice.edu.cn.ws.principal.UserPrincipal;
import com.yice.edu.cn.ws.service.cc.CloudCourseCacheService;
import com.yice.edu.cn.ws.service.cc.CloudCourseRecordService;
import com.yice.edu.cn.ws.service.cc.CloudSubCourseService;
import com.yice.edu.cn.ws.util.Token2IdUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;

/**
 * 云课堂有关的拦截 反订阅和意外掉线的处理
 */
@Component
public class CloudCourseInterceptor implements ChannelInterceptor {

    private Logger log = LoggerFactory.getLogger(CloudCourseInterceptor.class);

    @Autowired
    private CloudCourseCacheService cloudCourseCacheService;

    @Autowired
    private CloudCourseRecordService cloudCourseRecordService;

    @Autowired
    private CloudSubCourseService cloudSubCourseService;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        log.info("message:{},{},{}",message.getHeaders().get("simpDestination"),message.getHeaders().get("stompCommand"),message.getPayload());
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        UserPrincipal user = (UserPrincipal)accessor.getUser();
        if(!Constant.STOMP.CLIENT_CC.equals(user.getClientName())){
            return message;
        }
        if(StompCommand.UNSUBSCRIBE.equals(accessor.getCommand())){
            log.info("Unsubscribe user:{}",user);
            if(user.getClientName().equals(Constant.STOMP.CLIENT_CC)){
                String simpDestination = (String) accessor.getHeader("simpDestination");
                if(simpDestination.contains(Constant.CCourse.SUBSCRIBE.ROOM_MSG)){
                    String roomAddr = cloudCourseCacheService.getSubScribe(Constant.CCourse.SUBSCRIBE.ROOM_MSG, user.getId());
                    if(roomAddr!=null) {
                        log.info("Unsubscribe roomAddr:{}", roomAddr);
                        cloudCourseCacheService.removeUser(user.getId(), roomAddr);
                        cloudCourseCacheService.removeSubScribe(Constant.CCourse.SUBSCRIBE.ROOM_MSG, user.getId());
                        cloudCourseCacheService.removeSubScribe(Constant.CCourse.SUBSCRIBE.PAD_MSG, user.getId());
                        CloudSubCourse curCourse = cloudSubCourseService.getCurCourse(roomAddr);
                        cloudCourseRecordService.endRecord(curCourse.getId());
                    }
                }
            }
        }else if(StompCommand.DISCONNECT.equals(accessor.getCommand())){
            log.info("Disconnect user:{}",user);
            if(user!=null && user.getClientName().equals(Constant.STOMP.CLIENT_CC)) {
                cloudCourseCacheService.removeSubScribe(Constant.CCourse.SUBSCRIBE.HALL_MSG, user.getId());
                cloudCourseCacheService.removeSubScribe(Constant.CCourse.SUBSCRIBE.SINGLE_MSG, user.getId());
                String roomAddr = cloudCourseCacheService.getSubScribe(Constant.CCourse.SUBSCRIBE.ROOM_MSG, user.getId());
                if(roomAddr!=null) {
                    log.info("Disconnect roomAddr:{}", roomAddr);
                    cloudCourseCacheService.removeUser(user.getId(), roomAddr);
                    cloudCourseCacheService.removeSubScribe(Constant.CCourse.SUBSCRIBE.ROOM_MSG, user.getId());
                    cloudCourseCacheService.removeSubScribe(Constant.CCourse.SUBSCRIBE.PAD_MSG, user.getId());
                    CloudSubCourse curCourse = cloudSubCourseService.getCurCourse(roomAddr);
                    cloudCourseRecordService.endRecord(curCourse.getId());
                }
            }
        }
        return message;
    }

}
