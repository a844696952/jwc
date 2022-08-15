package com.yice.edu.cn.ws.event;

import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.ws.principal.UserPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;


@Component
public class CCWebsocketEvent implements ApplicationListener {

    private Logger log = LoggerFactory.getLogger(CCWebsocketEvent.class);


    /**
     * 事件类型可能有BrokerAvailabilityEvent,SessionConnectEvent,SessionConnectedEvent,SessionSubscribeEvent,SessionUnsubscribeEvent,SessionDisconnectEvent
     * https://docs.spring.io/spring/docs/5.1.7.RELEASE/spring-framework-reference/web.html#websocket-stomp-appplication-context-events
     * @param applicationEvent
     */
    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        if(applicationEvent instanceof SessionConnectedEvent){
            SessionConnectedEvent sc = (SessionConnectedEvent) applicationEvent;
            UserPrincipal user = (UserPrincipal)sc.getUser();
            if(user.getClientName().equals(Constant.STOMP.CLIENT_CC)){
                log.info(String.format("用户%s连接成功", user.getName()));
                //todo nothing
            }
        }else if(applicationEvent instanceof SessionDisconnectEvent){
            SessionUnsubscribeEvent sc = (SessionUnsubscribeEvent) applicationEvent;
            UserPrincipal user = (UserPrincipal)sc.getUser();
            log.info("用户:"+user.getName()+"断开了");
        }
    }

}
