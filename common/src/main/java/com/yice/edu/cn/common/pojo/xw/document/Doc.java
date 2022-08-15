package com.yice.edu.cn.common.pojo.xw.document;

import com.yice.edu.cn.common.pojo.Pager;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
*
*收文管理
*
*/
@Document
@Data
public class Doc{

    @ApiModelProperty(value = "主键id",dataType = "String")
    private String id;
    @ApiModelProperty(value = "创建时间",dataType = "String")
    private String createTime;
    @ApiModelProperty(value = "更新时间",dataType = "String")
    private String updateTime;
    @ApiModelProperty(value = "删除标识",dataType = "Integer")
    private Integer del;
    @ApiModelProperty(value = "收文时间",dataType = "String")
    private String receiptTime;
    @ApiModelProperty(value = "公文文号",dataType = "String")
    private String documentNumber;
    @ApiModelProperty(value = "文件路径（附件）",dataType = "String")
    private String fileUrl;
    @ApiModelProperty(value = "文件名称",dataType = "String")
    private String fileName;
    @ApiModelProperty(value = "标题",dataType = "String")
    private String subject;
    @ApiModelProperty(value = "领导id",dataType = "String")
    private String leaderId;
    @ApiModelProperty(value = "审核状态（1为未审核，2为已审核）",dataType = "Integer")
    private Integer documentType;
    @ApiModelProperty(value = "创建用户id",dataType = "String")
    private String userId;
    @ApiModelProperty(value = "创建用户名称",dataType = "String")
    private String userName;
    @ApiModelProperty(value = "学校id",dataType = "String")
    private String schoolId;
    @ApiModelProperty(value = "公文类型：1.公文  2.教育督导  3.党建公文",dataType = "Integer")
    private Integer docNumberType;

    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;

    //额外字段
    @Transient
    @ApiModelProperty(value = "时间段数组，Pc端用来查询时用",dataType = "String[]")
    private String[] searchTimeZone;//时间段数组
    @Transient
    private String startTime;//开始时间段
    @Transient
    private String endTime;//结束时间段
    @ApiModelProperty(value = "领导名称",dataType = "String")
    private String leaderName;//领导名称
    @ApiModelProperty(value = "多个批阅领导",dataType = "Object")
    private List<DocLeader> leaderList;//一对多字段

    @ApiModelProperty(value = "(接收时使用)多个抄送人(最多只允许20个抄送人)",dataType = "Object")
    private List<SendObject> sendObject;

    @ApiModelProperty(value = "发送对象",dataType="Object")
    private List<DocManagement> docManagement;

    @ApiModelProperty(value = "（传递时使用）抄送对象")
    private List<DocManagement> sendObjects;

    @ApiModelProperty(value = "记录已读人数",dataType = "Integer")
    private Integer readNum;
    @ApiModelProperty(value = "记录未读人数",dataType = "Integer")
    private Integer unReadNum;

    @ApiModelProperty(value = "文件路径数组",dataType = "String[]")
    private String[] fileUrlList;//文件数组附件
    @ApiModelProperty(value = "文件名称数组",dataType = "String[]")
    private String[] fileNameList;//文件数组名称

    @ApiModelProperty(value = "Pc端用来判断审核完毕后是否已经选择教师发送,false为未发送，true为已发送",dataType = "Boolean")
    private Boolean flag;

    @ApiModelProperty(value = "收文管理—我接收的查看类型(type字段，1未查看，2已查看)",dataType = "Integer")
    private Integer type;

    private List<DocManagement> readList;

    private List<DocManagement> unReadList;
}
