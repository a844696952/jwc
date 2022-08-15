package com.yice.edu.cn.common.pojo.oa.oaStats;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.oa.process.ProcessForm;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class OaStats {
    @ApiModelProperty("流程id,即schoolProcessId")
    private String id;
    @ApiModelProperty("流程类型")
    private String type;
    @ApiModelProperty("总次数")
    private int total;
    @ApiModelProperty("待审批次数")
    private int wait;
    @ApiModelProperty("审批成功次数")
    private int success;
    @ApiModelProperty("审批失败次数")
    private int fail;
    @ApiModelProperty(value = "流程表单列表",dataType = "List")
    private List<ProcessForm> processForms;
    @ApiModelProperty("流程库id,可以使用这个判断到底是什么流程")
    private String processLibId;
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
    @Transient
    @NotEmpty(message = "时间范围不能为空")
    private String[] rangeTime;
}
