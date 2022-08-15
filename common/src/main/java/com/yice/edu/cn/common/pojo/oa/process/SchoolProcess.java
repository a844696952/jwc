package com.yice.edu.cn.common.pojo.oa.process;

import com.yice.edu.cn.common.pojo.Pager;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
*
*学校流程
*
*/
@Data
@Document
public class SchoolProcess{

    @ApiModelProperty(value = "id",dataType = "String")
    private String id;
    @ApiModelProperty(value = "流程库id",dataType = "String")
    private String processLibId;
    @ApiModelProperty(value = "流程类型(名称)",dataType = "String")
    @Indexed
    private String type;
    @ApiModelProperty(value = "流程图标url",dataType = "String")
    private String imageIcon;
    @ApiModelProperty("给app端使用的图片路径")
    private String appIcon;
    @ApiModelProperty(value = "学校id",dataType = "String")
    @Indexed
    private String schoolId;
    @ApiModelProperty(value = "修改时间",dataType = "String")
    private String updateTime;
    @ApiModelProperty(value = "时间跨度，当该值不为null时表示,表单中beginTime和endTime之间必须小于该值",dataType = "Integer")
    private Integer timeSpan;
    @ApiModelProperty(value = "是否需要销假,为true时需要",dataType = "Boolean")
    private Boolean clearLeave;
    @ApiModelProperty(value = "流程是否具有占有性,如会议室在某一时段已被使用则该会议室不能再被申请",dataType = "String")
    private Boolean occupancy;
    @ApiModelProperty(value = "是否可用",dataType = "String")
    @Indexed
    private Boolean status;
    @ApiModelProperty(value = "是否串行",dataType = "Boolean")
    private Boolean sequential;
    @ApiModelProperty(value = "并行情况下,多少人审批通过算审批成功",dataType = "Integer")
    private Integer passCount;
    @ApiModelProperty(value = "抄送人",dataType = "List")
    private List<OaPeople> copyFor;
    @ApiModelProperty(value = "审批人",dataType = "List")
    private List<OaPeople> approver;
    @ApiModelProperty(value = "流程表单",dataType = "List")
    private List<ProcessForm> processForms;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
    @ApiModelProperty(value = "流程分项配置信息",dataType = "List")
    private List<ProcessCondition> processConditions;
    @ApiModelProperty(value = "流程分类id",dataType = "String")
    @Indexed
    private String groupId;
    @ApiModelProperty(value = "流程分类名称",dataType = "String")
    @Indexed
    private String groupName;
    @ApiModelProperty(value = "添加时间",dataType = "String")
    private String createTime;
}
