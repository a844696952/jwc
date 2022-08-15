package com.yice.edu.cn.common.pojo.oa.process;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
*
*流程库
*
*/
@Data
public class ProcessLib{

    @ApiModelProperty(value = "id",dataType = "String")
    private String id;
    @ApiModelProperty(value = "版本",dataType = "Integer")
    private Integer rev;
    @ApiModelProperty(value = "流程类型（名称）",dataType = "String")
    private String type;
    @ApiModelProperty(value = "流程图标url,存于七牛吧",dataType = "String")
    private String imageIcon;
    @ApiModelProperty("给app端使用的图片路径")
    private String appIcon;
    @ApiModelProperty(value = "时间跨度，当该值不为null时表示,表单中beginTime和endTime之间必须小于该值",dataType = "Integer")
    private Integer timeSpan;
    @ApiModelProperty(value = "是否需要销假,为true时需要",dataType = "Boolean")
    private Boolean clearLeave;
    @ApiModelProperty(value = "流程是否具有占有性,如会议室在某一时段已被",dataType = "String")
    private Boolean occupancy;
    @ApiModelProperty(value = "流程表单",dataType = "String")
    private List<ProcessForm> processForms;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
    //额外字段
    private Boolean disabled;
    //默认的分组名称
    @ApiModelProperty(value = "默认的分组名称",dataType = "String")
    private String defaultGroupName;
}
