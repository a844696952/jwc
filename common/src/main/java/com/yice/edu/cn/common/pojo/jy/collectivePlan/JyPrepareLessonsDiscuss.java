package com.yice.edu.cn.common.pojo.jy.collectivePlan;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.validation.constraints.NotNull;
import javax.validation.Valid;

import io.swagger.annotations.ApiModelProperty;


@Data
public class JyPrepareLessonsDiscuss {

    @ApiModelProperty(value = "id", dataType = "String")
    private String id;
    @ApiModelProperty(value = "被评论的教案id", dataType = "String")
    private String teachingPlanId;
    @ApiModelProperty(value = "评论人的id", dataType = "String")
    private String teacherId;
    @ApiModelProperty(value = "评论", dataType = "String")
    private String discuss;
    @ApiModelProperty(value = "评论的时间", dataType = "String")
    private String discussTime;
    @ApiModelProperty(value = "头像", dataType = "String")
    private String teacherIcon;
    @ApiModelProperty(value = "名称", dataType = "String")
    private String teacherName;
    @ApiModelProperty(value = "讨论组id", dataType = "String")
    private String usenetId;
    @ApiModelProperty(value = "学校id", dataType = "String")
    private String schoolId;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
}
