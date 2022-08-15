package com.yice.edu.cn.common.pojo.ts;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
/**
*
*
*
*/
@Data
public class TsExecuteLog{

    @ApiModelProperty(value = "主键id",dataType = "String")
    private String id;
    @ApiModelProperty(value = "类名",dataType = "String")
    private String className;
    @ApiModelProperty(value = "方法名",dataType = "String")
    private String methodName;
    @ApiModelProperty(value = "参数",dataType = "String")
    private String params;
    @ApiModelProperty(value = "执行时长（毫秒）",dataType = "String")
    private Long executeDuration;
    @ApiModelProperty(value = "备注",dataType = "String")
    private String remark;
    @ApiModelProperty(value = "创建时间",dataType = "String")
    private String createTime;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
}
