package com.yice.edu.cn.ts.receiver;

import cn.hutool.json.JSONUtil;
import com.yice.edu.cn.common.pojo.ts.jpush.Push;
import com.yice.edu.cn.ts.jpush.core.Jpush;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;

@Component
public class JpushReceiver {
    @Autowired
    private Jpush jpush;
    public void sendPush(String pushJson){
        Push push = JSONUtil.toBean(pushJson, Push.class);
        try {
            jpush.sendPush(push);
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
