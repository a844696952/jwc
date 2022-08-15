package com.yice.edu.cn.common.pojo.dm.parentMsg;

import io.netty.util.internal.StringUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("")
public class ParentMessage{
    @ApiModelProperty("回复时间")
    private String sendTime;
    @ApiModelProperty("文本消息")
    private String content;
    @ApiModelProperty("消息状态 0、未读 1、已读")
    private Integer mStatus;

    private String owner;
    public ParentMessage(String c,String t,Integer m,String o){
        c = StringUtil.isNullOrEmpty(c) ? "":c;
        t = StringUtil.isNullOrEmpty(t) ? "":t;
        if(m == null){
            m = new Integer(0);
        }
        this.content = c;
        this.sendTime = t;
        this.mStatus = m;
        this.owner = o;
    }
}
