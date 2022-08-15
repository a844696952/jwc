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
@ApiModel("党建活动表")
public class XwDjActivity implements Serializable {

    @ApiModelProperty("活动ID")
    private String id;
    @ApiModelProperty("分类ID")
    private String columnId;
    @ApiModelProperty("活动标题")
    private String activityTitle;
    @ApiModelProperty("内容")
    private String activityContent;
    @ApiModelProperty("活动开始时间")
    private String activityStartTime;
    @ApiModelProperty("活动结束时间")
    private String activityEndTime;
    @ApiModelProperty("报名截止时间")
    private String applyStopTime;
    @ApiModelProperty("是否需要签到 0--不需要 1--需要")
    private Integer isSiginIn;
    @ApiModelProperty("0--未发布 1--报名中 2-未开始 3--进行中 4--已结束 5--已关闭")
    private Integer activityState;
    @ApiModelProperty("活动地址名称")
    private String activityPositionName;
    @ApiModelProperty("活动地点坐标")
    private String activityPositionCode;
    @ApiModelProperty("活动创建人ID")
    private String activityCreatorId;
    @ApiModelProperty("创建日期")
    private String activityCreateDate;
    @ApiModelProperty("二维码")
    private String activityQrCode;
    @ApiModelProperty("附件集合")
    private List<XwDjAttachmentFile> attachmentFileList;
    @ApiModelProperty("签到负责人集合")
    private List<XwDjActivityUser> activityUserList;
    @ApiModelProperty("创建日期")
    private String createTime;
    @ApiModelProperty("学校ID")
    private String schoolId;
    @ApiModelProperty("角色ID")
    private String roleId;
    @ApiModelProperty("是否是管理员 0--不是 1--是")
    private String isAdmin;
    @ApiModelProperty("栏目名称")
    private String classifyName;
    @ApiModelProperty("操作人ID")
    private String operateId;
    @ApiModelProperty("操作日期")
    private String operateDate;
    @ApiModelProperty("操作人名称")
    private String operateName;
    @ApiModelProperty("当前时间")
    private Integer isSignUp;
    @ApiModelProperty("活动创建人名称")
    private String name;
    private Integer isSignIn;
    private Integer isVacate;
    private Integer tempState;
    private String activityUserId;
    private String searchStartTime;
    private String searchEndTime;
    private List<Integer> list;
    private Integer status;

    /**
     * 分页排序等
     * */
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
}
