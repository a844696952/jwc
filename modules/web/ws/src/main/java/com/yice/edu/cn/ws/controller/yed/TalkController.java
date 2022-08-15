package com.yice.edu.cn.ws.controller.yed;

import com.yice.edu.cn.common.pojo.jw.schoolPush.SchoolPush;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import java.util.Map;

@Controller
public class TalkController {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    /**
     * vue-admin demo聊天室
     */
    @MessageMapping("/sendMessage/{room}")
    public void roomTalk(@DestinationVariable String room, Map<String,Object> param) {
        simpMessagingTemplate.convertAndSendToUser(room,"/topic/roomTalk",param);
    }
}
