package com.yice.edu.cn.common.pojo.jw.stuEvaluate;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
*
*问卷表
*
*/
@Data
@Document
public class StuEvaluate{
    @Indexed
    @ApiModelProperty(value = "id",dataType = "String")
    private String id;
    @ApiModelProperty(value = "标题",dataType = "String")
    private String title;
    @ApiModelProperty(value = "简介",dataType = "String")
    private String introduction;
    @ApiModelProperty(value = "截止时间",dataType = "String")
    private String endTime;
    @ApiModelProperty(value = "创建时间",dataType = "String")
    private String createTime;
    @Indexed
    @ApiModelProperty(value = "学校id",dataType = "String")
    private String schoolId;
    @Transient
    @ApiModelProperty(value = "查询开始时间",dataType = "String")
    private String searchStartTime;
    @Transient
    @ApiModelProperty(value = "查询结束时间",dataType = "String")
    private String searchEndTime;
    @Transient
    @ApiModelProperty(value = "1、进行中 2、截止 ",dataType = "String")
    private String state;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
}
