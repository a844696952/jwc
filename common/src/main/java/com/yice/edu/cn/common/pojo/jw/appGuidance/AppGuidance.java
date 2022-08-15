package com.yice.edu.cn.common.pojo.jw.appGuidance;

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
public class AppGuidance{

    @ApiModelProperty(value = "id",dataType = "String")
    private String id;
    @ApiModelProperty(value = "功能名称",dataType = "String")
    private String name;
    @ApiModelProperty(value = "更新时间",dataType = "String")
    private String updateTime;
    @ApiModelProperty(value = "功能内容",dataType = "String")
    private String content;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
}
