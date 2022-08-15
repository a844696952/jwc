package com.yice.edu.cn.common.pojo.dm.sturespMsg;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;


@Data
@ApiModel("")
public class Sturespmsg{

    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("学校id")
    private String schoolId;
    @ApiModelProperty("家长手机设备标识")
    private String parentCardno;
    @ApiModelProperty(" 消息类型 1、文字消息 2、语音消息")
    private String mType;
    @ApiModelProperty("回复时间")
    private String sendTime;
    @ApiModelProperty("消息状态 0、未读 1、已读")
    private Integer mStatus;
    @ApiModelProperty("文本消息")
    private String content;
    @ApiModelProperty("语音消息")
    private String message;
    @ApiModelProperty("家长id")
    private String parentId;
    @ApiModelProperty("针对哪条家长消息进行回复")
    private String pmsgId;

    private String studentId;

    @ApiModelProperty(value = "创建时间",dataType = "String")
    private String createTime;//创建时间
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
}
