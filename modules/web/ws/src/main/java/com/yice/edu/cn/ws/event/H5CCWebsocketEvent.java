package com.yice.edu.cn.ws.event;

import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.ws.principal.UserPrincipal;
import com.yice.edu.cn.ws.service.cc.CloudCourseCacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;

@Component
public class H5CCWebsocketEvent implements ApplicationListener {

    private Logger log = LoggerFactory.getLogger(H5CCWebsocketEvent.class);

    @Autowired
    private CloudCourseCacheService cloudCourseCacheService;

    /**
     * 事件类型可能有BrokerAvailabilityEvent,SessionConnectEvent,SessionConnectedEvent,SessionSubscribeEvent,SessionUnsubscribeEvent,SessionDisconnectEvent
     * https://docs.spring.io/spring/docs/5.1.7.RELEASE/spring-framework-reference/web.html#websocket-stomp-appplication-context-events
     *
     * @param applicationEvent
     */
    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        if (applicationEvent instanceof SessionConnectedEvent) {
            SessionConnectedEvent sc = (SessionConnectedEvent) applicationEvent;
            UserPrincipal user = (UserPrincipal) sc.getUser();
            if (user.getClientName().equals(Constant.STOMP.CLIENT_CC_H5)) {
                //todo nohting
            }
        } else if (applicationEvent instanceof SessionSubscribeEvent) {
//           SessionSubscribeEvent sc = (SessionSubscribeEvent) applicationEvent;
//            UserPrincipal user = (UserPrincipal) sc.getUser();
//            if (user.getClientName().equals(Constant.STOMP.CLIENT_CC_H5)) {
//                StompHeaderAccessor accessor = StompHeaderAccessor.wrap(sc.getMessage());
//                String simpDestination = (String) accessor.getHeader("simpDestination");
//                if(simpDestination.contains("watchPeopleNum")) {
//                    String[] split = simpDestination.split("/");
//                    String channelCode = split[2];
//                    user.setGroupName(channelCode);
//                    accessor.setUser(user);
//                    cloudCourseCacheService.addWatchNum(channelCode);
//                    cloudCourseCacheService.sendWatchPeopleNum(channelCode);
//                }
//            }
        } else if (applicationEvent instanceof SessionUnsubscribeEvent) {
            SessionUnsubscribeEvent sc = (SessionUnsubscribeEvent) applicationEvent;
            UserPrincipal user = (UserPrincipal) sc.getUser();
            if (user.getClientName().equals(Constant.STOMP.CLIENT_CC_H5)) {
                StompHeaderAccessor accessor = StompHeaderAccessor.wrap(sc.getMessage());
                String simpDestination = (String) accessor.getHeader("simpDestination");
                if(simpDestination.contains("watchPeopleNum")) {
                    String[] split = simpDestination.split("/");
                    String channelCode = split[2];
                    cloudCourseCacheService.removeSubScribe("watchPeopleNum",user.getId());
                    cloudCourseCacheService.reduceWatchNum(channelCode);
                    cloudCourseCacheService.sendWatchPeopleNum(channelCode);
                }
            }
        } else if (applicationEvent instanceof SessionDisconnectEvent) {

        }
    }
}
