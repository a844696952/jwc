package com.yice.edu.cn.common.pojo.xw.dj.partyMember;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.validateClass.GroupOne;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.annotation.Transient;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;


@Data
@ApiModel("校务管理中的党建管理的党员管理")
public class XwPartyMember {

    @ApiModelProperty("党员管理编号")
    private String id;
    @ApiModelProperty("学校编号")
    private String schoolId;
    @ApiModelProperty("讲师编号")
    private String teacherId;
    @ApiModelProperty("党支部委员会编号")
    private String partyCommitteeId;
    @ApiModelProperty("党支部委员会名称")
    private String partyCommitteeName;
    @ApiModelProperty("党支部编号")
    private String partyBranchId;
    @ApiModelProperty("党支部名称")
    private String partyBranchName;
    @ApiModelProperty("党员职务编号")
    private String partyDutiesId;
    @ApiModelProperty("党员职务名称")
    private String partyDutiesName;
    @ApiModelProperty("头像")
    private String imgUrl;
    @ApiModelProperty("申请入党时间")
    private String applyTime;
    @ApiModelProperty("父id")
    private String parentId;
    @ApiModelProperty("名称")
    private String name;
    @ApiModelProperty("转正时间")
    private String workerTime;
    @ApiModelProperty("档案情况编号")
    private String archivalSituationId;
    @ApiModelProperty("档案情况名称")
    private String archivalSituationName;
    @ApiModelProperty("档案情况说明")
    private String archivalSituationRemark;
    @ApiModelProperty("讲师姓名")
    private String teacherName;
    @ApiModelProperty("性别")
    private String sex;
    @ApiModelProperty("手机号")
    private String tel;
    @ApiModelProperty("党员类别编号，关联字典表")
    private String partyTypeId;
    @ApiModelProperty("党员类别,获取名称")
    private String partyTypeName;
    @ApiModelProperty("资料是否完整，1:完整，2：不完整")
    private Integer intact;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("修改时间")
    private String updateTime;
    @ApiModelProperty(value = "状态（在职/离职/管理员)，在职：40，离职：41",dataType = "String")
    private String teacherStatus;//状态（在职/离职/管理员）
    @ApiModelProperty("组织类型 1--组织 2--用户")
    private String type;

    //额外字段
    @Transient
    private List<XwPartyMember> children;
    @ApiModelProperty("父名称")
    private String parentName;
    //分页排序等
    @Transient
    @Valid
    private Pager pager;
    @Transient
    private String rowData[];

    //是否在职
    @Transient
    @NotBlank(message = "教师状态不可为空",groups = GroupOne.class)
    private String online;

    private Integer computeIntact(){
        return StringUtils.isAnyBlank(
                this.partyCommitteeName,
                this.partyBranchName,
                this.partyDutiesName,
                this.applyTime,
                this.workerTime,
                //2019-06-04将党员类别移到可编辑区域
                this.partyTypeName,
                this.archivalSituationName) ||
                ("其他".equals(this.archivalSituationName) && StringUtils.isBlank(this.archivalSituationRemark))?
                2:1;
    }

    public void computeAndSetIntact(){
        this.setIntact(computeIntact());
    }
}
