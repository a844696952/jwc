package com.yice.edu.cn.common.pojo.jw.teacher;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
/**
*
*教师奖惩情况
*
*/
@Data
public class RewardsaPunishment{

    @ApiModelProperty(value = "主键",dataType = "String")
    private String id;
    @ApiModelProperty(value = "教师id",dataType = "String")
    private String teacherId;
    @ApiModelProperty(value = "奖惩类型 1奖2惩",dataType = "String")
    private String typeId;
    @ApiModelProperty(value = "时间",dataType = "String")
    private String rpTime;
    @ApiModelProperty(value = "奖惩内容",dataType = "String")
    private String content;
    @ApiModelProperty(value = "创建时间",dataType = "String")
    private String createTime;
    @ApiModelProperty(value = "奖惩图片",dataType = "String")
    private String imgUrl;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
}
