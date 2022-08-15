package com.yice.edu.cn.ws.interceper.cc;

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
 * 客户端连接的统一拦截处理
 */
@Component
public class CommonInterceptor implements ChannelInterceptor {

    private Logger log = LoggerFactory.getLogger(CommonInterceptor.class);

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        log.info("message:{},{},{}", message.getHeaders().get("simpDestination"), message.getHeaders().get("stompCommand"), message.getPayload());
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            String id = accessor.getFirstNativeHeader("id"); //用户id
            String clientName = accessor.getFirstNativeHeader("clientName");//业务标识
            if (id == null) {
                return null;
            }
            UserPrincipal userPrincipal = new UserPrincipal() {{
                this.setId(id);
                this.setClientName(clientName);
            }};
            accessor.setUser(userPrincipal);
        }
        return message;
    }
}
