package com.yice.edu.cn.common.pojo.jw.feedback;

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
public class Feedback{

    @ApiModelProperty(value = "主键",dataType = "String")
    private String id;
    @ApiModelProperty(value = "问题描述",dataType = "String")
    private String problemDescription;
    @ApiModelProperty(value = "问题来源",dataType = "String")
    private String sourceProblems;
    @ApiModelProperty(value = "提交时间",dataType = "String")
    private String updateTime;
    @ApiModelProperty(value = "处理状态",dataType = "String")
    private String status;
    @ApiModelProperty(value = "备注",dataType = "String")
    private String remarks;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
}
