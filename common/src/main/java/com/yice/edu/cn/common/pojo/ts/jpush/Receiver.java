package com.yice.edu.cn.common.pojo.ts.jpush;

import com.yice.edu.cn.common.pojo.Pager;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 消息推送 接收人
 */
@Data
@Document
public class Receiver {
    @ApiModelProperty(value = "接收人的id",dataType = "String")
    private String receiverId;
    @ApiModelProperty(value = "是否已读",dataType = "Boolean")
    private Boolean isRead;
    @Transient
    @ApiModelProperty(value = "推送记录的id",dataType = "String")
    private String pushId;
    @Transient
    @ApiModelProperty(value = "推送类型",dataType = "Integer")
    private Integer type;

    //分页排序等
    @Transient
    private Pager pager;

    public Receiver() {

    }

    public Receiver(String receiverId, Boolean isRead) {
        this.receiverId = receiverId;
        this.isRead = isRead;
    }
}
