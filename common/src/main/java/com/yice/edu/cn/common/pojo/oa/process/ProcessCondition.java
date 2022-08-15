package com.yice.edu.cn.common.pojo.oa.process;

import com.yice.edu.cn.common.pojo.general.node.Node;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import  java.util.List;

@Data
@Document
@ApiModel("流程配置的条件信息")
public class ProcessCondition {
    @ApiModelProperty(value = "条件优先级",dataType = "Integer,值越小,优先级越高")
    private Integer weight;
    @ApiModelProperty(value = "发起人",dataType = "List")
    private List<OaPeople> initiators;
    @ApiModelProperty(value = "验证字段,ProcessForm表单里的字段",dataType = "String")
    private String verifyField;
    @ApiModelProperty(value = "验证条件类型",dataType = "List")
    private List<Node> verifyType;
    @ApiModelProperty(value = "验证条件规则",dataType = "String")
     private String verifyRule;
    @ApiModelProperty(value = "条件值",dataType = "Double")
    private Double conditionVal;
    @ApiModelProperty(value="条件单位",dataType = "String")
    private String conditionUnit;
    @ApiModelProperty(value = "抄送人",dataType = "List")
    private List<OaPeople> copyFor;
    @ApiModelProperty(value = "审批人",dataType = "List")
    private List<OaPeople> approver;
    @ApiModelProperty(value = "是否串行",dataType = "Boolean")
    private Boolean sequential;
    @ApiModelProperty(value = "并行情况下,多少人审批通过算审批成功",dataType = "Integer")
    private Integer passCount;

}
