package com.yice.edu.cn.common.pojo.dm.parentMsg;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;


@Data
@ApiModel("班牌家长推送信息表")
public class Parentmsg{

    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("学校id")
    private String schoolId;
    @ApiModelProperty("家长id")
    private String parentId;
    @ApiModelProperty("班级id")
    private String classNo;
    @ApiModelProperty("学生姓名")
    private String studentName;
    @ApiModelProperty("班牌id")
    private String cardNo;
    @ApiModelProperty("消息类型")
    private Integer mType;
    @ApiModelProperty("发送时间")
    private String sendTime;
    @ApiModelProperty("消息状态 0.未读,1.已读")
    private Integer mStatus;
    @ApiModelProperty("文本消息")
    private String content;
    @ApiModelProperty("消息 1.文字消息 2.语音消息")
    private String message;

    @ApiModelProperty("学生id不能为空")
    private String studentId;

    @ApiModelProperty(value = "创建时间",dataType = "String")
    private String createTime;//创建时间
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid


    private Pager pager;
    private Integer status;
}
