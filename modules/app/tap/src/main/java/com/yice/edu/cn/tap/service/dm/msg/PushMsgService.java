package com.yice.edu.cn.tap.service.dm.msg;

import cn.hutool.json.JSONUtil;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.dm.msg.DmMsg;
import com.yice.edu.cn.common.pojo.dm.msg.MsgType;
import com.yice.edu.cn.common.pojo.ts.jpush.Extras;
import com.yice.edu.cn.common.pojo.ts.jpush.JpushApp;
import com.yice.edu.cn.common.pojo.ts.jpush.Push;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author dengfengfeng
 */
@Component
public class PushMsgService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public void pushMsg(DmMsg msg){
        Push push = buildPush(Push.newBuilder(JpushApp.ECC).setAlias(msg.getDmUser()),msg);
        stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH, JSONUtil.toJsonStr(push));

        Push push2 = buildPush(Push.newBuilder(JpushApp.BMP).setAlias(msg.getParentId()),msg);
        stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH, JSONUtil.toJsonStr(push2));
    }

    private Push buildPush(Push.Builder builder,DmMsg msg){
        return builder
                .setMessage(Push.Message.newBuilder()
                        .setMsgContent(JSONUtil.parse(msg).toString())
                        .setTitle("家校互动消息")
                        .setAlert(msg.getMsgType().equals(MsgType.AUDIO)?"收到一条语音消息":msg.getContent())
                        .setContentType(ContentType.TEXT_PLAIN.toString())
                        .setExtras(Extras.newBuilder()
                                .setId(msg.getId())
                                .setType(Extras.DM_MSG).build())
                        .build())
                .setOptions(Push.Options.newBuilder().build())
                .build();
    }

}
