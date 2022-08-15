package com.yice.edu.cn.common.pojo.dm.msg;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.validateClass.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
@ApiModel("云班牌-消息表")
public class DmMsg{

    @ApiModelProperty("主键")
    private String id;
    @ApiModelProperty("家长id")
    @NotBlank(message = "家长id不可为空",groups = {GroupOne.class})
    private String parentId;
    @ApiModelProperty("学生id")
    @NotBlank(message = "学生id不可为空",groups = {GroupOne.class,GroupTwo.class, GroupThree.class,GroupFive.class})
    private String studentId;
    @ApiModelProperty("教师id")
    @NotBlank(message = "教师id不可为空",groups = {GroupOne.class})
    private String teacherId;
    @ApiModelProperty("发送者id")
//    @NotBlank(message = "发送者id不可为空",groups = {GroupOne.class,GroupTwo.class})
    private String senderId;

    @ApiModelProperty("内容")
    @NotBlank(message = "发送内容不可为空",groups = {GroupSix.class})
    private String content;
    @ApiModelProperty("消息类型")
    private MsgType msgType;
    @ApiModelProperty("音频地址")
    private String audioUrl;
    @ApiModelProperty("音频时长")
    private Integer audioDuration;
    @ApiModelProperty("学校id")
    private String schoolId;
    @ApiModelProperty("班牌推送名")
    @NotBlank(message = "班牌名不可为空",groups = {GroupOne.class})
    private String dmUser;
    @ApiModelProperty("发送时间")
    private String sendTime;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空",groups = {GroupThree.class, GroupFour.class})
    @Valid
    private Pager pager;

    @ApiModelProperty("发送者对象")
    private Sender sender ;




}
