package com.yice.edu.cn.common.pojo.jw.schoolNotify;

import com.yice.edu.cn.common.pojo.Pager;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;


@Data
@ApiModel("校园通知")
@Document
public class SchoolNotify{
    @Indexed
    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("发送对象id")
    private ArrayList<SchoolNotifySendObject> schoolNotifySendObjectList;
    @ApiModelProperty("紧急程度:0一般,1紧急，2非常紧急")
    private String urgent;
    @ApiModelProperty("通知标题")
    private String title;
    @ApiModelProperty("通知内容")
    private String content;
    @ApiModelProperty("发布时间")
    private String createTime;
    @ApiModelProperty("学校id")
    private String schoolId;
    @ApiModelProperty(  "发起人id")
    private String senderId;
    @ApiModelProperty(  "发起人名字")
    private String senderName;
    @ApiModelProperty("发送类型： 1、教师  2、学生")
    private String sendType;
    @ApiModelProperty("已读人数")
    private Integer alreadyNum;
    @ApiModelProperty("总人数")
    private Integer totalNum;
    @ApiModelProperty("读取状态：0、全部 1、未读  2、已读  ")
    private String readState;
    @Transient
    @ApiModelProperty(value = "查询开始时间",dataType = "String")
    private String searchStartTime;
    @Transient
    @ApiModelProperty(value = "查询结束时间",dataType = "String")
    private String searchEndTime;
    @Transient
    @ApiModelProperty(value = "发送对象id",dataType = "String")
    private String sendObjectId;

    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
}
