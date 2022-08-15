package com.yice.edu.cn.common.pojo.xw.document;

import com.yice.edu.cn.common.pojo.Pager;
import io.swagger.annotations.Api;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
*
*发文管理
*
*/
@Data
public class Writing{

    @ApiModelProperty(value = "主键id",dataType = "String")
    private String id;
    @ApiModelProperty(value = "创建时间,用来做发文日期",dataType = "String")
    private String createTime;
    @ApiModelProperty(value = "更新时间",dataType = "String")
    private String updateTime;
    @ApiModelProperty(value = "删除标识符",dataType = "Integer")
    private Integer del;
    @ApiModelProperty(value = "发文部门",dataType = "String")
    private String serviceUnit;
    @ApiModelProperty(value = "标题",dataType = "String")
    private String subject;
    @ApiModelProperty(value = "公文文号",dataType = "String")
    private String documentNumber;
    @ApiModelProperty(value = "审批对象id",dataType = "String")
    private String leaderId;
    @ApiModelProperty(value = "创建用户id",dataType = "String")
    private String userId;
    @ApiModelProperty(value = "创建用户名称",dataType = "String")
    private String userName;
    @ApiModelProperty(value = "学校id",dataType = "String")
    private String schoolId;
    @ApiModelProperty(value = "审批状态(1为未审批，2为已完成，3为驳回)",dataType = "Integer")
    private Integer writingType;
    @ApiModelProperty(value = "发文日期,咱不使用，请使用createTime字段当发文日期",dataType = "String")
    private String writingTime;
    @ApiModelProperty(value = "审批人名称",dataType = "String")
    private String leaderName;
    @ApiModelProperty(value = "附件地址",dataType = "String")
    private String fileUrl;
    @ApiModelProperty(value = "附件名称",dataType = "String")
    private String fileName;
    @ApiModelProperty(value = "发文类型：1.公文  2.教育督导",dataType = "Integer")
    private Integer writingNumberType;
    @ApiModelProperty(value = "领导头像路径",dataType = "String")
    private String leaderImg;
    @ApiModelProperty(value = "驳回后是否已查看过，0为添加时的默认字段,防止该字段为空时被拦截，1为未查看2为已查看",dataType = "String")
    private Integer reject;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;

    @ApiModelProperty(value = "父节点Id",dataType = "String")
    private String departmentParentId;
    @ApiModelProperty(value = "发送对象(多个,接收)",dataType = "List<Object>")
    private List<SendObject> sendObject;    //发送对象
    @ApiModelProperty(value = "时间段筛选数组(Pc端用于查询时的时间段数组)",dataType = "String[]")
    private String[] searchTimeZone; //时间段
    private String startTime;   //开始时间
    private String endTime;     //结束时间
    @ApiModelProperty(value = "领导批阅意见",dataType = "String")
    private String remarks;     //领导批阅意见
    private WritingManagement writingManagement; //查看对象表

    private String writingId;   //公文id
    private Integer type;  //查看对象是否查看
    @ApiModelProperty(value = "上传文件路径数组",dataType = "String[]")
    private String[] fileUrlList;//文件数组附件
    @ApiModelProperty(value = "上传文件名称数组",dataType = "String[]")
    private String[] fileNameList;//文件数组名称

    private String order; //自定义排序字段，不为空则按WritingType升序和createTime降序排序

    @ApiModelProperty(value = "记录已读人数",dataType = "Integer")
    private Integer readNum;
    @ApiModelProperty(value = "记录未读人数",dataType = "Integer")
    private Integer unReadNum;


    @ApiModelProperty(value = "多个发送人对象(传递)",dataType = "List<Object>")
    private List<WritingManagement> writingManagementList;
    @ApiModelProperty(value = "发送对象Id",dataType = "String")
    private String sendObjectId;  //发送对象id
    @ApiModelProperty(value = "发送对象名称",dataType = "String")
    private String sendObjectName; //发送对象名称
    @ApiModelProperty(value = "发送对象头像",dataType = "String")
    private String sendObjectImg;
    @ApiModelProperty(value = "发送对象是否已阅的类型",dataType = "String")
    private String nameList;

}
