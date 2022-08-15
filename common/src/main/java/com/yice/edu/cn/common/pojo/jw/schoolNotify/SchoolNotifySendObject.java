package com.yice.edu.cn.common.pojo.jw.schoolNotify;

import com.yice.edu.cn.common.pojo.Pager;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@ApiModel("校园通知发送对象")
@Document
public class SchoolNotifySendObject{
    @Indexed
    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("对象id")
    private String objectId;
    @ApiModelProperty("departmentName")
    private String departmentName;
    @ApiModelProperty("departmentParentId")
    private String departmentParentId;
    @ApiModelProperty("读取状态：0、全部 1、未读  2、已读  ")
    @Indexed
    private String readState;
    @Indexed
    @ApiModelProperty("学校id")
    private String schoolId;
    @ApiModelProperty("通知对象")
    private SchoolNotify schoolNotify;
    @ApiModelProperty("类型:0为组织架构，1为成员")
    private String type;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("收发状态：1、发送  2、接受 ")
    private String state;
    @ApiModelProperty("班号")
    private Integer classNumber;
    @ApiModelProperty("头像 ")
    private String imgUrl;
    @ApiModelProperty("删除状态(接收列表那边可以删除)：1、未删除 2、已删除 ")
    private String del;

    @ApiModelProperty("班级总人数 ")
    private Integer classNum;
    @ApiModelProperty("班级读取人数")
    private Integer classReadNum;
    //分页排序等
    @Transient
    private Pager pager;
}
