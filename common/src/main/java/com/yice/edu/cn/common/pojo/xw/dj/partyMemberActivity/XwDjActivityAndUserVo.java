package com.yice.edu.cn.common.pojo.xw.dj.partyMemberActivity;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.xw.dj.partyMemberFile.XwDjAttachmentFile;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
@ApiModel("党建活动与人员")
public class XwDjActivityAndUserVo extends XwDjActivity {

    @ApiModelProperty("人员表主键ID")
    private String userId;
    @ApiModelProperty("活动ID")
    private String activityId;
    @ApiModelProperty("活动分发人ID")
    private String activityUserId;
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
    @ApiModelProperty("缺席")
    private Integer isAbsence;
    private String activityDate;
    private List<XwDjActivityUser> xwDjActivityUsers;
    private long isSignUpCount;
    private long isSendCount;
    private long isSignInCount;
    private long isAbsenceCount;
    private long isVacateCount;
    private long isSignInLeaderCount;
    private Integer tempState;

    /**
     * 分页排序等
     * */
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;

}
