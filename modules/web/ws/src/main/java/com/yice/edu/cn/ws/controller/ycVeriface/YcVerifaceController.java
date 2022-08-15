package com.yice.edu.cn.ws.controller.ycVeriface;


import com.yice.edu.cn.common.pojo.dm.ycVeriface.device.YcVerifaceDevice;
import com.yice.edu.cn.ws.principal.UserPrincipal;
import com.yice.edu.cn.ws.util.Token2IdUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/wsYcVeriface")
@MessageMapping("/wsYcVeriface")
public class YcVerifaceController {

    private Logger log = LoggerFactory.getLogger(YcVerifaceController.class);

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/getSomethingMsg")
    public void getSomethingMsg(UserPrincipal user){
        log.info("用户信息:{}",user );
        System.out.println(user.getUserName());
        String s = Token2IdUtil.token2Id(user.getId());
       log.info("token里的信息："+s);
       user.setUserName(s);
        simpMessagingTemplate.convertAndSendToUser(user.getUserName(),"topic/toldDevicePeopleHaveChange",user);

    }

    @PostMapping("/toldDevicePeopleHaveChange")
    public void toldDevicePeopleHaveChange( @RequestBody YcVerifaceDevice userList){
        System.out.println("告诉设备人员列表有变动");
        System.out.println(userList);
         simpMessagingTemplate.convertAndSendToUser( userList.getDeviceId(),"topic/toldDevicePeopleHaveChange", userList);
        //simpMessagingTemplate.convertAndSend( "/told/"+userList.getDeviceId()+"/topic/toldDevicePeopleHaveChange", userList);
    }




}
