package com.yice.edu.cn.common.pojo.dm.msg;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel("消息接收表")
public class ReceiveMsg{

    @ApiModelProperty("主键")
    protected String id;
    @ApiModelProperty("消息id")
    protected String msgId;
    @ApiModelProperty("发送者")
    protected Sender sender;
    @ApiModelProperty("是否已读")
//    @NotNull(message = "是否已读不可为空",groups = {GroupOne.class})
    protected Boolean reade;
    @ApiModelProperty("消息类型")
    protected String msgType;
    @ApiModelProperty("学校id")
    protected String schoolId;
    @ApiModelProperty("接收时间")
    protected String receiveTime;
    @ApiModelProperty("读取时间")
    protected String readTime;


    public ReceiveMsg(){

    }

    public ReceiveMsg(DmMsg msg){
        this.msgId = msg.getId();
        this.msgType = msg.getMsgType().toString();
        this.receiveTime = msg.getSendTime();
        this.schoolId = msg.getSchoolId();
        this.sender = msg.getSender();
    }


}
