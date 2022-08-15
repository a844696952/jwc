package com.yice.edu.cn.common.pojo.mes.schoolManage.audit;

import com.yice.edu.cn.common.pojo.jw.schoolYear.CurSchoolYear;
import com.yice.edu.cn.common.pojo.mes.schoolManage.mesAttachFile.MesAttachFile;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


@Data
@ApiModel("操作记录表")
public class MesOperateRecord extends CurSchoolYear {

    @ApiModelProperty("主键")
    private String id;
    @ApiModelProperty("审核主表id")
    private String auditId;
    @ApiModelProperty("发起人/操作人id")
    private String operatorId;
    @ApiModelProperty("操作内容")
    private String operateContent;
    @ApiModelProperty("操作日期")
    private String operateDate;
    @ApiModelProperty("0--申诉 1--审核")
    private Integer operateType;
    @ApiModelProperty("0--待审核/申诉中  1-审核通过/申诉成功  2--审核拒绝/申诉失败")
    private Integer operateStatus;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("学校id")
    private String schoolId;
    @ApiModelProperty("操作人名称")
    private String operatorName;
    //分页排序等

    private List<MesAttachFile> mesAttachFiles;
    private Integer recordUserType;
}
