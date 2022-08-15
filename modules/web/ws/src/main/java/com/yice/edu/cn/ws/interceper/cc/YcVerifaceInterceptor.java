package com.yice.edu.cn.ws.interceper.cc;

import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CreateCache;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.ws.principal.UserPrincipal;
import com.yice.edu.cn.ws.util.Token2IdUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;

/**
 * 人脸识别门禁有关的拦截 连接，断开的处理
 */
@Component
public class YcVerifaceInterceptor implements ChannelInterceptor {

    private Logger log = LoggerFactory.getLogger(YcVerifaceInterceptor.class);




    @CreateCache(name = Constant.Redis.YC_VERIFACE_ONLINE_DOOR_DEVICE_LIST)
    private Cache<String,String> ycVerifaceOnlineDoorDeviceList;//key:设备id，value：变化列表

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

   /*     if ("HEARTBEAT".equals(message.getHeaders().get("simpMessageType").toString())){
            UserPrincipal user =  (UserPrincipal) message.getHeaders().get("simpUser");
            String id = user.getId();//token
            String clientName =user.getClientName();
            if(id!=null){
                if (clientName.equals("ycVeriface")){
                    String deviceSign = Token2IdUtil.token2Id(user.getId());
                    ycVerifaceOnlineDoorDeviceList.put(deviceSign,deviceSign);
                    log.info(deviceSign+"设备心跳");
                }
            }
        }*/
        log.info("message:{},{},{}",message.getHeaders().get("simpDestination"),message.getHeaders().get("stompCommand"),message.getPayload());
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        UserPrincipal user = (UserPrincipal)accessor.getUser();
        if(!Constant.STOMP.CLIENT_YC_VERIFACE.equals(user.getClientName())){
            return message;
        }


        if(StompCommand.CONNECT.equals(accessor.getCommand())){
            log.info("connect user:{}",user);
            if(user.getClientName().equals(Constant.STOMP.CLIENT_YC_VERIFACE)){
                String deviceSign = Token2IdUtil.token2Id(user.getId());
                log.info(deviceSign);
                //存入缓存，表示设备在线；
                ycVerifaceOnlineDoorDeviceList.put(deviceSign,deviceSign);
            }
        }else if(StompCommand.UNSUBSCRIBE.equals(accessor.getCommand())){
            log.info("Unsubscribe user:{}",user);
            if(user.getClientName().equals(Constant.STOMP.CLIENT_YC_VERIFACE)){
                String simpDestination = (String) accessor.getHeader("simpDestination");

            }
        }else if(StompCommand.DISCONNECT.equals(accessor.getCommand())){
            log.info("Disconnect user:{}",user);
            if(user!=null && user.getClientName().equals(Constant.STOMP.CLIENT_YC_VERIFACE)) {
                String deviceSign = Token2IdUtil.token2Id(user.getId());
                log.info(deviceSign);
                //移除缓存，表示设备异常；
                ycVerifaceOnlineDoorDeviceList.remove(deviceSign);
            }
        }



        return message;
    }

}
