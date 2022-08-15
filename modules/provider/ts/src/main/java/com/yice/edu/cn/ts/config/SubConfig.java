package com.yice.edu.cn.ts.config;

import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.ts.receiver.JpushReceiver;
import com.yice.edu.cn.ts.receiver.MsnReceiver;
import com.yice.edu.cn.ts.receiver.SmsReceiver;
import com.yice.edu.cn.ts.receiver.ZcMsnReceiver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

@Configuration
public class SubConfig {
    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
                                            MessageListenerAdapter smsListenerAdapter,
                                            MessageListenerAdapter msnListenerAdapter,
                                            MessageListenerAdapter jpushListenerAdapter,
                                            MessageListenerAdapter zcListenerAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(msnListenerAdapter, new PatternTopic(Constant.MCS_CHANEL.AUTH_IDENTIFY));
        container.addMessageListener(jpushListenerAdapter, new PatternTopic(Constant.MCS_CHANEL.JPUSH_SEND_PUSH));
        container.addMessageListener(zcListenerAdapter, new PatternTopic(Constant.MCS_CHANEL.ZC_SENG_MSG));
        container.addMessageListener(smsListenerAdapter, new PatternTopic(Constant.MCS_CHANEL.SMS_SEND_MSG));
        return container;
    }

    @Bean
    MessageListenerAdapter msnListenerAdapter(MsnReceiver msnReceiver) {
        return new MessageListenerAdapter(msnReceiver, "receiveMessage");
    }
    @Bean
    MessageListenerAdapter jpushListenerAdapter(JpushReceiver jpushReceiver) {
        return new MessageListenerAdapter(jpushReceiver, "sendPush");
    }
    @Bean
    MessageListenerAdapter zcListenerAdapter(ZcMsnReceiver zcMsnReceiver) {
        return new MessageListenerAdapter(zcMsnReceiver, "zcMessage");
    }
    @Bean
    MessageListenerAdapter smsListenerAdapter(SmsReceiver smsReceiver) {
        return new MessageListenerAdapter(smsReceiver, "smsMessage");
    }

}
