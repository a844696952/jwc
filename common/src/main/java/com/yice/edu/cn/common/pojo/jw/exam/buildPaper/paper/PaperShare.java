package com.yice.edu.cn.common.pojo.jw.exam.buildPaper.paper;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Data
@ApiModel("分享试卷")
@Document
public class PaperShare{

    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("更新时间")
    private String updateTime;
    @ApiModelProperty("分享发起人Id")
    private String createUserId;
    @ApiModelProperty("分享发起人名称")
    private String createUserName;
    @ApiModelProperty("分享接收人id")
    private List<String> receiveIdList;
    @ApiModelProperty("分享接收人")
    private String receiveId;
    @ApiModelProperty("分享试卷id")
    private String paperId;
    @ApiModelProperty("分享试卷名称")
    private String paperName;
    @ApiModelProperty("状态1为待操作，2为已加入，3为已忽略")
    private Integer type;
    @ApiModelProperty("学校Id")
    private String schoolId;
    @ApiModelProperty("查询时间段")
    private String[] searchTimeZone;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
}
