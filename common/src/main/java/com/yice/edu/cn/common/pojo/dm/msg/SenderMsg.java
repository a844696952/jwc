package com.yice.edu.cn.common.pojo.dm.msg;

import lombok.Data;

@Data
public class SenderMsg {

    private Sender sender;
    private DmMsg lastMsg;
    private int count;
}
