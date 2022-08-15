package com.yice.edu.cn.ws.event;

import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CreateCache;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.ws.principal.UserPrincipal;
import com.yice.edu.cn.ws.util.Token2IdUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.discovery.event.HeartbeatEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;

import java.util.concurrent.TimeUnit;

@Component
public class YcVerifaceWebsocketEvent implements ApplicationListener {

    private Logger log = LoggerFactory.getLogger(YcVerifaceWebsocketEvent.class);
    @CreateCache(name = Constant.Redis.YC_VERIFACE_ONLINE_DOOR_DEVICE_LIST )
    private  Cache<String,String> ycVerifaceOnlineDoorDeviceList;//key:设备id，value：变化列表




 /*   @Autowired
    private CloudCourseCacheService cloudCourseCacheService;*/

    /**
     * 事件类型可能有BrokerAvailabilityEvent,SessionConnectEvent,SessionConnectedEvent,SessionSubscribeEvent,SessionUnsubscribeEvent,SessionDisconnectEvent
     * https://docs.spring.io/spring/docs/5.1.7.RELEASE/spring-framework-reference/web.html#websocket-stomp-appplication-context-events
     *
     * @param applicationEvent
     */
    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        if (applicationEvent instanceof SessionConnectedEvent) {//回话连接成功事件
            SessionConnectedEvent sc = (SessionConnectedEvent) applicationEvent;
            UserPrincipal user = (UserPrincipal) sc.getUser();
            if (user.getClientName().equals(Constant.STOMP.CLIENT_YC_VERIFACE)) {
                //log.info("设备建立连接成功——>"+user);
                String deviceSign = Token2IdUtil.token2Id(user.getId());
                //log.info(deviceSign);
                //存入缓存，表示设备在线；
                ycVerifaceOnlineDoorDeviceList.put(deviceSign,deviceSign);
            }
        } else if (applicationEvent instanceof SessionSubscribeEvent) {//订阅事件
            SessionSubscribeEvent sc = (SessionSubscribeEvent) applicationEvent;
            UserPrincipal user = (UserPrincipal) sc.getUser();
            if (user.getClientName().equals(Constant.STOMP.CLIENT_YC_VERIFACE)) {
                StompHeaderAccessor accessor = StompHeaderAccessor.wrap(sc.getMessage());
                String simpDestination = (String) accessor.getHeader("simpDestination");
                //log.info(simpDestination);
                if(simpDestination.contains("/topic/toldDevicePeopleHaveChange")) {
                    //log.info("设备订阅人员变化通知——>"+user);
                }
            }
        } else if (applicationEvent instanceof SessionUnsubscribeEvent) {//取消订阅事件
            SessionUnsubscribeEvent sc = (SessionUnsubscribeEvent) applicationEvent;
            UserPrincipal user = (UserPrincipal) sc.getUser();
            if (user.getClientName().equals(Constant.STOMP.CLIENT_YC_VERIFACE)) {
                StompHeaderAccessor accessor = StompHeaderAccessor.wrap(sc.getMessage());
                String simpDestination = (String) accessor.getHeader("simpDestination");
                if(simpDestination.contains("/topic/toldDevicePeopleHaveChange")) {
                    //log.info("设备取消订阅人员变化通知——>"+user);
                }
            }
        } else if (applicationEvent instanceof SessionDisconnectEvent) {//回话关闭事件
            SessionDisconnectEvent sc = (SessionDisconnectEvent) applicationEvent;
            UserPrincipal user = (UserPrincipal)sc.getUser();
            if(user.getClientName().equals(Constant.STOMP.CLIENT_YC_VERIFACE)) {
                //log.info("设备断开连接——>"+user);
                String deviceSign = Token2IdUtil.token2Id(user.getId());
               // log.info(deviceSign);
                //存入缓存，表示设备在线；
                ycVerifaceOnlineDoorDeviceList.remove(deviceSign);
            }
        }
    }
}
