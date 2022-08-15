package com.yice.edu.cn.ts.receiver;

import cn.hutool.json.JSONUtil;
import com.aliyuncs.exceptions.ClientException;
import com.yice.edu.cn.common.pojo.ts.aliMsn.Msn;
import com.yice.edu.cn.common.pojo.ts.aliMsn.Sms;
import com.yice.edu.cn.ts.aliMsn.AliMsn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SmsReceiver {
    @Autowired
    private AliMsn aliMsn;

    public void smsMessage(String json) throws ClientException {
        Sms sms = JSONUtil.toBean(json, Sms.class);
        aliMsn.sendSmsNew(sms);
    }
}
