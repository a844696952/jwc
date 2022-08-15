package com.yice.edu.cn.ts.receiver;

import cn.hutool.json.JSONUtil;
import com.aliyuncs.exceptions.ClientException;
import com.yice.edu.cn.common.pojo.ts.aliMsn.Msn;
import com.yice.edu.cn.common.pojo.ts.aliMsn.ZcMsn;
import com.yice.edu.cn.ts.aliMsn.AliMsn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ZcMsnReceiver {
    @Autowired
    private AliMsn aliMsn;
    public void zcMessage(String json) throws ClientException {
        System.out.println("收到一条消息："+json);
        ZcMsn zcMsn = JSONUtil.toBean(json, ZcMsn.class);
        aliMsn.sendZcVerifySms(zcMsn);
    }
}
