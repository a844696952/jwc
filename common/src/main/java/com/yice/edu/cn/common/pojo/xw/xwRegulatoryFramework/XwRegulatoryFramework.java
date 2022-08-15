package com.yice.edu.cn.common.pojo.xw.xwRegulatoryFramework;

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
public class XwRegulatoryFramework{

    @ApiModelProperty(value = "主键",dataType = "String")
    private String id;
    @ApiModelProperty(value = "创建时间",dataType = "String")
    private String createTime;
    @ApiModelProperty(value = "更新时间",dataType = "String")
    private String updateTime;
    @ApiModelProperty(value = "删除标识",dataType = "Integer")
    private Integer del;
    @ApiModelProperty(value = "分类管理id",dataType = "String")
    private String cmId;
    @ApiModelProperty(value = "主题",dataType = "String")
    private String theme;
    @ApiModelProperty(value = "内容",dataType = "String")
    private String content;
    @ApiModelProperty(value = "学校ID",dataType = "String")
    private String schoolId;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
}
