package com.yice.edu.cn.common.pojo.xw.document;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
/**
*
*
*
*/
@Data
public class DocLeader{

    @ApiModelProperty(value = "主键id",dataType = "String")
    private String id;
    @ApiModelProperty(value = "创建时间",dataType = "String")
    private String createTime;
    @ApiModelProperty(value = "更新时间",dataType = "String")
    private String updateTime;
    @ApiModelProperty(value = "删除标识",dataType = "Integer")
    private Integer del;
    @ApiModelProperty(value = "批阅领导id",dataType = "String")
    private String leaderId;
    @ApiModelProperty(value = "公文id",dataType = "String")
    private String docId;
    @ApiModelProperty(value = "备注",dataType = "String")
    private String remarks;
    @ApiModelProperty(value = "类型",dataType = "Integer")
    private Integer type;
    @ApiModelProperty(value = "批阅领导名称",dataType = "String")
    private String leaderName;
    @ApiModelProperty(value = "收文时间",dataType = "String")
    private String receiptTime;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;

    private String startTime;
    private String endTime;
}
