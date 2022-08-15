package com.yice.edu.cn.common.pojo.oa.processBusinessData;

import com.yice.edu.cn.common.pojo.jw.schoolYear.CurSchoolYear;
import com.yice.edu.cn.common.pojo.oa.process.OaPeople;
import com.yice.edu.cn.common.pojo.oa.process.ProcessForm;
import com.yice.edu.cn.common.pojo.validateClass.GroupOne;
import com.yice.edu.cn.common.pojo.validateClass.GroupTwo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
*
*流程业务数据
*
*/
@Data
@Document
public class ProcessBusinessData extends CurSchoolYear {

    @ApiModelProperty(value = "id",dataType = "String")
    private String id;
    @ApiModelProperty(value = "流程启动的key",dataType = "String")
    @NotBlank(message = "学校流程id不能为空",groups = {GroupOne.class, GroupTwo.class})
    private String schoolProcessId;
    @ApiModelProperty(value = "流程库的id",dataType = "String")
    @Indexed
    private String processLibId;
    @ApiModelProperty(value = "流程类型",dataType = "String")
    @Indexed
    private String type;
    @ApiModelProperty(value = "学校id",dataType = "String")
    @Indexed
    private String schoolId;
    @ApiModelProperty(value = "发起人姓名",dataType = "String")
    private String initiator;
    @ApiModelProperty(value = "发起人头像",dataType = "String")
    private String imgUrl;
    @ApiModelProperty(value = "发起人id",dataType = "String")
    @Indexed
    private String initiatorId;
    @ApiModelProperty(value = "发起时间",dataType = "String")
    private String createTime;
    @ApiModelProperty(value = "流程状态,0:待审批,1:审批成功,2:审批退回,3:已撤销",dataType = "Integer")
    private Integer status;
    @ApiModelProperty(value = "流程表单",dataType = "list")
    private List<ProcessForm> processForms;
    @ApiModelProperty(value = "是否串行",dataType = "Boolean")
    @NotNull(message = "是否串行不能为空",groups = GroupOne.class)
    private Boolean sequential;
    @ApiModelProperty(value = "并行情况下,多少人审批通过算审批成功",dataType = "Integer")
    private Integer passCount;
    @ApiModelProperty(value = "时间跨度，当该值不为null时表示,表单中beginTime和endTime之间必须小于该值",dataType = "Integer")
    private Integer timeSpan;
    @ApiModelProperty(value = "是否需要销假,为true时需要",dataType = "Boolean")
    private Boolean clearLeave;
    @ApiModelProperty(value = "销假时间",dataType = "String")
    private String clearLeaveTime;
    @ApiModelProperty(value = "流程是否具有占有性,如会议室在某一时段已被使用，在发起时验证已审批通过和待审批的改类型流程里有没有存在",dataType = "Boolean")
    private Boolean occupancy;
    @ApiModelProperty(value = "审批人",dataType = "list")
    @Indexed
    @NotEmpty(message = "审批人不能为空",groups = GroupOne.class)
    private List<OaPeople> approver;
    @ApiModelProperty(value = "抄送",dataType = "list")
    private List<OaPeople> copyFor;
    @ApiModelProperty(value = "表单业务数据(由processForms字段所决定)",dataType = "String")
    @NotEmpty(message = "表单数据不能为空",groups = GroupOne.class)
    private Map<String,Object> formData;
    //
    @ApiModelProperty(value = "请假撤销原审批人",dataType = "list")
    @Indexed
    private List<OaPeople> cancelApprover;
    @ApiModelProperty(value = "请假撤销原因",dataType = "String")
    private String cancelReason;
    @ApiModelProperty(value = "撤销时间",dataType = "String")
    private String canceDate;
    //额外字段
    @Transient
    @NotEmpty(message = "时间范围不能为空",groups = { GroupTwo.class})
    private String[] rangeTime;
    @ApiModelProperty(value = "流程结束时间",dataType = "String")
    private String endTime;
}
