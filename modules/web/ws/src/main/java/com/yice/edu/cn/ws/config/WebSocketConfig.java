package com.yice.edu.cn.ws.config;

import com.yice.edu.cn.ws.interceper.cc.CloudCourseInterceptor;
import com.yice.edu.cn.ws.interceper.cc.CommonInterceptor;
import com.yice.edu.cn.ws.interceper.cc.YcVerifaceInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * @author Administrator
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer  {

    @Autowired
    private CloudCourseInterceptor cloudCourseInterceptor;

    @Autowired
    private CommonInterceptor commonInterceptor;

    @Autowired
    private YcVerifaceInterceptor ycVerifaceInterceptor;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic","/user");
        config.setApplicationDestinationPrefixes("/app","/user");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/gs-guide-websocket","/stomp").setAllowedOrigins("*");
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(commonInterceptor,cloudCourseInterceptor,ycVerifaceInterceptor);
    }
}