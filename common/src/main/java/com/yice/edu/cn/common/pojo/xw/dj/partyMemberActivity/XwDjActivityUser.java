package com.yice.edu.cn.common.pojo.xw.dj.partyMemberActivity;

import com.yice.edu.cn.common.pojo.Pager;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;


@Data
@ApiModel("党建活动用户表")
public class XwDjActivityUser{

    @ApiModelProperty("主键ID")
    private String id;
    @ApiModelProperty("活动ID")
    private String activityId;
    @ApiModelProperty("活动分发人ID")
    private String activityUserId;
    @ApiModelProperty("名字")
    private String name;
    @ApiModelProperty("是否签到负责人")
    private Integer isSignInLeader;
    @ApiModelProperty("是否报名")
    private Integer isSignUp;
    @ApiModelProperty("是否请假")
    private Integer isVacate;
    @ApiModelProperty("是否签到")
    private Integer isSignIn;
    @ApiModelProperty("请假原因")
    private String vacateReason;
    @ApiModelProperty("签到日期")
    private String signInDate;
    private String activityDate;
    private String columnId;
    private String schoolId;
    private String classifyName;
    private String imgUrl;
    private String activityCreatorId;
    private Integer activityState;
    private String treeId;
    private String activityTitle;
    private String activityStartTime;
    private String createTime;
    @ApiModelProperty("缺席")
    private Integer isAbsence;
    @ApiModelProperty("活动结束时间")
    private String activityEndTime;
    @ApiModelProperty("报名截止时间")
    private String applyStopTime;

    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
}
