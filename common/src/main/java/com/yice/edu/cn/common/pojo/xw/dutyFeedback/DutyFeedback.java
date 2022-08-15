package com.yice.edu.cn.common.pojo.xw.dutyFeedback;

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
public class DutyFeedback{

    @ApiModelProperty(value = "主键",dataType = "String")
    private String id;
    @ApiModelProperty(value = "反馈时间",dataType = "String")
    private String feedbackTime;
    @ApiModelProperty(value = "反馈人",dataType = "String")
    private String feedbackPerson;
    @ApiModelProperty(value = "反馈内容",dataType = "String")
    private String feedbackContext;
    @ApiModelProperty(value = "图片",dataType = "String")
    private String imgUrl;
    @ApiModelProperty(value = "处理情况说明",dataType = "String")
    private String handlingInstructions;
    @ApiModelProperty(value = "创建时间",dataType = "String")
    private String createTime;
    @ApiModelProperty(value = "更新时间",dataType = "String")
    private String updateTime;
    @ApiModelProperty(value = "学校id",dataType = "String")
    private String schoolId;
    @ApiModelProperty(value = "是否处理",dataType = "String")
    private String status;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
    //额外字段
    @Transient
    private String[] rangeTime;
    //@ApiModelProperty(value = "查询开始日期",dataType = "String")
    private String startdate;
    //@ApiModelProperty(value = "查询结束日期",dataType = "String")
    private String enddate;
}
