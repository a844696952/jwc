package com.yice.edu.cn.ws.controller.ospNotification;

import com.yice.edu.cn.common.pojo.jw.schoolPush.SchoolPush;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.ws.principal.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class OspNotificationController {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public SchoolPush greeting(Teacher teacher) throws Exception {
        Thread.sleep(200); // simulated delay
        return new SchoolPush(){{
           this.setTitle("第一次通知");
           this.setContent("肚子饿了吗，我买东西给你吃");
        }};
    }
    @MessageMapping("/hello2")
    @SendToUser("/topic/greetings2")
    public SchoolPush greeting2(Teacher teacher) throws Exception {
        return new SchoolPush(){{
           this.setTitle("第一次通知");
           this.setContent("肚子饿了吗，我买东西给你吃");
        }};
    }
    @MessageMapping("/hello3")
    public void greeting3(Teacher teacher,StompHeaderAccessor headerAccessor){
        UserPrincipal userPrincipal = (UserPrincipal) headerAccessor.getUser();
        simpMessagingTemplate.convertAndSendToUser(userPrincipal.getSchoolId(),"/topic/greetings3",new SchoolPush(){{
            this.setTitle("第一次通知");
            this.setContent("肚子饿了吗，我买东西给你吃");
        }});
    }

    @SubscribeMapping("/greetings4")
    public String testSubscribeMapping(){
        System.out.println("/greetings4");
        return "/topic/greetings4订阅";
    }


}
