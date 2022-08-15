package com.yice.edu.cn.common.pojo.mes.schoolManage.audit;

import com.yice.edu.cn.common.pojo.jw.schoolYear.CurSchoolYear;
import com.yice.edu.cn.common.pojo.mes.schoolManage.mesAttachFile.MesAttachFile;
import com.yice.edu.cn.common.pojo.validateClass.GroupOne;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;


@Data
@ApiModel("事件审核表")
public class MesInstitutionAudit extends CurSchoolYear {

    @ApiModelProperty("主键")
    private String id;

    @ApiModelProperty("记录id")
    @NotBlank(groups = GroupOne.class,message = "记录id不能为空")
    private String recordId;

    @ApiModelProperty("0--待审核 1--通过 2--拒绝")
    @NotNull(groups = GroupOne.class,message = "申述状态不能为空")
    private Integer status;

    @ApiModelProperty("创建时间")
    private String createTime;


    @ApiModelProperty("学校id")
    @NotBlank(groups = GroupOne.class,message = "学校id不能为空")
    private String schoolId;

    @ApiModelProperty("申诉人名称")
    @NotBlank(groups = GroupOne.class,message = "申诉人名称不能为空")
    private String claimantName;

    private String searchTime;

    @ApiModelProperty("申诉人id")
    @NotBlank(groups = GroupOne.class,message = "申诉人id不能为空")
    private String claimantId;

    @ApiModelProperty("申述相关内容")
    @Length(min=1, max=200,groups = GroupOne.class)
    @NotBlank(groups = GroupOne.class,message = "申诉内容不能为空")
    private String operateContent;

    private List<MesAttachFile> mesAttachFiles;
}
