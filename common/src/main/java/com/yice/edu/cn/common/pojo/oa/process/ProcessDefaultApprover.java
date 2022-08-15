package com.yice.edu.cn.common.pojo.oa.process;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@ApiModel("流程没有配置审批人用户的默认审批人")
@Data
@Document
public class ProcessDefaultApprover {

    @ApiModelProperty(value = "id",dataType = "String")
    private String id;
    @ApiModelProperty(value = "流程启动的key",dataType = "String")
    @Indexed
    private String schoolProcessId;
    @ApiModelProperty(value = "发起人姓名",dataType = "String")
    private String initiator;
    @ApiModelProperty(value = "发起人id",dataType = "String")
    @Indexed
    private String initiatorId;
    @ApiModelProperty(value = "抄送人",dataType = "List")
    private List<OaPeople> copyFor;
    @ApiModelProperty(value = "审批人",dataType = "List")
    private List<OaPeople> approver;
    @ApiModelProperty(value = "学校ID",dataType = "String")
    private String schoolId;
    @ApiModelProperty(value = "创建时间",dataType = "String")
    private String createTime;
}
