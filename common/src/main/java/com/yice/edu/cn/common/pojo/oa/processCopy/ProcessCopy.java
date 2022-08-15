package com.yice.edu.cn.common.pojo.oa.processCopy;

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
*流程抄送
*
*/
@Data
@Document
public class ProcessCopy{

    @ApiModelProperty(value = "id",dataType = "String")
    private String id;
    @ApiModelProperty(value = "抄送人id",dataType = "String")
    @Indexed
    private String teacherId;
    @ApiModelProperty(value = "申请类型",dataType = "String")
    private String type;
    @ApiModelProperty(value = "发起人姓名",dataType = "String")
    private String initiator;
    @ApiModelProperty(value = "申请时间",dataType = "String")
    private String createTime;
    @ApiModelProperty(value = "是否已查看",dataType = "Boolean")
    private Boolean looked;
    @ApiModelProperty(value = "流程业务数据id",dataType = "String")
    private String processBusinessDataId;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
    //额外字段
    @Transient
    private String[] rangeTime;
    @ApiModelProperty(value = "学校ID",dataType = "String")
    private String schoolId;
}
